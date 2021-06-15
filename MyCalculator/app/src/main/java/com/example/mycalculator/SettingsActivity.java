package com.example.mycalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import static com.example.mycalculator.Constants.KEY_DARK_THEME;
import static com.example.mycalculator.Constants.MY_SHARED_PREFERENCES;

public class SettingsActivity extends AppCompatActivity {

    private SwitchCompat switchTheme;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        setTheme(preferences.getBoolean(KEY_DARK_THEME,false) ? R.style.AppThemeDark : R.style.AppThemeLight);
        setContentView(R.layout.activity_settings);
        switchTheme = findViewById(R.id.change_theme);

        checkNightModeActivated();

        initSwitchTheme();
    }

    public void checkNightModeActivated() {
        if (preferences.getBoolean(KEY_DARK_THEME,false)) {
            switchTheme.setChecked(true);
            switchTheme.setText("Dark Theme");
        } else {
            switchTheme.setChecked(false);
            switchTheme.setText("Light Theme");
        }
    }

    private void initSwitchTheme() {
        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor preferencesEditor = preferences.edit();
            preferencesEditor.putBoolean("USE_DARK_THEME", !preferences.getBoolean("USE_DARK_THEME", false));
            if (isChecked) {
                switchTheme.setText("Dark Theme");
                preferencesEditor.putBoolean("USE_DARK_THEME", true);
            } else {
                switchTheme.setText("Light Theme");
                preferencesEditor.putBoolean("USE_DARK_THEME", false);
            }
            preferencesEditor.apply();
            recreate();
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_DARK_THEME, switchTheme.isChecked());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        switchTheme.setChecked(savedInstanceState.getBoolean(KEY_DARK_THEME));
    }
//
//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent();
//        intent.putExtra(Constants.KEY_DARK_THEME, preferences.getBoolean(KEY_DARK_THEME, false));
//        setResult(RESULT_OK, intent);
//        finish();
//    }
}