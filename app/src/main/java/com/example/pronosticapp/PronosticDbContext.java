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
        values.put(UserContract.UserEntry.COLUMN_NAME_PASSWORD, user.getMotDePasse());
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
        values.put(UserContract.UserEntry.COLUMN_NAME_PASSWORD, user.getMotDePasse());
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
                UserContract.UserEntry.COLUMN_NAME_PASSWORD,
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
        String password = cursor.getString(
                cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_PASSWORD));
        String nom = cursor.getString(
                cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_NOM));
        String prenom = cursor.getString(
                cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_PRENOM));
        String role = cursor.getString(
                cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_ROLE));

        return new User(id, email, password, nom, prenom, Role.valueOf(role));
    }

    public long insertRencontre(Rencontre rencontre){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RencontreContract.RecontreEntry.COLUMN_NAME_NOM, rencontre.getNom());
        values.put(RencontreContract.RecontreEntry.COLUMN_NAME_DATE, rencontre.getDate());
        values.put(RencontreContract.RecontreEntry.COLUMN_NAME_CHAMPIONNAT, rencontre.getChampionnat());
        values.put(RencontreContract.RecontreEntry.COLUMN_NAME_EQUIPE_LOC, rencontre.getEquipeLocal());
        values.put(RencontreContract.RecontreEntry.COLUMN_NAME_EQUIPE_VIS, rencontre.getEquipeVisiteur());
        values.put(RencontreContract.RecontreEntry.COLUMN_NAME_EQUIPE_FAV, rencontre.getEquipeFavorite());

        return db.insert(RencontreContract.RecontreEntry.TABLE_NAME, null, values);
    }

    public int deleteRencontre(long id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = RencontreContract.RecontreEntry._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };

        return db.delete(RencontreContract.RecontreEntry.TABLE_NAME, selection, selectionArgs);
    }

    public List<Rencontre> getAllRecontre(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                RencontreContract.RecontreEntry.COLUMN_NAME_NOM,
                RencontreContract.RecontreEntry.COLUMN_NAME_DATE,
                RencontreContract.RecontreEntry.COLUMN_NAME_CHAMPIONNAT,
                RencontreContract.RecontreEntry.COLUMN_NAME_EQUIPE_LOC,
                RencontreContract.RecontreEntry.COLUMN_NAME_EQUIPE_VIS,
                RencontreContract.RecontreEntry.COLUMN_NAME_EQUIPE_FAV,
        };

        Cursor cursor = db.query(
                RencontreContract.RecontreEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null);

        List rencontres = new ArrayList<Rencontre>();
        while(cursor.moveToNext()) {
            long rencontreId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(RencontreContract.RecontreEntry._ID));
            String rencontreName = cursor.getString(
                    cursor.getColumnIndexOrThrow(RencontreContract.RecontreEntry.COLUMN_NAME_NOM));
            String rencontreDate = cursor.getString(
                    cursor.getColumnIndexOrThrow(RencontreContract.RecontreEntry.COLUMN_NAME_DATE));
            String rencontreChampionnat = cursor.getString(
                    cursor.getColumnIndexOrThrow(RencontreContract.RecontreEntry.COLUMN_NAME_CHAMPIONNAT));
            String rencontreEquipeLoc = cursor.getString(
                    cursor.getColumnIndexOrThrow(RencontreContract.RecontreEntry.COLUMN_NAME_EQUIPE_LOC));
            String rencontreEquipeVis = cursor.getString(
                    cursor.getColumnIndexOrThrow(RencontreContract.RecontreEntry.COLUMN_NAME_EQUIPE_VIS));
            String rencontreEquipeFav = cursor.getString(
                    cursor.getColumnIndexOrThrow(RencontreContract.RecontreEntry.COLUMN_NAME_EQUIPE_FAV));
            rencontres.add(new Rencontre(rencontreId, rencontreName, rencontreDate, rencontreChampionnat, rencontreEquipeLoc, rencontreEquipeVis, rencontreEquipeFav));
        }
        cursor.close();

        return rencontres;
    }

    public int updateRencontre(Rencontre rencontre){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RencontreContract.RecontreEntry.COLUMN_NAME_NOM, rencontre.getNom());
        values.put(RencontreContract.RecontreEntry.COLUMN_NAME_DATE, rencontre.getDate());
        values.put(RencontreContract.RecontreEntry.COLUMN_NAME_CHAMPIONNAT, rencontre.getChampionnat());
        values.put(RencontreContract.RecontreEntry.COLUMN_NAME_EQUIPE_LOC, rencontre.getEquipeLocal());
        values.put(RencontreContract.RecontreEntry.COLUMN_NAME_EQUIPE_VIS, rencontre.getEquipeVisiteur());
        values.put(RencontreContract.RecontreEntry.COLUMN_NAME_EQUIPE_FAV, rencontre.getEquipeFavorite());

        String selection = RencontreContract.RecontreEntry._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(rencontre.getId()) };

        return db.update(
                RencontreContract.RecontreEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }
}
