package com.reradio.di.dagger;

import com.reradio.StationListFragmentImpl;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiInterfaceModule.class})
public interface RootComponent {

    void inject(StationListFragmentImpl stationListFragment);

}
