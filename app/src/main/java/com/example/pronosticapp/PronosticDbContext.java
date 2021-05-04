package com.example.pronosticapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.speech.SpeechRecognizer;

import java.util.ArrayList;
import java.util.List;

public class PronosticDbContext {
    private PronosticDbHelper dbHelper;

    public PronosticDbContext(Context context) {
        dbHelper = new PronosticDbHelper(context);
    }

    public long insertUser(User user){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_NAME_MAIL, user.getEmail());
        values.put(UserContract.UserEntry.COLUMN_NAME_NOM, user.getNom());
        values.put(UserContract.UserEntry.COLUMN_NAME_PRENOM, user.getPrenom());
        values.put(UserContract.UserEntry.COLUMN_NAME_ROLE, user.getRole().toString());

        return db.insert(UserContract.UserEntry.TABLE_NAME, null, values);
    }

    public int deleteUser(long id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = UserContract.UserEntry._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };

        return db.delete(UserContract.UserEntry.TABLE_NAME, selection, selectionArgs);
    }

    public int updateUser(User user){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_NAME_MAIL, user.getEmail());
        values.put(UserContract.UserEntry.COLUMN_NAME_NOM, user.getNom());
        values.put(UserContract.UserEntry.COLUMN_NAME_PRENOM, user.getPrenom());
        values.put(UserContract.UserEntry.COLUMN_NAME_ROLE, user.getRole().toString());

        String selection = UserContract.UserEntry._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(user.getId()) };

        return db.update(
                UserContract.UserEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public User getUser(String email){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                UserContract.UserEntry.COLUMN_NAME_MAIL,
                UserContract.UserEntry.COLUMN_NAME_NOM,
                UserContract.UserEntry.COLUMN_NAME_PRENOM,
                UserContract.UserEntry.COLUMN_NAME_ROLE
        };

        String selection = UserContract.UserEntry.COLUMN_NAME_MAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(
                UserContract.UserEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null
        );

        cursor.moveToFirst();

        long id = cursor.getLong(
                cursor.getColumnIndexOrThrow(UserContract.UserEntry._ID));
        String nom = cursor.getString(
                cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_NOM));
        String prenom = cursor.getString(
                cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_PRENOM));
        String role = cursor.getString(
                cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_ROLE));

        return new User(id, email, nom, prenom, Role.valueOf(role));
    }
}
