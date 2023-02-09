package com.example.sqlConnect.services;

import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public interface IDataService {
    Single<String> getDataFromDB();

    Single<JsonArray> getData();

    Single<JsonObject> getDataByID(String id);

    Single<JsonObject> postData(JsonObject bObject);

    Single<JsonObject> updateData(String id, JsonObject bObject);

    Single<JsonObject> deleteData(String id);
}
