package com.reradio;

import android.view.View;

import com.reradio.controllers.ApiClientController;
import com.reradio.controllers.ApiClientControllerImpl;
import com.reradio.networking.ApiInterface;
import com.reradio.networking.data.Station;
import com.reradio.views.ListStationView;

import java.util.List;

public class StationListPresenterImpl implements StationListPresenter {

    private static final String FORMAT = "json";

    private ApiClientController mApiClientController;
    private ListStationView mListStationView;

    public StationListPresenterImpl(ListStationView listStationView, ApiInterface mApiInterface) {
        mListStationView = listStationView;
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
    }

    @Override
    public void startSearch(String searchQuery) {
        mApiClientController.search("Metallica");
    }

    @Override
    public void abortSearching() {
        mApiClientController.stopSearching();
    }
}
