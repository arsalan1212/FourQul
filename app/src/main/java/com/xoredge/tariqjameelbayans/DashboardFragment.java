package com.xoredge.tariqjameelbayans;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.xoredge.tariqjameelbayans.dummy.DummyContent;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DashboardFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER = "userLoggedIn";


    private DashboardRecycleViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @InjectView(R.id.recycler_view_dashboard) RecyclerView mRecyclerView;

    @InjectView(R.id.emptyText)
    TextView mEmptyTextView;

    //
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
        ButterKnife.inject(this, view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<Video> sampleVideos = new ArrayList<>();
        Video vid = new Video();
        vid.Name = "Izzate Nafs";
        vid.Description = "Bayan on respecting others";
        vid.Location="Faisalabad";
        vid.Time="Jan, 12, 2015";
        vid.Id=1;
        vid.Link = "http://youtube.com/?v=abcdef";
        sampleVideos.add(0,vid);
        Video vid1 = new Video();
        vid1.Id=2;
        vid1.Name = "Love and Hatred";
        vid1.Description = "Bayan on respecting others";
        sampleVideos.add(1,vid1);
        Video vid2 = new Video();
        vid2.Id=3;
        vid2.Name = "Parents prayers";
        vid2.Description = "Bayan on respecting others";
        sampleVideos.add(2,vid2);
        Video vid3 = new Video();
        vid3.Id=3;
        vid3.Name = "Playing with Children";
        vid3.Description = "Bayan on respecting others";
        sampleVideos.add(3,vid3);

        //_userService.setLoggedUserLatestLocation(userLoggedIn.getLocation());
        mAdapter = new DashboardRecycleViewAdapter(sampleVideos,R.layout.video_card_item);
        mRecyclerView.setAdapter(mAdapter);

        // Set the adapter

        // Set OnItemClickListener so we can be notified on item clicks
        LoadEmployeeJobs();

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

    private void LoadEmployeeJobs() {
    }



}

