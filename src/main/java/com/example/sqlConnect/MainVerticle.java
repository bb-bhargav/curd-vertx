package com.example.sqlConnect;

import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.Vertx;

public class MainVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    ConfigRenderer.renderConfig();
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new HttpRouterVerticle());
  }
}
