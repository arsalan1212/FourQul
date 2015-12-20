package com.xoredge.tariqjameelbayans;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.activeandroid.query.Select;
import com.xoredge.tariqjameelbayans.models.Video;

import java.util.List;
import java.util.Random;

/**
 * Created by Ahmad Ali on 12/3/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {
    public static final String Daily_ACTION = "com.xoredge.tariqjameelbayans.dailyAlarm";
    public static final String Weekly_ACTION = "com.xoredge.tariqjameelbayans.weeklyAlarm";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Daily_ACTION)) {
            showNotification(context);
        }
        if (intent.getAction().equals(Weekly_ACTION)) {
            showNotification(context);
        }
    }
    private void showNotification(Context context) {

        Uri soundUri = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent dailyBayyanIntent =  new Intent(context,
                Main2Activity.class);
        List<Video> vids = new Select().from(Video.class).execute();
        int randomVidIndex = new Random().nextInt(((vids.size()-1) - 0) + 1) + 0;;
        Video last =  vids.get(randomVidIndex);
        dailyBayyanIntent.putExtra("videoId",last.vid);
        dailyBayyanIntent.putExtra("videoTitle",last.title );
        dailyBayyanIntent.setAction("Play_Bayyan");
        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle("Daily Bayyan")
                .setContentText(last.title)
                .setContentIntent(
                        PendingIntent.getActivity(context, 0, dailyBayyanIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT))
                .setSound(soundUri).setSmallIcon(R.drawable.tariq_jameel)
                .build();
        NotificationManagerCompat.from(context).notify(0, notification);
    }
}
