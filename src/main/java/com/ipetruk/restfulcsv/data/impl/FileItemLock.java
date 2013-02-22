package com.ipetruk.restfulcsv.data.impl;

import com.ipetruk.restfulcsv.data.ItemLock;

import java.nio.channels.FileLock;

public class FileItemLock implements ItemLock {
    private FileLock fileLock;

    FileItemLock(FileLock fileLock) {
        this.fileLock = fileLock;
    }

    @Override
    public void close() throws Exception {
        fileLock.close();
    }
}
