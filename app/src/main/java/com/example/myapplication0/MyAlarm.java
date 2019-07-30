package com.example.myapplication0;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MyAlarm extends BroadcastReceiver {
    Ringtone ringtone;
    @Override
    public void onReceive(Context context, Intent intent) {
//        ChildVaccine VInst = ChildVaccine.instantiate();
        MediaPlayer mediaPlayer =   MediaPlayer.create(context, Settings.System.DEFAULT_NOTIFICATION_URI);
        mediaPlayer.start();


    }
}
