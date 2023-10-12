package com.example.qrcodemuseum.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Database.db";
    private static final int DATABASE_VERSION = 1;

    //TODO: Adicionar criação/drop de outras tabelas
    private static final String TABLE_NAME = "User";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createUser());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
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
}
