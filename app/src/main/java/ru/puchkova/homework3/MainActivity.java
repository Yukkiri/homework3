package ru.puchkova.homework3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import static ru.puchkova.homework3.DBHelper.KEY_AGE;
import static ru.puchkova.homework3.DBHelper.KEY_ID;
import static ru.puchkova.homework3.DBHelper.KEY_NAME;
import static ru.puchkova.homework3.DBHelper.KEY_PATRONYMIC;
import static ru.puchkova.homework3.DBHelper.KEY_SURNAME;
import static ru.puchkova.homework3.DBHelper.TABLE_CONTACTS;

public class MainActivity extends AppCompatActivity {

    ImageButton heart, health;
    Button save;
    EditText surname, name, patronymic, age;
    String sSurname, sName, sPatronymic;
    TextView welcome, warning;
    DBHelper dbHelper;
    int userId, iAge;
    boolean flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        heart = (ImageButton) findViewById(R.id.heart);
        health = (ImageButton) findViewById(R.id.health);
        surname = (EditText) findViewById(R.id.surname);
        name = (EditText) findViewById(R.id.name);
        patronymic = (EditText) findViewById(R.id.patronymic);
        age = (EditText) findViewById(R.id.age);
        save = (Button) findViewById(R.id.save);
        welcome = (TextView) findViewById(R.id.welcome);
        warning = (TextView) findViewById(R.id.warning);


        dbHelper = new DBHelper(this);



        View.OnClickListener oclSave = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    sSurname = surname.getText().toString();
                    sName = name.getText().toString();
                    sPatronymic = patronymic.getText().toString();
                    iAge = Integer.parseInt(age.getText().toString());

                    SQLiteDatabase database = dbHelper.getWritableDatabase();

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DBHelper.KEY_SURNAME, sSurname);
                    contentValues.put(DBHelper.KEY_NAME, sName);
                    contentValues.put(DBHelper.KEY_PATRONYMIC, sPatronymic);
                    contentValues.put(KEY_AGE, iAge);

                    userId = (int) database.insert(TABLE_CONTACTS, null, contentValues);
                    if(userId == -1){
                        Cursor cursor = database.rawQuery("SELECT DISTINCT " + KEY_ID + " FROM " + TABLE_CONTACTS + " WHERE " + KEY_SURNAME + " = '" + sSurname + "' AND " +
                                KEY_NAME + " = '" + sName + "' AND " + KEY_PATRONYMIC + " = '" + sPatronymic + "' AND " + KEY_AGE + " = " + iAge, null);
                        cursor.moveToFirst();
                        userId = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                    }


                    health.setVisibility(View.VISIBLE);
                    heart.setVisibility(View.VISIBLE);
                    welcome.setText(welcome.getText() + sName + "!\n" + userId);

                    surname.setVisibility(View.INVISIBLE);
                    name.setVisibility(View.INVISIBLE);
                    patronymic.setVisibility(View.INVISIBLE);
                    age.setVisibility(View.INVISIBLE);
                    save.setVisibility(View.INVISIBLE);
                    welcome.setVisibility(View.VISIBLE);
                    warning.setVisibility(View.INVISIBLE);
                } catch (NumberFormatException e) {
                    warning.setVisibility(View.VISIBLE);
                }



            }
        };



        View.OnClickListener oclHeart = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), HeartActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        };



        View.OnClickListener oclHealth = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HealthActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        };



        save.setOnClickListener(oclSave);
        heart.setOnClickListener(oclHeart);
        health.setOnClickListener(oclHealth);
    }


}
