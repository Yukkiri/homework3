package ru.puchkova.homework3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

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
        setInitialDateTime();

        Intent intent = getIntent();

        userId = intent.getIntExtra("userId", userId);

        dbHelper = new DBHelper(this);

        View.OnClickListener oclSave = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
