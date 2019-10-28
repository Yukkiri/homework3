package ru.puchkova.homework3;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHeart extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "heartDb";
    public static final String TABLE_CONTACTS = "heart";

    public static final String KEY_ID = "_id";
    public static final String KEY_SYSTOLIC = "systolic"; //систолическое
    public static final String KEY_DIASTOLIC = "diastolic"; //диастолическое
    public static final String KEY_PULSE = "pulse";
    public static final String KEY_TACHYCARDIA = "tachycardia";
    public static final String KEY_DATE = "date";

    public DBHeart(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CONTACTS + "(" + KEY_ID + " integer, "
                + KEY_SYSTOLIC + " integer, " + KEY_DIASTOLIC + " integer, " + KEY_PULSE + " integer, " + KEY_TACHYCARDIA + " numeric, " + KEY_DATE + " string" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CONTACTS);
        onCreate(db);
    }
}
