package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static int MAX_CHARACTERS = 15;

    private TextView operandOneTextView;
    private TextView operandTwoTextView;
    private TextView operatorTextView;
    private Double operandOne;
    private Double operandTwo;
    private Operator operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this, "Application has started", Toast.LENGTH_LONG).show();

        operandOneTextView = findViewById(R.id.input_value_1);
        operandTwoTextView = findViewById(R.id.input_value_2);
        operatorTextView = findViewById(R.id.input_operation);
        operator = Operator.NULL;
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
        handleClearButtonClick();
        handleOperatorButtonClick(R.id.button_plus);
        handleOperatorButtonClick(R.id.button_minus);
        handleOperatorButtonClick(R.id.button_divide);
        handleOperatorButtonClick(R.id.button_multiply);
        handleOperatorButtonClick(R.id.button_mod);
        handleOperatorButtonClick(R.id.button_pow);
        handleOperatorButtonClick(R.id.button_root);
    }

    private void handleNumberButtonClick(int id) {
        Button button = findViewById(id);
        button.setOnClickListener(it -> selectTextViewToAppend(button.getText().toString()));
    }

    private void handleClearButtonClick() {
        Button button = findViewById(R.id.button_clear);
        button.setOnClickListener(it -> clearAll());
    }

    private void handleOperatorButtonClick(int id) {
        Button button = findViewById(id);
        button.setOnClickListener(it -> enterOperator(button.getText().toString().trim()));
    }

    private void selectTextViewToAppend(String number) {
        if (operator.equals(Operator.NULL)) {
            appendNumber(operandOneTextView, number);
        } else {
            appendNumber(operandTwoTextView, number);
        }
    }

    private void appendNumber(TextView textView, String number) {
        if (number.equals(".")) {
            if (textView.getText().toString().contains(".")) {
                Toast.makeText(this, "Cannot have more than one decimal point in a number", Toast.LENGTH_LONG).show();
            } else {
                textView.append(".");
            }
        } else {
            if (textView.getText().toString().contains(".")) {
                MAX_CHARACTERS++;
            }
            if (textView.getText().length() < MAX_CHARACTERS) {
                textView.append(number);
            } else {
                Toast.makeText(MainActivity.this, "Cannot have more than 10 numbers", Toast.LENGTH_LONG).show();
            }
        }
        MAX_CHARACTERS = 10;
    }

    private void clearAll() {
        operandOneTextView.setText("");
        operandTwoTextView.setText("");
        operatorTextView.setText("");
        operandOne = 0.0;
        operandTwo = 0.0;
        operator = Operator.NULL;
    }

    private Operator getOperatorFromString(String operatorString) {
        return Arrays.stream(Operator.values())
                .filter(it -> getString(it.getOperatorResource()).trim().equals(operatorString))
                .findFirst()
                .orElse(Operator.NULL);
    }

    private void enterOperator(String operatorString) {
        if (!operandOneTextView.getText().toString().equals("")) {
            operator = getOperatorFromString(operatorString);
            operatorTextView.setText(operatorString);
        } else {
            Toast.makeText(this, "Enter first number before operation", Toast.LENGTH_LONG).show();
        }
    }

}