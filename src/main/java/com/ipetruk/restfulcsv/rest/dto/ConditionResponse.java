package com.ipetruk.restfulcsv.rest.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class ConditionResponse {
    private int conditionFired;

    public ConditionResponse(int conditionFired) {
        this.conditionFired = conditionFired;
    }

    public ConditionResponse() {
    }

    @XmlElement(name = "condition-fired")
    public int getConditionFired() {
        return conditionFired;
    }

    public void setConditionFired(int conditionFired) {
        this.conditionFired = conditionFired;
    }
}
