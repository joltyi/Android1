package com.example.mycalculator;

public enum Operator {
    ADD(R.string._plus),
    SUB(R.string._minus),
    MUL(R.string._mul),
    DIV(R.string._div),
    MOD(R.string._mod),
    ROOT(R.string._root),
    POW(R.string._pow),
    NULL(0);

    private final int operatorResource;

    Operator(int operatorResource) {
        this.operatorResource = operatorResource;
    }

    public int getOperatorResource() {
        return operatorResource;
    }
}
