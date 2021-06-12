package com.example.mycalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final static int MAX_OPERAND_LENGTH = 15;

    SharedPreferences preferences;

    private TextView operandOneTextView;
    private TextView operandTwoTextView;
    private TextView operatorTextView;
    private TextView completeOperationTextView;
    private TextView resultTextView;
    private Operator operator;

    private SwitchCompat sw;
    private boolean activityRecreating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getPreferences(MODE_PRIVATE);
        setTheme(preferences.getBoolean("USE_DARK_THEME",false) ? R.style.AppThemeDark : R.style.AppThemeLight);

        setContentView(R.layout.activity_main);
        operandOneTextView = findViewById(R.id.input_value_1);
        operandTwoTextView = findViewById(R.id.input_value_2);
        operatorTextView = findViewById(R.id.input_operation);
        completeOperationTextView = findViewById(R.id.complete_operation);
        resultTextView = findViewById(R.id.result);
        operator = Operator.NULL;
        initButtons();

        sw = findViewById(R.id.change_theme);
        activityRecreating = preferences.getBoolean("ACTIVITY_RECREATING", false);
        initSwitchTheme();


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        CalculatorState calculatorState = new CalculatorState(operandOneTextView.getText().toString(),
                operandTwoTextView.getText().toString(),
                operatorTextView.getText().toString(),
                resultTextView.getText().toString(),
                completeOperationTextView.getText().toString(),
                operator,
                sw.getText().toString(),
                sw.isChecked());
        outState.putParcelable("CALCULATOR_STATE", calculatorState);
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putBoolean("ACTIVITY_RECREATING", true);
        preferencesEditor.apply();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        CalculatorState calculatorState = savedInstanceState.getParcelable("CALCULATOR_STATE");
        operandOneTextView.setText(calculatorState.getOperand1());
        operandTwoTextView.setText(calculatorState.getOperand2());
        operatorTextView.setText(calculatorState.getOperatorText());
        resultTextView.setText(calculatorState.getResultText());
        completeOperationTextView.setText(calculatorState.getCompleteOperation());
        operator = calculatorState.getOperator();
        sw.setChecked(calculatorState.getSwChecked());
        sw.setText(calculatorState.getSwText());
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        activityRecreating = false;
        preferencesEditor.putBoolean("ACTIVITY_RECREATING", false);
        preferencesEditor.apply();
    }

    private void initSwitchTheme() {
        sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor preferencesEditor = preferences.edit();
            preferencesEditor.putBoolean("USE_DARK_THEME", !preferences.getBoolean("USE_DARK_THEME",false));
            if (isChecked) {
                sw.setText("Dark Theme");
                preferencesEditor.putBoolean("USE_DARK_THEME", true);
            } else {
                sw.setText("Light Theme");
                preferencesEditor.putBoolean("USE_DARK_THEME", false);
            }
            preferencesEditor.apply();
            if (!activityRecreating) {
                recreate();
            }

        });
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
        clearOperands();
        resultTextView.setText("");
        completeOperationTextView.setText("");
        operator = Operator.NULL;
    }

    private void clearOperands() {
        operandOneTextView.setText("");
        operandTwoTextView.setText("");
        operatorTextView.setText("");
    }

    private Operator getOperatorFromString(String operatorString) {
        return Arrays.stream(Operator.values())
                .filter(it -> it.getOperatorString().equals(operatorString))
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
                resultTextView.setText(calculator.getResultAsString());
            } catch (CalculatorException e) {
                resultTextView.setText(getString(R.string.error));
                Toast.makeText(this, getString(R.string.error) + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
            completeOperationTextView.setText(calculator.getOperationAsString());
            clearOperands();
        }
    }
}