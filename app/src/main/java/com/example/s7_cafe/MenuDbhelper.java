package com.example.s7_cafe;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.s7_cafe.MenuContract.*;
import com.example.s7_cafe.UserInputContract.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.s7_cafe.UserInputContract.UserTable.TABLE_USER;

public class MenuDbhelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Menu.db";
    public static final int DATABASE_VERSION = 1;

    public SQLiteDatabase db;

    public MenuDbhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_DEFAULT_TABLE = "CREATE TABLE " +
                MenuContract.MenuTable.TABLE_DEFAULT + " ( " +
                MenuContract.MenuTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MenuContract.MenuTable.COLUMN_NAME + " INTEGER, " +
                MenuContract.MenuTable.COLUMN_CUP + " INTEGER, " +
                MenuContract.MenuTable.COLUMN_BASE + " INTEGER, " +
                MenuContract.MenuTable.COLUMN_COFFEE + " INTEGER, " +
                MenuContract.MenuTable.COLUMN_ICE + " INTEGER, " +
                MenuContract.MenuTable.COLUMN_INGREDIENT1 + " INTEGER, " +
                MenuContract.MenuTable.COLUMN_INGREDIENT2 + " INTEGER, " +
                MenuContract.MenuTable.COLUMN_INGREDIENT3 + " INTEGER" + ")";

        final String SQL_CREATE_USER_TABLE = "CREATE TABLE " +
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


        db.execSQL(SQL_CREATE_DEFAULT_TABLE);
        db.execSQL(SQL_CREATE_USER_TABLE);
        fillMenuTable();
        fillUserTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public void fillMenuTable() {
        Menu americano_ice = new Menu(1, 100, 102, 105, 104, 0, 0, 0);
        addMenu(americano_ice);
        Menu americano_hot = new Menu(2, 101, 103, 105, 0, 0, 0, 0);
        addMenu(americano_hot);
        Menu latte_ice = new Menu(3,100,106,105,104,0,0,0);
        addMenu(latte_ice);
        Menu latte_hot = new Menu(4, 101, 107, 105, 0, 0, 0, 0);
        addMenu(latte_hot);
        Menu vanila_ice = new Menu(5,100,106,105,104,108,0,0);
        addMenu(vanila_ice);
        Menu vanila_hot = new Menu(6, 101, 107, 105, 0, 108, 0, 0);
        addMenu(vanila_hot);
        Menu smoothie_cho = new Menu(7, 100, 106, 0, 104, 109, 0, 110);
        addMenu(smoothie_cho);
        Menu smoothie_berry = new Menu(8, 100, 106, 0, 104, 111, 0, 110);
        addMenu(smoothie_berry);
        Menu X_choco_ice = new Menu(9,100,106,0,104,109,0,112);
        addMenu(X_choco_ice);
        Menu X_choco_hot = new Menu(10, 101, 107, 0, 0, 109, 0, 112);
        addMenu(X_choco_hot);
        Menu choco_ice = new Menu(11,100,106,0,104,109,0,0);
        addMenu(choco_ice);
        Menu choco_hot = new Menu(12, 101, 107, 0, 0, 109, 0, 0);
        addMenu(choco_hot);
        Menu mocha_ice = new Menu(13,100,106,105,104,109,0,0);
        addMenu(mocha_ice);
        Menu mocha_hot = new Menu(14, 101, 107, 105, 0, 109, 0, 0);
        addMenu(mocha_hot);
        Menu juice_cho = new Menu(15, 100, 106, 0, 0, 109, 113, 110);
        addMenu(juice_cho);
        Menu juice_berry = new Menu(16, 100, 106, 0, 0, 111, 113, 110);
        addMenu(juice_berry);
        Menu toffee_ice = new Menu(17,100,106,105,104,0,114,115);
        addMenu(toffee_ice);
        Menu toffee_hot = new Menu(18, 101, 107, 105, 0, 0, 114, 115);
        addMenu(toffee_hot);
    }

    public void fillUserTable() {
        User_Input user_input = new User_Input(0, 0, 0, 0, 0, 0, 0, 0);
        addIngredient(user_input);
    }



    public void addMenu(Menu menu) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MenuContract.MenuTable.COLUMN_NAME, menu.getMenu_name());
        contentValues.put(MenuContract.MenuTable.COLUMN_CUP, menu.getCup());
        contentValues.put(MenuContract.MenuTable.COLUMN_BASE, menu.getBase());
        contentValues.put(MenuContract.MenuTable.COLUMN_COFFEE, menu.getCoffee());
        contentValues.put(MenuContract.MenuTable.COLUMN_ICE, menu.getIce());
        contentValues.put(MenuContract.MenuTable.COLUMN_INGREDIENT1, menu.getIngredient1());
        contentValues.put(MenuContract.MenuTable.COLUMN_INGREDIENT2, menu.getIngredient2());
        contentValues.put(MenuContract.MenuTable.COLUMN_INGREDIENT3, menu.getIngredient3());

        db.insert(MenuContract.MenuTable.TABLE_DEFAULT, null, contentValues);

    }

    public void addIngredient(User_Input user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserInputContract.UserTable.COLUMN_NAME, user.getMenu_name());
        contentValues.put(UserInputContract.UserTable.COLUMN_CUP, user.getCup());
        contentValues.put(UserInputContract.UserTable.COLUMN_BASE, user.getBase());
        contentValues.put(UserInputContract.UserTable.COLUMN_COFFEE, user.getCoffee());
        contentValues.put(UserInputContract.UserTable.COLUMN_ICE, user.getIce());
        contentValues.put(UserInputContract.UserTable.COLUMN_INGREDIENT1, user.getIngredient1());
        contentValues.put(UserInputContract.UserTable.COLUMN_INGREDIENT2, user.getIngredient2());
        contentValues.put(UserInputContract.UserTable.COLUMN_INGREDIENT3, user.getIngredient3());

        db.insert(TABLE_USER, null, contentValues);

    }


    public List<User_Input> getUserInput() {
        List<User_Input> userList = new ArrayList<>();
        db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + UserTable.TABLE_USER, null);
        if (c.moveToFirst()) {
            do {
                User_Input user = new User_Input();
                user.setMenu_name(c.getInt(c.getColumnIndex(UserInputContract.UserTable.COLUMN_NAME)));
                user.setCup(c.getInt(c.getColumnIndex(UserInputContract.UserTable.COLUMN_CUP)));
                user.setBase(c.getInt(c.getColumnIndex(UserInputContract.UserTable.COLUMN_BASE)));
                user.setCoffee(c.getInt(c.getColumnIndex(UserInputContract.UserTable.COLUMN_COFFEE)));
                user.setIce(c.getInt(c.getColumnIndex(UserInputContract.UserTable.COLUMN_ICE)));
                user.setIngredient1(c.getInt(c.getColumnIndex(UserInputContract.UserTable.COLUMN_INGREDIENT1)));
                user.setIngredient2(c.getInt(c.getColumnIndex(UserInputContract.UserTable.COLUMN_INGREDIENT2)));
                user.setIngredient3(c.getInt(c.getColumnIndex(UserInputContract.UserTable.COLUMN_INGREDIENT3)));
                userList.add(user);
            } while (c.moveToNext());
        }
        c.close();
        return userList;
    }



    public List<Menu> getAllMenu() {
        List<Menu> menuList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " +MenuContract.MenuTable.TABLE_DEFAULT, null);
        if (c.moveToFirst()) {
            do {
                Menu menu = new Menu();
                menu.setMenu_name(c.getInt(c.getColumnIndex(MenuContract.MenuTable.COLUMN_NAME)));
                menu.setCup(c.getInt(c.getColumnIndex(MenuContract.MenuTable.COLUMN_CUP)));
                menu.setBase(c.getInt(c.getColumnIndex(MenuContract.MenuTable.COLUMN_BASE)));
                menu.setCoffee(c.getInt(c.getColumnIndex(MenuContract.MenuTable.COLUMN_COFFEE)));
                menu.setIce(c.getInt(c.getColumnIndex(MenuContract.MenuTable.COLUMN_ICE)));
                menu.setIngredient1(c.getInt(c.getColumnIndex(MenuContract.MenuTable.COLUMN_INGREDIENT1)));
                menu.setIngredient2(c.getInt(c.getColumnIndex(MenuContract.MenuTable.COLUMN_INGREDIENT2)));
                menu.setIngredient3(c.getInt(c.getColumnIndex(MenuContract.MenuTable.COLUMN_INGREDIENT3)));
                menuList.add(menu);

            } while (c.moveToNext());
        }

        c.close();
        return menuList;
    }

    /*
    cup 100 플라스틱 컵 / 101 종이 컵
    base 102 찬 물 / 103 뜨거운 물 / 106 찬 우유 / 107 뜨거운 우유
    ice 104 얼음
    coffee 105 커피
    ingredient1 108 바닐라 / 109 초콜릿 / 111 딸기
    ingredient2 113 바나나 / 114 토피넛
    ingredient3 110 블렌더 / 112 마시멜로우 / 115 쥐쿠키
    */
}