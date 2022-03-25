package com.example.root.quotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class ThemesActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themes);

        getSupportActionBar().setTitle(R.string.themes);

        //Toolbar toolbar = findViewById(R.id.app_bar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle(R.string.themes);
        //getSupportActionBar().setBackgroundDrawable
                //(new ColorDrawable(getResources().getColor(R.color.themeColorPrimary)));

        Fragment fragment = new ChooseTheme();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.themes_activity_container, fragment)
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }

    @Override
    public void onBackPressed()
    {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1)
            finish();
        else
            super.onBackPressed();
    }
}
