package com.example.calculator.adpaters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.calculator.R;
import com.example.calculator.db.DBHandler;
import com.example.calculator.db.Operation;
import com.example.calculator.ui.calculator.CalculatorFragment;

import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter {
    Context context;

    ArrayList<Operation> data;

    CalculatorFragment calculatorFragment = null;

    private static LayoutInflater inflater = null;

    public HistoryAdapter(@NonNull Context context, @NonNull ArrayList<Operation> data, @Nullable  CalculatorFragment calculatorFragment){
        this.context = context;

        this.data = data;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.calculatorFragment = calculatorFragment;
    }

    @Override
    public int getCount(){
        return data.size();
    }

    @Override
    public Operation getItem(int index){
        return data.size() > index && index > - 1 ? data.get(index) : null;
    }

    @Override
    public long getItemId(int index) {
        return data.size() > index && index > - 1 ? data.get(index).getId() : -1;
    }

    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {
        Operation operation = getItem(index);

        if(view == null){
            view = inflater.inflate(R.layout.history_row, null);
        }

        TextView operationTextView = (TextView) view.findViewById(R.id.history_row_operation_id);
        TextView resultTextView = (TextView) view.findViewById(R.id.history_row_result_id);
        TextView deleteButton = (TextView) view.findViewById(R.id.remove_button);

        operationTextView.setText(operation.toString());
        resultTextView.setText(operation.getResult());

        deleteButton.setOnClickListener(view1 -> {
            DBHandler db = new DBHandler(this.context);

            db.deleteOperation(getItemId(index));

            if(calculatorFragment != null){
                calculatorFragment.setHistoryData();
            }
        });

        return view;
    }

}
