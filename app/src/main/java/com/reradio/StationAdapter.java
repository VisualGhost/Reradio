package com.reradio;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.reradio.networking.data.Station;

import java.util.List;

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.ViewHolder> {

    private List<Station> mStations;
    private int mRowLayout;

    public StationAdapter(List<Station> stations, int rowLayout) {
        mStations = stations;
        mRowLayout = rowLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(mRowLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Station station = mStations.get(position);
        holder.bind(station);
    }

    @Override
    public int getItemCount() {
        return mStations != null ? mStations.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mStationIcon;
        TextView mStationName;
        TextView mTrackName;

        public ViewHolder(View itemView) {
            super(itemView);
            mStationIcon = (ImageView) itemView.findViewById(R.id.station_icon);
            mStationName = (TextView) itemView.findViewById(R.id.station_name);
            mTrackName = (TextView) itemView.findViewById(R.id.track_name);
        }

        void bind(Station station) {
            mStationName.setText(station.getName());
            mTrackName.setText(station.getCurrentTrack());
        }
    }
}
