package com.xoredge.tariqjameelbayans;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.xoredge.tariqjameelbayans.models.Video;

/**
 * Created by Ahmad Ali on 11/15/2015.
 */
public class DashboardRecycleViewAdapter extends RecyclerView.Adapter<DashboardRecycleViewAdapter.ViewHolder> {

    private LinkedHashMap<String, Video> videos;
    private int videoCardLayout;
    InterstitialAd mInterstitialAd;
    Context context;


    // Provide a suitable constructor (depends on the kind of dataset)
    public DashboardRecycleViewAdapter(List<Video> videos, int videoCardLayout, Context context, InterstitialAd intAdView ) {
        setVideos(videos == null ? new ArrayList<Video>() : videos);
        this.videoCardLayout = videoCardLayout;
        this.context = context;
        this.mInterstitialAd = intAdView;
    }

    public void setVideos(List<Video> videos) {
        this.videos = new LinkedHashMap<>();
        Video video;
        for (int i = 0; i < videos.size(); i++) {
            video = videos.get(i);
            this.videos.put(video.vid, video);
        }
    }

    public void updateVideo(Video video) {
        if (videos != null) {
            videos.put(video.vid, video);
        }
    }

    public void clearAll() {
        this.videos.clear();
        this.notifyDataSetChanged();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @Bind(R.id.video_title)
        TextView mVideoTitle;

        @Bind(R.id.video_dekh)
        TextView videoViews;

        @Bind(R.id.video_duration)
        TextView mVideoDuration;

        @Bind(R.id.video_time)
        TextView mVideoTime;

        @Bind(R.id.video_thumb)
        ImageView mVideo_thumb;

        //

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final List<Video> vidoesList = new ArrayList<Video>(videos.values());
        final Video videoEntity = vidoesList.get(position);
        holder.mVideoTitle.setText(videoEntity.title);
        holder.videoViews.setText("Views: "+videoEntity.views_total);
        int timeSecs = Integer.parseInt(videoEntity.duration);
        int hours = timeSecs / 3600;
        int minutes = (timeSecs % 3600) / 60;
        int seconds = timeSecs % 60;

        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        holder.mVideoDuration.setText("Duration: "+timeString);
        holder.mVideoTime.setText(videoEntity.record_start_time);
        String url = "http://www.dailymotion.com/thumbnail/video/"+videoEntity.vid;

        holder.mVideo_thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent videoIntent = new Intent(context,VideoActivity.class);
                videoIntent.putExtra("videoId",videoEntity.vid);
                videoIntent.putExtra("videoTitle",videoEntity.title);
                context.startActivity(videoIntent);
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });

        Picasso.with(context).load(url).into(holder.mVideo_thumb);

    }

    private void PlayVideo(Video video) {
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return videos.values().size();
    }


}
