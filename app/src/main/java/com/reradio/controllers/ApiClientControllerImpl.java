package com.reradio.controllers;

import com.reradio.networking.ApiInterface;
import com.reradio.networking.data.Station;
import com.reradio.networking.data.StationResponse;
import com.utils.DebugLogger;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Response;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ApiClientControllerImpl implements ApiClientController {

    private static final String TAG = ApiClientControllerImpl.class.getSimpleName();
    private static final long TIME_OUT = 20000;// 20 sec

    private final ApiInterface mApiInterface;
    private final String mDevKey;
    private final String mFormat;
    private final String mMediaType;
    private StationListener mStationListener;
    private Subscription mSubscription;

    public ApiClientControllerImpl(ApiInterface apiInterface, String devKey, String format, String mediaType) {
        mApiInterface = apiInterface;
        mDevKey = devKey;
        mFormat = format;
        mMediaType = mediaType;
    }

    @Override
    public void setStationListener(StationListener stationListener) {
        mStationListener = stationListener;
    }

    @Override
    public void search(String searchQuery) {
        Observable<Response<StationResponse>> responseObservable = mApiInterface.
                getStationList(mDevKey, searchQuery, mFormat, mMediaType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.timeout(TIME_OUT, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .filter(stationResponseResponse -> stationResponseResponse != null
                        && stationResponseResponse.body() != null
                        && stationResponseResponse.body().getResponse() != null
                        && stationResponseResponse.body().getResponse().getData() != null &&
                        stationResponseResponse.body().getResponse().getData().getStationList() != null);


        mSubscription = responseObservable.subscribe(
                stationResponseResponse -> {
                    DebugLogger.d(TAG, "Response: " + stationResponseResponse);
                    if (mStationListener != null) {
                        DebugLogger.d(TAG, getResponseStations(stationResponseResponse));
                        List<Station> stationList = getResponseStations(stationResponseResponse);
                        if (stationList != null && stationList.size() > 0) {
                            mStationListener.onStations(stationList);
                        } else {
                            mStationListener.onEmptyList(searchQuery);
                        }
                    }
                },
                throwable -> {
                    if (mStationListener != null) {
                        mStationListener.onError(throwable);
                    }
                });
    }

    private List<Station> getResponseStations(Response<StationResponse> stationResponseResponse) {
        return stationResponseResponse.body().getResponse().getData().getStationList().getStations();
    }

    @Override
    public void stopSearching() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
