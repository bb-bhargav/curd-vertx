package com.example.sqlConnect.controllers;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;

public interface IDataController {
    Handler<RoutingContext> getData();
    Handler<RoutingContext> getDataByID();
    Handler<RoutingContext> postData();
    Handler<RoutingContext> updateData();
    Handler<RoutingContext> deleteData();
}
