package com.xoredge.tariqjameelbayans;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.machinarius.preferencefragment.PreferenceFragment;

import java.util.Calendar;

public class SettingsFragment extends PreferenceFragment {

    public static final String Daily_ACTION = "com.xoredge.tariqjameelbayans.dailyAlarm";
    public static final String Weekly_ACTION = "com.xoredge.tariqjameelbayans.weeklyAlarm";
    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        addPreferencesFromResource(R.xml.preferences);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        sp.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equalsIgnoreCase("pref_key_notif_daily")) {
                    if (sharedPreferences.getBoolean(key, false))
                        setDailyNotification();
                    else
                        cancelDailyNotification();
                } else if (key.equalsIgnoreCase("pref_key_notif_weekly")) {
                    if (sharedPreferences.getBoolean(key, false))
                        setWeeklyNotification();
                    else
                        cancelWeeklyNotification();
                }
                Log.d("Key", key);
            }
        });
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void cancelDailyNotification() {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0,
                new Intent(Daily_ACTION),
                PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }
    public void cancelWeeklyNotification() {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0,
                new Intent(Daily_ACTION),
                PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

    public void setDailyNotification() {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0,
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
    public void setWeeklyNotification() {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1,
                new Intent(Weekly_ACTION),
                PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        // set the triggered time to currentHour:08:00 for testing
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 7);
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        //====================================
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY*7, pendingIntent);
        Log.d("Alarm..", "set at " + calendar.toString());
    }


}
