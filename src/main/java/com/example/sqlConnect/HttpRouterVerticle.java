package com.example.sqlConnect;

import com.example.sqlConnect.di.DaggerFactory;
import com.example.sqlConnect.di.Factory;
import com.example.sqlConnect.router.DataRouter;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;


public class HttpRouterVerticle extends AbstractVerticle {
    @Override
    public void start() {
        Router router = Router.router(vertx);

        Factory factory = DaggerFactory.builder().injectConfig(config()).injectVertx(vertx).injectRouter(router).build();

        DataRouter dataRouter = factory.dataRouter();
        dataRouter.setUpApi();

        vertx.createHttpServer().requestHandler(router).rxListen(ConfigRenderer.SERVER_PORT)
                .subscribe(
                  httpServer -> System.out.println("Application Started"),
                  err -> System.out.println("Error occurred")
                );
    }
}
