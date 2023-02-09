package com.example.sqlConnect.services;

import dagger.internal.Factory;
import io.vertx.reactivex.mysqlclient.MySQLPool;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DataService_Factory implements Factory<DataService> {
  private final Provider<MySQLPool> dbProvider;

  public DataService_Factory(Provider<MySQLPool> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public DataService get() {
    return provideInstance(dbProvider);
  }

  public static DataService provideInstance(Provider<MySQLPool> dbProvider) {
    DataService instance = new DataService();
    DataService_MembersInjector.injectDb(instance, dbProvider.get());
    return instance;
  }

  public static DataService_Factory create(Provider<MySQLPool> dbProvider) {
    return new DataService_Factory(dbProvider);
  }

  public static DataService newDataService() {
    return new DataService();
  }
}
