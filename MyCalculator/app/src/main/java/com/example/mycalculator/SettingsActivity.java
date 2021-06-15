package com.example.mycalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import static com.example.mycalculator.Constants.KEY_NIGHT_THEME;
import static com.example.mycalculator.Constants.MY_SHARED_PREFERENCES;

public class SettingsActivity extends AppCompatActivity {

    private SwitchCompat switchTheme;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);

        setContentView(R.layout.activity_settings);
        switchTheme = findViewById(R.id.change_theme);

        checkNightModeActivated();

        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                saveNightModeState(true);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                saveNightModeState(false);
            }
            recreate();
        });
    }

    public void checkNightModeActivated() {
        if (preferences.getBoolean(KEY_NIGHT_THEME, false)) {
            switchTheme.setChecked(true);
            switchTheme.setText(R.string.night_theme_text);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            switchTheme.setChecked(false);
            switchTheme.setText(R.string.light_theme_text);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void saveNightModeState(boolean nightMode) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_NIGHT_THEME, nightMode).apply();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_NIGHT_THEME, switchTheme.isChecked());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        switchTheme.setChecked(savedInstanceState.getBoolean(KEY_NIGHT_THEME));
    }

}