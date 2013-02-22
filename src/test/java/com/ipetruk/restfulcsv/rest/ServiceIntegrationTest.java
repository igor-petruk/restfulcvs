package com.ipetruk.restfulcsv.rest;

import com.ipetruk.restfulcsv.rest.dto.ConditionResponse;
import com.ipetruk.restfulcsv.rest.dto.PostRequest;
import com.ipetruk.restfulcsv.rest.dto.ValueResponse;
import com.ipetruk.restfulcsv.rest.tools.JettyBaseTest;
import com.ipetruk.restfulcsv.string.CSVStringService;
import com.sun.jersey.api.client.UniformInterfaceException;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Properties;

import static org.junit.Assert.*;

public class ServiceIntegrationTest extends JettyBaseTest{
    private static final Properties PROPERTIES = new Properties();
    private static int lineLength;

    private static File initialFile, activeFile;

    @BeforeClass
    public static void readProperties() throws Exception{
        PROPERTIES.load(ServiceIntegrationTest.class.getResourceAsStream("/settings.properties"));
        lineLength = Integer.parseInt(PROPERTIES.getProperty("csv.fixed.size"));
        initialFile = new File(PROPERTIES.getProperty("file.initial"));
        activeFile = new File(PROPERTIES.getProperty("file.active"));
    }

    protected void prepareFiles() throws Exception{
        CSVStringService stringService = new CSVStringService(lineLength);
        try(PrintWriter writer = new PrintWriter(new FileOutputStream(initialFile))){
            for (int i = 0; i<1000; i++){
                writer.print(stringService.produceLine((double)i/10.0));
            }
        }
        try(PrintWriter writer = new PrintWriter(new FileOutputStream(activeFile))){
            for (int i = 1000; i>=0; i--){
                writer.print(stringService.produceLine((double)i/10.0));
            }
        }
    }

    protected void cleanupFiles() throws Exception{
        initialFile.delete();
        activeFile.delete();
    }

    @Test
    public void testIsServiceAvailable(){
        assertEquals("OK", buildResource("/rest/status").get(String.class));
    }

    private void assertGetForIndex(int index, double value){
        assertEquals(value,
                buildResource("/rest/service/"+index).get(ValueResponse.class).getValue(),
                0.01);
    }

    @Test
    public void testGetServiceCorrected(){
        assertGetForIndex(100, 80);
        assertGetForIndex(321, 57.9);
    }

    @Test
    public void testGetServiceNonCorrected(){
        assertGetForIndex(900, 10.0);
        assertGetForIndex(943, 5.7);
    }

    @Test(expected = UniformInterfaceException.class)
    public void testGetServiceError(){
        buildResource("/rest/service/10000").get(ValueResponse.class);
    }

    @Test(expected = UniformInterfaceException.class)
    public void testGetServiceErrorOnNegative(){
        buildResource("/rest/service/-10").get(ValueResponse.class);
    }

    @Test
    public void testPostServiceConditionFired(){
        int v4 = 443;

        assertGetForIndex(v4, 45.7);

        PostRequest request = new PostRequest();
        request.setV2(2);
        request.setV3(3);
        request.setV4(v4);
        ConditionResponse response = buildResource("/rest/service")
                .accept(MediaType.APPLICATION_XML_TYPE)
                .type(MediaType.APPLICATION_XML_TYPE)
                .post(ConditionResponse.class,request);
        assertEquals(0,response.getConditionFired());

        assertGetForIndex(v4, 2.3);
    }

    @Test
    public void testPostServiceConditionNotFired(){
        int v4 = 343;

        assertGetForIndex(v4, 55.7);

        PostRequest request = new PostRequest();
        request.setV2(33);
        request.setV3(25);
        request.setV4(v4);
        ConditionResponse response = buildResource("/rest/service")
                .accept(MediaType.APPLICATION_XML_TYPE)
                .type(MediaType.APPLICATION_XML_TYPE)
                .post(ConditionResponse.class,request);
        assertEquals(1,response.getConditionFired());

        assertGetForIndex(v4, 25.5);
    }

}
