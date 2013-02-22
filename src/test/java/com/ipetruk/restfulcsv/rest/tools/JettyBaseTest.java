package com.ipetruk.restfulcsv.rest.tools;

import com.ipetruk.restfulcsv.MainGuiceModule;
import com.ipetruk.restfulcsv.boilerplate.CoreGuiceConfig;
import com.ipetruk.restfulcsv.boilerplate.MainGuiceFilter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.eclipse.jetty.server.Connector;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.FilterMapping;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.After;
import org.junit.Before;

import java.net.URL;
import java.security.ProtectionDomain;

public abstract class JettyBaseTest {
    private Server server;
    private Client client = Client.create();

    protected abstract void prepareFiles() throws Exception;

    protected abstract void cleanupFiles() throws Exception;

    public WebResource buildResource(String path){
        return client.resource("http://localhost:8080"+path);
    }

    @Before
    public void startJetty()throws Exception{
        prepareFiles();

        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        connector.setSoLingerTime(-1);
        server.setConnectors(new Connector[] { connector });

        ProtectionDomain domain = MainGuiceModule.class.getProtectionDomain();
        URL location = domain.getCodeSource().getLocation();

        WebAppContext context = new WebAppContext(location.toExternalForm(), "/");
        context.setServer(server);

        // Bug in Jetty makes me do it
        FilterHolder filterHolder = new FilterHolder(MainGuiceFilter.class);
        filterHolder.setName("guice");

        FilterMapping filterMapping = new FilterMapping();
        filterMapping.setFilterName("guice");
        filterMapping.setPathSpec("/*");

        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addFilter(filterHolder, filterMapping);

        context.addEventListener(new CoreGuiceConfig());
        context.setServletHandler(servletHandler);

        server.setHandler(context);
        server.start();
    }

    @After
    public void stopJetty() throws Exception{
        server.stop();
        server.join();

        cleanupFiles();
    }
}
