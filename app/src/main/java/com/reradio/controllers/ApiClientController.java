package com.reradio.controllers;

import com.reradio.networking.data.Station;

import java.util.List;

public interface ApiClientController {

    interface StationListener {

        void onStations(List<Station> stationList);

        void onError(String errorMessage);
    }

    void setStationListener(StationListener stationListener);

    void search(String searchQuery);

    void stopSearching();
}
