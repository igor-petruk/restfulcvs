package com.ipetruk.restfulcsv.data.impl;

import com.google.common.collect.ImmutableMap;
import com.ipetruk.restfulcsv.boilerplate.ShutdownService;
import com.ipetruk.restfulcsv.data.*;
import com.ipetruk.restfulcsv.string.CSVStringService;

import javax.inject.Inject;
import java.io.*;
import java.util.Map;

public abstract class AbstractFileRepository implements DataRepository, Closeable {

    public abstract CSVFile buildCSVFile(File file, AccessMode accessMode) throws IOException;

    private final int lineLength;
    private final CSVStringService stringService;
    private final Map<FileType, ? extends CSVFile> files;

    @Inject
    public AbstractFileRepository(ShutdownService shutdownService,
                                  CSVStringService stringService,
                                  int lineLength,
                                  String initialFileName,
                                  String activeFileName)
                            throws IOException{
        shutdownService.registerCloseable(this);
        this.lineLength = lineLength;
        this.stringService = stringService;

        CSVFile initialFile = buildCSVFile(new File(initialFileName), AccessMode.READ);
        CSVFile activeFile = buildCSVFile(new File(activeFileName), AccessMode.READ_AND_WRITE);

        files = ImmutableMap.of(FileType.ACTIVE, activeFile,
                                FileType.INITIAL, initialFile);
    }

    @Override
    public void close() throws IOException {
        try{
            for(CSVFile file: files.values()){
                file.close();
            }
        }catch (Exception e){
            throw new IOException(e);
        }
    }

    public double readValue(FileType fileType, int index) {
        try{
            CSVFile file = files.get(fileType);
            return stringService.parseLine(file.read(index*lineLength, lineLength));
        }catch (IOException e){
            throw new DataAccessException("Unable to read item "+index+" to file "+fileType, e);
        }catch(IllegalArgumentException e){
            throw new ItemIndexOutOfBoundsException(index+" item is out of bounds when reading from file "+fileType);
        }
    }

    public void writeValue(FileType fileType, int index, double value) {
        try{
            CSVFile file = files.get(fileType);
            file.write(index*lineLength, stringService.produceLine(value));
        }catch (IOException e){
            throw new DataAccessException("Unable to write item "+index+" to file "+fileType, e);
        }catch(IllegalArgumentException e){
            throw new ItemIndexOutOfBoundsException(index+" item is out of bounds when writing to file "+fileType);
        }
    }

    @Override
    public ItemLock lock(FileType fileType, int index, boolean shared) {
        try{
            CSVFile file = files.get(fileType);
            return new FileItemLock(file.lock(index*lineLength, lineLength, shared));
        }catch(IllegalArgumentException e){
            throw new ItemIndexOutOfBoundsException("Item index is negative:"+index+" when locking to file "+fileType);
        }catch (IOException e){
            throw new DataAccessException("Unable to read item "+index+" to file "+fileType, e);
        }
    }
}
