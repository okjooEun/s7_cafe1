package com.example.s7_cafe;


import android.provider.BaseColumns;

public final class MenuContract {

    private MenuContract() {}

    public static class MenuTable implements BaseColumns {
        public static final String TABLE_DEFAULT = "menu_recipe";
        public static final String TABLE_USER = "user_input";
        public static final String COLUMN_NAME = "menu_name";
        public static final String COLUMN_CUP = "cup";
        public static final String COLUMN_COFFEE = "coffee";
        public static final String COLUMN_ICE = "ice";
        public static final String COLUMN_BASE = "base";
        public static final String COLUMN_INGREDIENT1 = "ingredient1";
        public static final String COLUMN_INGREDIENT2 = "ingredient2";
        public static final String COLUMN_INGREDIENT3 = "ingredient3";

    }

}