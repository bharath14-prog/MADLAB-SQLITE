package com.example.ex7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "userdb";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "users";

    // Columns
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_AGE = "age";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_AGE + " INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert user
    public boolean insertUser(String name, int age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_AGE, age);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    // Get all users
    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    // Update user
    public boolean updateUser(int id, String name, int age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_AGE, age);
        int rowsUpdated = db.update(TABLE_NAME, values, COL_ID + " = ?", new String[]{String.valueOf(id)});
        return rowsUpdated > 0;
    }

    // Delete user
    public boolean deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_NAME, COL_ID + " = ?", new String[]{String.valueOf(id)});
        return rowsDeleted > 0;
    }
}
