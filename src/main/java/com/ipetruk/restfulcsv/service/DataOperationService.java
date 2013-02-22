package com.ipetruk.restfulcsv.service;

import com.ipetruk.restfulcsv.data.DataRepository;
import static com.ipetruk.restfulcsv.data.FileType.*;
import com.ipetruk.restfulcsv.data.ItemLock;
import com.ipetruk.restfulcsv.rest.dto.ValueResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataOperationService {
    @Inject
    DataRepository repository;

    public double operation1(int v1)throws Exception{
        try (ItemLock lock = repository.lock(ACTIVE, v1, true)){
            double value = repository.readValue(ACTIVE, v1);
            if (value>10){
                return value-10;
            } else {
                return value;
            }
        }
    }

    public int operation2(double v2, int v3, int v4) throws Exception{
        try (ItemLock lock = repository.lock(INITIAL, v3, true)){
            try (ItemLock lock2 = repository.lock(ACTIVE, v4, false)){
                double value = repository.readValue(INITIAL, v3)+v2;
                if (value<10){
                    repository.writeValue(ACTIVE, v4, value+10);
                    return 0;
                }else{
                    repository.writeValue(ACTIVE, v4, value);
                    return 1;
                }
            }
        }
    }
}
