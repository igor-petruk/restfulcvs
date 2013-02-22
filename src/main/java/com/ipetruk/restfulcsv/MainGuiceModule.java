package com.ipetruk.restfulcsv;

import com.google.inject.AbstractModule;
import com.ipetruk.restfulcsv.service.DataOperationService;

public class MainGuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new PropertiesModule());
        install(new DataBackendModule());

        bind(DataOperationService.class);
    }


}
