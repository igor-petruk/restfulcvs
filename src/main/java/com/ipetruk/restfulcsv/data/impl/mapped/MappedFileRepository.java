package com.ipetruk.restfulcsv.data.impl.mapped;

import com.ipetruk.restfulcsv.boilerplate.ShutdownService;
import com.ipetruk.restfulcsv.data.impl.AbstractFileRepository;
import com.ipetruk.restfulcsv.data.impl.AccessMode;
import com.ipetruk.restfulcsv.data.impl.CSVFile;
import com.ipetruk.restfulcsv.string.CSVStringService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;

@Singleton
public class MappedFileRepository extends AbstractFileRepository{

    @Inject
    public MappedFileRepository(ShutdownService shutdownService, CSVStringService stringService,
                                @Named("csv.fixed.size") int lineLength,
                                @Named("file.initial") String initialFileName,
                                @Named("file.active") String activeFileName) throws IOException {
        super(shutdownService, stringService, lineLength, initialFileName, activeFileName);
    }

    @Override
    public CSVFile buildCSVFile(File file, AccessMode accessMode) throws IOException{
        return new MappedFile(file, accessMode);
    }
}
