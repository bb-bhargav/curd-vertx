package com.example.sqlConnect.router;

import javax.inject.Inject;

import com.example.sqlConnect.controllers.IDataController;

import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.BodyHandler;

public class DataRouter {

    @Inject
    DataRouter(){

    }

    @Inject
    public Router router;

    @Inject
    IDataController dataController;

    public void setUpApi() {
        router.route().handler(BodyHandler.create());

        router.get("/employees").handler(dataController.getData());
        router.get("/employee").handler(dataController.getDataByID());
        router.post("/employee").handler(dataController.postData());
        router.put("/employee/:id").handler(dataController.updateData());
        router.delete("/employee/:id").handler(dataController.deleteData());
    }

}

