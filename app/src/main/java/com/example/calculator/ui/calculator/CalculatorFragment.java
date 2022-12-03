package com.example.calculator.ui.calculator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.calculator.R;
import com.example.calculator.adpaters.HistoryAdapter;
import com.example.calculator.db.DBHandler;
import com.example.calculator.db.Operation;

import java.util.ArrayList;
import java.util.Arrays;

public class CalculatorFragment extends Fragment {
    private String number1 = null, number2 = null, result = null, operator = null;

    private boolean historyShown = true;

    private void containerClickListener(){
        LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.calculator_container);

        linearLayout.setOnClickListener(view -> {
            if(historyShown){
                toggleHistory();
            };
        });
    }

    private void historyButtonListener(){
        ImageButton button = (ImageButton) getView().findViewById(R.id.history_button);

        button.setOnClickListener(view -> toggleHistory());
    }

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
                button.setOnClickListener(view -> numberButtonClickHandler(button));
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
                button.setOnClickListener(view -> operatorButtonClickHandler(button));
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
                button.setOnClickListener(view -> otherOperatorsButtonClickHandler(button));
            }
        });
    }

    private void equalButtonClickListener(){
        try {
            getView().findViewById(R.id.equal).setOnClickListener(view -> equalButtonClickHandler(true));
        }catch (Exception exception){

        }
    }

    private void eraseButtonClickListener(){
        try {
            getView().findViewById(R.id.erase).setOnClickListener(view -> eraseButtonClickHandler());
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
                button.setOnClickListener(view -> clearButtonsCLickHandler(button));
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
                    equalButtonClickHandler(false);
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

    private void equalButtonClickHandler(boolean clicked){
        try {
            if(!IsEmptyOrNull(result)){
                TextView resultTextView = (TextView) getView().findViewById(R.id.result);

                TextView operationTextView = (TextView) getView().findViewById(R.id.operation);

                operationTextView.setText(result+operator);
                resultTextView.setText(result);

                if(clicked){
                    DBHandler db = new DBHandler(this.getContext());

                    Operation operation = new Operation(-1, number1, number2, operator, result);

                    long id = db.insertOperation(operation);

                    if(id != -1){
                        setHistoryData();
                    }
                }


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

        calculateResult();
    }

    private void toggleHistory(){
        try{
            FrameLayout frameLayout = (FrameLayout) getView().findViewById(R.id.history_container);

            float value = this.getActivity().getResources().getDisplayMetrics().heightPixels / 2;

            AnimatorSet set = new AnimatorSet();

            // Using property animation
            ObjectAnimator animation = ObjectAnimator.ofFloat(
                    frameLayout,
                    "translationY",
                    historyShown ? value - 160 : value * 2,
                    historyShown ? value * 2 : value - 160
            );

            animation.setDuration(500);

            set.play(animation);

            set.start();

            historyShown = !historyShown;

        }catch (Exception exception){

        }

    }

    private void initHistory(){
        FrameLayout frameLayout = (FrameLayout) getView().findViewById(R.id.history_container);

        float height = this.getActivity().getResources().getDisplayMetrics().heightPixels / 2;

        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();

        layoutParams.height = (int) height;

        frameLayout.setLayoutParams(layoutParams);

        setHistoryData();
    }

    private void itemSelectedHandler(HistoryAdapter historyAdapter, int index){
        try{
            Operation operation = historyAdapter.getItem(index);

            if(operation != null){
                TextView operationTextView = (TextView) getView().findViewById(R.id.operation);

                number1 = operation.getNumber1();
                number2 = operation.getNumber2();
                operator = operation.getOperator();
                result = operation.getResult();

                Double number2Double = Double.parseDouble(number2);

                if(number2Double < 0 && operator.equals("+")){
                    operator = "-";

                    number2 = String.valueOf(Math.abs(number2Double));
                }

                operationTextView.setText(number1 + " " + operator + " " + number2);

                calculateResult();
            }

        }catch (Exception exception){
        }

    }

    public void setHistoryData(){
        try {
            DBHandler db = new DBHandler(this.getContext());

            ArrayList<Operation> operations = db.getOperations();

            ListView listView = (ListView) getView().findViewById(R.id.history);

            HistoryAdapter historyAdapter = new HistoryAdapter(this.getContext(), operations, this);

            listView.setAdapter(historyAdapter);

            listView.setOnItemClickListener((adapterView, view, index, l) -> itemSelectedHandler(historyAdapter, index));

        }catch (Exception exception){
        }
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

        if(savedInstanceState != null){
            number1 = savedInstanceState.getString("number1");
            number2 = savedInstanceState.getString("number2");
            result = savedInstanceState.getString("result");
            operator = savedInstanceState.getString("operator");
            historyShown = savedInstanceState.getBoolean("historyShown");
        }

        containerClickListener();
        historyButtonListener();
        numbersButtonsListeners();
        operatorsButtonsListeners();
        otherOperatorsButtonsListeners();
        clearButtonsClickListener();
        equalButtonClickListener();
        eraseButtonClickListener();

        initHistory();

        toggleHistory();

        calculateResult();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("number1", number1);
        outState.putString("number2", number2);
        outState.putString("operator", operator);
        outState.putString("result", result);
        outState.putBoolean("historyShown", historyShown);
    }
}
