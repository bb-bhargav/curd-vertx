package com.example.sqlConnect.di;

import com.example.sqlConnect.config.config;
import com.example.sqlConnect.controllers.DataController;
import com.example.sqlConnect.controllers.IDataController;
import com.example.sqlConnect.services.DataService;
import com.example.sqlConnect.services.IDataService;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.mysqlclient.MySQLPool;

@Module
public interface AppModule {

    @Provides
    static MySQLPool mySQLPool(Vertx vertx) {
        return config.getMySQLClient(vertx);
    }

    @Binds
    IDataController bIDataController(DataController dataController);

    @Binds
    IDataService bDataService(DataService dataService);
}
