package com.ipetruk.restfulcsv.data;

public interface DataRepository {
    public double readValue(FileType fileType, int index);
    public void writeValue(FileType fileType, int index, double value);

    public ItemLock lock(FileType fileType, int index, boolean shared);
}
