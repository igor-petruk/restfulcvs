package com.ipetruk.restfulcsv.data.impl.mapped;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class MappedFile implements AutoCloseable{
    FileChannel channel;
    MappedByteBuffer mappedByteBuffer;
    AccessMode mode;

    public MappedFile(File filename, AccessMode mode) throws IOException{
        this.mode = mode;

        RandomAccessFile file = new RandomAccessFile(filename, mode.getFileOpenMode());

        channel = file.getChannel();
        mappedByteBuffer = channel.map(mode.getMapMode(), 0, file.length());
    }

    @Override
    public void close() throws Exception {
        channel.close();
    }

    public FileLock lock(int from, int size, boolean shared) throws IOException{
        return channel.lock(from, size, shared);
    }

    public void write(int from, String line) throws IOException{
        byte[] lineBytes = line.getBytes("utf-8");
        ByteBuffer copy =  mappedByteBuffer.duplicate();
        copy.position(from);
        copy.put(lineBytes);
    }

    public String read(int from, int count) throws IOException{
        ByteBuffer copy =  mappedByteBuffer.duplicate();
        copy.position(from);
        byte[] bytes = new byte[count];
        copy.get(bytes);
        return new String(bytes, "utf-8");
    }
}