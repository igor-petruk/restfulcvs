package com.ipetruk.restfulcsv.string;

import static com.google.common.base.Strings.*;
import static com.google.common.base.Joiner.*;

import com.google.common.base.Joiner;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.inject.Named;

@Singleton
public class CSVStringService {
    private String separator = ";";
    private String lineEnd = separator+"\n";

    private final int size;

    @Inject
    public CSVStringService(@Named("csv.fixed.size") int size) {
        this.size = size;
    }

    public String produceLine(double value){
        return new StringBuilder()
            .append(padEnd(String.valueOf(value)+separator,size-lineEnd.length(),' '))
            .append(lineEnd)
            .toString();
    }

    public Double parseLine(String line){
        return Double.parseDouble(line.split(separator)[0]);
    }
}
