package com.reradio.views;

import com.reradio.networking.data.Station;

import java.util.List;

public interface ListStationView {

    void setStationList(List<Station> list);

    void showMessageScreen(String searchQuery);

    void showErrorScreen(Throwable throwable);

    void setOnRefreshListener(OnRefreshListener onRefreshListener);
}
