package com.FourQul;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guidedkeys.main.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AudioArabicFragment extends Fragment {


    int index=-1;
    String[] qualArabicArray;
    LinearLayout layout_arabicText;

    public AudioArabicFragment() {
        // Required empty public constructor
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            index = intent.getIntExtra("index",-1);
            ChangeViewsData();
            getAndSetQulArabicText();
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view= inflater.inflate(R.layout.fragment_audio_arabic, container, false);

         layout_arabicText = view.findViewById(R.id.layout_arabic_text);

         index = SingleQulActivity.index;

         qualArabicArray = getResources().getStringArray(R.array.qul_arabic_text);
         getAndSetQulArabicText();

         return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().registerReceiver(broadcastReceiver,new IntentFilter("com.arsalan.qul.broadcast"));

    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    //changing the views data
    private void ChangeViewsData() {
    }


    //getting data and generate view dynamically
    private void getAndSetQulArabicText(){


        String singleQualArray[]  = qualArabicArray[index].split(",");

        for(int i=singleQualArray.length-1; i>=0; i--){

            TextView textViewArabicText = new TextView(getContext());

            textViewArabicText.setText(singleQualArray[i]);

            textViewArabicText.setGravity(Gravity.CENTER);
            textViewArabicText.setTextColor(Color.BLACK);
            textViewArabicText.setTextSize(25);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textViewArabicText.setLayoutParams(params);


            //for separator
            View separator = new View(getContext());
            separator.setBackgroundColor(Color.parseColor("#8e8e8e"));
            LinearLayout.LayoutParams separator_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            separator_params.setMargins(0, 20, 0, 20);
            separator.setLayoutParams(separator_params);

            layout_arabicText.addView(textViewArabicText);
            layout_arabicText.addView(separator);
        }
    }


}
