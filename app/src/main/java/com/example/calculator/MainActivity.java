package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String number1 = null, number2 = null, result = null, operation = null;
    private String operator = null;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    private void numbersButtonsListeners(){
        ArrayList<Button> buttons = new ArrayList<Button>(
                Arrays.asList(
                        findViewById(R.id.number0),
                        findViewById(R.id.number1),
                        findViewById(R.id.number2),
                        findViewById(R.id.number3),
                        findViewById(R.id.number4),
                        findViewById(R.id.number5),
                        findViewById(R.id.number6),
                        findViewById(R.id.number7),
                        findViewById(R.id.number8),
                        findViewById(R.id.number9),
                        findViewById(R.id.comma)
                )
        );

        buttons.forEach(button -> {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    numberButtonClickHandler(button);
                }
            });
        });
    }

    private void operatorsButtonsListeners(){
        ArrayList<Button> buttons = new ArrayList<Button>(
                Arrays.asList(
                        findViewById(R.id.addition),
                        findViewById(R.id.minus),
                        findViewById(R.id.multiply),
                        findViewById(R.id.devide)
                )
        );

        buttons.forEach(button -> {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    operatorButtonClickHandler(button);
                }
            });
        });
    }

    private void otherOperatorsButtonsListeners(){
        ArrayList<Button> buttons = new ArrayList<Button>(
            Arrays.asList(
                findViewById(R.id.onx),
                findViewById(R.id.square),
                findViewById(R.id.sqrt),
                findViewById(R.id.percent),
                findViewById(R.id.plusMinus)
            )
        );

        buttons.forEach(button -> {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    otherOperatorsButtonClickHandler(button);
                }
            });
        });
    }

    private void equalButtonClickListener(){
        findViewById(R.id.equal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equalButtonClickHandler();
            }
        });
    }

    private void eraseButtonClickListener(){
        findViewById(R.id.erase).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eraseButtonClickHandler();
            }
        });
    }

    private void clearButtonsClickListener(){
        ArrayList<Button> buttons = new ArrayList<Button>(
                Arrays.asList(
                        findViewById(R.id.ce),
                        findViewById(R.id.c)
                )
        );

        buttons.forEach(button -> {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clearButtonsCLickHandler(button);
                }
            });
        });
    }

    private void numberButtonClickHandler(Button button){
        if(button != null){
            String number = null;

            try{
                String text = button.getText().toString();
                number = ""+text.charAt(0);

            }catch (Exception exception){

            }finally {
                if(number != null){
                    if(operator == null){
                        if(number.equals(".") && number1.indexOf(number) != -1){
                            number = "";
                        }

                        number1 = number1 != null &&
                                !(""+number1.charAt(0)).equals("0") ? number1 + number : number;
                    }else{
                        if(number.equals(".") && number2.indexOf(number) != -1){
                            number = "";
                        }

                        number2 = number2 != null &&
                                !(""+number2.charAt(0)).equals("0") ? number2  + number : number;
                    }

                    calculateResult();
                }
            }
        }
    }

    private void operatorButtonClickHandler(Button button){
        if(button != null){
            try{
                operator = button.getText().toString();

                if(result != null && !result.isEmpty()){
                    equalButtonClickHandler();
                }

                calculateResult();
            }catch (Exception exception){

            }
        }
    }

    private void otherOperatorsButtonClickHandler(Button button){
        if(button != null){
            try {
                String otherOperator = button.getText().toString();

                switch (otherOperator){
                    case ("1/x"):{
                        if(!IsEmptyOrNull(number2)){
                            number2 = Double.toString(1 / Double.parseDouble(number2));
                        }else if(!IsEmptyOrNull(number1)){
                            number1 = Double.toString(1 / Double.parseDouble(number1));
                        }
                        break;
                    }
                    case ("x²"):{
                        if(!IsEmptyOrNull(number2)){
                            number2 = Double.toString(Math.pow(Double.parseDouble(number2), 2));
                        }else if(!IsEmptyOrNull(number1)){
                            number1 = Double.toString(Math.pow(Double.parseDouble(number1), 2));
                        }
                        break;
                    }
                    case ("√x"):{
                        if(!IsEmptyOrNull(number2)){
                            number2 = Double.toString(Math.sqrt(Double.parseDouble(number2)));
                        }else if(!IsEmptyOrNull(number1)){
                            number1 = Double.toString(Math.sqrt(Double.parseDouble(number1)));
                        }
                        break;
                    }
                    case ("%"):{
                        if(!IsEmptyOrNull(number2)){
                            number2 = Double.toString(Double.parseDouble(number2) / 100);
                        }else if(!IsEmptyOrNull(number1)){
                            number1 = Double.toString(Double.parseDouble(number1) / 100);
                        }
                        break;
                    }
                    case ("+/-"):{
                        if(!IsEmptyOrNull(number2)){
                            number2 = Double.toString(-Double.parseDouble(number2));

                            if((""+number2.charAt(0)).equals("-") && operator.equals("+")){
                                number2 = Double.toString(-Double.parseDouble(number2));
                                operator = "-";
                            }
                        }else if(!IsEmptyOrNull(number1)){
                            number1 = Double.toString(-Double.parseDouble(number1));
                        }
                        break;
                    }
                    default: {
                        break;
                    }
                }

                calculateResult();
            }catch (Exception exception){

            }
        }
    }

    private void clearButtonsCLickHandler(Button button){
        try {
            String buttonText = button.getText().toString();

            switch (buttonText){
                case ("CE"):{
                    if(IsEmptyOrNull(operator)){
                        Reset();
                    }else{
                        number2 = null;
                        calculateResult();
                        TextView resultTextView = (TextView) findViewById(R.id.result);
                        resultTextView.setText("0");
                    }
                    break;
                }
                case ("C"):{
                    Reset();
                    break;
                }
                default:{
                    break;
                }
            }
        }catch (Exception exception){

        }
    }

    private void equalButtonClickHandler(){
        try {
            if(!IsEmptyOrNull(result)){
                TextView resultTextView = (TextView) findViewById(R.id.result);
                TextView operationTextView = (TextView) findViewById(R.id.operation);

                operationTextView.setText(result+operator);
                resultTextView.setText(result);

                number1 = result;
                number2 = null;
                result = null;
            }
        }catch (Exception exception){

        }

    }

    private void eraseButtonClickHandler(){
        Log.i("ersing", "You are erasing");
        try {
            if(!IsEmptyOrNull(number2)){
                number2 = number2.substring(0, number2.length() - 1);

                if(IsEmptyOrNull(number2)){
                    number2 = null;
                }
            }else if (!IsEmptyOrNull(operator)){
                operator = null;
            }else if (!IsEmptyOrNull(number1)){
                if(number1 == "Infinity"){
                    number1 = null;
                }else{
                    number1 = number1.substring(0, number1.length() - 1);
                    if(IsEmptyOrNull(number1)){
                        number1 = null;
                    }
                }
            }

            calculateResult();
        }catch (Exception exception){

        }
    }

    private void calculateResult() {
        try{
            TextView resultTextView = (TextView) findViewById(R.id.result);
            TextView operationTextView = (TextView) findViewById(R.id.operation);

            String leftSideNumber = !IsEmptyOrNull(number1) ? number1 : "0";
            String currentOperator = !IsEmptyOrNull(operator) ? operator : "";
            String rightSideNumber = !IsEmptyOrNull(number2) ? number2 : "";

            if (!currentOperator.equals("=")) {
                operationTextView.setText(leftSideNumber + currentOperator + rightSideNumber);
            }

            double newResult = Double.parseDouble(leftSideNumber);

            rightSideNumber = !rightSideNumber.isEmpty() ?
                    rightSideNumber : (currentOperator.equals("×") ? "1" : "0");

            if (!rightSideNumber.isEmpty()){

                switch (currentOperator) {
                    case ("+"): {
                        newResult += Double.parseDouble(rightSideNumber);
                        break;
                    }
                    case ("-"): {
                        newResult -= Double.parseDouble(rightSideNumber);
                        break;
                    }
                    case ("×"): {
                        newResult *= Double.parseDouble(rightSideNumber);
                        break;
                    }
                    case ("÷"): {
                        if (Double.parseDouble(leftSideNumber) != 0) {
                            newResult /= Double.parseDouble(rightSideNumber);
                        }
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }

            result = Double.toString(newResult);

            resultTextView.setText(Double.toString(newResult));
        }catch (Exception exception){

        }
    }

    private boolean IsEmptyOrNull(String value){
        return value == null || value.isEmpty();
    }

    private void Reset(){
        number1 = null;
        number2 = null;
        result = null;
        operator = null;
        operation = null;

        calculateResult();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        numbersButtonsListeners();
        operatorsButtonsListeners();
        otherOperatorsButtonsListeners();
        clearButtonsClickListener();
        equalButtonClickListener();
        eraseButtonClickListener();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}