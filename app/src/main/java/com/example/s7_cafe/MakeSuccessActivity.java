
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
    String name;
    ArrayList<String> cus1, cus2, cus3,cus4, cus6, cus8, cus10, black;
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

        cus1 = sharedPreference.getStringArrayPref(this, "list");
        cus2 = sharedPreference.getStringArrayPref(this,"list2");
        cus3 = sharedPreference.getStringArrayPref(this,"list3");
        cus4 = sharedPreference.getStringArrayPref(this,"list4");
        cus6 = sharedPreference.getStringArrayPref(this,"list6");
        cus8 = sharedPreference.getStringArrayPref(this,"list8");
        cus10 = sharedPreference.getStringArrayPref(this,"list10");
        black = sharedPreference.getStringArrayPref(this,"black");

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

                    if(count == 5) {
                        Intent intent = new Intent(MakeSuccessActivity.this, DayActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (count ==11){
                        Intent intent = new Intent(MakeSuccessActivity.this, DayActivity.class);
                       // sharedPreference.setInt(MakeSuccessActivity.this,"count",6);
                        startActivity(intent);
                        finish();
                    } else if (count ==17){
                        Intent intent = new Intent(MakeSuccessActivity.this, DayActivity.class);
                        // sharedPreference.setInt(MakeSuccessActivity.this,"count",6);
                        startActivity(intent);
                        finish();
                    } else if (count ==24){
                        Intent intent = new Intent(MakeSuccessActivity.this, DayActivity.class);
                        // sharedPreference.setInt(MakeSuccessActivity.this,"count",6);
                        startActivity(intent);
                        finish();
                    } else if (count ==31){
                        Intent intent = new Intent(MakeSuccessActivity.this, DayActivity.class);
                        // sharedPreference.setInt(MakeSuccessActivity.this,"count",6);
                        startActivity(intent);
                        finish();
                    } else if (count ==39){
                        Intent intent = new Intent(MakeSuccessActivity.this, DayActivity.class);
                        // sharedPreference.setInt(MakeSuccessActivity.this,"count",6);
                        startActivity(intent);
                        finish();
                    } else if (count ==48){
                        Intent intent = new Intent(MakeSuccessActivity.this, DayActivity.class);
                        // sharedPreference.setInt(MakeSuccessActivity.this,"count",6);
                        startActivity(intent);
                        finish();
                    } else if (count ==58){
                        Intent intent = new Intent(MakeSuccessActivity.this, DayActivity.class);
                        // sharedPreference.setInt(MakeSuccessActivity.this,"count",6);
                        startActivity(intent);
                        finish();
                    } else if (count ==68){
                        Intent intent = new Intent(MakeSuccessActivity.this, DayActivity.class);
                        // sharedPreference.setInt(MakeSuccessActivity.this,"count",6);
                        startActivity(intent);
                        finish();
                    }else if (count ==79){
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



                SharedPreferenceUtill.setBoolean(MakeSuccessActivity.this, "savename", true);
                SharedPreferenceUtill.setBoolean(this,"next", true);
        name = sharedPreference.getString(MakeSuccessActivity.this, "name");
                switch (name) {
                    case "00":
                        cus1.set(0, "빈칸");
                        break;
                    case "01":
                        cus1.set(1, "빈칸");
                        break;
                    case "02":
                        cus1.set(2, "빈칸");
                        break;
                    case "03":
                        cus1.set(3, "빈칸");
                        break;
                    case "04":
                        cus1.set(4, "빈칸");
                        break;
                    case "05":
                        cus1.set(5, "빈칸");
                        break;
                    case "06":
                        cus1.set(6, "빈칸");
                        break;
                    case "07":
                        cus1.set(7, "빈칸");
                        break;
                    case "08":
                        cus1.set(8, "빈칸");
                        break;
                    case "09":
                        cus1.set(9, "빈칸");
                    case "0":
                        cus1.set(10, "빈칸");
                        break;
                    case "1":
                        cus1.set(11, "빈칸");
                        break;
                    case "2":
                        cus1.set(12, "빈칸");
                        break;
                    case "4":
                        cus1.set(13, "빈칸");
                        break;
                    case "13":
                        cus1.set(14, "빈칸");
                        break;
                    case "14":
                        cus1.set(15, "빈칸");
                        break;
                    case "3":
                        cus2.set(0,"빈칸");
                        break;
                    case "5":
                        cus2.set(1,"빈칸");
                        break;
                    case "6":
                        cus2.set(2,"빈칸");
                        break;
                    case "7":
                        cus2.set(3,"빈칸");
                        break;
                    case "8":
                        cus2.set(4,"빈칸");
                        break;
                    case "9":
                        cus2.set(5,"빈칸");
                        break;
                    case "10":
                        cus2.set(6,"빈칸");
                        break;
                    case "11":
                        cus2.set(7,"빈칸");
                        break;
                    case "12":
                        cus2.set(8,"빈칸");
                        break;
                    case "010":
                        cus2.set(9,"빈칸");
                        break;
                    case "011":
                        cus2.set(10,"빈칸");
                        break;
                    case "012":
                        cus2.set(11,"빈칸");
                        break;
                    case "013":
                        cus2.set(12,"빈칸");
                        break;
                    case "014":
                        cus2.set(13,"빈칸");
                        break;
                    case "15":
                        cus3.set(0,"빈칸");
                        break;
                    case "16":
                        cus3.set(1,"빈칸");
                        break;
                    case "17":
                        cus3.set(2,"빈칸");
                        break;
                    case "18":
                        cus3.set(3,"빈칸");
                        break;
                    case "19":
                        cus3.set(4,"빈칸");
                        break;
                    case "015":
                        cus3.set(5,"빈칸");
                        break;
                    case "016":
                        cus3.set(6,"빈칸");
                        break;
                    case "017":
                        cus3.set(7,"빈칸");
                        break;
                    case "018":
                        cus3.set(8,"빈칸");
                        break;
                    case "019":
                        cus4.set(0,"빈칸");
                        break;
                    case "020":
                        cus4.set(1,"빈칸");
                        break;
                    case "021":
                        cus4.set(2,"빈칸");
                        break;
                    case "022":
                        cus4.set(3,"빈칸");
                        break;
                    case "0222":
                        cus4.set(4,"빈칸");
                        break;
                    case "023":
                        cus4.set(5,"빈칸");
                        break;
                    case "024":
                        cus4.set(6,"빈칸");
                        break;
                    case "025":
                        cus4.set(7,"빈칸");
                        break;
                    case "026":
                        cus4.set(8,"빈칸");
                        break;
                    case "027":
                        cus4.set(9,"빈칸");
                        break;
                    case "028":
                        cus4.set(10,"빈칸");
                        break;
                    case "029":
                        cus4.set(11,"빈칸");
                        break;
                    case "030":
                        cus4.set(12,"빈칸");
                        break;
                    case "031":
                        cus6.set(0,"빈칸");
                        break;
                    case "032":
                        cus6.set(1,"빈칸");
                        break;
                    case "033":
                        cus6.set(2,"빈칸");
                        break;
                    case "034":
                        cus6.set(3,"빈칸");
                        break;
                    case "035":
                        cus6.set(4,"빈칸");
                        break;
                    case "036":
                        cus6.set(5,"빈칸");
                        break;
                    case "037":
                        cus6.set(6,"빈칸");
                        break;
                    case "038":
                        cus6.set(7,"빈칸");
                        break;
                    case "039":
                        cus8.set(0,"빈칸");
                        break;
                    case "040":
                        cus8.set(1,"빈칸");
                        break;
                    case "041":
                        cus8.set(2,"빈칸");
                        break;
                    case "042":
                        cus8.set(3,"빈칸");
                        break;
                    case "043":
                        cus8.set(4,"빈칸");
                        break;
                    case "044":
                        cus8.set(5,"빈칸");
                        break;
                    case "045":
                        cus8.set(6,"빈칸");
                        break;
                    case "046":
                        cus8.set(7,"빈칸");
                        break;
                    case "047":
                        cus8.set(8,"빈칸");
                        break;
                    case "048":
                        cus8.set(9,"빈칸");
                        break;
                    case "049":
                        cus8.set(10,"빈칸");
                        break;
                    case "050":
                        cus8.set(11,"빈칸");
                        break;
                    case "20":
                        cus10.set(0,"빈칸");
                        break;
                    case "21":
                        cus10.set(1,"빈칸");
                        break;
                    case "22":
                        cus10.set(2,"빈칸");
                        break;
                    case "23":
                        cus10.set(3,"빈칸");
                        break;
                    case "24":
                        cus10.set(4,"빈칸");
                        break;
                    case "25":
                        cus10.set(5,"빈칸");
                        break;
                    case "26":
                        cus10.set(6,"빈칸");
                        break;
                    case "27":
                        cus10.set(7,"빈칸");
                        break;
                    case "28":
                        cus10.set(8,"빈칸");
                        break;
                    case "29":
                        cus10.set(9,"빈칸");
                        break;
                    case "051":
                        cus10.set(10,"빈칸");
                        break;
                    case "052":
                        cus10.set(11,"빈칸");
                        break;
                    case "053":
                        cus10.set(12,"빈칸");
                        break;
                    case "054":
                        cus10.set(13,"빈칸");
                        break;
                    case "055":
                        cus10.set(14,"빈칸");
                        break;
                    case "056":
                        cus10.set(15,"빈칸");
                        break;
                    case "057":
                        cus10.set(16,"빈칸");
                        break;
                    case "058":
                        cus10.set(17,"빈칸");
                        break;
                    case "b0":
                        black.set(0,"빈칸");
                        break;
                    case "b1":
                        black.set(1,"빈칸");
                        break;
                    case "b2":
                        black.set(2,"빈칸");
                        break;
                    case "b3":
                        black.set(3,"빈칸");
                        break;
                    case "b4":
                        black.set(4,"빈칸");
                        break;
                    case "b5":
                        black.set(5,"빈칸");
                        break;
                    case "b6":
                        black.set(6,"빈칸");
                        break;
                    case "b7":
                        black.set(7,"빈칸");
                        break;
                    case "b8":
                        black.set(8,"빈칸");
                        break;
                    case "b9":
                        black.set(9,"빈칸");
                        break;
                    case "b10":
                        black.set(10,"빈칸");
                        break;
                    case "b11":
                        black.set(11,"빈칸");
                        break;


                }
                sharedPreference.setStringArrayPref(MakeSuccessActivity.this, "list",cus1);
                sharedPreference.setStringArrayPref(MakeSuccessActivity.this, "list2",cus2);
                sharedPreference.setStringArrayPref(MakeSuccessActivity.this, "list3",cus3);
                sharedPreference.setStringArrayPref(MakeSuccessActivity.this, "list4",cus4);
                sharedPreference.setStringArrayPref(MakeSuccessActivity.this,"list6",cus6);
                sharedPreference.setStringArrayPref(MakeSuccessActivity.this,"list8",cus8);
                sharedPreference.setStringArrayPref(MakeSuccessActivity.this, "list10", cus10);
                sharedPreference.setStringArrayPref(MakeSuccessActivity.this,"black",black);


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