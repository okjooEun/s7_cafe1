package com.example.s7_cafe;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.util.ArrayList;

public class TimeOverEndingActivity extends AppCompatActivity {

    LinearLayout lin2;
    ImageView setting;
    private View    decorView;
    private int   uiOption, count;
    ArrayList<String> cuslist, cuslist2, cuslist3,cuslist4;
    String name;

    SharedPreferenceUtill sharedPreference = new SharedPreferenceUtill();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeover);

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



        count = sharedPreference.getInt(this, "count");

        lin2 = (LinearLayout) findViewById(R.id.lin2);
        setting = (ImageView) findViewById(R.id.setting);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeOverEndingActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        cuslist = sharedPreference.getStringArrayPref(this, "list");
        cuslist2 = sharedPreference.getStringArrayPref(this,"list2");
        cuslist3 = sharedPreference.getStringArrayPref(this,"list3");
        cuslist4 = sharedPreference.getStringArrayPref(this,"list4");

        SharedPreferenceUtill.setBoolean(TimeOverEndingActivity.this, "savename", true);
        SharedPreferenceUtill.setBoolean(this,"next", true);
        name = sharedPreference.getString(TimeOverEndingActivity.this, "name");
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

        }
        sharedPreference.setStringArrayPref(TimeOverEndingActivity.this, "list",cuslist);
        sharedPreference.setStringArrayPref(TimeOverEndingActivity.this, "list2",cuslist2);
        sharedPreference.setStringArrayPref(TimeOverEndingActivity.this, "list3",cuslist3);
        sharedPreference.setStringArrayPref(TimeOverEndingActivity.this, "list4",cuslist4);



        lin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count == 6) {
                    Intent intent = new Intent(TimeOverEndingActivity.this, DayActivity.class);
                    startActivity(intent);
                    finish();
                } else if (count ==12){
                    Intent intent = new Intent(TimeOverEndingActivity.this, DayActivity.class);
                    startActivity(intent);
                    finish();
                } else if (count ==24){
                    Intent intent = new Intent(TimeOverEndingActivity.this, DayActivity.class);
                    startActivity(intent);
                    finish();
                } else if (count ==33){
                    Intent intent = new Intent(TimeOverEndingActivity.this, DayActivity.class);
                    startActivity(intent);
                    finish();
                } else if (count ==43){
                    Intent intent = new Intent(TimeOverEndingActivity.this, DayActivity.class);
                    startActivity(intent);
                    finish();
                } else if (count ==53){
                    Intent intent = new Intent(TimeOverEndingActivity.this, DayActivity.class);
                    startActivity(intent);
                    finish();
                } else if (count ==66){
                    Intent intent = new Intent(TimeOverEndingActivity.this, DayActivity.class);
                    startActivity(intent);
                    finish();
                } else if (count ==80){
                    Intent intent = new Intent(TimeOverEndingActivity.this, DayActivity.class);
                    startActivity(intent);
                    finish();
                } else if (count ==95){
                    Intent intent = new Intent(TimeOverEndingActivity.this, DayActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(TimeOverEndingActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                finish();
            }
        });

    }

    public void onBackPressed(){
        Intent intent = new Intent(TimeOverEndingActivity.this,EndGameActivity.class);
        startActivity(intent);
    }

}