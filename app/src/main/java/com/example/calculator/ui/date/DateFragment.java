package com.example.calculator.ui.date;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.calculator.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class DateFragment extends Fragment {
    Date fromDate = new Date(), toDate = new Date();

    enum PickedDate{
        FROM_DATE,
        TO_DATE
    }

    private void SetDifference(){
        int compareDates = fromDate.compareTo(toDate);

        TextView diffDatesTextView = (TextView) getView().findViewById(R.id.dates_diff);

        if(compareDates == 0){
            diffDatesTextView.setText("Same dates");
        }else{
            long diff = Math.abs(fromDate.getTime() - toDate.getTime());

            long diffDays = diff / (1000 * 60 * 60 * 24);

            long diffMonths = diffDays / 30;

            long diffYears = diffDays / 365;

            diffDays %= 30;

            diffMonths %= 12;

            String difference = ("" +
                    (
                            diffYears > 0 ?
                                    diffYears + " " + (diffYears > 1 ? "years" : "year") + ", " : ""
                    ) +
                    (
                            diffMonths > 0 ?
                                    diffMonths + " " + (diffMonths > 1 ? "months" : "month") + ", " : ""
                    ) +
                    (
                            diffDays > 0 ?
                                    diffDays + " " + (diffDays > 1 ? "days" : "day") + ", " : ""
                    )
            ).trim();

            difference = difference.length() > 0 && difference.charAt(difference.length() - 1) == ','
                    ? difference.substring(0, difference.length() - 1) : difference;



            diffDatesTextView.setText(difference);

            if(diffYears > 0 || diffMonths > 0){
                TextView diffDaysTextView = (TextView) getView().findViewById(R.id.dates_diff_days);

                long diffDays2 = diff / (1000 * 60 * 60 * 24);

                diffDaysTextView.setText(
                        (
                                diffDays2 > 0 ?
                                        diffDays2 + " " + (diffDays2 > 1 ? "days" : "day") : ""
                        )
                );
            }
        }
    }

    private void DisplayDates(){
        TextView fromDateTextView = (TextView) getView().findViewById(R.id.from_date);
        TextView toDateTextView = (TextView) getView().findViewById(R.id.to_date);

        fromDateTextView.setText(fromDate.toString());
        toDateTextView.setText(toDate.toString());
    }

    private void FromDatePickerListener(){
        ArrayList<Button> buttons = new ArrayList<Button>(Arrays.asList(
                (Button) getView().findViewById(R.id.from_date_pick_button),
                (Button) getView().findViewById(R.id.to_date_pick_button)
        ));

        buttons.forEach(button -> {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ButtonClickHandler(view);
                }
            });
        });
    }

    private void ButtonClickHandler(View view){
        int viewId = view.getId();

        if (viewId == R.id.from_date_pick_button || viewId == R.id.to_date_pick_button) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity());

            Calendar calendar = Calendar.getInstance();

            calendar.setTime(view.getId() == R.id.from_date_pick_button ? fromDate : toDate);

            datePickerDialog.updateDate(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );

            datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    DatePickerHandler(viewId == R.id.from_date_pick_button ? PickedDate.FROM_DATE : PickedDate.TO_DATE, y, m, d);
                }
            });

            datePickerDialog.show();
        }
    }

    private void DatePickerHandler(PickedDate pickedDate, int y, int m, int d){
        Calendar calendar = Calendar.getInstance();

        calendar.set(y, m, d, 0,0, 0);

        if(pickedDate.equals(PickedDate.FROM_DATE)){
            fromDate = new Date(calendar.getTimeInMillis());

        }else if(pickedDate.equals(PickedDate.TO_DATE)){
            toDate = new Date(calendar.getTimeInMillis());
        }

        DisplayDates();

        SetDifference();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_date, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FromDatePickerListener();

        DisplayDates();
    }

}
