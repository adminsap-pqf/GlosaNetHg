package org.mx.servicio.monitoreo;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.logging.Logger;

/** Added just for monitoring and debugging. */
@WebListener
public class ContextListener implements ServletContextListener, HttpSessionListener {
    private static final Logger log = Logger.getLogger(ContextListener.class.getName());
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info(" *** ContextListener.contextInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info(" *** ContextListener.contextDestroyed");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.info(" *** ContextListener.sessionCreated - NOT GOOD!");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info(" *** ContextListener.sessionDestroyed - NOT GOOD!");
    }
}