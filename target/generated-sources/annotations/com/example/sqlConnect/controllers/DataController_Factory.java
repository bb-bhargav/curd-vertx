package com.example.sqlConnect.controllers;

import com.example.sqlConnect.services.IDataService;
import dagger.internal.Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DataController_Factory implements Factory<DataController> {
  private final Provider<IDataService> dataServiceProvider;

  public DataController_Factory(Provider<IDataService> dataServiceProvider) {
    this.dataServiceProvider = dataServiceProvider;
  }

  @Override
  public DataController get() {
    return provideInstance(dataServiceProvider);
  }

  public static DataController provideInstance(Provider<IDataService> dataServiceProvider) {
    DataController instance = new DataController();
    DataController_MembersInjector.injectDataService(instance, dataServiceProvider.get());
    return instance;
  }

  public static DataController_Factory create(Provider<IDataService> dataServiceProvider) {
    return new DataController_Factory(dataServiceProvider);
  }

  public static DataController newDataController() {
    return new DataController();
  }
}
