package com.reradio;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reradio.views.InputFormScreenView;
import com.utils.DebugLogger;

public class SearchFormFragmentImpl extends Fragment implements SearchFormFragment {

    private static final String TAG = SearchFormFragmentImpl.class.getSimpleName();

    private InputFormScreenView mInputFormScreenView;
    private OnSearchListener mOnSearchListener;

    public SearchFormFragmentImpl() {
        DebugLogger.d(TAG, "constructor");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_search_form, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mInputFormScreenView = (InputFormScreenView) view.findViewById(R.id.input_form_screen);
    }

    @Override
    public void onResume() {
        super.onResume();
        DebugLogger.d(TAG, "onResume");
        mInputFormScreenView.setOnSearchListener(mOnSearchListener);
    }


    @Override
    public void onPause() {
        super.onPause();
        DebugLogger.d(TAG, "onPause");
        mInputFormScreenView.setOnSearchListener(null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        DebugLogger.d(TAG, "onAttach context");
        if (getActivity() instanceof OnSearchListener) {
            mOnSearchListener = (OnSearchListener) getActivity();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        DebugLogger.d(TAG, "onAttach activity");
        if (getActivity() instanceof OnSearchListener) {
            mOnSearchListener = (OnSearchListener) getActivity();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        DebugLogger.d(TAG, "onDetach");
        mOnSearchListener = null;
    }
}
