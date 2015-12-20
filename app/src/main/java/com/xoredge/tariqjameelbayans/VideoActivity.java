package com.xoredge.tariqjameelbayans;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.activeandroid.query.Select;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.xoredge.tariqjameelbayans.models.Video;

public class VideoActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Bind(R.id.dmWebVideoView)
    DMWebVideoView mDMWebView;

    String videoId = "";
    boolean favouriteVideo = false;
    @Bind(R.id.adView)
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        mAdView.setVisibility(View.GONE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final String videoId = getIntent().getStringExtra("videoId");
        this.videoId = videoId;
        String videoTitle = getIntent().getStringExtra("videoTitle");

        getSupportActionBar().setTitle(videoTitle);


        mDMWebView.setAutoPlay(true);
        mDMWebView.setVideoId(videoId);
        try {
            favouriteVideo = false;//toggleVideoFavourite(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdView.setVisibility(View.VISIBLE);
            }
        });
        mAdView.loadAd(adRequest);
    }

    boolean toggleVideoFavourite(boolean act) throws Exception {
        List<Video> fvs = new Select().from(Video.class).where("vid = ?", videoId).execute();
        if (fvs.size() < 0) {
            throw new Exception("Video Not Found");
        } else {
            Video v = fvs.get(0);
            if (act) {
                v.isFavourite = !v.isFavourite;
                v.save();
            }
            return v.isFavourite;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mDMWebView.onPause();

        try {
            Class.forName("android.webkit.WebView")
                    .getMethod("onPause", (Class[]) null)
                    .invoke(mDMWebView, (Object[]) null);

        } catch (ClassNotFoundException cnfe) {
        } catch (NoSuchMethodException nsme) {
        } catch (InvocationTargetException ite) {
        } catch (IllegalAccessException iae) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.video, menu);
        //restoreActionBar();
        MenuItem favMenu = menu.findItem(R.id.fav_menu);
        boolean nowResult = false;
        try {
            nowResult = toggleVideoFavourite(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (nowResult)
            favMenu.setIcon(android.R.drawable.star_big_on);
        else
            favMenu.setIcon(android.R.drawable.star_big_off);
        return true;
    }

    void ToggleStar(final MenuItem menItem) {
        boolean nowResult = false;
        try {
            nowResult = toggleVideoFavourite(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (nowResult)
            menItem.setIcon(android.R.drawable.star_big_on);
        else
            menItem.setIcon(android.R.drawable.star_big_off);
        Snackbar.make(findViewById(android.R.id.content), nowResult ? "Video marked favourite" : "Removed from favourites", Snackbar.LENGTH_LONG)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToggleStar(menItem);
                    }
                })
                .setActionTextColor(Color.RED)
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.fav_menu:
                ToggleStar(item);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDMWebView.onResume();

    }

}
