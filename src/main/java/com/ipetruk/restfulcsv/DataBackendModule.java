package com.ipetruk.restfulcsv;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.ipetruk.restfulcsv.data.DataRepository;
import com.ipetruk.restfulcsv.data.impl.cached.CachedFileRepository;
import com.ipetruk.restfulcsv.data.impl.mapped.MappedFileRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

public class DataBackendModule extends AbstractModule {
    @Singleton
    @Provides
    @Inject
    public DataRepository dataRepository(
            @Named("data.backend.implementation") String backend,
            Provider<CachedFileRepository> cachedFileRepository,
            Provider<MappedFileRepository> mappedFileRepository) throws Exception{
        switch(backend.trim().toLowerCase()){
            case "mapped":
                return mappedFileRepository.get();
            case "cached":
                return cachedFileRepository.get();
            default:
                throw new InstantiationException("Unknown backend implementation: "+backend);
        }
    }

    @Override
    protected void configure() {
        bind(CachedFileRepository.class);
        bind(MappedFileRepository.class);
    }
}
