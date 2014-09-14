package com.miksinouf.chronowars.domain.server;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import javax.management.*;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.miksinouf.chronowars.domain.administration.GamesAdminitrator;

public class EmbeddedJetty {
//    private static final Logger logger = LoggerFactory
//            .getLogger(EmbeddedJetty.class);
    private static final int DEFAULT_PORT = 4567;
    private static final String CONTEXT_PATH = "/";
    private static final String CONFIG_LOCATION = "com.miksinouf.chronowars.domain.config";
    private static final String MAPPING_URL = "/*";
    private static final String DEFAULT_PROFILE = "dev";

    public static void main(String[] args) throws Exception {
        final EmbeddedJetty embeddedJetty = new EmbeddedJetty();
        embeddedJetty.startJetty(getPortFromArgs(args));
        embeddedJetty.startJmxServer();
    }

    private static int getPortFromArgs(String[] args) {
        if (args.length > 0) {
            try {
                return Integer.valueOf(args[0]);
            } catch (NumberFormatException ignore) {
            }
        }
        return DEFAULT_PORT;
    }

    private void startJetty(int port) throws Exception {
        Server server = new Server(port);
        server.setHandler(getServletContextHandler(getContext()));
        server.start();
        server.join();
    }

    private static ServletContextHandler getServletContextHandler(
            WebApplicationContext context) throws IOException {
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setErrorHandler(null);
        contextHandler.setContextPath(CONTEXT_PATH);
        contextHandler.addServlet(new ServletHolder(new DispatcherServlet(
                context)), MAPPING_URL);
        contextHandler.addEventListener(new ContextLoaderListener(context));
        contextHandler.setResourceBase(new ClassPathResource("resources").getURI()
                .toString());
        ServletHolder holderEvents = new ServletHolder("ws-events", EventServlet.class);
		contextHandler.addServlet(holderEvents, "/events/*");
        return contextHandler;
    }

    private static WebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(CONFIG_LOCATION);
        context.getEnvironment().setDefaultProfiles(DEFAULT_PROFILE);
        return context;
    }

    public void startJmxServer() {
        final MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        try {
            final ObjectName gamesAdministrator =
                    new ObjectName("com.miksinouf.chronowars.domain.administration:type=GamesAdministratorMBean");
            mBeanServer.registerMBean(new GamesAdminitrator(), gamesAdministrator);
        } catch (MalformedObjectNameException
                | NotCompliantMBeanException
                | InstanceAlreadyExistsException
                | MBeanRegistrationException e) {
            e.printStackTrace();
        }
    }
}
