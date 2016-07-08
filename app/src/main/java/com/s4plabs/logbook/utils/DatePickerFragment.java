package com.s4plabs.logbook.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.s4plabs.logbook.ui.Editor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sahil on 08-Jul-16.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user

        Date date = new Date();

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        try {
            //Below i have done month+1 because dont know why datepicker is returning value that is one month less.
            date = format.parse(Integer.toString(day)+"-"+Integer.toString(month+1)+"-"+Integer.toString(year));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String formattedDate = format.format(date);

        //Pass this formatted Date to search function

        Cursor cursor = Editor.searchLogs(formattedDate, getActivity().getApplicationContext());

        if(cursor != null){
            Log.v("sahil search result", "FOUND");
        }
        else{
            Log.v("sahil search result", "MISS");
        }

    }
}