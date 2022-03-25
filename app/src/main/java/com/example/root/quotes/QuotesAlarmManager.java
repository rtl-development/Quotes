package com.example.root.quotes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class QuotesAlarmManager
{
    private final Context context;

    public QuotesAlarmManager(Context context)
    {
        this.context = context;
    }

    private AlarmManager getAlarmManager()
    {
        return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    protected static PendingIntent getPendingIntent(Context context
            , Class<?> cls, final int requestCode, final int flag)
    {
        Intent intent = new Intent(context, cls);
        return (PendingIntent.getBroadcast(context, requestCode, intent, flag));
        // for more info about flags: https://developer.android.com/reference/android/app/PendingIntent.html#FLAG_UPDATE_CURRENT
    }

    public void createAlarmManager(final int alarmType,final long trigger,
                                   final long interval, PendingIntent pendingIntent)
    {
        if(getAlarmManager() != null)
            getAlarmManager().setInexactRepeating(alarmType, trigger, interval, pendingIntent);
    }

    // i think alarmManager needs to be passed as parameter
    public void cancelAlarmManager(PendingIntent pendingIntent)
    {
        if(getAlarmManager() != null && pendingIntent != null)
        {
            getAlarmManager().cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }

    // A pending intent that fires when the alarm is triggered.
    // When you set a second alarm that uses the same pending intent, it replaces the original alarm.
    /*public void updateAlarmManager(PendingIntent oldPendingIntent, final int newAlarmType,
                                      final long newTrigger, final long newInterval,
                                      PendingIntent newPendingIntent)
    {
        cancelAlarmManager(oldPendingIntent);
        createAlarmManager(newAlarmType, newTrigger, newInterval, newPendingIntent);
    }*/
}
