package com.example.sqlConnect.di;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.mysqlclient.MySQLPool;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_MySQLPoolFactory implements Factory<MySQLPool> {
  private final Provider<Vertx> vertxProvider;

  public AppModule_MySQLPoolFactory(Provider<Vertx> vertxProvider) {
    this.vertxProvider = vertxProvider;
  }

  @Override
  public MySQLPool get() {
    return provideInstance(vertxProvider);
  }

  public static MySQLPool provideInstance(Provider<Vertx> vertxProvider) {
    return proxyMySQLPool(vertxProvider.get());
  }

  public static AppModule_MySQLPoolFactory create(Provider<Vertx> vertxProvider) {
    return new AppModule_MySQLPoolFactory(vertxProvider);
  }

  public static MySQLPool proxyMySQLPool(Vertx vertx) {
    return Preconditions.checkNotNull(
        AppModule.mySQLPool(vertx), "Cannot return null from a non-@Nullable @Provides method");
  }
}
