package com.example.mycalculator;

public enum Operator {
    ADD(R.string._plus, "+"),
    SUB(R.string._minus, "-"),
    MUL(R.string._mul, "x"),
    DIV(R.string._div, "/"),
    MOD(R.string._mod, "%"),
    SQRT(R.string._sqrt, "√"),
    POW(R.string._pow, "^"),
    NULL(0, "");

    private final int operatorResource;
    private final String operatorString;

    Operator(int operatorResource, String operatorString) {
        this.operatorResource = operatorResource;
        this.operatorString = operatorString;
    }

    public int getOperatorResource() {
        return operatorResource;
    }

    public String getOperatorString() {
        return operatorString;
    }
}
