package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static int MAX_CHARACTERS = 10;

    private TextView operandOneTextView;
    private TextView operandTwoTextView;
    private TextView operatorTextView;
    private Double operandOne;
    private Double operandTwo;
    private boolean operatorEntered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this, "Application has started", Toast.LENGTH_LONG).show();

        operandOneTextView = findViewById(R.id.input_value_1);
        operandTwoTextView = findViewById(R.id.input_value_2);
        operatorTextView = findViewById(R.id.input_operation);
        operatorEntered = false;
        initButtons();

    }

    private void initButtons() {
        handleNumberButtonClick(R.id.button_one);
        handleNumberButtonClick(R.id.button_two);
        handleNumberButtonClick(R.id.button_three);
        handleNumberButtonClick(R.id.button_four);
        handleNumberButtonClick(R.id.button_five);
        handleNumberButtonClick(R.id.button_six);
        handleNumberButtonClick(R.id.button_seven);
        handleNumberButtonClick(R.id.button_eight);
        handleNumberButtonClick(R.id.button_nine);
        handleNumberButtonClick(R.id.button_zero);
        handleNumberButtonClick(R.id.button_dot);
    }

    private void handleNumberButtonClick(int id) {
        Button button = findViewById(id);
        button.setOnClickListener(view -> selectTextViewToAppend(button.getText().toString()));
    }

    private void handleOperatorButtonClick(int id) {

    }

    private void selectTextViewToAppend(String number) {
        if (operatorEntered) {
            appendNumber(operandTwoTextView, number);
        } else {
            appendNumber(operandOneTextView, number);
        }
    }

    private void appendNumber(TextView textView, String number) {
        if (textView.getText().toString().contains(".")) {
            MAX_CHARACTERS++;
        }
        if (textView.getText().length() < MAX_CHARACTERS) {
            textView.append(number);
        } else {
            Toast.makeText(MainActivity.this, "Cannot have more than 10 numbers", Toast.LENGTH_LONG)
                    .show();
        }
        MAX_CHARACTERS = 10;
    }



}