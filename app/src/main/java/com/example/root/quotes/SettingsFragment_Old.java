package com.example.root.quotes;

import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.TwoStatePreference;

public class SettingsFragment_Old /*extends PreferenceFragmentCompat*/ {

    /*private SharedPreferences.OnSharedPreferenceChangeListener preferencesListener;

    private SentenceViewModel sentenceViewModel;

    protected static final String RANDOM_NOTIFICATION_KEY = "prefRandomNotification";
    // Resources.getSystem().getString(R.string.pref_random_notification_key);
    protected static final String NUMBER_OF_RANDOM_NOTIFICATION_KEY = "prefNumberOfRandomNotification";

    protected static final String ADD_DEFAULT_QUOTES_KEY = "prefAddDefaultQuotes";

    protected static final String CHOSEN_THEME = "chosenTheme";


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
    {
        setPreferencesFromResource(R.xml.mot_preferences, rootKey);

        if(PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(NUMBER_OF_RANDOM_NOTIFICATION_KEY, "6").isEmpty() &&
                PreferenceManager.getDefaultSharedPreferences(getActivity())
                        .getBoolean(RANDOM_NOTIFICATION_KEY, true) &&
                sentenceViewModel.getSentencesCount() > 0)
        {
            setNotificationAlarmManager(6, getActivity());
        }

        sentenceViewModel = new ViewModelProvider(getActivity()).get(SentenceViewModel.class);

        preferencesListener = new SharedPreferences.OnSharedPreferenceChangeListener()
        {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                // all the registered observers will be notified that there has been a change
                // to the shared preferences (including your own!)

                if(key.equals(RANDOM_NOTIFICATION_KEY))
                {
                    if(sharedPreferences.getBoolean(RANDOM_NOTIFICATION_KEY, true) &&
                            sentenceViewModel.getSentencesCount() > 0)
                    {
                        int repeat = Integer.parseInt(sharedPreferences.getString(
                                NUMBER_OF_RANDOM_NOTIFICATION_KEY, "6"));
                        setNotificationAlarmManager(repeat, getActivity());
                    }
                    else if(!sharedPreferences.getBoolean(RANDOM_NOTIFICATION_KEY, true))
                    {
                        int repeat = Integer.parseInt(sharedPreferences.getString(
                                NUMBER_OF_RANDOM_NOTIFICATION_KEY, "6"));
                        cancelNotificationAlarmManager(repeat, getActivity());
                    }
                }
                else if(key.equals(NUMBER_OF_RANDOM_NOTIFICATION_KEY))
                {
                    int repeat = Integer.parseInt(sharedPreferences.getString(
                            NUMBER_OF_RANDOM_NOTIFICATION_KEY, "6"));
                    updateNotificationAlarmManager(repeat , getActivity());
                }
                else if(key.equals(ADD_DEFAULT_QUOTES_KEY))
                {
                    if(sharedPreferences.getBoolean(ADD_DEFAULT_QUOTES_KEY, false)
                            && sentenceViewModel.getDfltSentencesCount() == 0)
                    {
                        // why getContext() but not getActivity?
                        sentenceViewModel.insertDefSentences(getContext());
                    }
                    else if(!sharedPreferences.getBoolean(ADD_DEFAULT_QUOTES_KEY, false)
                            && sentenceViewModel.getDfltSentencesCount() > 0)
                    {
                        sentenceViewModel.deleteDfltSentences();
                    }
                }
            }
        };
    }

    private void setNotificationAlarmManager(int repeat, Context context)
    {
        NotificationAlarmManager.getInstance(repeat, context);
        //alarmManager.createAlarmManager(repeat, context);
    }

    private void cancelNotificationAlarmManager(int repeat, Context context)
    {
        NotificationAlarmManager notificationAlarmManager =
                NotificationAlarmManager.getInstance(repeat, context);

        PendingIntent pendingIntent = notificationAlarmManager.getPendingIntent(getActivity());

        notificationAlarmManager.cancelAlarmManager(pendingIntent);
    }

    private void updateNotificationAlarmManager(int repeat, Context context)
    {
        NotificationAlarmManager notificationAlarmManager =
                NotificationAlarmManager.getInstance(repeat, context);

        PendingIntent pendingIntent = notificationAlarmManager.getPendingIntent(getActivity());

        notificationAlarmManager.updateAlarmManager(repeat, context, pendingIntent);
    }

    // to review later:
    public void changeAddDefaultSentencesState(boolean state)
    {
        if(state != ((TwoStatePreference) SettingsFragment_Old.this.findPreference(ADD_DEFAULT_QUOTES_KEY))
                .isChecked())
            ((TwoStatePreference) SettingsFragment_Old.this.findPreference(ADD_DEFAULT_QUOTES_KEY))
                    .setChecked(state);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(preferencesListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(preferencesListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(preferencesListener);
    }*/
}