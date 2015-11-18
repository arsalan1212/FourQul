package com.xoredge.tariqjameelbayans;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import events.VideosLoadedEvent;
import models.DM_VideoResponse;
import models.Video;
import network.EndPointVideos;
import network.RetroGetService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DashboardFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER = "userLoggedIn";


    private DashboardRecycleViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Bind(R.id.recycler_view_dashboard)
    RecyclerView mRecyclerView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */

    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DashboardFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        }
        //Register bus provider

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        //Unregister bus provider
        super.onPause();
    }

    ///=========================================================
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videoslist_list, container, false);
        ButterKnife.bind(this, view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //_userService.setLoggedUserLatestLocation(userLoggedIn.getLocation());
        mAdapter = new DashboardRecycleViewAdapter(null, R.layout.video_card_item,getContext());
        mRecyclerView.setAdapter(mAdapter);

        // Set the adapter

        // Set OnItemClickListener so we can be notified on item clicks
        LoadVideosJobs();

        //

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Subscribe
    public void VideosLoaded(VideosLoadedEvent vle) {
        mAdapter.setVideos(vle.videosList);
    }

    private void LoadVideosJobs() {
        Log.d("RETROFIT CALL ","Making Call...........");
        EndPointVideos servVids = RetroGetService.createService(EndPointVideos.class);
        Call<DM_VideoResponse> callback =  servVids.getByPlaylist("x3ig5c",100,"id,title,views_total,record_start_time,duration");

        callback.enqueue(new Callback<DM_VideoResponse>() {
            @Override
            public void onResponse(Response<DM_VideoResponse> response, Retrofit retrofit) {
               Log.d("RETROFIT Received ", response.body().list.size()+"");
                mAdapter.setVideos(response.body().list);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("RETROFIT","ex"+t.getMessage());
            }
        });

    }


}

