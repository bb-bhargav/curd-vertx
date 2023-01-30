package com.example.sqlConnect.di;

import com.example.sqlConnect.controllers.DataController;
import com.example.sqlConnect.controllers.DataController_Factory;
import com.example.sqlConnect.controllers.DataController_MembersInjector;
import com.example.sqlConnect.router.DataRouter;
import com.example.sqlConnect.router.DataRouter_Factory;
import com.example.sqlConnect.router.DataRouter_MembersInjector;
import com.example.sqlConnect.services.DataService;
import com.example.sqlConnect.services.DataService_Factory;
import com.example.sqlConnect.services.DataService_MembersInjector;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dagger.internal.Preconditions;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.mysqlclient.MySQLPool;
import javax.annotation.processing.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerFactory implements Factory {
  private Router injectRouter;

  private Vertx injectVertx;

  private DaggerFactory(Builder builder) {
    initialize(builder);
  }

  public static Factory.Builder builder() {
    return new Builder();
  }

  private MySQLPool getMySQLPool() {
    return AppModule_MySQLPoolFactory.proxyMySQLPool(injectVertx);
  }

  private DataService getDataService() {
    return injectDataService(DataService_Factory.newDataService());
  }

  private DataController getDataController() {
    return injectDataController(DataController_Factory.newDataController());
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.injectRouter = builder.injectRouter;
    this.injectVertx = builder.injectVertx;
  }

  @Override
  public DataRouter dataRouter() {
    return injectDataRouter(DataRouter_Factory.newDataRouter());
  }

  @CanIgnoreReturnValue
  private DataService injectDataService(DataService instance) {
    DataService_MembersInjector.injectDb(instance, getMySQLPool());
    return instance;
  }

  @CanIgnoreReturnValue
  private DataController injectDataController(DataController instance) {
    DataController_MembersInjector.injectDataService(instance, getDataService());
    return instance;
  }

  @CanIgnoreReturnValue
  private DataRouter injectDataRouter(DataRouter instance) {
    DataRouter_MembersInjector.injectRouter(instance, injectRouter);
    DataRouter_MembersInjector.injectDataController(instance, getDataController());
    return instance;
  }

  private static final class Builder implements Factory.Builder {
    private Vertx injectVertx;

    private JsonObject injectConfig;

    private Router injectRouter;

    @Override
    public Factory build() {
      if (injectVertx == null) {
        throw new IllegalStateException(Vertx.class.getCanonicalName() + " must be set");
      }
      if (injectConfig == null) {
        throw new IllegalStateException(JsonObject.class.getCanonicalName() + " must be set");
      }
      if (injectRouter == null) {
        throw new IllegalStateException(Router.class.getCanonicalName() + " must be set");
      }
      return new DaggerFactory(this);
    }

    @Override
    public Builder injectVertx(Vertx vertx) {
      this.injectVertx = Preconditions.checkNotNull(vertx);
      return this;
    }

    @Override
    public Builder injectConfig(JsonObject config) {
      this.injectConfig = Preconditions.checkNotNull(config);
      return this;
    }

    @Override
    public Builder injectRouter(Router router) {
      this.injectRouter = Preconditions.checkNotNull(router);
      return this;
    }
  }
}
