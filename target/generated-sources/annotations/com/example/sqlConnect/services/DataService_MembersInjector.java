package com.example.sqlConnect.services;

import dagger.MembersInjector;
import io.vertx.reactivex.mysqlclient.MySQLPool;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DataService_MembersInjector implements MembersInjector<DataService> {
  private final Provider<MySQLPool> dbProvider;

  public DataService_MembersInjector(Provider<MySQLPool> dbProvider) {
    this.dbProvider = dbProvider;
  }

  public static MembersInjector<DataService> create(Provider<MySQLPool> dbProvider) {
    return new DataService_MembersInjector(dbProvider);
  }

  @Override
  public void injectMembers(DataService instance) {
    injectDb(instance, dbProvider.get());
  }

  public static void injectDb(DataService instance, MySQLPool db) {
    instance.db = db;
  }
}
