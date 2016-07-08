package com.s4plabs.logbook.ui;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.s4plabs.logbook.R;
import com.s4plabs.logbook.db.DBContract;
import com.s4plabs.logbook.db.DBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sahil on 06-Jul-16.
 */
public class Editor extends Activity {

    protected String log;
    EditText editor_main;
    protected int update_flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor_main);
        editor_main = (EditText) findViewById(R.id.editor_main);
        Cursor cursor = getLog();
        if(cursor != null){
            cursor.moveToFirst();
            String existing_log = cursor.getString(cursor.getColumnIndex(DBContract.DayLogs.COLUMN_NAME_LOG));
            editor_main.setText(existing_log);
            update_flag = 1;
            editor_main.setSelection(existing_log.length());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }if (id == R.id.action_save) {
            if(update_flag == 1){
                updateLog();
            }else{
                saveLog();
            }
            return true;
        }if (id == R.id.action_discard) {
            discardLog();
            return true;
        }if (id == R.id.action_search) {
            return true;
        }if (id == R.id.action_read){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void discardLog(){

        Cursor cursor = getLog();

        if(cursor.getCount()!=0){

            DBHelper dbHelper = new DBHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            Date currentDate = Calendar.getInstance().getTime();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            String date = format.format(currentDate);
            date = date.substring(0, 2)+date.substring(3, 5)+date.substring(6, 10);

            String selection = DBContract.DayLogs.COLUMN_NAME_ID + " = " + date;

            db.delete(DBContract.DayLogs.TABLE_NAME, selection, null);

            db.close();
            dbHelper.close();

            editor_main.setText("");

            Toast.makeText(getApplicationContext(), "Discarded", Toast.LENGTH_SHORT).show();
        }
    }

    protected Cursor getLog(){

        DBHelper dbHelper = new DBHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date = format.format(currentDate);
        date = date.substring(0, 2)+date.substring(3, 5)+date.substring(6, 10);

        String query = "Select * from " + DBContract.DayLogs.TABLE_NAME +" where " + DBContract.DayLogs.COLUMN_NAME_ID + " = "+ date + " ; ";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount()!=0){
            return cursor;
        }
        else{
            return null;
        }
    }

    protected void saveLog(){
        log =  editor_main.getText().toString();

        DBHelper dbHelper = new DBHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date = format.format(currentDate);
        date = date.substring(0, 2)+date.substring(3, 5)+date.substring(6, 10);
        Log.v("Checking date", date);
        ContentValues values = new ContentValues();
        values.put(DBContract.DayLogs.COLUMN_NAME_ID, Integer.parseInt(date));
        values.put(DBContract.DayLogs.COLUMN_NAME_LOG, log);

        long newLogId = db.insert(DBContract.DayLogs.TABLE_NAME, null, values );

        db.close();
        dbHelper.close();

        Toast.makeText(getApplicationContext(), "Log Saved", Toast.LENGTH_SHORT).show();

    }

    protected void updateLog(){

        DBHelper dbHelper = new DBHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date = format.format(currentDate);
        date = date.substring(0, 2)+date.substring(3, 5)+date.substring(6, 10);

        String selection = DBContract.DayLogs.COLUMN_NAME_ID + " = " + date;

        db.delete(DBContract.DayLogs.TABLE_NAME, selection, null);

        db.close();
        dbHelper.close();

        saveLog();

    }
}
