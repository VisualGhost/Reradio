package com.reradio.networking.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StationList {

    @SerializedName("station")
    private List<Station> mStations;

    @SerializedName("tunein")
    private TuneIn mTuneIn;

    public void setStations(List<Station> stations) {
        mStations = stations;
    }

    public void setTuneIn(TuneIn tuneIn) {
        mTuneIn = tuneIn;
    }

    public List<Station> getStations() {
        return mStations;
    }

    public TuneIn getTuneIn() {
        return mTuneIn;
    }
}
