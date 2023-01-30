package com.example.sqlConnect.config;

import com.example.sqlConnect.ConfigRenderer;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;

import java.io.FileReader;
import java.util.Properties;

public class config {

    public static MySQLPool getMySQLClient(Vertx vertx) {
      Properties properties = new Properties();
      try {
        properties.load(new FileReader("src/main/resources/config.properties"));
      } catch (Throwable e) {
        System.out.println("can't read config file");
      }
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
