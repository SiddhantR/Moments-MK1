package com.s4plabs.logbook.ui;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.s4plabs.logbook.R;
import com.s4plabs.logbook.db.DBContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by sahil on 12-Jul-16.
 */
public class SearchViewer extends Activity {

    protected String log;
    EditText search_main;
    TextView searchDateView;
    String formattedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);
        search_main = (EditText) findViewById(R.id.searchMain);
        searchDateView = (TextView) findViewById(R.id.searchDateView);
        formattedDate = getIntent().getStringExtra("formatted_date");
        Cursor cursor = Editor.searchLogs(formattedDate, getApplicationContext());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy");

        if(cursor != null){
            cursor.moveToFirst();
            log = cursor.getString(cursor.getColumnIndex(DBContract.DayLogs.COLUMN_NAME_LOG));
            search_main.setText(log);
            try {
                searchDateView.setText(format.format(dateFormat.parse(formattedDate)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else{
            searchDateView.setText("No Log Found   :(");
        }

    }
}
