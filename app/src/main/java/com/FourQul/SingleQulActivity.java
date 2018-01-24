package com.FourQul;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.guidedkeys.main.R;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SingleQulActivity extends AppCompatActivity {

    public static int index =-1;
    LinearLayout layout_tab1, layout_tab2;
    AdView adView;
    TextView tvName,tvQulNameTransalation,tvTranslationEnglishOrUrdu,tvQulTitle;
    AudioArabicFragment audioArabicFragment;
    TransalationFragment transalationFragment;
    String[] qulNamesArray,qulNameEnglishMeaning;
    ImageButton imageButtonBack,imageButtonNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_qul);

        index = getIntent().getIntExtra("index",-1);

        qulNameEnglishMeaning = getResources().getStringArray(R.array.qul_name_enlish_translatin);
        qulNamesArray = getResources().getStringArray(R.array.qul_names);


        tvName = findViewById(R.id.tv_qul_nm);
        tvQulNameTransalation = findViewById(R.id.tv_qul_translation);
        tvTranslationEnglishOrUrdu = findViewById(R.id.tv_translation);
        tvQulTitle = findViewById(R.id.tv_qul_title);
        imageButtonBack = findViewById(R.id.img_btn_back);
        imageButtonNext = findViewById(R.id.img_btn_next);

        audioArabicFragment = new AudioArabicFragment();
        transalationFragment = new TransalationFragment();

        layout_tab1 = findViewById(R.id.layout_tab1);
        layout_tab2 = findViewById(R.id.layout_tab2);

        //By Default Tab1 is selected
        layout_tab1.setBackgroundColor(Color.parseColor("#082A00"));
        layout_tab2.setBackgroundColor(Color.parseColor("#136101"));

        tvName.setTypeface(null, Typeface.BOLD);
        tvTranslationEnglishOrUrdu.setTypeface(null, Typeface.NORMAL);


        tvName.setText(qulNamesArray[index]);
        tvQulTitle.setText(qulNamesArray[index]);
        tvQulNameTransalation.setText(qulNameEnglishMeaning[index]);


        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(index > 0){

                    index--;
                }else{
                    index =0;
                }
                //sending broadcast to kalma audio fragment
                Intent intent = new Intent();
                intent.putExtra("index",index);
                intent.setAction("com.arsalan.qul.broadcast");
                sendBroadcast(intent);

                setResourceThroughIndex();
            }
        });

        imageButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index < qulNamesArray.length-1){
                    index++;
                }else{
                    index =3;
                }


                //sending broadcast to kalma audio fragment
                Intent intent = new Intent();
                intent.putExtra("index",index);
                intent.setAction("com.arsalan.qul.broadcast");
                sendBroadcast(intent);
                setResourceThroughIndex();
            }
        });

        //adding fragment to container
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,audioArabicFragment)
        .commit();

        adView = findViewById(R.id.adView_player);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                adView.setVisibility(View.GONE);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

                adView.setVisibility(View.VISIBLE);
            }
        });
    }

    //setting data to views
    private void setResourceThroughIndex() {

        tvName.setText(qulNamesArray[index]);
        tvQulTitle.setText(qulNamesArray[index]);
        tvQulNameTransalation.setText(qulNameEnglishMeaning[index]);
    }


    //tab listiner
    public void TabLayoutListiner(View view) {

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if(view.getId() == R.id.layout_tab1){

            layout_tab1.setBackgroundColor(Color.parseColor("#082A00"));
            layout_tab2.setBackgroundColor(Color.parseColor("#136101"));

            tvName.setTypeface(null, Typeface.BOLD);
            tvTranslationEnglishOrUrdu.setTypeface(null, Typeface.NORMAL);

            fragmentTransaction.replace(R.id.fragment_container,audioArabicFragment);

        }
        else if(view.getId() == R.id.layout_tab2){

            layout_tab1.setBackgroundColor(Color.parseColor("#136101"));
            layout_tab2.setBackgroundColor(Color.parseColor("#082A00"));

            tvName.setTypeface(null, Typeface.NORMAL);
            tvTranslationEnglishOrUrdu.setTypeface(null, Typeface.BOLD);

            fragmentTransaction.replace(R.id.fragment_container,transalationFragment);
        }
        fragmentTransaction.commit();
    }



}
