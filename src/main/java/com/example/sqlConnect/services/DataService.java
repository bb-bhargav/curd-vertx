package com.example.sqlConnect.services;

import javax.inject.Inject;

import com.example.sqlConnect.model.Data;
import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.mysqlclient.MySQLClient;
import io.vertx.reactivex.mysqlclient.MySQLPool;
import io.vertx.reactivex.sqlclient.Row;
import io.vertx.reactivex.sqlclient.RowSet;
import io.vertx.reactivex.sqlclient.Tuple;
import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.WritePolicy;

public class DataService implements IDataService {

  @Inject
  DataService() {
  }

  @Inject
  MySQLPool db;

  @Inject
  AerospikeClient client;

  public Single<String> getDataFromDB() {
    WritePolicy writePolicy = new WritePolicy();
    writePolicy.expiration = 15;

    return db.preparedQuery("SELECT * FROM test_data.employee_details").rxExecute().map(rows -> {
      for (Row row : rows) {
        Key key = new Key("test", "employeeDetails", String.valueOf(row.getInteger("id")));
        client.put(writePolicy, key, new Bin("value", row.toJson().toString()));
      }
      return "Fetched DB and stored in cache";
    });
  }

  public Single<JsonArray> getData() {
    JsonArray data = getFromCache();
    if (data.isEmpty()) {
      System.out.println("No cache data found, fetching from DB...");
      return getDataFromDB().map(res -> {
        return getFromCache();
      });
    }
    return Single.just(data);
  }

  private JsonArray getFromCache() {
    JsonArray data = new JsonArray();
    client.scanAll(null, "test", "employeeDetails", (key, record) -> {
      data.add(new JsonObject(record.bins.get("value").toString()));
    });
    return data;
  }

  public Single<JsonObject> getDataByID(String id) {
    Key key = new Key("test", "employeeDetails", id);
    Record record = client.get(null, key);
    if (record == null) {
      System.out.println("No cache data found, fetching from DB...");
      return isEmpWithIdExists(id).map(rows -> {
        JsonObject result = new JsonObject();
        if (rows.size() > 0) {
          for (Row row : rows) {
            result = JsonObject.mapFrom(new Data(row.getInteger("id"), row.getString("first_name"), row.getString("last_name")));
          }
        } else {
          result.put("message", "id: " + id + " not found");
        }
        return result;
      });
    } else {
      return Single.just(new JsonObject(record.bins.get("value").toString()));
    }
  }

  public Single<JsonObject> postData(JsonObject bodyObject) {
    return isEmployeeExists(bodyObject.getString("first_name"), bodyObject.getString("last_name"))
        .flatMap(res -> {
          for (Row rowData : res) {
            return Single.just(new JsonObject().put("message", "employee exists with id " + rowData.getInteger("id")));
          }
          return db.preparedQuery("INSERT INTO test_data.employee_details (first_name, last_name) VALUES (?, ?)")
              .rxExecute(Tuple.of(bodyObject.getString("first_name"), bodyObject.getString("last_name")))
              .map(rows -> bodyObject.put("message", "added").put("id", rows.property(MySQLClient.LAST_INSERTED_ID)));
        });
  }

  public Single<JsonObject> updateData(String id, JsonObject bodyObject) {
    return isEmpWithIdExists(id).flatMap(res -> {
      if (res.size() > 0) {
        return db.preparedQuery("UPDATE test_data.employee_details SET first_name = ?, last_name = ? WHERE id = ?")
            .rxExecute(Tuple.of(bodyObject.getString("first_name"),
                bodyObject.getString("last_name"),
                id))
            .map(rows -> bodyObject.put("message", "updated"));
      }
      return Single.just(new JsonObject().put("message", "employee doesn't exist with id: " + id));
    });
  }

  public Single<JsonObject> deleteData(String id) {
    JsonObject bodyObject = new JsonObject();
    return isEmpWithIdExists(id).flatMap(res -> {
      if (res.size() > 0) {
        return db.preparedQuery("DELETE FROM test_data.employee_details WHERE id = ?")
            .rxExecute(Tuple.of(id)).map(rows -> bodyObject.put("message", "deleted").put("id", id));
      }
      return Single.just(new JsonObject().put("message", "employee doesn't exist with id: " + id));
    });
  }

  private Single<RowSet<Row>> isEmployeeExists(String first_name, String last_name) {
    return db.preparedQuery("SELECT * from test_data.employee_details WHERE first_name=? AND last_name=?")
        .rxExecute(Tuple.of(first_name.trim(), last_name.trim()));
  }

  private Single<RowSet<Row>> isEmpWithIdExists(String id) {
    return db.preparedQuery("SELECT * FROM test_data.employee_details WHERE id = ?").rxExecute(Tuple.of(id.trim()));
  }
}
