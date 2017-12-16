package ru.javaops.masterjava.upload;

import one.util.streamex.StreamEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.dao.UserDao;
import ru.javaops.masterjava.persist.model.User;
import ru.javaops.masterjava.persist.model.UserFlag;
import ru.javaops.masterjava.xml.schema.ObjectFactory;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.JaxbUnmarshaller;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserProcessor {
    private static final JaxbParser jaxbParser = new JaxbParser(ObjectFactory.class);

    private static Logger logger = LoggerFactory.getLogger(UserProcessor.class);

    private static final int THREAD_NUMBER = 4;
    private ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUMBER);

    public static class FailedEmail {
        private String emailOrRange;
        private String reasen;

        public FailedEmail(String emailOrRange, String reasen) {
            this.emailOrRange = emailOrRange;
            this.reasen = reasen;
        }

        @Override
        public String toString() {
            return emailOrRange + " : " + reasen;
        }
    }

    private static class ChunkFuture {
        String emaiRange;
        Future<List<String>> future;

        public ChunkFuture(List<User> chunk, Future<List<String>> future) {
            this.emaiRange = chunk.get(0).getEmail();
            if (chunk.size() > 1)
                this.emaiRange += " - " + chunk.get(chunk.size() - 1).getEmail();
            this.future = future;
        }
    }

    private UserDao dao = DBIProvider.getDao(UserDao.class);

    public List<FailedEmail> process(final InputStream is, int chunkSize) throws XMLStreamException, JAXBException {
        final StaxStreamProcessor processor = new StaxStreamProcessor(is);
        List<User> chunk = new ArrayList<>(chunkSize);

        List<ChunkFuture> futures = new ArrayList<>();

        JaxbUnmarshaller unmarshaller = jaxbParser.createUnmarshaller();
        while (processor.doUntil(XMLEvent.START_ELEMENT, "User")) {
            ru.javaops.masterjava.xml.schema.User xmlUser = unmarshaller.unmarshal(processor.getReader(), ru.javaops.masterjava.xml.schema.User.class);
            final User user = new User(xmlUser.getValue(), xmlUser.getEmail(), UserFlag.valueOf(xmlUser.getFlag().value()));
            chunk.add(user);
            if (chunk.size() == chunkSize) {
                futures.add(submit(chunk));
                chunk = new ArrayList<>(chunkSize);
            }
        }

        List<FailedEmail> failed = new ArrayList<>();
        futures.forEach(cf -> {
                    try {
                        failed.addAll(StreamEx.of(cf.future.get())
                                .map(email -> new FailedEmail(email, "already present"))
                                .toList());
                        logger.info(cf.emaiRange + " successfully executed");
                    } catch (Exception e) {
                        logger.error(cf.emaiRange + " faied", e);
                        failed.add(new FailedEmail(cf.emaiRange, e.toString()));
                    }
                }
        );
        return failed;
    }

    private ChunkFuture submit(List<User> chunk) {
        return new ChunkFuture(chunk, executor.submit(() -> dao.insertAndGetConflictEmails(chunk)));
    }
}
