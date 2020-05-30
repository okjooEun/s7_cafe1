package com.example.s7_cafe;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RecipebookActivity extends AppCompatActivity {
    int cuscount;
    Button next;
    LinearLayout background;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipebook);

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

        background = (LinearLayout) findViewById(R.id.background);
        next = (Button) findViewById(R.id.next);
        SharedPreferenceUtill sharedPreference = new SharedPreferenceUtill();
        cuscount = sharedPreference.getInt(this,"count");

        if(cuscount >= 3){
            Resources re = getResources();
            Drawable d = re.getDrawable(R.drawable.recipe_first);
            background.setBackground(d);
            next.setVisibility(decorView.VISIBLE);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RecipebookActivity.this, RecipebookActivity2.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

    }
}