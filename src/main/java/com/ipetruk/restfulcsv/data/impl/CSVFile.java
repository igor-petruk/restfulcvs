package com.ipetruk.restfulcsv.data.impl;

import java.io.IOException;
import java.nio.channels.FileLock;

public interface CSVFile extends AutoCloseable{
    public FileLock lock(long from, int size, boolean shared) throws IOException;
    public void write(long from, String line) throws IOException;
    public String read(long from, int count) throws IOException;
}
