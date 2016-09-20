package com.reradio.networking.data;

import com.google.gson.annotations.SerializedName;

public class Station {

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("ct")
    private String mCurrentTrack;

    @SerializedName("cst")
    private String mNextTrack;

    @SerializedName("lc")
    private String mListeners;

    public void setId(String id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setCurrentTrack(String currentTrack) {
        mCurrentTrack = currentTrack;
    }

    public void setNextTrack(String nextTrack) {
        mNextTrack = nextTrack;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getCurrentTrack() {
        return mCurrentTrack;
    }

    public String getNextTrack() {
        return mNextTrack;
    }

    public String getListeners() {
        return mListeners;
    }

    public void setListeners(String listeners) {
        mListeners = listeners;
    }
}
