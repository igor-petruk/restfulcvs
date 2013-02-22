package com.ipetruk.restfulcsv.data.impl.cached;

public class CacheKey {
    long from;
    int size;

    public CacheKey(long from, int size) {
        this.from = from;
        this.size = size;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CacheKey cacheKey = (CacheKey) o;

        if (from != cacheKey.from) return false;
        if (size != cacheKey.size) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (from ^ (from >>> 32));
        result = 31 * result + (int) (size ^ (size >>> 32));
        return result;
    }
}
