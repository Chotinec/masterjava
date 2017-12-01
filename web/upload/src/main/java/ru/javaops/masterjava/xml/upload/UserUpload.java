package ru.javaops.masterjava.xml.upload;

import ru.javaops.masterjava.xml.model.UserTo;
import ru.javaops.masterjava.xml.model.UserFlag;
import ru.javaops.masterjava.xml.schema.User;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.*;

public class UserUpload {

    public static List<UserTo> process(InputStream inputStream) throws Exception{
        StaxStreamProcessor processor = new StaxStreamProcessor(inputStream);
        List<UserTo> userTos = new ArrayList<>();

        JaxbParser parser = new JaxbParser(User.class);
        while (processor.doUntil(XMLEvent.START_ELEMENT, "User")) {
            //final String fullName = processor.getReader().getElementText();
            //final String email = processor.getAttribute("email");
            //final UserFlag flag = UserFlag.valueOf(processor.getAttribute("flag"));
            User user = parser.unmarshal(processor.getReader(), User.class);
            final UserTo userTo = new UserTo(user.getValue(), user.getEmail(), UserFlag.valueOf(user.getFlag().value()));
            userTos.add(userTo);
        }

        return userTos;
    }
}
