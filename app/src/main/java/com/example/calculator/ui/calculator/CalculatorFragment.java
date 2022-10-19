package com.example.calculator.ui.calculator;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.calculator.R;

import java.util.ArrayList;
import java.util.Arrays;

public class CalculatorFragment extends Fragment {
    private String number1 = null, number2 = null, result = null, operation = null;
    private String operator = null;

    private void numbersButtonsListeners(){
        ArrayList<Button> buttons = new ArrayList<Button>(
                Arrays.asList(
                        getView().findViewById(R.id.number0),
                        getView().findViewById(R.id.number1),
                        getView().findViewById(R.id.number2),
                        getView().findViewById(R.id.number3),
                        getView().findViewById(R.id.number4),
                        getView().findViewById(R.id.number5),
                        getView().findViewById(R.id.number6),
                        getView().findViewById(R.id.number7),
                        getView().findViewById(R.id.number8),
                        getView().findViewById(R.id.number9),
                        getView().findViewById(R.id.comma)
                )
        );

        buttons.forEach(button -> {
            if(button != null){
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        numberButtonClickHandler(button);
                    }
                });
            }
        });
    }

    private void operatorsButtonsListeners(){
        ArrayList<Button> buttons = new ArrayList<Button>(
                Arrays.asList(
                        getView().findViewById(R.id.addition),
                        getView().findViewById(R.id.minus),
                        getView().findViewById(R.id.multiply),
                        getView().findViewById(R.id.devide)
                )
        );

        buttons.forEach(button -> {
            if(button != null){
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        operatorButtonClickHandler(button);
                    }
                });
            }
        });
    }

    private void otherOperatorsButtonsListeners(){
        ArrayList<Button> buttons = new ArrayList<Button>(
                Arrays.asList(
                        getView().findViewById(R.id.onx),
                        getView().findViewById(R.id.square),
                        getView().findViewById(R.id.sqrt),
                        getView().findViewById(R.id.percent),
                        getView().findViewById(R.id.plusMinus)
                )
        );

        buttons.forEach(button -> {
            if(button != null){
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        otherOperatorsButtonClickHandler(button);
                    }
                });
            }
        });
    }

    private void equalButtonClickListener(){
        try {
            getView().findViewById(R.id.equal).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    equalButtonClickHandler();
                }
            });
        }catch (Exception exception){

        }
    }

    private void eraseButtonClickListener(){
        try {
            getView().findViewById(R.id.erase).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eraseButtonClickHandler();
                }
            });
        }catch (Exception exception){

        }
    }

    private void clearButtonsClickListener(){
        ArrayList<Button> buttons = new ArrayList<Button>(
                Arrays.asList(
                        getView().findViewById(R.id.ce),
                        getView().findViewById(R.id.c)
                )
        );

        buttons.forEach(button -> {
            if(button != null){
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clearButtonsCLickHandler(button);
                    }
                });
            }
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
                        TextView resultTextView = (TextView) getView().findViewById(R.id.result);
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
                TextView resultTextView = (TextView) getView().findViewById(R.id.result);
                TextView operationTextView = (TextView) getView().findViewById(R.id.operation);

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
            TextView resultTextView = (TextView) getView().findViewById(R.id.result);
            TextView operationTextView = (TextView) getView().findViewById(R.id.operation);

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_calculator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        numbersButtonsListeners();
        operatorsButtonsListeners();
        otherOperatorsButtonsListeners();
        clearButtonsClickListener();
        equalButtonClickListener();
        eraseButtonClickListener();
    }
}
