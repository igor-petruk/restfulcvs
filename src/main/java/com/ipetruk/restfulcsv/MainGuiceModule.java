package com.ipetruk.restfulcsv;

import com.google.inject.AbstractModule;
import com.ipetruk.restfulcsv.data.DataRepository;
import com.ipetruk.restfulcsv.data.impl.mapped.MappedFileRepository;
import com.ipetruk.restfulcsv.service.DataOperationService;

public class MainGuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new PropertiesModule());

        bind(DataOperationService.class);
        bind(DataRepository.class).to(MappedFileRepository.class);
    }


}
