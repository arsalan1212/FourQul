package com.xoredge.tariqjameelbayans;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Ahmad Ali on 11/15/2015.
 */
public class DashboardRecycleViewAdapter extends RecyclerView.Adapter<DashboardRecycleViewAdapter.ViewHolder> {

    private LinkedHashMap<Integer, Video> videos;
    private int videoCardLayout;


    // Provide a suitable constructor (depends on the kind of dataset)
    public DashboardRecycleViewAdapter(List<Video> videos, int videoCardLayout) {
        setVideos(videos == null ? new ArrayList<Video>() : videos);
        this.videoCardLayout = videoCardLayout;
    }

    public void setVideos(List<Video> videos) {
        this.videos = new LinkedHashMap<>();
        Video video;
        for (int i = 0; i < videos.size(); i++) {
            video = videos.get(i);
            this.videos.put(video.Id, video);
        }
    }

    public void updateVideo(Video video) {
        if (videos != null) {
            videos.put(video.Id, video);
        }
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @InjectView(R.id.video_title)
        TextView mVideoTitle;
        @InjectView(R.id.video_description)
        TextView mVideoDescription;
        @InjectView(R.id.video_location)
        TextView mVideoLocation;
        @InjectView(R.id.video_time)
        TextView mVideoTime;
        @InjectView(R.id.video_play)
        TextView mVideoBtnPlay;



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(videoCardLayout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        List<Video> vidoesList = new ArrayList<Video>(videos.values());
        final Video videoEntity = vidoesList.get(position);
        holder.mVideoTitle.setText(videoEntity.Name);
        holder.mVideoDescription.setText(videoEntity.Description);
        holder.mVideoLocation.setText(videoEntity.Location);
        holder.mVideoTime.setText(videoEntity.Time);
        holder.mVideoBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("VideoApp","Play Video Now");
            }
        });

    }

    private void PlayVideo(Video video) {
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return videos.values().size();
    }


}
