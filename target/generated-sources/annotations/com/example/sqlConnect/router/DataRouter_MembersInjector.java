package com.example.sqlConnect.router;

import com.example.sqlConnect.controllers.IDataController;
import dagger.MembersInjector;
import io.vertx.reactivex.ext.web.Router;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DataRouter_MembersInjector implements MembersInjector<DataRouter> {
  private final Provider<Router> routerProvider;

  private final Provider<IDataController> dataControllerProvider;

  public DataRouter_MembersInjector(
      Provider<Router> routerProvider, Provider<IDataController> dataControllerProvider) {
    this.routerProvider = routerProvider;
    this.dataControllerProvider = dataControllerProvider;
  }

  public static MembersInjector<DataRouter> create(
      Provider<Router> routerProvider, Provider<IDataController> dataControllerProvider) {
    return new DataRouter_MembersInjector(routerProvider, dataControllerProvider);
  }

  @Override
  public void injectMembers(DataRouter instance) {
    injectRouter(instance, routerProvider.get());
    injectDataController(instance, dataControllerProvider.get());
  }

  public static void injectRouter(DataRouter instance, Router router) {
    instance.router = router;
  }

  public static void injectDataController(DataRouter instance, IDataController dataController) {
    instance.dataController = dataController;
  }
}
