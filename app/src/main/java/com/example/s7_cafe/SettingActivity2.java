package com.example.s7_cafe;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class SettingActivity2 extends AppCompatActivity {
Boolean onoff,touchonoff;
    int count;

    SharedPreferenceUtill sharedPreference = new SharedPreferenceUtill();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //소프트키(네비게이션바) 없애기 시작
        View decorView = getWindow().getDecorView();

        int uiOption = getWindow().getDecorView().getSystemUiVisibility();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility(uiOption);
        //소프트키(네비게이션바) 없애기 끝
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setGravity(Gravity.CENTER);

        int x = (int)500;
        int y = (int)300;

        final Button onBack = findViewById(R.id.onBack);
        final Button offBack =findViewById(R.id.offBack);
        final Button onSound = findViewById(R.id.onSound);
        final Button offSound =findViewById(R.id.offSound);

        count = sharedPreference.getInt(this, "count");
        onoff = sharedPreference.getBoolean(SettingActivity2.this,"sound");
        touchonoff = sharedPreference.getBoolean(SettingActivity2.this,"touch");


        if(onoff == true){
            Resources res1 = getResources();
            Drawable drawable = res1.getDrawable(R.drawable.on_light);
            Drawable drawable1 = res1.getDrawable(R.drawable.off_dark);
            onBack.setBackground(drawable);
            offBack.setBackground(drawable1);
        } else if (onoff == false){
            Resources res1 = getResources();
            Drawable drawable = res1.getDrawable(R.drawable.on_dark);
            Drawable drawable1 = res1.getDrawable(R.drawable.off_light);
            onBack.setBackground(drawable);
            offBack.setBackground(drawable1);
        }
        if(touchonoff == true){
            Resources res1 = getResources();
            Drawable drawable = res1.getDrawable(R.drawable.on_light);
            Drawable drawable1 = res1.getDrawable(R.drawable.off_dark);
            onSound.setBackground(drawable);
            offSound.setBackground(drawable1);
        } else if (touchonoff == false){
            Resources res1 = getResources();
            Drawable drawable = res1.getDrawable(R.drawable.on_dark);
            Drawable drawable1 = res1.getDrawable(R.drawable.off_light);
            onSound.setBackground(drawable);
            offSound.setBackground(drawable1);
        }
        onBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources res1 = getResources();
                Drawable drawable = res1.getDrawable(R.drawable.on_light);
                Drawable drawable1 = res1.getDrawable(R.drawable.off_dark);
                onBack.setBackground(drawable);
                offBack.setBackground(drawable1);

                SharedPreferenceUtill.setBoolean(SettingActivity2.this,"sound",true);

               stopService(new Intent(getApplicationContext(), TotalMusic.class));
                startService(new Intent(getApplicationContext(), TotalMusic.class));


            }
        });

        offBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources res1 = getResources();
                Drawable drawable = res1.getDrawable(R.drawable.on_dark);
                Drawable drawable1 = res1.getDrawable(R.drawable.off_light);
                onBack.setBackground(drawable);
                offBack.setBackground(drawable1);
                SharedPreferenceUtill.setBoolean(SettingActivity2.this,"sound",false);

                stopService(new Intent(getApplicationContext(), TotalMusic.class));
                startService(new Intent(getApplicationContext(), TotalMusic.class));

            }
        });

        onSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources res1 = getResources();
                Drawable drawable = res1.getDrawable(R.drawable.on_light);
                Drawable drawable1 = res1.getDrawable(R.drawable.off_dark);
                onSound.setBackground(drawable);
                offSound.setBackground(drawable1);

                SharedPreferenceUtill.setBoolean(SettingActivity2.this,"touch",true);
            }
        });

        offSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources res1 = getResources();
                Drawable drawable = res1.getDrawable(R.drawable.on_dark);
                Drawable drawable1 = res1.getDrawable(R.drawable.off_light);
                onSound.setBackground(drawable);
                offSound.setBackground(drawable1);

                SharedPreferenceUtill.setBoolean(SettingActivity2.this,"touch",false);
            }
        });


    }
}