package com.example.sqlConnect.controllers;

import javax.inject.Inject;

import com.aerospike.client.AerospikeClient;
//import com.aerospike.client.Bin;
//import com.aerospike.client.Key;
//import com.aerospike.client.policy.Policy;
//import com.aerospike.client.policy.WritePolicy;
//import com.aerospike.client.Record;
import com.example.sqlConnect.services.IDataService;

import io.vertx.core.Handler;
import io.vertx.reactivex.core.MultiMap;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.http.HttpServerResponse;
import io.vertx.reactivex.ext.web.RoutingContext;

public class DataController implements IDataController {
    AerospikeClient client = new AerospikeClient("hostname", 3000);

    @Inject
    DataController() {
    }

    @Inject
    IDataService dataService;

    @Override
    public Handler<RoutingContext> getData() {

        return routingContext -> {
            HttpServerResponse httpServerResponse = routingContext.response();
            httpServerResponse.putHeader("content-type", "application/json");
            dataService.getData().subscribe(
                    res -> httpServerResponse.setStatusCode(200).end(Json.encodePrettily(res)),
                    err -> httpServerResponse.setStatusCode(500)
                            .end(new JsonObject().put("message", "Error occurred").toString()));
        };
    }

    public Handler<RoutingContext> getDataByID() {
        return routingContext -> {
            HttpServerResponse httpServerResponse = routingContext.response();
            httpServerResponse.putHeader("content-type", "application/json");
            MultiMap params = routingContext.request().params();
            dataService.getDataByID(params.get("id")).subscribe(
                    res -> httpServerResponse.setStatusCode(200).end(Json.encodePrettily(res)),
                    err -> httpServerResponse.setStatusCode(500)
                            .end(new JsonObject().put("message", "Error occurred").toString()));
        };
    }

    public Handler<RoutingContext> postData() {
        return routingContext -> {
            HttpServerResponse httpServerResponse = routingContext.response();
            httpServerResponse.putHeader("content-type", "application/json");
            dataService.postData(routingContext.body().asJsonObject()).subscribe(
                    res -> httpServerResponse.setStatusCode(200).end(Json.encodePrettily(res)),
                    err -> httpServerResponse.setStatusCode(500)
                            .end(new JsonObject().put("message", "Error occurred").toString()));
        };
    }

    public Handler<RoutingContext> updateData() {
        return routingContext -> {
            HttpServerResponse httpServerResponse = routingContext.response();
            httpServerResponse.putHeader("content-type", "application/json");
            dataService.updateData(routingContext.pathParam("id"), routingContext.body().asJsonObject()).subscribe(res -> {
                httpServerResponse.setStatusCode(200).end(Json.encodePrettily(res));
            }, err -> {
                httpServerResponse.setStatusCode(500).end(new JsonObject().put("message",
                        "Error occurred").toString());
            });
        };
    }

    public Handler<RoutingContext> deleteData() {
        return routingContext -> {
            HttpServerResponse httpServerResponse = routingContext.response();
            httpServerResponse.putHeader("content-type", "application/json");
            dataService.deleteData(routingContext.pathParam("id")).subscribe(res -> {
                httpServerResponse.setStatusCode(200).end(Json.encodePrettily(res));
            }, err -> {
                httpServerResponse.setStatusCode(500).end(new JsonObject().put("message",
                        "Error occurred").toString());
            });
        };
    }
}
