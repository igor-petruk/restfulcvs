package com.ipetruk.restfulcsv.rest;

import com.ipetruk.restfulcsv.string.CSVStringService;
import org.junit.Test;

import static org.junit.Assert.*;

public class CSVStringServiceTest {
    public static final int LINE_LENGTH = 20;

    CSVStringService stringService = new CSVStringService(LINE_LENGTH);

    @Test
    public void testFormat(){
        String line = stringService.produceLine(1234.4);
        assertEquals(LINE_LENGTH,line.length());
        assertEquals("1234.4;           ;\n",line);
    }

    @Test
    public void testParse(){
        assertEquals(503.2, stringService.parseLine("503.2;     ;"),0.1);
    }
}
