package com.example.sqlConnect.di;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.client.policy.WritePolicy;
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

    @Provides
    static AerospikeClient aerospikeConnect() {
        ClientPolicy policy = new ClientPolicy();
        AerospikeClient client = new AerospikeClient(policy, "localhost", 3000);
        return client;
    }

    @Binds
    IDataController bIDataController(DataController dataController);

    @Binds
    IDataService bDataService(DataService dataService);
}
