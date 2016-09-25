package com.reradio;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.StrictMode;

import com.reradio.views.OnRefreshListener;
import com.utils.DebugLogger;
import com.utils.ViewUtils;


public class MainActivity extends Activity implements OnSearchListener, OnRefreshListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String SEARCH_FORM_FRAGMENT = "search_form_fragment";
    private static final String STATION_LIST_FRAGMENT_TAG = "station_list_fragment_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DebugLogger.d(TAG, "onCreate");
        setContentView(R.layout.v_activity_main);
        enableStrictMode();
        showSearchForm();
    }

    private void showSearchForm() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_activity_container, new SearchFormFragmentImpl(), SEARCH_FORM_FRAGMENT);
        transaction.commit();
    }

    private void showStationsList(String searchQuery) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_activity_container, StationListFragmentImpl.getInstance(searchQuery), STATION_LIST_FRAGMENT_TAG);
        transaction.addToBackStack(STATION_LIST_FRAGMENT_TAG);
        transaction.commit();
    }

    private void enableStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog().build());
        }
    }

    @Override
    public void onSearch(String searchQuery) {
        showStationsList(searchQuery);
        ViewUtils.hideKeyboard(this);
    }

    @Override
    public void onRefresh() {
        FragmentManager fragmentManager = getFragmentManager();
        StationListFragment stationListFragment = (StationListFragment) fragmentManager.findFragmentByTag(STATION_LIST_FRAGMENT_TAG);
        if (stationListFragment != null) {
            stationListFragment.refresh();
        }
    }
}
