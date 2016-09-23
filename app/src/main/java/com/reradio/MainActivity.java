package com.reradio;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.StrictMode;


public class MainActivity extends Activity {

    private static final String STATION_LIST_FRAGMENT_TAG = "station_list_fragment_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.v_activity_main);
        enableStrictMode();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_activity_container, new StationListFragmentImpl(), STATION_LIST_FRAGMENT_TAG);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StationListFragment stationListFragment = (StationListFragment) getFragmentManager().findFragmentByTag(STATION_LIST_FRAGMENT_TAG);
        stationListFragment.search("Manowar");
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


}
