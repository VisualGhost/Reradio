package com.reradio.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.reradio.R;

public class EmptyStationViewImpl extends RelativeLayout implements EmptyStationView {

    private TextView mMessage;

    public EmptyStationViewImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.v_empty_station_list_view, this);
        mMessage = (TextView) view.findViewById(R.id.empty_view_text_message);
    }

    @Override
    public void setMessage(String message) {
        mMessage.setText(message);
    }
}
