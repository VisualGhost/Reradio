package com.reradio.di.dagger;

import com.reradio.networking.ApiClient;
import com.reradio.networking.ApiInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiInterfaceModule {

    @Provides
    @Singleton
    public ApiInterface provideApiInterface() {
        return ApiClient.getClient().create(ApiInterface.class);
    }

}
