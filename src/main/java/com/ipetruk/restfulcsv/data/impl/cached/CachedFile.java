package com.ipetruk.restfulcsv.data.impl.cached;

import com.ipetruk.restfulcsv.data.ItemIndexOutOfBoundsException;
import com.ipetruk.restfulcsv.data.impl.AccessMode;
import com.ipetruk.restfulcsv.data.impl.CSVFile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class CachedFile implements CSVFile{
    FileChannel channel;

    public CachedFile(File filename, AccessMode mode) throws IOException{
        RandomAccessFile file = new RandomAccessFile(filename, mode.getFileOpenMode());
        channel = file.getChannel();
    }

    @Override
    public FileLock lock(long from, int size, boolean shared) throws IOException {
        return channel.lock(from, size, shared);
    }

    public void write(long from, String line) throws IOException{
        byte[] lineBytes = line.getBytes("utf-8");
        ByteBuffer buffer =  ByteBuffer.wrap(lineBytes);
        long position = from;
        while (buffer.position()<buffer.limit()){
            position += channel.write(buffer, position);
        }
    }

    public String read(long from, int count) throws IOException{
        ByteBuffer buffer = ByteBuffer.allocate(count);
        long position = from;
        int read;
        while (true){
            read = channel.read(buffer, position);
            if (read>0){
                position+=read;
            }else{
                break;
            }
        }
        if (read==-1){
            throw new ItemIndexOutOfBoundsException("Reached the end of file "+channel);
        }
        buffer.rewind();
        return new String(buffer.array(), "utf-8");
    }


    @Override
    public void close() throws Exception {
        channel.close();
    }
}
