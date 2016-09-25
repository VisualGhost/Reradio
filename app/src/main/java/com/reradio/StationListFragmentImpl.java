package com.reradio;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reradio.di.InjectHelper;
import com.reradio.networking.ApiInterface;
import com.reradio.views.ListStationView;
import com.reradio.views.OnRefreshListener;
import com.utils.DebugLogger;

import javax.inject.Inject;

public class StationListFragmentImpl extends Fragment implements StationListFragment {

    private static final String TAG = StationListFragmentImpl.class.getSimpleName();
    private static final String SEARCH_QUERY = "search_query";

    private StationListPresenter mStationListPresenter;
    private OnRefreshListener mOnRefreshListener;
    private ListStationView mListStationView;

    @Inject
    ApiInterface mApiInterface;

    static Fragment getInstance(String searchQuery) {
        StationListFragmentImpl fragment = new StationListFragmentImpl();
        Bundle bundle = new Bundle();
        bundle.putString(SEARCH_QUERY, searchQuery);
        fragment.setArguments(bundle);
        return fragment;
    }

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
        mListStationView = (ListStationView) view.findViewById(R.id.list_station_view);
        mListStationView.setOnRefreshListener(mOnRefreshListener);
        mStationListPresenter = new StationListPresenterImpl(mListStationView, mApiInterface);
        startSearch();
    }

    private void startSearch() {
        if (getArguments() != null && getArguments().containsKey(SEARCH_QUERY) && mStationListPresenter != null) {
            mStationListPresenter.startSearch(getArguments().getString(SEARCH_QUERY));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof OnRefreshListener) {
            mOnRefreshListener = (OnRefreshListener) getActivity();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (getActivity() instanceof OnRefreshListener) {
            mOnRefreshListener = (OnRefreshListener) getActivity();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnRefreshListener = null;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DebugLogger.d(TAG, "onDestroy");
        mStationListPresenter.abortSearching();
    }

    @Override
    public void refresh() {
        startSearch();
    }
}
