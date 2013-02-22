package com.ipetruk.restfulcsv.boilerplate;

import com.google.inject.Singleton;

import javax.inject.Inject;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class ShutdownService {
    public List<Closeable> closeables = Collections.synchronizedList(new ArrayList<Closeable>());

    @Inject
    Logger logger;

    public void registerCloseable(Closeable closeable){
        closeables.add(closeable);
    }

    void shutdown(){
        synchronized (closeables){
            for (Closeable closeable: closeables){
                try{
                    closeable.close();
                }catch (IOException e){
                    logger.log(Level.SEVERE, "Unable to close {0}, cause {1}",
                            new Object[]{closeable, e});
                }
            }
        }
    }
}
