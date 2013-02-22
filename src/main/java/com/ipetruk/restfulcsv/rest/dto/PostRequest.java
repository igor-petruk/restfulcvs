package com.ipetruk.restfulcsv.rest.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
public class PostRequest {
    double v2;
    int v3, v4;

    @XmlElement
    public double getV2() {
        return v2;
    }

    public void setV2(double v2) {
        this.v2 = v2;
    }

    @XmlElement
    public int getV3() {
        return v3;
    }

    public void setV3(int v3) {
        this.v3 = v3;
    }

    @XmlElement
    public int getV4() {
        return v4;
    }

    public void setV4(int v4) {
        this.v4 = v4;
    }
}
