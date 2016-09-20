package com.reradio;

import android.app.Activity;
import android.os.Bundle;

import com.reradio.controllers.ApiClientController;
import com.reradio.controllers.ApiClientControllerImpl;
import com.reradio.di.InjectHelper;
import com.reradio.networking.ApiInterface;
import com.reradio.networking.data.Station;
import com.utils.DebugLogger;

import java.util.List;

import javax.inject.Inject;


public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String FORMAT = "json";

    @Inject
    ApiInterface mApiInterface;

    private ApiClientController mApiClientController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InjectHelper.getRootComponent().inject(this);
        mApiClientController = new ApiClientControllerImpl(mApiInterface, BuildConfig.DEV_KEY, FORMAT);
        mApiClientController.setStationListener(new ApiClientController.StationListener() {
            @Override
            public void onStations(List<Station> stationList) {
                for(Station station: stationList){
                    DebugLogger.d(TAG, station.getName());
                }
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
        mApiClientController.search("BBC");
    }

}
