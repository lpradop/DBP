package com.example.carteraclientes.database;

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

    public void insert(String name, String address, String email, String phone) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.NAME, name);
        contentValue.put(DatabaseHelper.ADDRESS, address);
        contentValue.put(DatabaseHelper.EMAIL, email);
        contentValue.put(DatabaseHelper.PHONE, phone);

        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[]{DatabaseHelper._ID,DatabaseHelper.NAME, DatabaseHelper.ADDRESS, DatabaseHelper.EMAIL,DatabaseHelper.PHONE};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long id,String name, String address,String email,String phone) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME, name);
        contentValues.put(DatabaseHelper.ADDRESS,address );
        contentValues.put(DatabaseHelper.EMAIL,email );
        contentValues.put(DatabaseHelper.PHONE,phone );


        return database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + id, null);
    }

    public void delete(String name) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.NAME + "=?", new String[]{name});
    }
    public Cursor getByName(String name){
        String[] columns = new String[]{DatabaseHelper.NAME, DatabaseHelper.ADDRESS, DatabaseHelper.EMAIL,DatabaseHelper.PHONE};
        String[] args=new String[]{name};
        Cursor cursor= database.query(DatabaseHelper.TABLE_NAME,columns,DatabaseHelper.NAME+"=?",args,null,null,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

}

