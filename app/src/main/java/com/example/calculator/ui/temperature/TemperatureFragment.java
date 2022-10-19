package com.example.calculator.ui.temperature;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.calculator.R;

import java.util.ArrayList;
import java.util.Arrays;

public class TemperatureFragment extends Fragment {
    private String selectedItemFrom = "Celsius", selectedItemTo = "Celsius";
    private String fromValue = null, toValue = null;

    final private ArrayList<String> units = new ArrayList<>(
            Arrays.asList("Celsius", "Fahrenheit", "Kelvin")
    );

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
        }catch (Exception NullPointerException){

        }
    }

    private boolean Convert(){
        if(units.contains(selectedItemFrom) && units.contains(selectedItemTo)) {
            try {
                int toIndex = units.indexOf(selectedItemTo);
                int fromIndex = units.indexOf(selectedItemFrom);

                if (toIndex == fromIndex) {
                    toValue = fromValue;
                    return true;
                }

                Double value = Double.parseDouble(fromValue);

                if (selectedItemFrom.equals(units.get(0))) {

                    if (selectedItemTo.equals(units.get(1))) {
                        toValue = Double.toString(
                                32 + value * 9 / 5
                        );

                        return true;
                    }

                    toValue = Double.toString(
                            273.15 + value
                    );

                    return true;
                }

                if (selectedItemFrom.equals(units.get(1))) {

                    if (selectedItemTo.equals(units.get(0))) {
                        toValue = Double.toString(
                                (value - 32) * 5 / 9
                        );

                        return true;
                    }

                    toValue = Double.toString(
                            273.15 + (value - 32) * 5 / 9
                    );

                    return true;
                }

                if (selectedItemTo.equals(units.get(0))) {
                    toValue = Double.toString(
                            value - 273.15
                    );

                    return true;
                }

                toValue = Double.toString(
                        32 + (value - 273.15) * 9 / 5
                );

                return true;


            } catch (Exception exception) {
                Log.e("Error", exception.toString());
                return false;
            } finally {
                Display();
            }
        }

        return false;
    }

    private void eraseButtonClickHandler(){
        try {
            String value = fromValue.length() > 1 ?
                    fromValue.substring(0, fromValue.length() - 1) : null;

            fromValue = value;

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

    private void SpinnerOnSelectListener(Spinner spinner){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();

                if (units.contains(selectedItem)) {
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

    private void SpinnerAdapterSetter(Spinner ...spinners){
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.temperature_units,
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

        return inflater.inflate(R.layout.fragment_temperature, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        numbersButtonsListeners();
        eraseButtonClickListener();
        clearButtonClickListener();

        Spinner spinnerFrom = (Spinner) getView().findViewById(R.id.currency_from);

        Spinner spinnerTo = (Spinner) getView().findViewById(R.id.currency_to);

        SpinnerAdapterSetter(spinnerFrom, spinnerTo);
    }
}
