package com.example.mycalculator;

public class Calculator {
    private final Double operand1;
    private final Double operand2;
    private final Operator operator;

    private Double result = 0.0;
    
    public Calculator(Double operand1, Double operand2, Operator operator) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operator = operator;
    }

    public void calculate() throws CalculatorException {
        switch (operator) {
            case ADD:
                sum();
                break;
            case DIV:
                divide();
                break;
            case MOD:
                modulus();
                break;
            case MUL:
                multiply();
                break;
            case POW:
                pow();
                break;
            case SUB:
                subtract();
                break;
            case SQRT:
                sqrt();
                break;
            case NULL:
            default:
                break;
        }
    }

    public String getResultAsString() {
        return String.valueOf(result);
    }

    public String getOperationAsString() {
        return operand1 + operator.getOperatorString() + operand2;
    }
    
    private void sum() {
        result = operand1 + operand2;
    }

    private void subtract() {
        result = operand1 - operand2;
    }

    private void multiply() {
        result = operand1 * operand2;
    }

    private void divide() throws CalculatorException {
        if (operand2 == 0) {
            throw new CalculatorException("Division by zero");
        }
        result = operand1 / operand2;
    }

    private void modulus() {
        result = operand1 % operand2;
    }

    private void sqrt() {
        result = Math.sqrt(operand1);
    }

    private void pow(){
        result = Math.pow(operand1, operand2);
    }
}
