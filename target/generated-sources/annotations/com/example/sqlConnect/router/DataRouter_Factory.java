package com.example.sqlConnect.router;

import com.example.sqlConnect.controllers.IDataController;
import dagger.internal.Factory;
import io.vertx.reactivex.ext.web.Router;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DataRouter_Factory implements Factory<DataRouter> {
  private final Provider<Router> routerProvider;

  private final Provider<IDataController> dataControllerProvider;

  public DataRouter_Factory(
      Provider<Router> routerProvider, Provider<IDataController> dataControllerProvider) {
    this.routerProvider = routerProvider;
    this.dataControllerProvider = dataControllerProvider;
  }

  @Override
  public DataRouter get() {
    return provideInstance(routerProvider, dataControllerProvider);
  }

  public static DataRouter provideInstance(
      Provider<Router> routerProvider, Provider<IDataController> dataControllerProvider) {
    DataRouter instance = new DataRouter();
    DataRouter_MembersInjector.injectRouter(instance, routerProvider.get());
    DataRouter_MembersInjector.injectDataController(instance, dataControllerProvider.get());
    return instance;
  }

  public static DataRouter_Factory create(
      Provider<Router> routerProvider, Provider<IDataController> dataControllerProvider) {
    return new DataRouter_Factory(routerProvider, dataControllerProvider);
  }

  public static DataRouter newDataRouter() {
    return new DataRouter();
  }
}
