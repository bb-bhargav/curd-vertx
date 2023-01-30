package com.example.sqlConnect.di;

import com.example.sqlConnect.router.DataRouter;
import dagger.BindsInstance;
import dagger.Component;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.core.Vertx;

@Component(modules = AppModule.class)
public interface Factory {

    DataRouter dataRouter();

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder injectVertx(Vertx vertx);

        @BindsInstance
        Builder injectConfig(JsonObject config);

        @BindsInstance
        Builder injectRouter(Router router);

        Factory build();
    }

}
