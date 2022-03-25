package com.example.root.quotes;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(SettingSharedPreferences.getInstance(this).getCurrentTheme());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (findViewById(R.id.settings_container) != null) {
            if (savedInstanceState != null)
                return;

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings_container, new SettingsFragment())
                    .commit();
        }
    }
}
