package ru.puchkova.homework3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class HeartActivity extends AppCompatActivity {

    EditText systolic, diastolic, pulse, date;
    CheckBox tachycardia;
    Button save, history, back;
    DBHelper dbHelper;
    int iSystolic, iDiastolic, iPulse, userId;
    boolean bTachycardia;
    String sDate;
    Calendar dateAndTime=Calendar.getInstance();
    TextView historyView, warning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart);


        systolic = (EditText) findViewById(R.id.systolic);
        diastolic = (EditText) findViewById(R.id.diastolic);
        pulse = (EditText) findViewById(R.id.pulse);
        date = (EditText) findViewById(R.id.date);
        tachycardia = (CheckBox) findViewById(R.id.tachycardia);
        save = (Button) findViewById(R.id.save);
        history = (Button) findViewById(R.id.history);
        back = (Button) findViewById(R.id.back);
        historyView = (TextView) findViewById(R.id.historyView);
        warning = (TextView) findViewById(R.id.warning);

        setInitialDateTime();

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", userId);

        dbHelper = new DBHelper(this);



        View.OnClickListener oclSave = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    iSystolic = Integer.parseInt(systolic.getText().toString());
                    iDiastolic = Integer.parseInt(diastolic.getText().toString());
                    iPulse = Integer.parseInt(pulse.getText().toString());
                    bTachycardia = tachycardia.isChecked();
                    sDate = date.getText().toString();
                    SQLiteDatabase database = dbHelper.getWritableDatabase();


                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DBHelper.KEY_ID, userId);
                    contentValues.put(DBHelper.KEY_SYSTOLIC, iSystolic);
                    contentValues.put(DBHelper.KEY_DIASTOLIC, iDiastolic);
                    contentValues.put(DBHelper.KEY_PULSE, iPulse);
                    contentValues.put(DBHelper.KEY_TACHYCARDIA, bTachycardia);
                    contentValues.put(DBHelper.KEY_DATE, sDate);
                    database.insert(DBHelper.TABLE_HEART, null, contentValues);
                    warning.setVisibility(View.INVISIBLE);
                } catch (NumberFormatException e){
                    warning.setVisibility(View.VISIBLE);
                }

                systolic.setText("");
                diastolic.setText("");
                pulse.setText("");
                tachycardia.setChecked(false);
            }
        };



        View.OnClickListener oclHistory = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                systolic.setVisibility(View.INVISIBLE);
                diastolic.setVisibility(View.INVISIBLE);
                pulse.setVisibility(View.INVISIBLE);
                tachycardia.setVisibility(View.INVISIBLE);
                date.setVisibility(View.INVISIBLE);
                save.setVisibility(View.INVISIBLE);
                history.setVisibility(View.INVISIBLE);
                historyView.setVisibility(View.VISIBLE);
                warning.setVisibility(View.INVISIBLE);
                String text = "\n\n";


                SQLiteDatabase database = dbHelper.getWritableDatabase();
                Cursor cursor = database.rawQuery("SELECT " + DBHelper.KEY_SYSTOLIC + ", " + DBHelper.KEY_DIASTOLIC + ", " + DBHelper.KEY_PULSE +
                        ", " + DBHelper.KEY_TACHYCARDIA + ", " + DBHelper.KEY_DATE + " FROM " + DBHelper.TABLE_HEART +
                        " WHERE " + DBHelper.KEY_ID + " = " + userId +  " ORDER BY " + DBHelper.KEY_DATE, null);
                cursor.moveToFirst();
                while (cursor.moveToNext()) {
                    String currText = text;
                    String tachycardia;
                    if (cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_TACHYCARDIA)) == 1){
                        tachycardia = "есть";
                    } else
                        tachycardia = "нет";
                    text = currText + "Дата: " + cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DATE)) +
                            "\n   Давление: " + cursor.getString(cursor.getColumnIndex(DBHelper.KEY_SYSTOLIC)) + "/" + cursor.getString(cursor.getColumnIndex(DBHelper.KEY_SYSTOLIC)) +
                            "\n   Пульс: " + cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PULSE)) +
                            "\n   Тахикардия: " + tachycardia  + "\n\n";
                }

                text = text + "\n\n\n";
                historyView.setText(text);
            }
        };



        View.OnClickListener oclBack = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };



        save.setOnClickListener(oclSave);
        history.setOnClickListener(oclHistory);
        back.setOnClickListener(oclBack);

    }



    public void setDate(View v) {
        new DatePickerDialog(HeartActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }



    private void setInitialDateTime() {
        SimpleDateFormat form = new SimpleDateFormat("dd.MM.yyyy");
        sDate = form.format(dateAndTime.getTimeInMillis());
        date.setText(sDate);
    }



    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };
}

