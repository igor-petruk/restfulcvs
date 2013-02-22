package com.ipetruk.restfulcsv.data.impl;

import java.nio.channels.FileChannel;
import static java.nio.channels.FileChannel.MapMode.*;

public enum  AccessMode {
    READ(READ_ONLY, "r"), READ_AND_WRITE(READ_WRITE,"rw");

    FileChannel.MapMode mapMode;
    String fileOpenMode;

    private AccessMode(FileChannel.MapMode mapMode, String fileOpenMode) {
        this.mapMode = mapMode;
        this.fileOpenMode = fileOpenMode;
    }

    public FileChannel.MapMode getMapMode() {
        return mapMode;
    }

    public String getFileOpenMode() {
        return fileOpenMode;
    }
}
