package com.example.qrcodemuseum.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Database.db";
    private static final int DATABASE_VERSION = 1;

    //TODO: Adicionar criação/drop de outras tabelas
    private static final String TABLE_NAME_USER = "User";
    private static final String TABLE_NAME_ITEM = "Item";
    private static final String TABLE_NAME_CHECKIN = "Checkin";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createUser());
        db.execSQL(createItem());
        db.execSQL(createCheckin());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CHECKIN);
        onCreate(db);
    }

    private String createUser() {
        return "CREATE TABLE " + "User" + " (" +
                "id"        + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username " + " VARCHAR(50) , " +
                "email "    + " VARCHAR(100), " +
                "phone"     + " VARCHAR(20) , " +
                "password"  + " VARCHAR(50) , " +
                "userType"  + " INTEGER);";
    }

    private String createItem() {
        return "CREATE TABLE " + "Item" + " (" +
                "id"          + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title"       + " VARCHAR(50)  , " +
                "year"        + " VARCHAR(4), " +
                "description" + " VARCHAR(500) , " +
                "user_id"     + " INTEGER);";
    }

    private String createCheckin() {
        return "CREATE TABLE " + "Checkin" + " (" +
                "id"          + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "postalCode"  + " VARCHAR(50)  , " +
                "addressLine" + " VARCHAR(200) , " +
                "city"        + " VARCHAR(100) , " +
                "state"       + " VARCHAR(100) , " +
                "country"     + " VARCHAR(100) , " +
                "datetime"    + " VARCHAR(100) , " +
                "user_id"     + " INTEGER);";
    }
}
