package com.example.pronosticapp;

import android.provider.BaseColumns;

public final class UserContract {
    public UserContract() {}

    public static class UserEntry implements BaseColumns{
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_MAIL = "mail";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_NOM = "nom";
        public static final String COLUMN_NAME_PRENOM = "prenom";
        public static final String COLUMN_NAME_ROLE = "role";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                    UserEntry._ID + " INTEGER PRIMARY KEY," +
                    UserEntry.COLUMN_NAME_MAIL + " TEXT," +
                    UserEntry.COLUMN_NAME_PASSWORD + " TEXT," +
                    UserEntry.COLUMN_NAME_NOM + " TEXT," +
                    UserEntry.COLUMN_NAME_PRENOM + " TEXT," +
                    UserEntry.COLUMN_NAME_ROLE + " TEXT)";


    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;

}
