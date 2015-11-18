package com.xoredge.tariqjameelbayans;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.lang.reflect.InvocationTargetException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VideoActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Bind(R.id.dmWebVideoView)
    DMWebVideoView mDMWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);


        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String videoId = getIntent().getStringExtra("videoId");
        String videoTitle = getIntent().getStringExtra("videoTitle");

        getSupportActionBar().setTitle(videoTitle);


        mDMWebView.setAutoPlay(true);
        mDMWebView.setVideoId(videoId);
    }

    @Override
    public void onPause() {
        super.onPause();

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
