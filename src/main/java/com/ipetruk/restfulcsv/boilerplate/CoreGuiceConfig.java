package com.ipetruk.restfulcsv.boilerplate;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.ipetruk.restfulcsv.MainGuiceModule;
import com.ipetruk.restfulcsv.rest.RestService;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;
import java.util.Map;

@WebListener
public class CoreGuiceConfig extends GuiceServletContextListener {
    private Injector injector;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        injector =  Guice.createInjector(new ServletModule(){
            @Override
            protected void configureServlets() {
                bind(ShutdownService.class);
                bind(GuiceContainer.class);
                Map<String, String> parameters = new HashMap<>();
                parameters.put(JSONConfiguration.FEATURE_POJO_MAPPING, "true");
                parameters.put("com.sun.jersey.config.property.packages", RestService.class.getPackage().getName());
                serve("/*").with(GuiceContainer.class, parameters);
            }
        }, new MainGuiceModule());
        super.contextInitialized(servletContextEvent);
    }

    @Override
    protected Injector getInjector() {
        return injector;
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        injector.getInstance(ShutdownService.class).shutdown();
        super.contextDestroyed(servletContextEvent);
    }
}
