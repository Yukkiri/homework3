package ru.puchkova.homework3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHealth extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "healthDb";
    public static final String TABLE_CONTACTS = "health";

    public static final String KEY_ID = "_id";
    public static final String KEY_WEIGHT = "weight";
    public static final String KEY_STEPS = "steps";
    public static final String KEY_DATE = "date";


    public DBHealth(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CONTACTS + "(" + KEY_ID + " integer, "
                + KEY_WEIGHT + " real, " + KEY_STEPS + " integer, " + KEY_DATE + " string" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CONTACTS);
        onCreate(db);
    }
}
