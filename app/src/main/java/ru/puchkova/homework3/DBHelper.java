package ru.puchkova.homework3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "contactDb";
    public static final String TABLE_CONTACTS = "contacts";
    public static final String TABLE_HEART = "heart";
    public static final String TABLE_HEALTH = "health";

    public static final String KEY_ID = "_id";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_NAME = "name";
    public static final String KEY_PATRONYMIC = "patronymic";
    public static final String KEY_AGE = "age";

    public static final String KEY_SYSTOLIC = "systolic"; //систолическое
    public static final String KEY_DIASTOLIC = "diastolic"; //диастолическое
    public static final String KEY_PULSE = "pulse";
    public static final String KEY_TACHYCARDIA = "tachycardia";
    public static final String KEY_DATE = "date";

    public static final String KEY_WEIGHT = "weight";
    public static final String KEY_STEPS = "steps";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CONTACTS + "(" + KEY_ID + " integer primary key, "
                + KEY_SURNAME + " text, " + KEY_NAME + " text, " + KEY_PATRONYMIC + " text, " + KEY_AGE + " integer, UNIQUE(" + KEY_SURNAME + ", " + KEY_NAME +
                ", " + KEY_PATRONYMIC + ", " + KEY_AGE + ")" + ")");

        db.execSQL("create table " + TABLE_HEART + "(" + KEY_ID + " integer, "
                + KEY_SYSTOLIC + " integer, " + KEY_DIASTOLIC + " integer, " + KEY_PULSE + " integer, "
                + KEY_TACHYCARDIA + " numeric, " + KEY_DATE + " string" + ")");

        db.execSQL("create table " + TABLE_HEALTH + "(" + KEY_ID + " integer, "
                + KEY_WEIGHT + " real, " + KEY_STEPS + " integer, " + KEY_DATE + " string" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CONTACTS);
        onCreate(db);
    }
}
