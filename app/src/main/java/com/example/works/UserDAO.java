package com.example.works;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class UserDAO extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DBNAME = "user.db";
    public static final String TABLE_NAME = "users";
    public static final String USER_NAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String IS_ADMIN = "isadmin";

    /**
     * Konstruktorius, kuris inicializuoja UserDAO objektą.
     * Jis kviečia tevinės klasės konstruktorių ir nustato duomenų bazės pavadinimą (DBNAME) bei versiją (DATABASE_VERSION).
     * @param context
     */

    public UserDAO(Context context) {
        super(context, DBNAME, null, DATABASE_VERSION);
    }

    /**
     * Metodas, kuris sukuria lentelę duomenų bazėje.
     * Sukuria "users" lentelę su stulpeliais: "user_name" (teksto tipas, pagrindinis raktas), "password" (teksto tipas), "email" (teksto tipas) ir "is_admin" (teksto tipas).
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + USER_NAME + " TEXT PRIMARY KEY," + PASSWORD + " TEXT, " + EMAIL + " TEXT, " + IS_ADMIN + " TEXT" + ")";

        db.execSQL(CREATE_USERS_TABLE);

    }

    /**
     * Metodas, kuris atnaujina duomenų bazės schemą.
     * Ištrina esamą "users" lentelę (jei ji egzistuoja) ir vėl sukuria ją iš naujo, naudodamas onCreate metodą.
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
     * Metodas, kuris įterpia naują vartotojo įrašą į duomenų bazę.
     * Jis paima vartotojo duomenis ir įrašo juos į "users" lentelę.
     * Slaptažodis yra saugomas su slaptu hash'u.
     *
     * @param users
     * @return - Grąžina true, jei įrašymas buvo sėkmingas, arba false, jei niekas nebuvo įrašyta.
     */
    public Boolean insertData(User users) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, users.getUsername());

        String hashedPassword = BCrypt.hashpw(users.getPassword(), BCrypt.gensalt(12));
        contentValues.put(PASSWORD, hashedPassword);

        //contentValues.put(PASSWORD, users.getPassword());
        contentValues.put(EMAIL, users.getEmail());
        contentValues.put(IS_ADMIN, users.isAdmin() ? 1 : 0);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == 1)
            return false;
        else
            return true;
    }

    /**
     * Metodas, kuris patikrina, ar duomenų bazėje yra vartotojas su nurodytu vartotojo vardu.
     * @param users
     * @return - Grąžina true, jei vartotojas egzistuoja, arba false, jei vartotojas neegzistuoja.
     */

    public Boolean checkUserName(User users) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + USER_NAME + " = ?", new String[]{users.getUsername()});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;

    }

    /**
     * Metodas, kuris patikrina, ar duomenų bazėje yra vartotojas su nurodytu vartotojo vardu ir slaptažodžiu.
     * @param users
     * @return - Grąžina true, jei vartotojas su nurodytais prisijungimo duomenimis egzistuoja,
     * arba false, jei vartotojas neegzistuoja arba slaptažodis neteisingas.
     */

    public Boolean checkUserNamePassword(User users) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + USER_NAME + " = ? and " + PASSWORD + " = ?", new String[]{users.getUsername(), users.getPassword()});
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + USER_NAME + " = ?", new String[]{users.getUsername()});

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();
            int passwordColumnIndex = cursor.getColumnIndexOrThrow(PASSWORD);
            String storedHashedPassword = cursor.getString(passwordColumnIndex);
            return BCrypt.checkpw(users.getPassword(), storedHashedPassword);
        }

           // return true;
        else
            return false;
    }

    /**
     * Metodas, kuris patikrina, ar duomenų bazėje yra vartotojas su nurodytu vartotojo vardu ir slaptažodžiu, ir ar jis yra administratorius.
     * @param users
     * @return - Grąžina true, jei vartotojas su nurodytais prisijungimo duomenimis egzistuoja ir yra administratorius,
     * arba false, jei vartotojas neegzistuoja arba nėra administratorius.
     */

    public Boolean checkUserNamePasswordAdmin(User users) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + USER_NAME + " = ? and " + PASSWORD + " = ? and " + IS_ADMIN + " = 1 ", new String[]{users.getUsername(), users.getPassword()});
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + USER_NAME + " = ? and " + IS_ADMIN + " = 1", new String[]{users.getUsername()});
        if (cursor.getCount() > 0) {

            cursor.moveToFirst();
            int passwordColumnIndex = cursor.getColumnIndexOrThrow(PASSWORD);
            String storedHashedPassword = cursor.getString(passwordColumnIndex);
            return BCrypt.checkpw(users.getPassword(), storedHashedPassword);
        }

        //    return true;
        else
            return false;
    }

    /**
     * Metodas, kuris skaito visus vartotojus iš "users" lentelės ir sukuria User objektus su gautais duomenimis.
     * @return - Grąžina sąrašą visų vartotojų, esančių duomenų bazėje.
     */

    public List<User> readAllUsers() {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                User users = new User();
                users.setUsername(cursor.getString(0));
                users.setEmail(cursor.getString(2));
                String isAdminValue = cursor.getString(3);
                boolean isAdmin = isAdminValue.equals("1");
                users.setAdmin(isAdmin);

                userList.add(users);
            }
            while (cursor.moveToNext());
        }
        db.close();

        return userList;
    }


    /**
     * Metodas, kuris ištrina vartotojo duomenis iš duomenų bazės pagal nurodytą vartotojo vardą.
     * Ištrina atitinkamą įrašą iš "users" lentelės.
     * @param username
     * @return - Grąžina true, jei ištrynimas buvo sėkmingas, arba false, jei niekas nebuvo ištrinta.
     */

    public boolean deleteUserData(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, USER_NAME + "=?", new String[]{username});
        db.close();
        return result > 0;
    }

}
