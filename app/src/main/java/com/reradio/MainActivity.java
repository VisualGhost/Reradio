package com.reradio;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;

import com.reradio.controllers.ApiClientController;
import com.reradio.controllers.ApiClientControllerImpl;
import com.reradio.di.InjectHelper;
import com.reradio.networking.ApiInterface;
import com.reradio.networking.data.Station;
import com.reradio.views.ListStationView;

import java.util.List;

import javax.inject.Inject;


public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String FORMAT = "json";

    private ApiClientController mApiClientController;
    private ListStationView mListStationView;

    @Inject
    ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.v_activity_main);
        mListStationView = (ListStationView) findViewById(R.id.list_station_view);
        enableStrictMode();
        InjectHelper.getRootComponent().inject(this);
        mApiClientController = new ApiClientControllerImpl(mApiInterface, BuildConfig.DEV_KEY, FORMAT);
        mApiClientController.setStationListener(new ApiClientController.StationListener() {
            @Override
            public void onStations(List<Station> stationList) {
                mListStationView.setStationList(stationList);
            }

            @Override
            public void onError(Throwable e) {
                mListStationView.showErrorScreen(e);
            }

            @Override
            public void onEmptyList(String searchQuery) {
                mListStationView.showMessageScreen(searchQuery);
            }
        });

        mApiClientController.search("Metallica");
    }

    private void enableStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog().build());
        }
    }


}
