package com.example.s7_cafe;


import android.provider.BaseColumns;

public final class UserInputContract {

    private UserInputContract() {}

    public static class UserTable implements BaseColumns {
        public static final String TABLE_USER = "user_input";
        public static final String COLUMN_NAME = "menu_name";
        public static final String COLUMN_CUP = "cup";
        public static final String COLUMN_COFFEE = "coffee";
        public static final String COLUMN_ICE = "ice";
        public static final String COLUMN_BASE = "base";
        public static final String COLUMN_INGREDIENT1 = "ingredient1";
        public static final String COLUMN_INGREDIENT2 = "ingredient2";
        public static final String COLUMN_INGREDIENT3 = "ingredient3";

        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

        public static final String SQL_CREATE_USER_TABLE = "CREATE TABLE " +
                MenuContract.MenuTable.TABLE_USER + " ( " +
                MenuContract.MenuTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MenuContract.MenuTable.COLUMN_NAME + " INTEGER, " +
                MenuContract.MenuTable.COLUMN_CUP + " INTEGER, " +
                MenuContract.MenuTable.COLUMN_BASE + " INTEGER, " +
                MenuContract.MenuTable.COLUMN_COFFEE + " INTEGER, " +
                MenuContract.MenuTable.COLUMN_ICE + " INTEGER, " +
                MenuContract.MenuTable.COLUMN_INGREDIENT1 + " INTEGER, " +
                MenuContract.MenuTable.COLUMN_INGREDIENT2 + " INTEGER, " +
                MenuContract.MenuTable.COLUMN_INGREDIENT3 + " INTEGER" + ")";
    }
}
