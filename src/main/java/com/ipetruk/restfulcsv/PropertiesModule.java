package com.ipetruk.restfulcsv;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.ipetruk.restfulcsv.util.ApplicationInitializatioException;

import java.io.IOException;
import java.util.Properties;

public class PropertiesModule extends AbstractModule {
    @Override
    protected void configure() {
        try{
            Properties properties = new Properties();
            properties.load(MainGuiceModule.class.getResourceAsStream("/settings.properties"));
            Names.bindProperties(binder(), properties);
        }catch(IOException e){
            throw new ApplicationInitializatioException("Unable to find settings.properties", e);
        }
    }
}
