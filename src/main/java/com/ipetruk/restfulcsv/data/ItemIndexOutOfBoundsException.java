package com.ipetruk.restfulcsv.data;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ItemIndexOutOfBoundsException extends WebApplicationException {
    public ItemIndexOutOfBoundsException(String s) {
        super(Response
                .status(Response.Status.NOT_FOUND)
                //.entity("404 NOT FOUND \n"+s)
                .type(MediaType.TEXT_PLAIN_TYPE)
                .build());
    }
}
