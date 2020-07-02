package com.example.s7_cafe;

import android.content.Intent;
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
import java.util.Random;

public class TrashEndingActivity extends AppCompatActivity {
    RelativeLayout trel;
    ImageView imageView;
    private View decorView;
    private int uiOption, count;
    String name;
    ArrayList<String> black;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash);
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

        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setGravity(Gravity.CENTER);

        int x = (int) 200;
        int y = (int) 200;
        trel = (RelativeLayout) findViewById(R.id.trel);
        imageView = (ImageView) findViewById(R.id.tview);

        Intent intent = getIntent();
        int t = intent.getExtras().getInt("trash");

        if (t == 0) {
            Glide.with(this).load(R.drawable.trashending1).into(imageView);
        } else if (t == 1) {
            Glide.with(this).load(R.drawable.trashending2).into(imageView);
        } else if (t == 2) {
            Glide.with(this).load(R.drawable.trashending3).into(imageView);
        }

        black = sharedPreference.getStringArrayPref(this,"black");

        SharedPreferenceUtill.setBoolean(TrashEndingActivity.this, "savename", true);

        name = sharedPreference.getString(TrashEndingActivity.this, "name");
        black.set(8,"빈칸");
        sharedPreference.setStringArrayPref(TrashEndingActivity.this,"black",black);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (count == 5) {
                    Intent intent = new Intent(TrashEndingActivity.this, DayActivity.class);
                    startActivity(intent);
                    finish();
                } else if (count == 11) {
                    Intent intent = new Intent(TrashEndingActivity.this, DayActivity.class);
                    startActivity(intent);
                    finish();
                } else if (count == 23) {
                    Intent intent = new Intent(TrashEndingActivity.this, DayActivity.class);
                    startActivity(intent);
                    finish();
                } else if (count == 32) {
                    Intent intent = new Intent(TrashEndingActivity.this, DayActivity.class);
                    startActivity(intent);
                    finish();
                } else if (count == 42) {
                    Intent intent = new Intent(TrashEndingActivity.this, DayActivity.class);
                    startActivity(intent);
                    finish();
                } else if (count == 52) {
                    Intent intent = new Intent(TrashEndingActivity.this, DayActivity.class);
                    startActivity(intent);
                    finish();
                } else if (count == 65) {
                    Intent intent = new Intent(TrashEndingActivity.this, DayActivity.class);
                    startActivity(intent);
                    finish();
                } else if (count == 79) {
                    Intent intent = new Intent(TrashEndingActivity.this, DayActivity.class);
                    startActivity(intent);
                    finish();
                } else if (count == 94) {
                    Intent intent = new Intent(TrashEndingActivity.this, DayActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(TrashEndingActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1100);
    }

            public void onBackPressed() {
                Intent intent = new Intent(TrashEndingActivity.this, EndGameActivity.class);
                startActivity(intent);
            }
        }