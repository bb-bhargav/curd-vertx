package com.example.sqlConnect;

import com.example.sqlConnect.di.DaggerFactory;
import com.example.sqlConnect.di.Factory;
import com.example.sqlConnect.router.DataRouter;

//import io.vertx.core.impl.logging.Logger;
//import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;

import java.io.FileReader;
import java.util.Properties;

public class HttpRouterVerticle extends AbstractVerticle {
//    Logger logger = LoggerFactory.getLogger(HttpRouterVerticle.class);
    @Override
    public void start() {
        Properties properties = new Properties();
        try {
          properties.load(new FileReader("src/main/resources/config.properties"));
        } catch (Throwable e) {
          System.out.println("can't read config file");
        }
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
