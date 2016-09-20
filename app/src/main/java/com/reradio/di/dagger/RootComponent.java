package com.reradio.di.dagger;

import com.reradio.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiInterfaceModule.class})
public interface RootComponent {

    void inject(MainActivity activity);

}
