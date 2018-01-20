package ru.javaops.masterjava.upload;


import lombok.AllArgsConstructor;
import lombok.val;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ProcessPayload {

    private final CityProcessor cityProcessor = new CityProcessor();
    private final UserProcessor userProcessor = new UserProcessor();

    @AllArgsConstructor
    public static class FailedEmails {
        public String emailsOrRange;
        public String reason;

        @Override
        public String toString() {
            return emailsOrRange + " : " + reason;
        }
    }

    public List<FailedEmails> process(InputStream is, int chunkSize) throws JAXBException, XMLStreamException, IOException {
        val processor = new StaxStreamProcessor(is);
        val cities = cityProcessor.process(processor);
        return userProcessor.process(processor, cities, chunkSize);
    }
}

