package ru.puchkova.homework3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HealthActivity extends AppCompatActivity {
    Button save, history, back;
    EditText weight, steps, date;

    DBHeart dbHealth;

    String sDate;
    int userId, iSteps;
    float fWeight;
    Calendar dateAndTime=Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        Intent intent = getIntent();

        userId = intent.getIntExtra("userId", userId);

        save = (Button) findViewById(R.id.save);
        history = (Button) findViewById(R.id.history);
        back = (Button) findViewById(R.id.back);
        weight = (EditText) findViewById(R.id.weight);
        steps = (EditText) findViewById(R.id.steps);
        date = (EditText) findViewById(R.id.date);
        dbHealth = new DBHeart(this);

        setInitialDateTime();
        View.OnClickListener oclSave = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fWeight = Float.parseFloat(weight.getText().toString());
                iSteps = Integer.parseInt(steps.getText().toString());
                sDate = date.getText().toString();


                SQLiteDatabase database = dbHealth.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(DBHealth.KEY_ID, userId);
                contentValues.put(DBHealth.KEY_WEIGHT, fWeight);
                contentValues.put(DBHealth.KEY_STEPS, iSteps);
                contentValues.put(DBHealth.KEY_DATE, sDate);

                database.insert(DBHealth.TABLE_CONTACTS, null, contentValues);
            }
        };

        View.OnClickListener oclHistory = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        new DatePickerDialog(HealthActivity.this, d,
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
