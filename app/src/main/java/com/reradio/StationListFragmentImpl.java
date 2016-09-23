package com.reradio;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reradio.R;
import com.reradio.StationListPresenter;
import com.reradio.StationListPresenterImpl;
import com.reradio.di.InjectHelper;
import com.reradio.networking.ApiInterface;
import com.reradio.views.ListStationView;

import javax.inject.Inject;

public class StationListFragmentImpl extends Fragment implements StationListFragment{

    private StationListPresenter mStationListPresenter;

    @Inject
    ApiInterface mApiInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_list_station, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectHelper.getRootComponent().inject(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListStationView listStationView = (ListStationView) view.findViewById(R.id.list_station_view);
        mStationListPresenter = new StationListPresenterImpl(listStationView, mApiInterface);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mStationListPresenter.abortSearching();
    }

    @Override
    public void search(String searchQuery) {
        mStationListPresenter.startSearch(searchQuery);
    }
}
