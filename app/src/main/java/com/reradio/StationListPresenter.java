package com.reradio;

import com.reradio.views.ListStationView;

public interface StationListPresenter {

    void startSearch(String searchQuery);

    void abortSearching();

}
