package com.example.root.quotes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;

import androidx.preference.PreferenceManager;

import static com.example.root.quotes.SettingsFragment.NUMBER_OF_RANDOM_NOTIFICATION_KEY;

public class RescheduleAlarms extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if(intent.getAction() != null &&
        intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            rescheduleAlarm(context, intent);
        }

    }

    private void rescheduleAlarm(Context context, Intent intent)
    {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        if(pref.getBoolean(SettingsFragment.RANDOM_NOTIFICATION_KEY, true))
        {
            int repeat = Integer.parseInt(pref.getString(NUMBER_OF_RANDOM_NOTIFICATION_KEY, "6"));

            QuotesAlarmManager quotesAlarmManager = new QuotesAlarmManager(context);
            quotesAlarmManager.createAlarmManager(AlarmManager.ELAPSED_REALTIME_WAKEUP
                    , SystemClock.elapsedRealtime()+AlarmManager.INTERVAL_FIFTEEN_MINUTES
                    , repeat*AlarmManager.INTERVAL_HOUR
                    , QuotesAlarmManager.getPendingIntent
                            (context, NotificationReceiver.class
                                    , SettingsFragment.ALARM_INTENT_REQUEST_CODE
                                    , PendingIntent.FLAG_UPDATE_CURRENT));
        }
    }
}