package com.example.s7_cafe;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Ending3Activity extends AppCompatActivity {
    LinearLayout ending3;
    ImageView setting;
Boolean onoff;
    private View decorView;
    private int   uiOption;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end3);
        SharedPreferenceUtill sharedPreference = new SharedPreferenceUtill();
        onoff = sharedPreference.getBoolean(this,"sound");

        if(onoff == true){
            stopService(new Intent(getApplicationContext(),GameMusicService.class));
            startService(new Intent(getApplicationContext(),EndingMusicService.class));
        }
        if(onoff == false){
            stopService(new Intent(getApplicationContext(),GameMusicService.class));
        }

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


        ending3 = (LinearLayout) findViewById(R.id.end3);
        setting = (ImageView) findViewById(R.id.setting);

        ending3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ending3Activity.this,NewActivity.class);
                startActivity(intent);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ending3Activity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

    }
    public void onBackPressed(){
        Intent intent = new Intent(Ending3Activity.this,EndGameActivity.class);
        startActivity(intent);
    }
}