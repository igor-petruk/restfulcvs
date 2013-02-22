package com.ipetruk.restfulcsv.rest.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class ValueResponse {
    private double value;

    public ValueResponse(double value) {
        this.value = value;
    }

    public ValueResponse() {
    }

    @XmlElement
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
