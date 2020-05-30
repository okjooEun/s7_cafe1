package com.example.s7_cafe;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NewMenuActivity extends AppCompatActivity {

    LinearLayout rel;
    ImageView setting, menu, menu1,menu2,menu3;
    TextView textView4, textView5;
    int cuscount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_menu);

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

        SharedPreferenceUtill sharedPreference = new SharedPreferenceUtill();
        cuscount = sharedPreference.getInt(this,"count");

        rel = (LinearLayout) findViewById(R.id.rel);
        setting =(ImageView) findViewById(R.id.setting);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView)findViewById(R.id.textView5);
        menu = (ImageView) findViewById(R.id.menu);
        menu1 = (ImageView) findViewById(R.id.menu1);
        menu2 =(ImageView) findViewById(R.id.menu2);
        menu3 =(ImageView) findViewById(R.id.menu3);

                switch (cuscount){

            case 1:
//                textView4.setText("신메뉴 등장!");
//               textView5.setText("아메리카노, 카페라떼가 레시피에 추가되었습니다!");
//                Resources gu = getResources();
//                Drawable ic = gu.getDrawable(R.drawable.ms_ic);
//                Drawable lim = gu.getDrawable(R.drawable.ms_iim);
//                Drawable cal = gu.getDrawable(R.drawable.ms_cal);
//                Drawable cali = gu.getDrawable(R.drawable.ms_cali);
//                menu.setImageDrawable(ic);
//                menu1.setImageDrawable(lim);
//                menu2.setImageDrawable(cal);
//                menu3.setImageDrawable(cali);

                textView4.setText("신메뉴 등장!");
                textView5.setText("초코스무디, 딸기스무디, 카페라떼가 레시피에 추가되었습니다!");
                Resources gu1 = getResources();
                Drawable cs = gu1.getDrawable(R.drawable.ms_cs);
                Drawable ss = gu1.getDrawable(R.drawable.ms_ss);
                Drawable bl = gu1.getDrawable(R.drawable.ms_bl);
                Drawable bli = gu1.getDrawable(R.drawable.ms_bli);
                menu.setImageDrawable(cs);
                menu1.setImageDrawable(ss);
                menu2.setImageDrawable(bl);
                menu3.setImageDrawable(bli);
                break;
            case 3:
                textView4.setText("신메뉴 등장!");
                textView5.setText("크리스마스 한정 초콜릿라떼가 레시피에 추가되었습니다!");
                Resources gu2 = getResources();
                Drawable cscl = gu2.getDrawable(R.drawable.ms_cscl);
                Drawable cscli = gu2.getDrawable(R.drawable.ms_cscli);
                menu1.setImageDrawable(cscl);
                menu2.setImageDrawable(cscli);
//                textView4.setText("신메뉴 등장!");
//                textView5.setText("초코스무디, 딸기스무디, 카페라떼가 레시피에 추가되었습니다!");
//                Resources gu1 = getResources();
//                Drawable cs = gu1.getDrawable(R.drawable.ms_cs);
//                Drawable ss = gu1.getDrawable(R.drawable.ms_ss);
//                Drawable bl = gu1.getDrawable(R.drawable.ms_bl);
//                Drawable bli = gu1.getDrawable(R.drawable.ms_bli);
//                menu.setImageDrawable(cs);
//                menu1.setImageDrawable(ss);
//                menu2.setImageDrawable(bl);
//                menu3.setImageDrawable(bli);
                break;


            case 5:
                textView4.setText("신메뉴 등장!");
                textView5.setText("신년 토피넛라떼가 레시피에 추가되었습니다!");
                Resources gu3 = getResources();
                Drawable ms_ntl = gu3.getDrawable(R.drawable.ms_ntl);
                Drawable ms_ntli = gu3.getDrawable(R.drawable.ms_ntli);
                menu1.setImageDrawable(ms_ntl);
                menu2.setImageDrawable(ms_ntli);
                break;

//            case 6:
//                textView4.setText("메뉴 변경!");
//                textView5.setText("크리스마스 한정 초코라떼 → 초코라떼");
//                break;
        }

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.scale);
        menu.startAnimation(animation);
        menu1.startAnimation(animation);
        menu2.startAnimation(animation);
        menu3.startAnimation(animation);


        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewMenuActivity.this, SettingActivity2.class);
                startActivity(intent);
            }
        });

        rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewMenuActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void onBackPressed(){
        Intent intent = new Intent(NewMenuActivity.this,EndGameActivity.class);
        startActivity(intent);
    }
}
