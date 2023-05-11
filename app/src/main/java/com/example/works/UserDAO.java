package com.example.works;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDAO extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DBNAME = "login.db";
    public static final String TABLE_NAME = "users";

    public static final String USER_NAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";


    public UserDAO(Context context) {
        super(context, DBNAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + USER_NAME +  " TEXT PRIMARY KEY," + PASSWORD + " TEXT, " + EMAIL + " TEXT" + ")" ;

        db.execSQL(CREATE_USERS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

   // public Boolean insertData(String username, String password, String email) {
    public Boolean insertData(User users) {

    SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, users.getUsername());
        contentValues.put(PASSWORD, users.getPassword());
        contentValues.put(EMAIL, users.getEmail());
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == 1)
            return false;
        else
            return true;
    }

    public Boolean checkUserName(User users) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + USER_NAME + " = ?", new String[]{users.getUsername()});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;

    }

    public Boolean checkUserNamePassword(User users) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + USER_NAME + " = ? and " + PASSWORD + " = ?", new String[]{users.getUsername(), users.getPassword()});

        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

}
