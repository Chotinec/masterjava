package ru.javaops.masterjava.xml.servlet;

import org.thymeleaf.context.WebContext;
import ru.javaops.masterjava.xml.model.UserTo;
import ru.javaops.masterjava.xml.upload.UserUpload;
import ru.javaops.masterjava.xml.util.thymeleaf.ThymeleafAppUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static ru.javaops.masterjava.xml.servlet.ThymeleafListener.engine;

@WebServlet(urlPatterns = "/")
@MultipartConfig
public class WelcomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException  {

        WebContext ctx = new WebContext(request, response, request.getServletContext(),
                request.getLocale());
        final Part filePart = request.getPart("file");
        InputStream filecontent = null;
        try {
            filecontent = filePart.getInputStream();
           List<UserTo> userTos = UserUpload.process(filecontent);
            ctx.setVariable("users", userTos);
            engine.process("users", ctx, response.getWriter());
        } catch (Exception ex) {
            ctx.setVariable("exception", ex);
            engine.process("exception", ctx, response.getWriter());
        } finally {
            if (filecontent != null) {
                filecontent.close();
            }
        }
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        WebContext ctx = new WebContext(request, response, request.getServletContext(),
                request.getLocale());
        engine.process("upload", ctx, response.getWriter());
    }
}
