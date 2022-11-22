package com.example.calculator.ui.currency;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.calculator.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CurrencyFragment extends Fragment
{
    private String selectedItemFrom = "Dirham - Morocco", selectedItemTo = "Dirham - Morocco";
    private String fromValue = null, toValue = null;

    private HashMap<String, HashMap<String, Double>> currencies = new HashMap(){{
        put("Dirham - Morocco", new HashMap<String, Double>(){{
            put("Dirham - Morocco", 1.00);
            put("Dollar $ - US", 0.091);
            put("Euro € - Europe", 0.09);
            put("Pound £ - UK", 0.08);
            put("Riyal - Saudi Arabia", 0.34);
        }});
        put("Dollar $ - US", new HashMap<String, Double>(){{
            put("Dirham - Morocco", 10.80);
            put("Dollar $ - US", 1.00);
            put("Euro € - Europe", 0.99);
            put("Pound £ - UK", 0.87);
            put("Riyal - Saudi Arabia", 3.70);
        }});
        put("Euro € - Europe", new HashMap<String, Double>(){{
            put("Dirham - Morocco", 10.95);
            put("Dollar $ - US", 1.01);
            put("Euro € - Europe", 1.00);
            put("Pound £ - UK", 0.88);
            put("Riyal - Saudi Arabia", 3.75);
        }});
        put("Pound £ - UK", new HashMap<String, Double>(){{
            put("Dirham - Morocco", 12.40);
            put("Dollar $ - US", 1.13);
            put("Euro € - Europe", 1.14);
            put("Pound £ - UK", 1.00);
            put("Riyal - Saudi Arabia", 4.24);
        }});
        put("Riyal - Saudi Arabia", new HashMap<String, Double>(){{
            put("Dirham - Morocco", 2.92);
            put("Dollar $ - US", 0.26);
            put("Euro € - Europe", 0.27);
            put("Pound £ - UK", 0.23);
            put("Riyal - Saudi Arabia", 1.00);
        }});
    }};

    private void Reset(){
        selectedItemFrom = "Dirham - Morocco";
        selectedItemTo = "Dirham - Morocco";
        fromValue = null;
        toValue = null;

        Convert();
    }

    private void Display(){
        try {
            TextView toTextView = (TextView) getView().findViewById(R.id.currency_to_value);

            toTextView.setText(""+(toValue != null ? toValue : 0.00));

            TextView fromTextView = (TextView) getView().findViewById(R.id.currency_from_value);

            fromTextView.setText(""+(fromValue != null ? fromValue : 0.00));

        }catch (Exception exception){

        }
    }

    private void Convert(){
        if(currencies.containsKey(selectedItemFrom)){
            HashMap<String, Double> currentCurrency = currencies.get(selectedItemFrom);

            if(currentCurrency.containsKey(selectedItemTo)){
                Double rate = currentCurrency.get(selectedItemTo);

                try {
                    toValue = Double.toString(
                            Double.parseDouble(fromValue != null ? fromValue : "0.00") * rate
                    );


                }catch (Exception exception){

                }finally {
                    Display();
                }

            }
        }
    }

    private void negativeButtonClickHandler(){
        if(fromValue != null && !fromValue.isEmpty() && fromValue.charAt(0) != '-'){
            fromValue = "-" + fromValue;
            Convert();
        }
    }

    private void eraseButtonClickHandler(){
        try {
            fromValue = fromValue.length() > 1 ?
                    fromValue.substring(0, fromValue.length() - 1) : null;

            Convert();

        }catch (Exception exception){

        }
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
                    if(fromValue == null){
                        fromValue = number;
                    }else{
                        fromValue += number;
                    }

                    Convert();
                }
            }
        }
    }

    private void negativeButtonClickListener(){
        try {
            getView().findViewById(R.id.plusMinus).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { negativeButtonClickHandler(); }
            });
        }catch (Exception NullPointerException){

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
        }catch (Exception NullPointerException){

        }
    }

    private void clearButtonClickListener(){
        try {
            getView().findViewById(R.id.c).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Reset();
                }
            });
        }catch (Exception exception){

        }
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
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        numberButtonClickHandler(button);
                    }
                });
            }
        });
    }

    private void SpinnerOnSelectListener(@NonNull Spinner spinner){
          spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                  String selectedItem = adapterView.getItemAtPosition(i).toString();

                  if (currencies.containsKey(selectedItem)) {
                      if (spinner.getId() == R.id.currency_from) {
                          selectedItemFrom = selectedItem;
                      }else{
                          selectedItemTo = selectedItem;
                      }

                      Convert();
                  }
              }

              @Override
              public void onNothingSelected(AdapterView<?> adapterView) {

              }
          });
    }

    private void SpinnerAdapterSetter(@NonNull Spinner ...spinners){
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.currencies_array,
                R.layout.spinner_item
        );

        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        for(int i = 0; i < spinners.length; i++){
            Spinner spinner = spinners[i];
            spinner.setAdapter(arrayAdapter);

            SpinnerOnSelectListener(spinner);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_currency, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        numbersButtonsListeners();
        eraseButtonClickListener();
        clearButtonClickListener();
        negativeButtonClickListener();

        Spinner spinnerFrom = (Spinner) getView().findViewById(R.id.currency_from);

        Spinner spinnerTo = (Spinner) getView().findViewById(R.id.currency_to);

        SpinnerAdapterSetter(spinnerFrom, spinnerTo);
    }
}
