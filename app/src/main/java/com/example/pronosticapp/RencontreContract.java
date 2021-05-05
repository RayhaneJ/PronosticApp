package com.example.pronosticapp;

import android.provider.BaseColumns;

public final class RencontreContract {
    public RencontreContract() {}

    public static class RecontreEntry implements BaseColumns {
        public static final String TABLE_NAME = "rencontre";
        public static final String COLUMN_NAME_NOM = "nom";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_EQUIPE_LOC = "equipeLocale";
        public static final String COLUMN_NAME_EQUIPE_VIS = "equipeVisiteur";
        public static final String COLUMN_NAME_EQUIPE_FAV = "equipeFavorite";
        public static final String COLUMN_NAME_CHAMPIONNAT = "championnat";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + RencontreContract.RecontreEntry.TABLE_NAME + " (" +
                    RencontreContract.RecontreEntry._ID + " INTEGER PRIMARY KEY," +
                    RencontreContract.RecontreEntry.COLUMN_NAME_NOM + " TEXT," +
                    RencontreContract.RecontreEntry.COLUMN_NAME_DATE + " TEXT," +
                    RencontreContract.RecontreEntry.COLUMN_NAME_EQUIPE_LOC + " TEXT," +
                    RencontreContract.RecontreEntry.COLUMN_NAME_EQUIPE_VIS + " TEXT," +
                    RencontreContract.RecontreEntry.COLUMN_NAME_EQUIPE_FAV + " TEXT," +
                    RencontreContract.RecontreEntry.COLUMN_NAME_CHAMPIONNAT + " TEXT)";


    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + RencontreContract.RecontreEntry.TABLE_NAME;
}
