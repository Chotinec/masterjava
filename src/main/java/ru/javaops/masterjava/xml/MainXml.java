package ru.javaops.masterjava.xml;


import com.google.common.io.Resources;
import j2html.tags.ContainerTag;
import ru.javaops.masterjava.xml.schema.ObjectFactory;
import ru.javaops.masterjava.xml.schema.Payload;
import ru.javaops.masterjava.xml.schema.Project;
import ru.javaops.masterjava.xml.schema.User;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.Schemas;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;
import ru.javaops.masterjava.xml.util.XsltProcessor;


import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static j2html.TagCreator.*;

public class MainXml {

    private static final Comparator<User> USER_COMPARATOR = Comparator.comparing(User:: getValue).thenComparing(User::getEmail);

    private static final JaxbParser JAXB_PARSER = new JaxbParser(ObjectFactory.class);

    private static final String XSD_FILE_NAME = "payload.xsd";
    private static final String XML_FILE_NAME = "payload.xml";


    static {
        JAXB_PARSER.setSchema(Schemas.ofClasspath(XSD_FILE_NAME));
    }

    public static void main(String[] args) throws Exception {
        MainXml mainXml = new MainXml();
        String projectName = "masterjava";

        //Set<User> usrs = mainXml.JaxbOutPut(projectName);
        //String users = outHtml(usrs, projectName, Paths.get("out/userJaxb.html"));
        //System.out.println(users);

        //Set<User> usrs = mainXml.StAXOutPut(projectName);
        //String users = outHtml(usrs, projectName, Paths.get("out/userStAX.html"));
        //System.out.println(users);

        System.out.println(mainXml.XSLTOutPut(projectName));
    }

    public  Set<User> JaxbOutPut(String projectName) throws Exception {
        Payload payload = JAXB_PARSER.unmarshal(
                Resources.getResource(XML_FILE_NAME).openStream());

        Project project = payload.getProjects().getProject()
                .stream()
                .filter((e) -> projectName.equals(e.getName()))
                .findFirst().get();

        Set<Project.Group> groups = new HashSet<>(project.getGroup());

        return payload.getUsers().getUser()
                .stream()
                .filter((e) -> {
                    for (Project.Group group : e.getGroupRefs()) {
                        if (groups.contains(group)) return true;
                    }
                    return false;
                })
                .collect(Collectors.toCollection(() -> new TreeSet<User>(USER_COMPARATOR)));
    }

    public Set<User> StAXOutPut(String projectName) throws Exception {
        try (StaxStreamProcessor processor =
                     new StaxStreamProcessor(Resources.getResource(XML_FILE_NAME).openStream())) {
            Set<User> users = new TreeSet<>(USER_COMPARATOR);
            while (processor.startElement("User", "Users")) {
                if (processor.getAttribute("groupRefs").contains(projectName)) {
                    User user = new User();
                    user.setEmail(processor.getAttribute("email"));
                    user.setValue(processor.getText());
                    users.add(user);
                }
            }

            return users;
        }
    }

    public String XSLTOutPut(String projectName) throws Exception {
        try (InputStream xslInputStream = Resources.getResource("groups.xsl").openStream();
             InputStream xmlInputStream = Resources.getResource("payload.xml").openStream()) {
            XsltProcessor processor = new XsltProcessor(xslInputStream);
            processor.setParametr("projectName", projectName);
            return processor.transform(xmlInputStream);
        }
    }

    private static String outHtml(Set<User> users, String project, Path path) throws IOException {
        try (Writer writer = Files.newBufferedWriter(path)) {
            final ContainerTag table  = table().with(
                    tr().with(th().with(span("FullName")), th().with(span(String.valueOf("Email")))),
                    each(users, user -> tr().with(td().with(span(user.getValue())), td().with(span(user.getEmail()))))
            );
            table.attr("border", "1");
            table.attr("cellpadding", "8");
            table.attr("cellspacing", "0");

            String out = html(
                    head(title("Users")),
                    body(h1(project), table)
            ).renderFormatted();

            writer.write(out);
            return out;
        }
    }
}
