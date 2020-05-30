package com.example.s7_cafe;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TimeOverEndingActivity extends AppCompatActivity {

    LinearLayout lin2;
    ImageView setting;
    private View    decorView;
    private int   uiOption, count;

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

        SharedPreferenceUtill sharedPreference = new SharedPreferenceUtill();
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

        lin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count == 3) {
                    Intent intent = new Intent(TimeOverEndingActivity.this, DayActivity.class);
                    startActivity(intent);
                } else if (count ==5){
                    Intent intent = new Intent(TimeOverEndingActivity.this, DayActivity.class);
                    startActivity(intent);
                }else if (count ==7){
                    Intent intent = new Intent(TimeOverEndingActivity.this, WhichEndingActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(TimeOverEndingActivity.this, MainActivity.class);
                    startActivity(intent);
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