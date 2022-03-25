package com.example.root.quotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class SettingSharedPreferences
{
    private SharedPreferences sharedPreferences;
    private static volatile SettingSharedPreferences instance;
    private Context context;
    //protected static boolean isThereQuotes = false;

    private SettingSharedPreferences(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.registerOnSharedPreferenceChangeListener(prefChangeListener);
    }

    protected static synchronized SettingSharedPreferences getInstance(Context context) {
        if (instance == null)
            instance = new SettingSharedPreferences(context);

        return instance;
    }

    private void finishAllActivities()
    {
        Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Exit", true); // to use to MainActivity
        context.startActivity(intent);
    }

    private SharedPreferences.OnSharedPreferenceChangeListener prefChangeListener =
            new SharedPreferences.OnSharedPreferenceChangeListener()
            {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
                {
                    if(key.equals(SettingsFragment.CHOSEN_THEME))
                        finishAllActivities();
                }
            };

    protected void setCurrentTheme(int theme)
    {
        sharedPreferences.edit().putInt(SettingsFragment.CHOSEN_THEME, theme).apply();
    }

    protected int getCurrentTheme()
    {
        return sharedPreferences.getInt(SettingsFragment.CHOSEN_THEME, R.style.AppTheme1);
    }

}