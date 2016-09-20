package com.reradio.networking;

import com.reradio.networking.data.StationResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiInterface {

    @GET("nowplaying")
    Observable<Response<StationResponse>> getStationList(
            @Query("k") String devKey,
            @Query("ct") String searchQuery,
            @Query("f") String format
    );

}
