package com.example.s7_cafe;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class DayActivity extends AppCompatActivity {

    RelativeLayout rel;
    TextView textView3;
    ImageView setting;
    int cuscount;
    Boolean onoff;
    ArrayList<String> cus1, cus2, cus3, cus4;

    SharedPreferenceUtill sharedPreferenceUtill = new SharedPreferenceUtill();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

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

        rel = (RelativeLayout) findViewById(R.id.rel);
        textView3 =(TextView)findViewById(R.id.textView3);
        setting =(ImageView) findViewById(R.id.setting);
        stopService(new Intent(getApplicationContext(),MusicService.class));

        SharedPreferenceUtill sharedPreference = new SharedPreferenceUtill();
        cuscount = sharedPreference.getInt(this,"count");
        onoff = sharedPreference.getBoolean(this,"sound");


        if(onoff == true){
            startService(new Intent(getApplicationContext(),GameMusicService.class));
        }
        if(onoff == false){
            stopService(new Intent(getApplicationContext(),GameMusicService.class));
        }
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DayActivity.this, SettingActivity2.class);
                startActivity(intent);
            }
        });

        rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DayActivity.this, NewMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });


        switch (cuscount){
            case 1:
                textView3.setText("2일차");
                cus1 = new ArrayList<>();
                cus1.add("저기요. 아이스 아메리카노 1잔 주세요.");
                cus1.add("뜨거운 카페라떼 1잔 주실래요?");
                cus1.add("아이스 아메리카노 한잔, 뜨거운 카페라떼 한잔이요.");
                cus1.add("바닐라라떼, 아메리카노 둘다 따뜻하게요.");
                cus1.add("뭐 먹지.....아이스 카페라떼 주세요.");
                cus1.add("안녕하세요! 초코스무디 하나 주세요.");
                cus1.add("저기 주문할게요. 초코랑 딸기 스무디 하나씩이요.");
                cus1.add("아이스 바닐라라떼 부탁드립니다.");
                cus1.add("딸기스무디 하나 주세요!!");
                cus1.add("따땃한 바닐라라떼 하나 부탁해요.");
                cus1.add("아이스 아메리카노, 딸기 스무디 주세요.");
                cus1.add("카페라떼, 바닐라라떼 아이스로 주문할게요.");
                cus1.add("딸기 스무디 한잔이면 피로가 없어질 것 같아요!");
                cus1.add("겨울엔 역시 따뜻한 아메리카노죠!");
                cus1.add("카페라떼 아이스랑 따뜻한거 하나씩이요.");
                sharedPreferenceUtill.setStringArrayPref(DayActivity.this,"list",cus1);

                cus4 = new ArrayList<>();
                cus4.add("와 진짜 @#$@#%$ 따~아 뭐? 정말? #$%^@ 주세요~ #$%^*%");
                cus4.add("카페라떼 차가운 걸로 한 잔. 할인이랑 적립해줘.");
                cus4.add("아아랑, 따바랑, 따아 주시고요. \n\n여기서 제일 잘생긴 사람 카드로 긁어주세요!");
                cus4.add("아이스 바닐라라떼, 딸기 스무디, 따뜻한 아메리카노 한 잔씩 주세요. \n\n(아이가 한 켠에서 진열된 케이크를 만지는 것을 목격했다.");
                cus4.add("이봐요, 이 케이크 누가 만진 것 같은데요?");
                cus4.add("어제 제가 시킨 메뉴가 뭐게~요?");
                cus4.add("언니~ 뜨거운 아메리카노 한 잔이랑 컵 좀 더 줄래~?");
                cus4.add("자바칩 프라푸치노 자바칩은 반 갈고 반은 휩에 얹어주시고, \n\n카라멜 드리즐 별 모양으로 한 잔 주세요!");
                cus4.add("저기요. 쓰레기통 주변에서 냄새가 진동하는데 확인 좀 해보세요.");
                cus4.add("따뜻한 라떼에 샷 추가해서 더 달게 한 잔 해줘요~");
                cus4.add("아이스 아메리카노. (지불해야 할 금액보다 적은 금액을 던진다.) \n\n이 동네는 커피가 뭐 이리 비싸?");
                cus4.add("어이 거기 아가씨! 여기 달달~하게 커피 한 잔 타와!");
                sharedPreferenceUtill.setStringArrayPref(DayActivity.this,"list4", cus4);

                break;

//            case 2:
//                textView3.setText("2일차");
//                break;

            case 3:
                textView3.setText("3일차");
                cus2 = new ArrayList<>();
                cus2.add("크리스마스 한정 초코요? 따뜻한거로 주세요!");
                cus2.add("메리크리스마스~ 핫 초코라떼로 부탁합니다.");
                cus2.add("한정 아이스 초코라떼랑 아이스 아메리카노 할게요.");
                cus2.add("크리스마스 핫 초코라떼, 핫 바닐라라떼요!");
                cus2.add("마시멜로랑 핫초코라니,,,,그거 주세요.");
                sharedPreferenceUtill.setStringArrayPref(DayActivity.this,"list2",cus2);
                if(onoff == true){
                    stopService(new Intent(getApplicationContext(),GameMusicService.class));
                    startService(new Intent(getApplicationContext(),ChristmasMusicService.class));
                }
                if(onoff == false){
                    stopService(new Intent(getApplicationContext(),GameMusicService.class));
                    stopService(new Intent(getApplicationContext(),ChristmasMusicService.class));
                }
                break;

            case 5:
                textView3.setText("10일차");
                cus3 = new ArrayList<>();
                cus3.add("초코바나나주스 맛있겠다! 그거로 주세요.");
                cus3.add("핫 카페모카랑 딸기바나나주스 할게요.");
                cus3.add("핫 토피넛라떼 주세요.");
                cus3.add("헬로~ 아이스 토피넛라떼 한잔 마시고 싶네요~");
                cus3.add("핫 토피넛 라떼 한잔이랑 핫 초코라떼 한잔 주쇼");
                cus3.add("아메리카노, 토피넛라떼 아이스로 만들어주세요.");
                cus3.add("저...초코스무디랑 아이스 초코라떼요...");
                cus3.add("토피넛라떼랑 카페모카 뜨겁게 주세요.");
                cus3.add("달달하게~ 초코스무디 초코바나나주스 할게요!");
                cus3.add("아이스 토피넛, 핫 토피넛 마실게요~");
                sharedPreferenceUtill.setStringArrayPref(DayActivity.this,"list3",cus3);
                if(onoff == true){
                    stopService(new Intent(getApplicationContext(),ChristmasMusicService.class));
                    startService(new Intent(getApplicationContext(),GameMusicService.class));
                }
                if(onoff == false){
                    stopService(new Intent(getApplicationContext(),GameMusicService.class));
                    stopService(new Intent(getApplicationContext(),ChristmasMusicService.class));
                }
                break;
        }

    }
}
