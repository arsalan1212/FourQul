package com.FourQul;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.guidedkeys.main.R;
import com.xoredge.main.AboutusFragment;
import com.xoredge.main.AnalyticSingalton;
import com.xoredge.main.Main2Activity;
import com.xoredge.main.SettingsFragment;

public class FourQulActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SettingsFragment.OnFragmentInteractionListener{

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    public static int idDrawerSelected = R.id.all_qul;
    FrameLayout frameLayout;
    LinearLayout linearLayout_content;
    AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_qul);

        toolbar = findViewById(R.id.toolbar_four_qual);

        frameLayout = findViewById(R.id.container2);
        linearLayout_content = findViewById(R.id.layout_content);

        adView = findViewById(R.id.adView_qul);

        MobileAds.initialize(this);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.setAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                adView.setVisibility(View.GONE);
                Toast.makeText(FourQulActivity.this, "Ad ErrorCode: "+errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adView.setVisibility(View.VISIBLE);
            }
        });
        adView.loadAd(adRequest);


       drawerLayout = findViewById(R.id.drawer_four_qual_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        navigationView = findViewById(R.id.four_qual_nav_view);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setCheckedItem(idDrawerSelected);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        visibleContentLayout();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        visibleContentLayout();
    }

    //each qual click listiner
    public void GoToSingleQual(View view){


        int index=-1;
        switch (view.getId()){

            case R.id.tv_al_kafirun:
                index =0;
                break;

            case R.id.tv_al_ikhlas:

                index =1;
                break;


            case R.id.tv_al_falaq:

                index = 2;
                break;

            case R.id.tv_al_nas:

                index =3;

                break;
        }

        Intent intent = new Intent(this,SingleQulActivity.class);
        intent.putExtra("index",index);
        startActivity(intent);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_four_qual_layout);
        Fragment placeHolder = null;
        int id = item.getItemId();

        //Used to prevent Multiple Back Stacking for Fragments
        if (idDrawerSelected == id) {
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        if (id == R.id.nav_home) {

            Intent intent = new Intent(this, Main2Activity.class);
            startActivity(intent);

            drawer.closeDrawer(GravityCompat.START);

            visibleFrameLayout();

            return true;
        }

        else if(id==R.id.all_qul){

            Intent intent= new Intent(this, FourQulActivity.class);
            startActivity(intent);
            visibleContentLayout();
            idDrawerSelected = id;

        }

        else if (id == R.id.nav_settings) {
            idDrawerSelected = id;

            visibleFrameLayout();

            AnalyticSingalton.getInstance(this).sendEventAnalytics(AnalyticSingalton.DRAWER_CATEGORY, AnalyticSingalton.SETTINGS_EVENT);
            placeHolder = (Fragment) SettingsFragment.newInstance();

        } else if (id == R.id.nav_aboutus) {
            idDrawerSelected = id;

            visibleFrameLayout();

            AnalyticSingalton.getInstance(this).sendEventAnalytics(AnalyticSingalton.DRAWER_CATEGORY, AnalyticSingalton.ABOUT_US_EVENT);
            placeHolder = (Fragment) AboutusFragment.newInstance();
        } else if (id == R.id.nav_share) {
            AnalyticSingalton.getInstance(this).sendEventAnalytics(AnalyticSingalton.DRAWER_CATEGORY, AnalyticSingalton.SHARE_APP_EVENT);
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT,
                    getString(R.string.app_name));
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    getString(R.string.share_message) + getPackageName());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            drawer.closeDrawer(GravityCompat.START);

            visibleContentLayout();

            return true;
        } else if (id == R.id.nav_rateus) {
            AnalyticSingalton.getInstance(this).sendEventAnalytics(AnalyticSingalton.DRAWER_CATEGORY, AnalyticSingalton.RATE_US_EVENT);
            openAppLink();
            drawer.closeDrawer(GravityCompat.START);

            visibleContentLayout();

            return true;
        }else if (id == R.id.nav_more_apps) {
            AnalyticSingalton.getInstance(this).sendEventAnalytics(AnalyticSingalton.DRAWER_CATEGORY, AnalyticSingalton.MORE_APPS_EVENT);
            moreApps();
            drawer.closeDrawer(GravityCompat.START);

            visibleContentLayout();

            return true;
        } else if (id == R.id.nav_update_check) {
            AnalyticSingalton.getInstance(this).sendEventAnalytics(AnalyticSingalton.DRAWER_CATEGORY, AnalyticSingalton.UPADTES_EVENT);
            openAppLink();
            drawer.closeDrawer(GravityCompat.START);

            visibleContentLayout();

            return true;
        }

        if(getSupportFragmentManager().findFragmentById(R.id.container2)!=null){

            getSupportFragmentManager().popBackStack();

        }

        if(placeHolder!=null){

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container2, placeHolder).addToBackStack(null)
                    .commit();
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {

        try{

            if(drawerLayout.isDrawerOpen(GravityCompat.START)){

                drawerLayout.closeDrawer(GravityCompat.START);
            }
            else{
                visibleContentLayout();
                super.onBackPressed();
            }


        }
        catch (Exception e){}

    }

    private void openAppLink() {
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
                    Uri.parse("https://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }
    }

    private void moreApps() {
        String moreAppsLink = "market://search?q=pub:Guided+Keys";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(moreAppsLink));
        startActivity(intent);

    }

    private void visibleContentLayout(){

        linearLayout_content.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.GONE);
    }

    private void visibleFrameLayout(){

        linearLayout_content.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
