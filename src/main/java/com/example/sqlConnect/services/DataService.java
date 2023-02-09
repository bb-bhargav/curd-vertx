package com.example.sqlConnect.services;

import javax.inject.Inject;
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
import com.aerospike.client.policy.ClientPolicy;

public class DataService implements IDataService {
  private AerospikeClient client;
  private Key key;

  @Inject
  DataService() {
    try {
      ClientPolicy policy = new ClientPolicy();
      client = new AerospikeClient(policy, "localhost", 3000);
      key = new Key("test", "demo", "key1");
    } catch (Throwable e) {
      System.out.println("Aerospike not connected");
    }
  }

  @Inject
  MySQLPool db;

  public Single<String> getDataFromDB() {
    return db.preparedQuery("SELECT * FROM test_data.employee_details").rxExecute().map(rows -> {
      Bin[] bins = new Bin[rows.size()];
      Integer rowIndex = 0;

      for (Row row : rows) {
        bins[rowIndex] = new Bin(String.valueOf(rowIndex), row.toJson().toString());
        rowIndex++;
      }

      client.put(null, key, bins);

      return "Fetched DB and stored in cache";
    });
  }

  public Single<JsonArray> getData() {
    JsonArray result = new JsonArray();

    Record record = client.get(null, key);
    record.bins.values().forEach(bin -> {
      result.add(new JsonObject(bin.toString()));
    });

    return Single.just(result);
  }

  public Single<JsonObject> getDataByID(String id) {
    Record record = client.get(null, key);
    for (Object bin : record.bins.values()) {
      JsonObject obj = new JsonObject(bin.toString());
      if (obj.getString("id").equals(id)) {
        return Single.just(obj);
      }
    }
    return Single.just(new JsonObject().put("message", "id: " + id + " not found"));
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
