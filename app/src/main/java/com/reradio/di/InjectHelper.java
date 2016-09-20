package com.reradio.di;

import com.reradio.di.dagger.DaggerRootComponent;
import com.reradio.di.dagger.RootComponent;

public class InjectHelper {
    private static RootComponent sRootComponent;

    static {
        initModules();
    }

    private static void initModules() {
        sRootComponent = DaggerRootComponent.builder().build();
    }

    public static RootComponent getRootComponent() {
        if (sRootComponent == null) {
            initModules();
        }
        return sRootComponent;
    }

}
