package com.xoredge.tariqjameelbayans;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.otto.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Utils.BusProvider;
import butterknife.Bind;
import butterknife.ButterKnife;
import events.EventServingVids;
import events.ShowProgressBar;
import events.VideosLoadedEvent;

import com.xoredge.tariqjameelbayans.models.DM_VideoResponse;
import com.xoredge.tariqjameelbayans.models.Video;
import com.xoredge.tariqjameelbayans.models.VideoApi;

import network.EndPointVideos;
import network.RetroGetService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DashboardFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER = "userLoggedIn";

    InterstitialAd mInterstitialAd;
    private DashboardRecycleViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Bind(R.id.scrollUp)
    FloatingActionButton mScrollUp;

    @Bind(R.id.recycler_view_dashboard)
    RecyclerView mRecyclerView;

    @Bind(R.id.empty_view)
    TextView empty_view;

    @Bind(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    @Bind(R.id.adView)
    AdView mAdView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */

    public static DashboardFragment newInstance(boolean favsOnly) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        if (favsOnly)
            args.putBoolean("favsOnly", true);
        else
            args.putBoolean("favsOnly", false);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DashboardFragment() {
    }

    boolean favsOnly = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            favsOnly = getArguments().getBoolean("favsOnly");

        }
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-4587822745173962/1539616938");
        requestNewInterstitial();

    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
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
        mAdView.setVisibility(View.GONE);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());

        mScrollUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.scrollToPosition(View.SCROLLBAR_POSITION_DEFAULT);
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);

        //_userService.setLoggedUserLatestLocation(userLoggedIn.getLocation());
        mAdapter = new DashboardRecycleViewAdapter(null, R.layout.video_card_item, getContext(), mInterstitialAd);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        mScrollUp.setVisibility(View.GONE);
                        break;
                    case RecyclerView.SCROLL_STATE_IDLE:
                        mScrollUp.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        if(!favsOnly)
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                BusProvider.getInstance().post(new ShowProgressBar());
                mAdapter.clearAll();
                LoadVideosJobs(true);
            }
        });

        LoadVideosJobs(false);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdView.setVisibility(View.VISIBLE);
            }
        });
        mAdView.loadAd(adRequest);

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

    private void serveVideos(List<Video> videos) {
        if (videos.size() < 1) {
            mScrollUp.setVisibility(View.GONE);
            empty_view.setVisibility(View.VISIBLE);
        } else {
            empty_view.setVisibility(View.GONE);
            mScrollUp.setVisibility(View.VISIBLE);
        }

        mAdapter.setVideos(videos);
        swipeContainer.setRefreshing(false);
        mAdapter.notifyDataSetChanged();

    }

    private void saveVideos(List<Video> videos) {
        List<Video> savedVideos = new Select().from(Video.class).execute();
        boolean save = true;
        for (Video v : videos) {
            for (Video vs : savedVideos) {
                if (vs.vid.equalsIgnoreCase(v.vid))
                    save = false;
            }
            if (save) {
                v.save();
            } else {
                save = true;
            }
        }
        savedVideos = new Select().from(Video.class).execute();
        Collections.shuffle(savedVideos);
        serveVideos(savedVideos);
        //mAdapter.setVideos(savedVideos);
        //mAdapter.notifyDataSetChanged();
    }

    private void LoadVideosJobs(boolean hardLoad) {
        if (!hardLoad) {
            List<Video> savedVideos = new Select().from(Video.class).execute();
            Log.d("Saved========", savedVideos.size() + "");
            if (favsOnly) {
                List<Video> favs = new ArrayList<Video>();
                for (Video fv : savedVideos) {
                    if (fv.isFavourite)
                        favs.add(fv);
                }
                Log.d("Favs========", favs.size() + "");
                serveVideos(favs);
                return;
            } else {
                serveVideos(savedVideos);
                if (savedVideos.size() > 0) {
                    BusProvider.getInstance().post(new EventServingVids(savedVideos));
                    return;
                }
            }
        }
        new DownloadVidoes().execute("x3ig5c","x3vgzl","x34pow","x47x8z","x46el7","x3o1aq","x3w0v4","x3khrw","x453ei","x44zdf","x40cq4","x3uq1p","x40q62","x337ot","x3vek5","x39sib","x3ltbf","x452nb");
    }


    private class DownloadVidoes extends AsyncTask<String, Void, List<Video>> {
        @Override
        protected void onPreExecute() {
            Log.d("Task","Download vids started");
            super.onPreExecute();
        }

        @Override
        protected List<Video> doInBackground(String... lists) {
            List<Video> outputVids = new ArrayList<>();
            for(String list : lists){
                outputVids.addAll(vidsByList(list));
            }
            return outputVids;
        }
        /** The system calls this to perform work in a worker thread and
         * delivers it the parameters given to AsyncTask.execute() */
        @Override
        protected void onPostExecute(List<Video> videos) {
            BusProvider.getInstance().post(new EventServingVids(videos));
            serveVideos(videos);
            saveVideos(videos);
            Log.d("Task", "Download vids ended");
            super.onPostExecute(videos);
        }
    }
    private List<Video> vidsByList(String playList){
        EndPointVideos servVids = RetroGetService.createService(EndPointVideos.class);

        try {
            List<Video> vids = new ArrayList<Video>();
            List<VideoApi> videosResp = new ArrayList<>();
            DM_VideoResponse dvr;
            int page=1;
            do{
                Call<DM_VideoResponse> callback = servVids.getByPlaylist(playList, 100, page, "id,title,views_total,record_start_time,duration");
                dvr = callback.execute().body();
                if(dvr!=null) {
                    videosResp = dvr.list;
                    for (VideoApi vip : videosResp) {
                        vids.add(new Video(vip));
                    }
                    page++;
                }
            }
            while(videosResp.size()==100 && dvr!=null);
            Log.d("RETRO: Received "+playList + " = ", vids.size() + "");
            return vids;

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("RETRO: Error  ",e.getMessage());
            return new ArrayList<Video>();
        }
    }
}

