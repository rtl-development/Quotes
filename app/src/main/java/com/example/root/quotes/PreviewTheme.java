package com.example.root.quotes;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class PreviewTheme extends Fragment
{
    private int chosenTheme;
    private Button applyThemeBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_preview_theme, container, false);

        if(getArguments() != null)
            chosenTheme = getArguments().getInt(SettingsFragment.CHOSEN_THEME, R.style.AppTheme1);

        View dynamicBg = view.findViewById(R.id.dynamic_bg);

        applyThemeBtn = view.findViewById(R.id.apply_theme_btn);
        applyThemeBtn.setOnClickListener(applyThemeListener);

        switch (chosenTheme)
        {
            case R.style.AppTheme1:
                TemporaryChangeViewStyle(dynamicBg
                        , Utility.getColorAccordingToAPILevel(getActivity(), R.color.colorPrimary)
                        , R.drawable.gradiant1
                        , Utility.getColorAccordingToAPILevel(getActivity(), R.color.buttonsColor)
                        , Utility.getColorAccordingToAPILevel(getActivity(), R.color.colorPrimaryDark));
                break;

            case R.style.AppTheme2:
                TemporaryChangeViewStyle(dynamicBg
                        , Utility.getColorAccordingToAPILevel(getActivity(), R.color.colorPrimary2)
                        , R.drawable.gradiant2
                        , Utility.getColorAccordingToAPILevel(getActivity(), R.color.buttonsColor_2)
                        , Utility.getColorAccordingToAPILevel(getActivity(), R.color.colorPrimaryDark2));
                break;

            case R.style.AppTheme3:
                TemporaryChangeViewStyle(dynamicBg
                        , Utility.getColorAccordingToAPILevel(getActivity(), R.color.colorPrimary3)
                        , R.drawable.gradiant3
                        , Utility.getColorAccordingToAPILevel(getActivity(), R.color.buttonsColor_3)
                        , Utility.getColorAccordingToAPILevel(getActivity(), R.color.colorPrimaryDark3));
                break;

            case R.style.AppTheme4:
                TemporaryChangeViewStyle(dynamicBg
                        , Utility.getColorAccordingToAPILevel(getActivity(), R.color.colorPrimary4)
                        , R.drawable.gradiant4
                        , Utility.getColorAccordingToAPILevel(getActivity(), R.color.buttonsColor_4)
                        , Utility.getColorAccordingToAPILevel(getActivity(), R.color.colorPrimaryDark4));
                break;

            case R.style.AppTheme5:
                TemporaryChangeViewStyle(dynamicBg
                        , Utility.getColorAccordingToAPILevel(getActivity(), R.color.colorPrimary5)
                        , R.drawable.gradiant5
                        , Utility.getColorAccordingToAPILevel(getActivity(), R.color.buttonsColor_5)
                        , Utility.getColorAccordingToAPILevel(getActivity(), R.color.colorPrimaryDark5));
                break;

            case R.style.AppTheme6:
                TemporaryChangeViewStyle(dynamicBg
                        , Utility.getColorAccordingToAPILevel(getActivity(), R.color.colorPrimary6)
                        , R.drawable.gradiant6
                        , Utility.getColorAccordingToAPILevel(getActivity(), R.color.buttonsColor_6)
                        , Utility.getColorAccordingToAPILevel(getActivity(), R.color.colorPrimaryDark6));
                break;
        }

        return view;
    }

    private View.OnClickListener applyThemeListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            SettingSharedPreferences pref = SettingSharedPreferences.getInstance(getActivity());
            pref.setCurrentTheme(chosenTheme);

            getActivity().finish();
        }
    };

    private void TemporaryChangeViewStyle(View dynamicBg, int actionBarColor
            , int bgColor, int buttonColor, int statusBarColor)
    {
        dynamicBg.setBackgroundResource(bgColor);
        applyThemeBtn.setBackgroundColor(buttonColor);

        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setBackgroundDrawable(new ColorDrawable(actionBarColor));

        //getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getActivity().getWindow().setStatusBarColor(statusBarColor);
    }

    @Override
    public void onPause() {
        super.onPause();

        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setBackgroundDrawable(
                        new ColorDrawable(Utility.getColorAccordingToAPILevel(getActivity(), R.color.themeColorPrimary)));

        getActivity().getWindow().setStatusBarColor(
                Utility.getColorAccordingToAPILevel(getActivity(), R.color.themeColorPrimaryDark));

    }
}