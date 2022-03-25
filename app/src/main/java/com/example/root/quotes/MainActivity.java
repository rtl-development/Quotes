package com.example.root.quotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private SearchView searchView;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // int currentTheme = PreferenceManager.getDefaultSharedPreferences(this).getInt(SettingsFragment.CHOSEN_THEME, R.style.AppTheme1);
        setTheme(SettingSharedPreferences.getInstance(this).getCurrentTheme());

        if(getIntent().getBooleanExtra("Exit", false))
        {
            startActivity(new Intent(this, MainActivity.class));
            this.finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment = new SentencesFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

        /*FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(fabListener);*/
    }


    /*View.OnClickListener fabListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (fragment != null) {
                Intent intent = new Intent(MainActivity.this
                        , AddEditSentenceActivity.class);
                startActivityForResult(intent, ADD_SENTENCE_REQ);
            }
        }
    };*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.app_menu, menu);

        //=============================
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (fragment != null)
                    ((SentencesFragment) fragment).getSentenceAdapter().getFilter().filter(newText);

                return false;
            }
        });
        //=============================

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
