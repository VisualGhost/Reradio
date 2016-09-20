package com.reradio.networking.data;

import com.google.gson.annotations.SerializedName;

public class StationResponse {

    @SerializedName("response")
    private Response mResponse;

    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

    public Response getResponse() {
        return mResponse;
    }

    public void setResponse(Response response) {
        mResponse = response;
    }
}
