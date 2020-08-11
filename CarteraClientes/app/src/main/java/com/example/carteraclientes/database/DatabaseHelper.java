package com.example.carteraclientes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "CLIENTS.DB";

    static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "Clients";

    public static final String _ID = "_id";
    public static final String NAME = "Name";
    public static final String ADDRESS = "Address";
    public static final String EMAIL = "Email";
    public static final String PHONE = "Phone";
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME
            + " TEXT NOT NULL, " + ADDRESS + " TEXT, " + EMAIL + " TEXT," + PHONE + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
