package com.example.root.quotes;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class ChooseTheme extends Fragment {

    private ImageButton button1;
    private ImageButton button2;
    private ImageButton button3;
    private ImageButton button4;
    private ImageButton button5;
    private ImageButton button6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_choose_theme, container, false);

        int currentTheme = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getInt(SettingsFragment.CHOSEN_THEME, R.style.AppTheme1);

        button1 = view.findViewById(R.id.theme1_btn);
        button1.setOnClickListener(btn1ClickListener);

        button2 = view.findViewById(R.id.theme2_btn);
        button2.setOnClickListener(btn2ClickListener);

        button3 = view.findViewById(R.id.theme3_btn);
        button3.setOnClickListener(btn3ClickListener);

        button4 = view.findViewById(R.id.theme4_btn);
        button4.setOnClickListener(btn4ClickListener);

        button5 = view.findViewById(R.id.theme5_btn);
        button5.setOnClickListener(btn5ClickListener);

        button6 = view.findViewById(R.id.theme6_btn);
        button6.setOnClickListener(btn6ClickListener);

        setBorderToCurrentBtn(currentTheme);

        return view;
    }

    private void setBorderToCurrentBtn(int themeId)
    {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setStroke
                (8, Utility.getColorAccordingToAPILevel(getActivity(), R.color.themeColorBackground));
                // same color of bg of themes activity

        switch (themeId)
        {
            case R.style.AppTheme1:
                button1.setPadding(20,20,20,20);
                button1.setImageDrawable(gradientDrawable);
                break;

            case R.style.AppTheme2:
                button2.setPadding(20,20,20,20);
                button2.setImageDrawable(gradientDrawable);
                break;

            case R.style.AppTheme3:
                button3.setPadding(20,20,20,20);
                button3.setImageDrawable(gradientDrawable);
                break;

            case R.style.AppTheme4:
                button4.setPadding(20,20,20,20);
                button4.setImageDrawable(gradientDrawable);
                break;

            case R.style.AppTheme5:
                button5.setPadding(20,20,20,20);
                button5.setImageDrawable(gradientDrawable);
                break;

            case R.style.AppTheme6:
                button6.setPadding(20,20,20,20);
                button6.setImageDrawable(gradientDrawable);
                break;
        }
    }

    private void previewChosenTheme(int btnId)
    {
        int chosenTheme;
        switch (btnId)
        {
            default:
            case R.id.theme1_btn:
                chosenTheme = R.style.AppTheme1;
                break;

            case R.id.theme2_btn:
                chosenTheme = R.style.AppTheme2;
                break;

            case R.id.theme3_btn:
                chosenTheme = R.style.AppTheme3;
                break;

            case R.id.theme4_btn:
                chosenTheme = R.style.AppTheme4;
                break;

            case R.id.theme5_btn:
                chosenTheme = R.style.AppTheme5;
                break;

            case R.id.theme6_btn:
                chosenTheme = R.style.AppTheme6;
                break;
        }

        Fragment previewTheme = new PreviewTheme();
        Bundle bundle = new Bundle();
        bundle.putInt(SettingsFragment.CHOSEN_THEME, chosenTheme);
        previewTheme.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.themes_activity_container, previewTheme)
                //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(previewTheme.getClass().getName())
                .commit();
    }

    private View.OnClickListener btn1ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {
            previewChosenTheme(view.getId());
        }
    };

    private View.OnClickListener btn2ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {
            previewChosenTheme(view.getId());
        }
    };

    private View.OnClickListener btn3ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {
            previewChosenTheme(view.getId());
        }
    };

    private View.OnClickListener btn4ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {
            previewChosenTheme(view.getId());
        }
    };

    private View.OnClickListener btn5ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {
            previewChosenTheme(view.getId());
        }
    };

    private View.OnClickListener btn6ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {
            previewChosenTheme(view.getId());
        }
    };
}