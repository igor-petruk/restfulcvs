package com.ipetruk.restfulcsv.data.impl.mapped;

import com.ipetruk.restfulcsv.data.ItemLock;

import java.nio.channels.FileLock;

public class MappedFileAreaLock implements ItemLock {
    private FileLock fileLock;

    MappedFileAreaLock(FileLock fileLock) {
        this.fileLock = fileLock;
    }

    @Override
    public void close() throws Exception {
        fileLock.close();
    }
}
