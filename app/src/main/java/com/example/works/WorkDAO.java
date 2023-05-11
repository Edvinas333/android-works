package com.example.works;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WorkDAO extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "work.db";
    public static final String TABLE_NAME = "wroks";

    public static final String ID = "id";
    public static final String COMMENT = "comment";


    public WorkDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORKS_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY," + COMMENT + " TEXT " + ")";

        db.execSQL(CREATE_WORKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Boolean insertData(Work works) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMMENT, works.getComment());
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == 1)
            return false;
        else
            return true;
    }

    public List<Work> readAll(){
        List<Work> workList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Work weather = new Work();
                weather.setId(Integer.parseInt(cursor.getString(0)));
                weather.setComment(cursor.getString(1));

                workList.add(weather);
            }
            while (cursor.moveToNext());
        }
        db.close();

        return workList;
    }

    public boolean deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    public boolean updateData(Work work) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMMENT, work.getComment());
        int result = db.update(TABLE_NAME, contentValues, ID + "=?", new String[]{String.valueOf(work.getId())});
        db.close();
        return result > 0;
    }




}
