package com.example.root.quotes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragmentCompat {
    // Changes to the preferences are committed to SharedPreferences automatically.

    //private SharedPreferences sharedPreferences;

    private SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener;

    private SentenceViewModel sentenceViewModel;

    private int sentencesCount;

    protected static final int ALARM_INTENT_REQUEST_CODE = 1;

    protected static final String RANDOM_NOTIFICATION_KEY = "prefRandomNotification";
    // Resources.getSystem().getString(R.string.pref_random_notification_key);
    protected static final String NUMBER_OF_RANDOM_NOTIFICATION_KEY = "prefNumberOfRandomNotification";

    protected static final String ADD_DEFAULT_QUOTES_KEY = "prefAddDefaultQuotes";

    protected static final String CHOSEN_THEME = "chosenTheme";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.mot_preferences, rootKey);

        if (getActivity() != null) {
            //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            sentenceViewModel = new ViewModelProvider(getActivity()).get(SentenceViewModel.class);
        }

        sentencesCount = sentenceViewModel.getSentencesCount();
/*
        // set default alarm for 1st time if there sentences
        if (sentencesCount > 0
                && sharedPreferences.getBoolean(RANDOM_NOTIFICATION_KEY, true)
                && !alarmIsSet(getActivity()))
        {
            sharedPreferences.edit().putString(NUMBER_OF_RANDOM_NOTIFICATION_KEY, "6").apply();
            setNotificationAlarmManager(6);
        }
        // there are no sentences and notification is on
        else if (sentencesCount == 0
                && sharedPreferences.getBoolean(RANDOM_NOTIFICATION_KEY, true))
        {
            sharedPreferences.edit().putBoolean(RANDOM_NOTIFICATION_KEY, false).apply();
            cancelNotificationAlarmManager();
        }*/

        sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                final int repeat = Integer.parseInt(sharedPreferences.getString(NUMBER_OF_RANDOM_NOTIFICATION_KEY, "6"));

                switch (s) {
                    case RANDOM_NOTIFICATION_KEY:
                        if (sentencesCount > 0 && sharedPreferences.getBoolean(RANDOM_NOTIFICATION_KEY, true))
                            setNotificationAlarmManager(repeat);
                        else
                            cancelNotificationAlarmManager();
                        break;

                    case NUMBER_OF_RANDOM_NOTIFICATION_KEY:
                        if (sentencesCount > 0 && sharedPreferences.getBoolean(RANDOM_NOTIFICATION_KEY, true))
                            setNotificationAlarmManager(repeat);
                        break;

                    case ADD_DEFAULT_QUOTES_KEY:
                        if (sentenceViewModel.getDfltSentencesCount() == 0
                                && sharedPreferences.getBoolean(ADD_DEFAULT_QUOTES_KEY, false))
                            sentenceViewModel.insertDefSentences(getContext());
                        else if (sentenceViewModel.getDfltSentencesCount() > 0
                                && !sharedPreferences.getBoolean(ADD_DEFAULT_QUOTES_KEY, false)) {
                            sentenceViewModel.deleteDfltSentences();
                            sharedPreferences.edit().putBoolean(RANDOM_NOTIFICATION_KEY, false).apply();
                        }
                        break;

                    case CHOSEN_THEME:
                        Intent intent = new Intent( getActivity(), ChooseTheme.class);
                        startActivity(intent);
                        break;
                }
            }
        };
    }


    private void setNotificationAlarmManager(int repeat) {
        QuotesAlarmManager alarmManager = new QuotesAlarmManager(getActivity());

        PendingIntent pendingIntent = QuotesAlarmManager.getPendingIntent(getActivity()
                , NotificationReceiver.class
                , ALARM_INTENT_REQUEST_CODE, PendingIntent.FLAG_IMMUTABLE);

        alarmManager.createAlarmManager(AlarmManager.ELAPSED_REALTIME_WAKEUP
                , SystemClock.elapsedRealtime()+AlarmManager.INTERVAL_FIFTEEN_MINUTES
                , repeat*AlarmManager.INTERVAL_HOUR
                , pendingIntent);
    }

    private void cancelNotificationAlarmManager() {
        QuotesAlarmManager quotesAlarmManager = new QuotesAlarmManager(getActivity());

        PendingIntent pendingIntent = QuotesAlarmManager.getPendingIntent(getActivity(), NotificationReceiver.class
                , ALARM_INTENT_REQUEST_CODE, PendingIntent.FLAG_IMMUTABLE);

        quotesAlarmManager.cancelAlarmManager(pendingIntent);
    }

    private boolean alarmIsSet(Context context)
    {
        return QuotesAlarmManager.getPendingIntent(context
                ,NotificationReceiver.class, ALARM_INTENT_REQUEST_CODE
                ,PendingIntent.FLAG_NO_CREATE) != null;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }
}
