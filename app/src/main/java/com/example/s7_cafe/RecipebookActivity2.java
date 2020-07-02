package com.example.s7_cafe;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RecipebookActivity2 extends AppCompatActivity {
    LinearLayout next, priv;
    int cuscount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipebook2);

        //소프트키(네비게이션바) 없애기 시작
        final View decorView = getWindow().getDecorView();

        int uiOption = getWindow().getDecorView().getSystemUiVisibility();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility(uiOption);
        //소프트키(네비게이션바) 없애기 끝

        next = (LinearLayout) findViewById(R.id.next);
        priv = (LinearLayout) findViewById(R.id.priv);

        priv.setVisibility(View.VISIBLE);
        SharedPreferenceUtill sharedPreference = new SharedPreferenceUtill();
        cuscount = sharedPreference.getInt(this, "count");

        if (cuscount >= 11 && cuscount < 16) {
            next.setVisibility(decorView.VISIBLE);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RecipebookActivity2.this, RecipebookActivity3.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
        if (cuscount >= 16) {
            next.setVisibility(decorView.VISIBLE);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RecipebookActivity2.this, RecipebookActivity4.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
        priv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipebookActivity2.this, RecipebookActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
