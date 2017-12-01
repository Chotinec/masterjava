package ru.javaops.masterjava.xml.servlet;

import org.thymeleaf.TemplateEngine;
import ru.javaops.masterjava.xml.util.thymeleaf.ThymeleafAppUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ThymeleafListener implements ServletContextListener {

    public static TemplateEngine engine;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        engine = ThymeleafAppUtil.getTemplateEngine(servletContextEvent.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
