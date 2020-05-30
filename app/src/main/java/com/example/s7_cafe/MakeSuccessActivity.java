
package com.example.s7_cafe;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.s7_cafe.UserInputContract.UserTable.SQL_CREATE_USER_TABLE;
import static com.example.s7_cafe.UserInputContract.UserTable.SQL_DELETE_TABLE;

public class MakeSuccessActivity extends AppCompatActivity {

    SQLiteDatabase db, db2;
    final MenuDbhelper userDbhelper = new MenuDbhelper(this);
    RelativeLayout rel;
    ImageView success;
    String name, blackarray[];
    ArrayList<String> cuslist, cuslist2, cuslist3,cuslist4;
    SharedPreferenceUtill sharedPreference;

    private View    decorView;
    private int   uiOption, count;
    private Integer[] gifs ={R.raw.ms_bl,R.raw.ms_bli,R.raw.ms_cal,R.raw.ms_cali,R.raw.ms_cb,R.raw.ms_cl,R.raw.ms_cli,R.raw.ms_cs,R.raw.ms_cscl,R.raw.ms_cscli,
            R.raw.ms_ic,R.raw.ms_iim,R.raw.ms_ntl,R.raw.ms_ntli,R.raw.ms_sb,R.raw.ms_ss, R.raw.ms_tl,R.raw.ms_tli};

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makesuccess);
        //소프트키(네비게이션바) 없애기 시작
        decorView = getWindow().getDecorView();

        uiOption = getWindow().getDecorView().getSystemUiVisibility();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility(uiOption);
        //소프트키(네비게이션바) 없애기 끝

        final SharedPreferenceUtill sharedPreference = new SharedPreferenceUtill();
        count = sharedPreference.getInt(this, "count");

        cuslist = sharedPreference.getStringArrayPref(this, "list");
        cuslist2 = sharedPreference.getStringArrayPref(this,"list2");
        cuslist3 = sharedPreference.getStringArrayPref(this,"list3");
        cuslist4 = sharedPreference.getStringArrayPref(this,"list4");

        blackarray = getResources().getStringArray(R.array.BLACK);

        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setGravity(Gravity.CENTER);

        int x = (int)200;
        int y = (int)200;
        rel = (RelativeLayout) findViewById(R.id.rel);
        success = (ImageView) findViewById(R.id.success);

        db = userDbhelper.getReadableDatabase();
        db2 = userDbhelper.getWritableDatabase();
        String sql_user = "select * from user_input";
        Cursor c2 = db.rawQuery(sql_user, null);
        final int userCount = c2.getCount();

        Intent intent = getIntent();

        int a = intent.getExtras().getInt("menuname");

        if(a == 1){
            Glide.with(this).load(R.raw.ms_iim).into(success);
        } else if (a == 2) {
            Glide.with(this).load(R.raw.ms_ic).into(success);
        } else if (a == 3) {
            Glide.with(this).load(R.raw.ms_cali).into(success);
        } else if (a == 4) {
            Glide.with(this).load(R.raw.ms_cal).into(success);
        } else if (a == 5){
            Glide.with(this).load(R.raw.ms_bli).into(success);
        } else if (a == 6){
            Glide.with(this).load(R.raw.ms_bl).into(success);
        } else if (a == 7) {
            Glide.with(this).load(R.raw.ms_cs).into(success);
        } else if (a == 8) {
            Glide.with(this).load(R.raw.ms_ss).into(success);
        } else if (a == 9) {
            Glide.with(this).load(R.raw.ms_cscli).into(success);
        } else if (a == 10){
            Glide.with(this).load(R.raw.ms_cscl).into(success);
        } else if (a == 11){
            Glide.with(this).load(R.raw.ms_cli).into(success);
        } else if (a == 12){
            Glide.with(this).load(R.raw.ms_cl).into(success);
        } else if (a == 13) {
            Glide.with(this).load(R.raw.ms_imo).into(success); //모카 이미지 수정
        } else if (a == 14) {
            Glide.with(this).load(R.raw.ms_mo).into(success); //모카 이미지 수정
        } else if (a == 15) {
            Glide.with(this).load(R.raw.ms_cb).into(success);
        } else if (a == 16){
            Glide.with(this).load(R.raw.ms_sb).into(success);
        } else if (a == 17){
            Glide.with(this).load(R.raw.ms_ntli).into(success);
        } else if (a == 18) {
            Glide.with(this).load(R.raw.ms_ntl).into(success);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (userCount == 0) {
                    db2.execSQL(SQL_DELETE_TABLE);
                    db2.execSQL(SQL_CREATE_USER_TABLE);

                    if(count == 3) {
                        Intent intent = new Intent(MakeSuccessActivity.this, DayActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (count ==5){
                        Intent intent = new Intent(MakeSuccessActivity.this, DayActivity.class);
                        startActivity(intent);
                        finish();
                    }else if (count ==7){
                        Intent intent = new Intent(MakeSuccessActivity.this, WhichEndingActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Intent intent = new Intent(MakeSuccessActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    finish();
                }
            }
        }, 1100);


                name = sharedPreference.getString(MakeSuccessActivity.this, "name");
                SharedPreferenceUtill.setBoolean(MakeSuccessActivity.this, "savename", true);
                switch (name) {
                    case "0":
                        cuslist.set(0, "빈칸");
                        break;
                    case "1":
                        cuslist.set(1, "빈칸");
                        break;
                    case "2":
                        cuslist.set(2, "빈칸");
                        break;
                    case "3":
                        cuslist.set(3, "빈칸");
                        break;
                    case "4":
                        cuslist.set(4, "빈칸");
                        break;
                    case "5":
                        cuslist.set(5, "빈칸");
                        break;
                    case "6":
                        cuslist.set(6, "빈칸");
                        break;
                    case "7":
                        cuslist.set(7, "빈칸");
                        break;
                    case "8":
                        cuslist.set(8, "빈칸");
                        break;
                    case "9":
                        cuslist.set(9, "빈칸");
                        break;
                    case "10":
                        cuslist.set(10, "빈칸");
                        break;
                    case "11":
                        cuslist.set(11, "빈칸");
                        break;
                    case "12":
                        cuslist.set(12, "빈칸");
                        break;
                    case "13":
                        cuslist.set(13, "빈칸");
                        break;
                    case "14":
                        cuslist.set(14, "빈칸");
                        break;
                    case "15":
                        cuslist2.set(0, "빈칸");
                        break;
                    case "16":
                        cuslist2.set(1, "빈칸");
                        break;
                    case "17":
                        cuslist2.set(2, "빈칸");
                        break;
                    case "18":
                        cuslist2.set(3, "빈칸");
                        break;
                    case "19":
                        cuslist2.set(4, "빈칸");
                        break;
                    case "20":
                        cuslist3.set(0, "빈칸");
                        break;
                    case "21":
                        cuslist3.set(1, "빈칸");
                        break;
                    case "22":
                        cuslist3.set(2, "빈칸");
                        break;
                    case "23":
                        cuslist3.set(3, "빈칸");
                        break;
                    case "24":
                        cuslist3.set(4, "빈칸");
                        break;
                    case "25":
                        cuslist3.set(5, "빈칸");
                        break;

                    case "26":
                        cuslist3.set(6, "빈칸");
                        break;
                    case "27":
                        cuslist3.set(7, "빈칸");
                        break;
                    case "28":
                        cuslist3.set(8, "빈칸");
                        break;
                    case "29":
                        cuslist3.set(9, "빈칸");
                        break;
                    case "b0":
                        cuslist4.set(0, "빈칸");
                        break;
                    case "b1":
                        cuslist4.set(1, "빈칸");
                        break;
                    case "b2":
                        cuslist4.set(2, "빈칸");
                        break;
                    case "b3":
                        cuslist4.set(3, "빈칸");
                        break;
                    case "b4":
                        cuslist4.set(4, "빈칸");
                        break;
                    case "b5":
                        cuslist4.set(5, "빈칸");
                        break;
                    case "b6":
                        cuslist4.set(6, "빈칸");
                        break;
                    case "b7":
                        cuslist4.set(7, "빈칸");
                        break;
                    case "b8":
                        cuslist4.set(8, "빈칸");
                        break;
                    case "b9":
                        cuslist4.set(9, "빈칸");
                        break;
                    case "b10":
                        cuslist4.set(10, "빈칸");
                        break;
                    case "b11":
                        cuslist4.set(11, "빈칸");
                        break;


                }
            }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        // super.onWindowFocusChanged(hasFocus);

        if( hasFocus ) {
            decorView.setSystemUiVisibility( uiOption );
        }
    }


}