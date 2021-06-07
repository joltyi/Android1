package com.example.mycalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Locale;

import static com.example.mycalculator.Constants.KEY_COMPLETE_OPERATION;
import static com.example.mycalculator.Constants.KEY_OPERAND1;
import static com.example.mycalculator.Constants.KEY_OPERAND2;
import static com.example.mycalculator.Constants.KEY_OPERATOR;
import static com.example.mycalculator.Constants.KEY_OPERATOR_VALUE;
import static com.example.mycalculator.Constants.KEY_RESULT;

public class MainActivity extends AppCompatActivity {
    private final static int MAX_OPERAND_LENGTH = 15;

    private TextView operandOneTextView;
    private TextView operandTwoTextView;
    private TextView operatorTextView;
    private TextView completeOperationTextView;
    private TextView resultTextView;
    private Operator operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        operandOneTextView = findViewById(R.id.input_value_1);
        operandTwoTextView = findViewById(R.id.input_value_2);
        operatorTextView = findViewById(R.id.input_operation);
        completeOperationTextView = findViewById(R.id.complete_operation);
        resultTextView = findViewById(R.id.result);
        operator = Operator.NULL;
        initButtons();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_OPERAND1, operandOneTextView.getText().toString());
        outState.putString(KEY_OPERAND2, operandTwoTextView.getText().toString());
        outState.putString(KEY_OPERATOR, operatorTextView.getText().toString());
        outState.putString(KEY_RESULT, resultTextView.getText().toString());
        outState.putString(KEY_COMPLETE_OPERATION, completeOperationTextView.getText().toString());
        outState.putString(KEY_OPERATOR_VALUE, operator.getOperatorString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        operandOneTextView.setText(savedInstanceState.getString(KEY_OPERAND1, ""));
        operandTwoTextView.setText(savedInstanceState.getString(KEY_OPERAND2, ""));
        operatorTextView.setText(savedInstanceState.getString(KEY_OPERATOR, ""));
        resultTextView.setText(savedInstanceState.getString(KEY_RESULT, ""));
        completeOperationTextView.setText(savedInstanceState.getString(KEY_COMPLETE_OPERATION, ""));
        operator = getOperatorFromString(savedInstanceState.getString(KEY_OPERATOR_VALUE, ""));
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
        handleEqualButtonClick();
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
        button.setOnClickListener(it -> operatorClick(button.getText().toString().trim()));
    }

    private void handleEqualButtonClick() {
        Button button = findViewById(R.id.button_equals);
        button.setOnClickListener(it -> equalsClick());
    }

    private void selectTextViewToAppend(String number) {
        if (operator.equals(Operator.NULL)) {
            appendNumber(operandOneTextView, number);
        } else {
            appendNumber(operandTwoTextView, number);
        }
    }

    private void appendNumber(TextView textView, String number) {
        int maxLength = MAX_OPERAND_LENGTH;
        if (number.equals(".")) {
            if (textView.getText().toString().contains(".")) {
                Toast.makeText(this, "Cannot have more than one decimal point in a number", Toast.LENGTH_LONG).show();
            } else {
                textView.append(".");
            }
        } else {
            if (textView.getText().toString().contains(".")) {
                maxLength++;
            }
            if (textView.getText().length() < maxLength) {
                textView.append(number);
            } else {
                Toast.makeText(MainActivity.this, String.format(Locale.getDefault(), "Cannot have more than %d numbers", MAX_OPERAND_LENGTH), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void clearAll() {
        operandOneTextView.setText("");
        operandTwoTextView.setText("");
        operatorTextView.setText("");
        resultTextView.setText("");
        completeOperationTextView.setText("");
        operator = Operator.NULL;
    }

    private Operator getOperatorFromString(String operatorString) {
        return Arrays.stream(Operator.values())
                .filter(it -> getString(it.getOperatorResource()).trim().equals(operatorString))
                .findFirst()
                .orElse(Operator.NULL);
    }

    private void operatorClick(String operatorString) {
        if (!operandOneTextView.getText().toString().equals("")) {
            operator = getOperatorFromString(operatorString);
            if (operator.equals(Operator.SQRT)) {
                operandTwoTextView.setText("2");
            }
            operatorTextView.setText(operatorString);
        } else {
            Toast.makeText(this, "Enter first number before operation", Toast.LENGTH_LONG).show();
        }
    }

    private void equalsClick() {
        if (operandOneTextView.getText().toString().equals("")
                || operatorTextView.getText().toString().equals("")
                || operandTwoTextView.getText().toString().equals("")) {
            Toast.makeText(this, "Enter both operands and the operation", Toast.LENGTH_LONG).show();
        } else {
            Calculator calculator = new Calculator(Double.parseDouble(operandOneTextView.getText().toString()),
                    Double.parseDouble(operandTwoTextView.getText().toString()), operator);
            try {
                calculator.calculate();
            } catch (CalculatorException e) {
                Toast.makeText(this, getString(R.string.error) + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }

            resultTextView.setText(calculator.getResultAsString());
            completeOperationTextView.setText(calculator.getOperationAsString());
        }
    }
}