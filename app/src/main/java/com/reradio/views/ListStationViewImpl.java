package com.reradio.views;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.ViewSwitcher;

import com.reradio.R;
import com.reradio.StationAdapter;
import com.reradio.errorhandler.ErrorHandler;
import com.reradio.networking.data.Station;
import com.utils.DebugLogger;

import java.util.List;

public class ListStationViewImpl extends RelativeLayout implements ListStationView {

    private static final String TAG = ListStationViewImpl.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ViewSwitcher mViewSwitcher;
    private EmptyStationView mEmptyStationView;
    private OnRefreshListener mOnRefreshListener;

    public ListStationViewImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.v_list_station, this);
        mEmptyStationView = (EmptyStationView) view.findViewById(R.id.empty_view);
        initViewSwitcher(view, context);
        initRecyclerView(view, context);
        initRefreshLayout(view);
    }

    private void initViewSwitcher(View view, Context context) {
        mViewSwitcher = (ViewSwitcher) view.findViewById(R.id.recycler_switcher);
        mViewSwitcher.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_right));
        mViewSwitcher.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_left));
    }

    private void initRecyclerView(View view, Context context) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    private void initRefreshLayout(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_list);
        mSwipeRefreshLayout.measure(View.MEASURED_SIZE_MASK, View.MEASURED_HEIGHT_STATE_SHIFT);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.black));
        mSwipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            if (mOnRefreshListener != null) {
                mOnRefreshListener.onRefresh();
                clearList();
                showListIfEmptyViewVisible();
            }
        });
    }

    @Override
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        DebugLogger.d(TAG, "onDetachedFromWindow");
        mOnRefreshListener = null;
    }

    private void showRecyclerView() {
        stopRefreshing();
        enableRefreshLayoutDraggable();
        showSwitcher();
    }

    private void showEmptyView() {
        stopRefreshing();
        enableRefreshLayoutDraggable();
        showSwitcher();
        switchToEmptyView();
    }

    private void stopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * For dragging
     */
    private void enableRefreshLayoutDraggable() {
        mSwipeRefreshLayout.setEnabled(true);
    }

    private void clearList() {
        if (mRecyclerView != null) {
            StationAdapter stationAdapter = (StationAdapter) mRecyclerView.getAdapter();
            if (stationAdapter != null) {
                stationAdapter.clear();
            }
        }
    }

    private void showListIfEmptyViewVisible() {
        if (mViewSwitcher.getCurrentView().getId() == R.id.empty_view) {
            mViewSwitcher.showPrevious();
        }
    }

    private void showSwitcher() {
        mViewSwitcher.setVisibility(View.VISIBLE);
    }

    private void switchToEmptyView() {
        if (mViewSwitcher.getCurrentView().getId() == R.id.recycler_view) {
            mViewSwitcher.showNext();
        }
    }

    private void setEmptyViewMessage(String message) {
        mEmptyStationView.setMessage(message);
    }

    private String getEmptyViewMessage(String searchQuery) {
        return getResources().getString(R.string.no_results, searchQuery);
    }

    private String getErrorEmptyViewMessage(Throwable e) {
        return getResources().getString(ErrorHandler.getErrorMessageResId(e));
    }

    @Override
    public void setStationList(List<Station> list) {
        showRecyclerView();
        StationAdapter mStationAdapter = new StationAdapter(list, R.layout.v_station_row);
        mRecyclerView.setAdapter(mStationAdapter);
    }

    @Override
    public void showMessageScreen(String searchQuery) {
        showEmptyView();
        setEmptyViewMessage(getEmptyViewMessage(searchQuery));
    }

    @Override
    public void showErrorScreen(Throwable e) {
        DebugLogger.e(TAG, e.toString());
        DebugLogger.e(TAG, getErrorEmptyViewMessage(e));
        showEmptyView();
        setEmptyViewMessage(getErrorEmptyViewMessage(e));
    }
}
