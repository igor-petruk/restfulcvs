package com.ipetruk.restfulcsv.data.impl;

import java.io.IOException;
import java.nio.channels.FileLock;

public interface CSVFile extends AutoCloseable{
    public FileLock lock(int from, int size, boolean shared) throws IOException;
    public void write(int from, String line) throws IOException;
    public String read(int from, int count) throws IOException;
}
