package com.example.sqlConnect.controllers;

import com.example.sqlConnect.services.IDataService;
import dagger.MembersInjector;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DataController_MembersInjector implements MembersInjector<DataController> {
  private final Provider<IDataService> dataServiceProvider;

  public DataController_MembersInjector(Provider<IDataService> dataServiceProvider) {
    this.dataServiceProvider = dataServiceProvider;
  }

  public static MembersInjector<DataController> create(Provider<IDataService> dataServiceProvider) {
    return new DataController_MembersInjector(dataServiceProvider);
  }

  @Override
  public void injectMembers(DataController instance) {
    injectDataService(instance, dataServiceProvider.get());
  }

  public static void injectDataService(DataController instance, IDataService dataService) {
    instance.dataService = dataService;
  }
}
