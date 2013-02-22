package com.ipetruk.restfulcsv.rest;

import com.google.common.base.Strings;
import com.ipetruk.restfulcsv.data.impl.mapped.AccessMode;
import com.ipetruk.restfulcsv.data.impl.mapped.MappedFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import static org.junit.Assert.*;

public class MappedFileTest {
    public static final File FILE = new File("./test.txt");

    @Before
    public void init() throws Exception{
        try(PrintWriter writer = new PrintWriter(new FileOutputStream(FILE))){
            writer.println(Strings.repeat(" ",100));
            writer.flush();
        }
    }

    @Test
    public void testWriteAndRead() throws Exception{
        String line = "Hello world";
        try(MappedFile mappedFile = new MappedFile(FILE, AccessMode.READ_AND_WRITE)){
            mappedFile.write(30, line);
        }
        try(MappedFile mappedFile = new MappedFile(FILE, AccessMode.READ)){
            assertEquals(line, mappedFile.read(30, line.length()));
        }
    }

    @After
    public void cleanup() throws Exception{
        FILE.delete();
    }
}
