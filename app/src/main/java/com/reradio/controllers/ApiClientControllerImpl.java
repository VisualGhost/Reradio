package com.reradio.controllers;

import com.reradio.networking.ApiInterface;
import com.reradio.networking.data.Station;
import com.reradio.networking.data.StationResponse;

import java.util.List;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ApiClientControllerImpl implements ApiClientController {

    private final ApiInterface mApiInterface;
    private final String mDevKey;
    private final String mFormat;
    private StationListener mStationListener;
    private Subscription mSubscription;

    public ApiClientControllerImpl(ApiInterface apiInterface, String devKey, String format) {
        mApiInterface = apiInterface;
        mDevKey = devKey;
        mFormat = format;
    }

    @Override
    public void setStationListener(StationListener stationListener) {
        mStationListener = stationListener;
    }

    @Override
    public void search(String searchQuery) {
        Observable<Response<StationResponse>> responseObservable = mApiInterface.
                getStationList(mDevKey, searchQuery, mFormat)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(stationResponseResponse -> stationResponseResponse != null
                        && stationResponseResponse.body() != null
                        && stationResponseResponse.body().getResponse() != null
                        && stationResponseResponse.body().getResponse().getData() != null &&
                        stationResponseResponse.body().getResponse().getData().getStationList() != null);
        mSubscription = responseObservable.subscribe(new Subscriber<Response<StationResponse>>() {
            @Override
            public void onCompleted() {
                // empty
            }

            @Override
            public void onError(Throwable e) {
                // empty
            }

            @Override
            public void onNext(Response<StationResponse> stationResponseResponse) {
                if (mStationListener != null) {
                    mStationListener.onStations(getResponseStations(stationResponseResponse));
                }
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
