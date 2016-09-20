package com.reradio.networking.data;

import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("statusCode")
    private String mStatusCode;

    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

    public String getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(String statusCode) {
        mStatusCode = statusCode;
    }
}
