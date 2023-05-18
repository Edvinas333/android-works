package com.example.works;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class WorkDAO extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "work.db";
    public static final String TABLE_NAME = "works";
    public static final String ID = "id";
    public static final String COMMENT = "comment";


    /**
     * Konstruktorius, kuris inicializuoja WorkDAO objektą.
     * Jis kviečia tevinės klasės konstruktorių ir nustato duomenų bazės pavadinimą (DATABASE_NAME) bei versiją (DATABASE_VERSION).
     * @param context
     */

    public WorkDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Metodas, kuris sukuria lentelę duomenų bazėje, jei ji dar neegzistuoja.
     * Sukuria "works" lentelę su stulpeliais: "id" (sveikas skaičius, pagrindinis raktas) ir "comment" (teksto tipas).
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORKS_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY," + COMMENT + " TEXT " + ")";
        db.execSQL(CREATE_WORKS_TABLE);
    }

    /**
     * Metodas, kuris atnaujina duomenų bazės schemą.
     * Jis ištrina esamą "works" lentelę (jei ji egzistuoja) ir vėl sukuria ją iš naujo, naudodamas onCreate metodą.
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Metodas, kuris įterpia naują darbo įrašą į duomenų bazę.
     * Jis paima darbo objekto duomenis ir įrašo juos į "works" lentelę.
     * @param works
     * @return - Grąžina true, jei įrašymas buvo sėkmingas, arba false, jei niekas nebuvo įrašyta.
     */

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

    /**
     * Metodas, kuris grąžina sąrašą visų darbų įrašų, esančių duomenų bazėje.
     * Skaito visus įrašus iš "works" lentelės ir sukuria Work objektus su gautais duomenimis.
     * @return
     */

    public List<Work> readAll() {
        List<Work> workList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Work works = new Work();
                works.setId(Integer.parseInt(cursor.getString(0)));
                works.setComment(cursor.getString(1));

                workList.add(works);
            }
            while (cursor.moveToNext());
        }
        db.close();

        return workList;
    }

    /**
     * Metodas, kuris ištrina darbo įrašą iš duomenų bazės pagal nurodytą ID.
     * Ištrina atitinkamą įrašą iš "works" lentelės.
     * @param id
     * @return - Grąžina true, jei ištrynimas buvo sėkmingas, arba false, jei niekas nebuvo ištrinta.
     */

    public boolean deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    /**
     * Metodas, kuris atnaujina darbo įrašą duomenų bazėje.
     * Atnaujina "works" lentelėje esantį įrašą, pakeisdamas komentaro reikšmę pagal nurodytą darbo objektą.
     * @param work
     * @return - Grąžina true, jei atnaujinimas buvo sėkmingas, arba false, jei nieko nebuvo atnaujinta.
     */

    public boolean updateData(Work work) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMMENT, work.getComment());
        int result = db.update(TABLE_NAME, contentValues, ID + "=?", new String[]{String.valueOf(work.getId())});
        db.close();
        return result > 0;
    }


}
