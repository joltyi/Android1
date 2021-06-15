package com.example.mycalculator;

import android.os.Parcel;
import android.os.Parcelable;

public class CalculatorState implements Parcelable {
    private final String operand1;
    private final String operand2;
    private final String operatorText;
    private final String resultText;
    private final String completeOperation;
    private final Operator operator;
//    private final String swText;
//    private final Boolean swChecked;

    public CalculatorState(String operand1, String operand2, String operatorText, String resultText, String completeOperation, Operator operator) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operatorText = operatorText;
        this.resultText = resultText;
        this.completeOperation = completeOperation;
        this.operator = operator;
//        this.swText = swText;
//        this.swChecked = swChecked;
    }

    public String getOperand1() {
        return operand1;
    }

    public String getOperand2() {
        return operand2;
    }

    public String getOperatorText() {
        return operatorText;
    }

    public String getResultText() {
        return resultText;
    }

    public String getCompleteOperation() {
        return completeOperation;
    }

    public Operator getOperator() {
        return operator;
    }

//    public String getSwText() {
//        return swText;
//    }
//
//    public Boolean getSwChecked() {
//        return swChecked;
//    }

    protected CalculatorState(Parcel in) {
        operand1 = in.readString();
        operand2 = in.readString();
        operatorText = in.readString();
        resultText = in.readString();
        completeOperation = in.readString();
//        swText = in.readString();
        operator = Operator.valueOf(in.readString());
        byte tmpSwChecked = in.readByte();
//        swChecked = tmpSwChecked == 0 ? null : tmpSwChecked == 1;
    }

    public static final Creator<CalculatorState> CREATOR = new Creator<CalculatorState>() {
        @Override
        public CalculatorState createFromParcel(Parcel in) {
            return new CalculatorState(in);
        }

        @Override
        public CalculatorState[] newArray(int size) {
            return new CalculatorState[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(operand1);
        dest.writeString(operand2);
        dest.writeString(operatorText);
        dest.writeString(resultText);
        dest.writeString(completeOperation);
        dest.writeString(operator.name());
//        dest.writeString(swText);
//        dest.writeByte((byte) (swChecked == null ? 0 : swChecked ? 1 : 2));
    }
}
