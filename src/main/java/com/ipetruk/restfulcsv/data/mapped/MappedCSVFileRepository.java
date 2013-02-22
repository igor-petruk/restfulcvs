package com.ipetruk.restfulcsv.data.mapped;

import com.google.common.collect.ImmutableMap;
import com.ipetruk.restfulcsv.boilerplate.ShutdownService;
import com.ipetruk.restfulcsv.data.*;
import com.ipetruk.restfulcsv.string.CSVStringService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.*;
import java.util.Map;

@Singleton
public class MappedCSVFileRepository implements DataRepository, Closeable {

    private final int lineLength;
    private final CSVStringService stringService;
    private final Map<FileType, MappedFile> files;

    @Inject
    public MappedCSVFileRepository(ShutdownService shutdownService,
                                   CSVStringService stringService,
                                   @Named("csv.fixed.size") int lineLength,
                                   @Named("file.initial") String initialFileName,
                                   @Named("file.active") String activeFileName)
                            throws IOException{
        shutdownService.registerCloseable(this);
        this.lineLength = lineLength;
        this.stringService = stringService;

        MappedFile initialFile = new MappedFile(new File(initialFileName), AccessMode.READ);
        MappedFile activeFile = new MappedFile(new File(activeFileName), AccessMode.READ_AND_WRITE);

        files = ImmutableMap.of(FileType.ACTIVE, activeFile,
                                FileType.INITIAL, initialFile);
    }

    @Override
    public void close() throws IOException {
        try{
            for(MappedFile file: files.values()){
                file.close();
            }
        }catch (Exception e){
            throw new IOException(e);
        }
    }

    public double readValue(FileType fileType, int index) {
        try{
            MappedFile file = files.get(fileType);
            return stringService.parseLine(file.read(index*lineLength, lineLength));
        }catch (IOException e){
            throw new DataAccessException("Unable to read item "+index+" to file "+fileType, e);
        }catch(IllegalArgumentException e){
            throw new ItemIndexOutOfBoundsException(index+" item is out of bounds when reading from file "+fileType);
        }
    }

    public void writeValue(FileType fileType, int index, double value) {
        try{
            MappedFile file = files.get(fileType);
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
            MappedFile file = files.get(fileType);
            return new MappedFileAreaLock(file.lock(index*lineLength, lineLength, shared));
        }catch(IllegalArgumentException e){
            throw new ItemIndexOutOfBoundsException("Item index is negative:"+index+" when locking to file "+fileType);
        }catch (IOException e){
            throw new DataAccessException("Unable to read item "+index+" to file "+fileType, e);
        }
    }
}
