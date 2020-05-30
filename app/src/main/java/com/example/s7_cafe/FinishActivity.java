package com.example.s7_cafe;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;


public class FinishActivity extends AppCompatActivity {

    ImageView setting;
    LinearLayout lin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        stopService(new Intent(getApplicationContext(),GameMusicService.class));
        startService(new Intent(getApplicationContext(),EndingMusic2Service.class));

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
        //소프트키(네비게이션바) 없애기

        lin = (LinearLayout) findViewById(R.id.lin);

        setting = (ImageView) findViewById(R.id.setting);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishActivity.this,NewActivity.class);
                startActivity(intent);
            }
        });
    }
    public void onBackPressed(){
        Intent intent = new Intent(FinishActivity.this,EndGameActivity.class);
        startActivity(intent);
    }
}