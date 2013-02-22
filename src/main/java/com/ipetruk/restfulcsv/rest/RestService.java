package com.ipetruk.restfulcsv.rest;

import com.google.inject.Inject;
import com.ipetruk.restfulcsv.data.DataRepository;
import com.ipetruk.restfulcsv.data.FileType;
import com.ipetruk.restfulcsv.data.ItemLock;
import com.ipetruk.restfulcsv.rest.dto.ConditionResponse;
import com.ipetruk.restfulcsv.rest.dto.ValueResponse;
import com.ipetruk.restfulcsv.rest.dto.PostRequest;
import com.ipetruk.restfulcsv.service.DataOperationService;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Singleton
@Path("/rest")
public class RestService {
    @Inject
    DataOperationService operationService;

    @GET
    @Path("/status")
    public String status(){
        return "OK";
    }

    @GET
    @Path("/service/{v1}")
    @Produces(MediaType.APPLICATION_XML)
    public ValueResponse get(@PathParam("v1") int v1) throws Exception{
        double value = operationService.operation1(v1);

        return new ValueResponse(value);
    }

    @POST
    @Path("/service")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public ConditionResponse post(PostRequest request) throws Exception{
        int value = operationService.operation2(request.getV2(),
                request.getV3(), request.getV4());

        return new ConditionResponse(value);
    }
}
