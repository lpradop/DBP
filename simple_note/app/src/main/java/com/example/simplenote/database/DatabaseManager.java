package com.example.simplenote.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class DatabaseManager {

    private DatabaseHelper database_helper;

    private Context context;

    private SQLiteDatabase database;

    public DatabaseManager(Context c) {
        context = c;
    }

    public void open() throws SQLException {
        database_helper = new DatabaseHelper(context);
        database = database_helper.getWritableDatabase();
    }

    public void close() {
        database_helper.close();
    }

    public void insert(String file_name) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.FILE_NAME, file_name);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[]{DatabaseHelper._ID, DatabaseHelper.FILE_NAME};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(String file_name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.FILE_NAME, file_name);
        return database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.FILE_NAME + " = " + file_name, null);
    }

    public void delete(String file_name) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.FILE_NAME + "=?", new String[]{file_name});
    }
    public boolean noteExists(String file_name) {
        String[] columns = new String[]{DatabaseHelper.FILE_NAME};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, DatabaseHelper.FILE_NAME+"=?", new String[]{file_name}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return (cursor.getCount()>0);
    }

}

