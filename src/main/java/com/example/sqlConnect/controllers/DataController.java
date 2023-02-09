package com.example.sqlConnect.controllers;

import javax.inject.Inject;
import com.example.sqlConnect.services.IDataService;

import io.vertx.core.Handler;
import io.vertx.reactivex.core.MultiMap;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.http.HttpServerResponse;
import io.vertx.reactivex.ext.web.RoutingContext;

public class DataController implements IDataController {

    @Inject
    DataController() {
    }

    @Inject
    IDataService dataService;

    @Override
    public Handler<RoutingContext> getDataFromDB() {

        return routingContext -> {
            HttpServerResponse httpServerResponse = routingContext.response();
            httpServerResponse.putHeader("content-type", "application/json");
            dataService.getDataFromDB().subscribe(
                    res -> httpServerResponse.setStatusCode(200)
                            .end(new JsonObject().put("message", res).toString()),
                    err -> httpServerResponse.setStatusCode(500)
                            .end(new JsonObject().put("message", "Error occurred").toString()));
        };
    }

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
            dataService.updateData(routingContext.pathParam("id"), routingContext.body().asJsonObject())
                    .subscribe(res -> {
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
