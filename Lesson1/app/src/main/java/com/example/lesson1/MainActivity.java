package com.example.lesson1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button1);
        button.setOnClickListener(view -> {
            setContentView(R.layout.activity_edit_text);
        });

        //Switch
        Switch sw = findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sw.setText(R.string.switch_on);
            } else {
                sw.setText(R.string.switch_off);
            }
        });

    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()) {
            case R.id.checkbox1:
                if (checked) {
                    ((CheckBox) view).setText(R.string.check_box + " Checked");
                } else {
                    ((CheckBox) view).setText(R.string.check_box);
                }
                break;
            case R.id.checkbox2:
                if (checked) {
                    ((CheckBox) view).setText(R.string.check_box + " Checked");
                } else {
                    ((CheckBox) view).setText(R.string.check_box);
                }
                break;
        }
    }

}