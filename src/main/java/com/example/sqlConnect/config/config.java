package com.example.sqlConnect.config;

import com.example.sqlConnect.ConfigRenderer;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;

public class config {

  public static MySQLPool getMySQLClient(Vertx vertx) {
    MySQLConnectOptions co = new MySQLConnectOptions()
        .setPort(ConfigRenderer.DB_PORT)
        .setHost(ConfigRenderer.HOST)
        .setDatabase(ConfigRenderer.DB_NAME)
        .setUser(ConfigRenderer.DB_USERNAME)
        .setPassword(ConfigRenderer.DB_PASSWORD);

    PoolOptions po = new PoolOptions().setMaxSize(5);

    return MySQLPool.pool(vertx, co, po);
  }
}
