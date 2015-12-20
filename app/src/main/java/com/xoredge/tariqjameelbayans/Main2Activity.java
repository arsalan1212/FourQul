package com.xoredge.tariqjameelbayans;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.otto.Subscribe;

import java.util.Calendar;

import Utils.BusProvider;
import butterknife.Bind;
import butterknife.ButterKnife;
import events.EventServingVids;
import events.ShowProgressBar;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SettingsFragment.OnFragmentInteractionListener {

    SharedPreferences mPrefs;
    InterstitialAd mInterstitialAd;
    final String welcomeScreenShownPref = "welcomeScreenShown";
    @Bind(R.id.progress_wheel)
    ProgressWheel progress_wheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4587822745173962/5969816534");

        requestNewInterstitial();

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.vid.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (getIntent().getAction().equalsIgnoreCase("Play_Bayyan")) {
            handleIntent(getIntent());
            return;
        }
        handleIntent(getIntent());
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, DashboardFragment.newInstance(false))
                .commit();

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // second argument is the default to use if the preference can't be found
        Boolean welcomeScreenShown = mPrefs.getBoolean(welcomeScreenShownPref, false);

        if (!welcomeScreenShown) {
            // here you can launch another activity if you like
            // the code below will display a popup

            String whatsNewTitle = "Content Disclaimer";
            String whatsNewText = "The content provided in this application is not hosted by us, we don’t own or claim it. The content is available free on internet and it is the copy right of the respective owners. We just provide an organized way to explore it.";
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(whatsNewTitle).setMessage(whatsNewText).setPositiveButton(
                    "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            setDailyNotification();
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putBoolean(welcomeScreenShownPref, true);
            editor.commit(); // Very important to save the preference
        }
    }
    public static final String Daily_ACTION = "com.xoredge.tariqjameelbayans.dailyAlarm";
    public void setDailyNotification() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                new Intent(Daily_ACTION),
                PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        // set the triggered time to currentHour:08:00 for testing
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 21);
        //====================================
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Log.d("Alarm..", "set at " + calendar.toString());
    }
    @Subscribe
    public void OnEventServingVids(EventServingVids esv) {
        Log.d("Progress bar", "Stopp");
        progress_wheel.stopSpinning();
        progress_wheel.setVisibility(View.GONE);
    }
    @Subscribe
    public void ShowProgressBar(ShowProgressBar spb){
        progress_wheel.setVisibility(View.VISIBLE);
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
        if (intent.getAction().equalsIgnoreCase("Play_Bayyan")) {
            Intent videoIntent = new Intent(this, VideoActivity.class);
            videoIntent.putExtra("videoId", intent.getStringExtra("videoId"));
            videoIntent.putExtra("videoTitle", intent.getStringExtra("videoTitle"));
            this.startActivity(videoIntent);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //restoreActionBar();
        // Associate searchable configuration with the SearchView
       /* SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem mi = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) mi.getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));*/

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment placeHolder = null;
        int id = item.getItemId();

        if (id == R.id.nav_favs) {
            placeHolder = (Fragment) DashboardFragment.newInstance(true);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            placeHolder = (Fragment) DashboardFragment.newInstance(false);
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        } else if (id == R.id.nav_settings) {
            placeHolder = (Fragment) SettingsFragment.newInstance();

        } else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "“Download the Beautiful Application of Molana Tariq Jameel Bayans – Free, Download Now!” at: https://play.google.com/store/apps/details?vid=com.xoredge.tariqjameelbayanas");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            return true;
        } else if (id == R.id.nav_aboutus) {
            placeHolder = (Fragment) AboutusFragment.newInstance();
        }
        else if(id==R.id.nav_rateus){
            Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
            }
            return true;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, placeHolder)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
