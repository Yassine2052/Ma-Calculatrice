package com.example.calculator.ui.length;

import android.os.Bundle;
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
import java.util.HashMap;

public class LengthFragment extends Fragment {
    private String selectedItemFrom = "Kilometre", selectedItemTo = "Kilometre";
    private String fromValue = null, toValue = null;

    private HashMap<String, HashMap<String, Double>> length = new HashMap(){{
        put("Meter", new HashMap<String, Double>(){{
            put("Meter", 1.00);
            put("Kilometre", 0.001);
            put("Centimetre", 100.00);
            put("Millimetre", 1000.00);
            put("Micrometres", 1e6);
            put("Nanometre", 1e9);
            put("Mile", 0.000621371);
            put("Yard", 1.09361);
            put("Foot", 3.28084);
            put("Inch", 39.3701);
            put("Nautical mile", 0.000539957);
        }});
        put("Kilometre", new HashMap<String, Double>(){{
            put("Meter", 1000.00);
            put("Kilometre", 1.00);
            put("Centimetre", 1e5);
            put("Millimetre", 1e6);
            put("Micrometres", 1e9);
            put("Nanometre", 1e12);
            put("Mile", 0.621371);
            put("Yard", 1093.61);
            put("Foot", 3280.84);
            put("Inch", 39370.1);
            put("Nautical mile", 0.539957);
        }});
        put("Centimetre", new HashMap<String, Double>(){{
            put("Meter", 1e-2);
            put("Kilometre", 1e-5);
            put("Centimetre", 1.00);
            put("Millimetre", 10.00);
            put("Micrometres", 1e4);
            put("Nanometre", 1e7);
            put("Mile", 6.2137e-6);
            put("Yard", 0.0109361);
            put("Foot", 0.0328084);
            put("Inch", 0.393701);
            put("Nautical mile", 5.3996e-6);
        }});
        put("Millimetre", new HashMap<String, Double>(){{
            put("Meter", 0.001);
            put("Kilometre", 1e-6);
            put("Centimetre", 0.1);
            put("Millimetre", 1.00);
            put("Micrometres", 1000.00);
            put("Nanometre", 1e+6);
            put("Mile", 6.2137e-7);
            put("Yard", 0.00109361);
            put("Foot", 0.00328084);
            put("Inch", 0.0393701);
            put("Nautical mile", 5.3996e-7);
        }});
        put("Micrometres", new HashMap<String, Double>(){{
            put("Meter", 1e-6);
            put("Kilometre", 1e-9);
            put("Centimetre", 1e-4);
            put("Millimetre", 0.001);
            put("Micrometres", 1.00);
            put("Nanometre", 1000.00);
            put("Mile", 6.2137e-10);
            put("Yard", 1.0936e-6);
            put("Foot", 3.2808e-6);
            put("Inch", 3.937e-5);
            put("Nautical mile", 5.3996e-10);
        }});
        put("Nanometre", new HashMap<String, Double>(){{
            put("Meter", 1e-9);
            put("Kilometre", 1e-12);
            put("Centimetre", 1e-7);
            put("Millimetre", 1e-6);
            put("Micrometres", 0.001);
            put("Nanometre", 1.00);
            put("Mile", 6.2137e-13);
            put("Yard", 1.0936e-9);
            put("Foot", 3.2808e-9);
            put("Inch", 3.937e-8);
            put("Nautical mile", 5.3996e-13);
        }});
        put("Mile", new HashMap<String, Double>(){{
            put("Meter", 1609.34);
            put("Kilometre", 1.60934);
            put("Centimetre", 160934.00);
            put("Millimetre", 1.609e+6);
            put("Micrometres", 1.609e+9);
            put("Nanometre", 1.609e+12);
            put("Mile", 1.00);
            put("Yard", 1760.00);
            put("Foot", 5280.00);
            put("Inch", 63360.00);
            put("Nautical mile", 0.868976);
        }});
        put("Yard", new HashMap<String, Double>(){{
            put("Meter", 0.9144);
            put("Kilometre", 0.0009144);
            put("Centimetre", 91.44);
            put("Millimetre", 914.4);
            put("Micrometres", 914400.00);
            put("Nanometre", 9.144e+8);
            put("Mile", 0.000568182);
            put("Yard", 1.00);
            put("Foot", 3.00);
            put("Inch", 36.00);
            put("Nautical mile", 0.000493737);
        }});
        put("Foot", new HashMap<String, Double>(){{
            put("Meter", 0.0003048);
            put("Kilometre", 0.3048);
            put("Centimetre", 30.48);
            put("Millimetre", 304.8);
            put("Micrometres", 304800.00);
            put("Nanometre", 3.048e+8);
            put("Mile", 0.000189394);
            put("Yard", 0.333333);
            put("Foot", 1.00);
            put("Inch", 12.00);
            put("Nautical mile", 0.000164579);
        }});
        put("Inch", new HashMap<String, Double>(){{
            put("Meter", 0.0254);
            put("Kilometre", 2.54e-5);
            put("Centimetre", 2.54);
            put("Millimetre", 25.4);
            put("Micrometres", 25400.00);
            put("Nanometre", 2.54e+7);
            put("Mile", 1.5783e-5);
            put("Yard", 0.0277778);
            put("Foot", 0.0833333);
            put("Inch", 1.00);
            put("Nautical mile", 1.3715e-5);
        }});
        put("Nautical mile", new HashMap<String, Double>(){{
            put("Meter", 1852.00);
            put("Kilometre", 1.852);
            put("Centimetre", 185200.00);
            put("Millimetre", 1.852e+6);
            put("Micrometres", 1.852e+9);
            put("Nanometre", 1.852e+12);
            put("Mile", 1.15078);
            put("Yard", 2025.37);
            put("Foot", 6076.12);
            put("Inch", 72913.4);
            put("Nautical mile", 1.00);
        }});
    }};

    private void Reset(){
        selectedItemFrom = "Kilometre";
        selectedItemTo = "Kilometre";
        fromValue = null;
        toValue = null;

        ((Spinner) getView().findViewById(R.id.length_to)).setSelection(0);

        ((Spinner) getView().findViewById(R.id.length_from)).setSelection(0);

        Convert();
    }

    private void Display(){
        try {
            TextView toTextView = (TextView) getView().findViewById(R.id.length_to_value);

            toTextView.setText(""+(toValue != null ? toValue : 0.00));

            TextView fromTextView = (TextView) getView().findViewById(R.id.length_from_value);

            fromTextView.setText(""+(fromValue != null ? fromValue : 0.00));

        }catch (Exception exception){

        }
    }

    private void Convert(){
        if(length.containsKey(selectedItemFrom)){
            HashMap<String, Double> currentCurrency = length.get(selectedItemFrom);

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

                if (length.containsKey(selectedItem)) {
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
                R.array.length_array,
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

        return inflater.inflate(R.layout.fragment_length, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        numbersButtonsListeners();
        eraseButtonClickListener();
        clearButtonClickListener();

        Spinner spinnerFrom = (Spinner) getView().findViewById(R.id.length_from);

        Spinner spinnerTo = (Spinner) getView().findViewById(R.id.length_to);

        SpinnerAdapterSetter(spinnerFrom, spinnerTo);
    }
}
