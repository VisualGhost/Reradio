package com.reradio.networking.data;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("stationlist")
    private StationList mStationList;

    public StationList getStationList() {
        return mStationList;
    }

    public void setStationList(StationList stationList) {
        mStationList = stationList;
    }
}
