package com.example.calculator.db;

public class Operation {
    private long id;
    private String number1, number2, operator, result;

    public Operation(int id, String number1, String number2, String  operator, String result){
        this.id = id;
        this.number1 = number1;
        this.number2 = number2;
        this.operator = operator;
        this.result = result;
    }

    public long getId() {
        return id;
    }

    public String getNumber1() {
        return number1;
    }

    public String getNumber2() {
        return number2;
    }

    public String getOperator() {
        return operator;
    }

    public String getResult() {
        return result;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return number1 + " " + operator + " " + number2;
    }
}
