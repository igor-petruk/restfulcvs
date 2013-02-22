package com.ipetruk.restfulcsv.data;

public interface DataRepository {
    public double readValue(FileType fileType, long index);
    public void writeValue(FileType fileType, long index, double value);

    public ItemLock lock(FileType fileType, long index, boolean shared);
}
