package com.reradio;

import com.reradio.controllers.ApiClientController;
import com.reradio.controllers.ApiClientControllerImpl;
import com.reradio.networking.ApiInterface;
import com.reradio.networking.data.Station;
import com.reradio.views.ListStationView;

import java.util.List;

public class StationListPresenterImpl implements StationListPresenter {

    private static final String FORMAT = "json";

    private ApiClientController mApiClientController;

    public StationListPresenterImpl(ListStationView listStationView, ApiInterface mApiInterface) {
        mApiClientController = new ApiClientControllerImpl(mApiInterface, BuildConfig.DEV_KEY, FORMAT);
        mApiClientController.setStationListener(new ApiClientController.StationListener() {
            @Override
            public void onStations(List<Station> stationList) {
                listStationView.setStationList(stationList);
            }

            @Override
            public void onError(Throwable e) {
                listStationView.showErrorScreen(e);
            }

            @Override
            public void onEmptyList(String searchQuery) {
                listStationView.showMessageScreen(searchQuery);
            }
        });
    }

    @Override
    public void startSearch(String searchQuery) {
        mApiClientController.search(searchQuery);
    }

    @Override
    public void abortSearching() {
        mApiClientController.stopSearching();
    }
}
