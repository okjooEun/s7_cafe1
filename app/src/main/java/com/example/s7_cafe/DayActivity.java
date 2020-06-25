package com.example.s7_cafe;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ArrayRes;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

//일차별
public class DayActivity extends AppCompatActivity {

    RelativeLayout rel;
    TextView textView3;
    ImageView setting;
    int cuscount;
    Boolean onoff;
    ArrayList<String> cus1, cus2, cus3, cus4, cus6, cus8, cus10, black;

    SharedPreferenceUtill sharedPreference = new SharedPreferenceUtill();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        stopService(new Intent(getApplicationContext(),MusicService.class));

        startService(new Intent(getApplicationContext(),TotalMusic.class));

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

        cuscount = sharedPreference.getInt(this,"count");
        sharedPreference.setBoolean(DayActivity.this, "savename",true);
        onoff = sharedPreference.getBoolean(DayActivity.this,"sound");


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

        //각 날짜 별로 손님 추가
        switch (cuscount){
            //23일 손님
            case 0: case 1: case 2: case 3: case 4: case 5:
                textView3.setText("1일차");
                //첫날부터 마지막날까지 모두 가능한 손님
               cus1 = new ArrayList<>();
               cus1.add("하~암... 아이스 아메리카노 한 잔이요....");
               cus1.add("뭐가 제일 싸요? 아메리카노? \n\n 그럼 따뜻한 걸로 주세요.");
               cus1.add("저기..뜨거운 카페라떼 한 잔 주세요...");
               cus1.add("누나~ 뜨거운 카페라떼 한 잔이요~");
               cus1.add("좋은 하루~ 오후에는 아이스 카페라떼가 땡기네요~");
               cus1.add("처음 뵙는 분이네! 아이스 아메리카노요~");
               cus1.add("요즘 날씨엔 따뜻한 아메리카노가 좋겠죠?");
               cus1.add("처음 보는 학생이네~ 핫 아메리카노로 한 잔 부탁해요~");
               cus1.add("으으 너무 피곤해요... 아이스 아메리카노요...");
               cus1.add("아 목말라...아이스 아메리카노요.");
                cus1.add("저기요. 아이스 아메리카노 1잔 주세요.");
                cus1.add("뜨거운 카페라떼 1잔 주실래요?");
                cus1.add("아이스 아메리카노 한잔, 뜨거운 카페라떼 한잔이요.");
                cus1.add("뭐 먹지.....아이스 카페라떼 주세요.");
                cus1.add("겨울엔 역시 따뜻한 아메리카노죠!");
                cus1.add("카페라떼 아이스랑 따뜻한거 하나씩이요.");
               sharedPreference.setStringArrayPref(DayActivity.this,"list", cus1);

                //진상 손님
                black = new ArrayList<>();
                black.add("와 진짜 @#$@#%$ 따~아 뭐? 정말? #$%^@ 주세요~ #$%^*%");
                black.add("카페라떼 차가운 걸로 한 잔. 할인이랑 적립해줘.");
                black.add("아아랑, 따바랑, 따아 주시고요. \n\n여기서 제일 잘생긴 사람 카드로 긁어주세요!");
                black.add("아이스 바닐라라떼, 딸기 스무디, 따뜻한 아메리카노 한 잔씩 주세요. \n\n(아이가 한 켠에 진열된 케이크를 만지는 것을 목격했다.)");
                black.add("이봐요, 이 케이크 누가 만진 것 같은데요?");
                black.add("어제 제가 시킨 메뉴가 뭐게~요?");
                black.add("언니~ 뜨거운 아메리카노 한 잔이랑 컵 좀 더 줄래~?");
                black.add("자바칩 프라푸치노 자바칩은 반 갈고 반은 휩에 얹어주시고, \n\n카라멜 드리즐 별 모양으로 한 잔 주세요!");
                black.add("저기요. 쓰레기통 주변에서 냄새가 진동하는데 확인 좀 해보세요.");
                black.add("따뜻한 라떼에 샷 추가해서 더 달게 한 잔 해줘요~");
                black.add("(지불해야 할 금액보다 적은 금액을 던진다.) \n\n이 동네는 아이스 아메리카노가 뭐 이리 비싸?");
                black.add("(마감 후 청소 중인 매장에 들어온다.)\n\n어이 거기 아가씨! 여기 달달~하게 커피 한 잔 타와!");
                sharedPreference.setStringArrayPref(DayActivity.this,"black", black);

                break;

                //24일 손님
            case 6: case 7: case 8: case 9: case 10: case 11:
                textView3.setText("2일차");
                cus2 = new ArrayList<>();
                cus2.add("바닐라라떼, 아메리카노 둘다 따뜻하게요.");
                cus2.add("안녕하세요! 초코스무디 하나 주세요.");
                cus2.add("저기 주문할게요. 초코랑 딸기 스무디 하나씩이요.");
                cus2.add("아이스 바닐라라떼 부탁드립니다.");
                cus2.add("딸기스무디 하나 주세요!!");
                cus2.add("따땃한 바닐라라떼 하나 부탁해요.");
                cus2.add("아이스 아메리카노, 딸기 스무디 주세요.");
                cus2.add("카페라떼, 바닐라라떼 아이스로 주문할게요.");
                cus2.add("딸기 스무디 한잔이면 피로가 없어질 것 같아요!");
                cus2.add("초코 스무디 맛있게 한 잔 주세요~!");
                cus2.add("후후 따뜻한 아메리카노 한 잔 부탁해요.");
                cus2.add("밥 먹고 달달한게 땡기네~ 딸기 스무디요!");
                cus2.add("아가씨, 아이스 아메리카노 주시지요.");
                cus2.add("오늘 같은 날씨엔 따뜻한 아메리카노 한 잔이 딱이겠어요.");
                sharedPreference.setStringArrayPref(DayActivity.this,"list2",cus2);

                break;

            //25일 손님
            case 12: case 13: case 14: case 15: case 16: case 17: case 18: case 19: case 20: case 21: case 22: case 23:
                textView3.setText("3일차");
                stopService(new Intent(getApplicationContext(), TotalMusic.class));
                startService(new Intent(getApplicationContext(), TotalMusic.class));
                //크리스마스때만 가능한 손님
                cus3 = new ArrayList<>();
                cus3.add("크리스마스 한정 초코요? 따뜻한거로 주세요!");
                cus3.add("메리크리스마스~ 핫 초코라떼로 부탁합니다.");
                cus3.add("한정 아이스 초코라떼랑 아이스 아메리카노 할게요.");
                cus3.add("크리스마스 핫 초코라떼, 핫 바닐라라떼요!");
                cus3.add("마시멜로랑 핫초코라니,,,,그거 주세요.");
                cus3.add("약속 늦었는데 아이스 초코 한 잔 빨리요!!!");
                cus3.add("으음..핫 초코가 나으려나? 그걸로 주세요.");
                cus3.add("오늘도 안녕~ 특별한 초코라떼 따뜻하게 주세요~");
                cus3.add("흐음...어디... 따뜻한 초코라떼를 한 잔 마셔볼까?");
                sharedPreference.setStringArrayPref(DayActivity.this,"list3",cus3);
                break;

                //26일 손님
                case 24: case 25: case 26: case 27: case 28: case 29: case 30: case 31: case 32:
                textView3.setText("4일차");
                cus4 = new ArrayList<>();
                cus4.add("이제는 마시멜로 안 줘요? 아쉽다~ \n\n따뜻한 초코라떼요." );
                cus4.add("....아이스 아메리카노..");
                cus4.add("날이 춤네요... 따뜻한 초코라떼 주세요.");
                cus4.add("얼어 죽어도 아이스! 아이스 카페라떼로 줘요!");
                cus4.add("저...저기 아..아이스 초..초코라떼..요..");
                cus4.add("음...? 아이스 바닐라라떼요.");
                cus4.add("굿모닝~ 아이스 카페라떼요~!");
                cus4.add("아이스 아메리카노로 부탁합니다!");
                cus4.add("학생이 고생이 많네~ 따뜻한 라떼로 줘요.");
                cus4.add("겨울에도 딸기 스무디는 포기 못해요!");
                cus4.add("날이 추우니 따뜻한 아메리카노 한 잔 후딱 주이소.");
                cus4.add("잠 좀 깨게 아이스 바닐라라떼로 주문할게요!");
                cus4.add("오늘은 초코 스무디로 정했다~! 초코 스무디요!");
                sharedPreference.setStringArrayPref(DayActivity.this,"list4",cus4);
                break;

                //27일 손님
            case 33: case 34: case 35: case 36: case 37: case 38: case 39: case 40: case 41: case 42:
                textView3.setText("5일차");
                break;

                //28일 손님
            case 43: case 44: case 45: case 46: case 47: case 48: case 49: case 50: case 51: case 52:
                textView3.setText("6일차");
                cus6 = new ArrayList<>();
                cus6.add("");
                cus6.add("바닐라라떼 말고 다른 단 커피가 먹고 싶은데...\n\n아! 모카라떼 따뜻하게 주세요!");
                cus6.add("오늘은 모카라떼로 마셔볼까~ 아이스로 주세요!");
                cus6.add("오늘도 힘내세요! 저는 아이스 모카라떼요!");
                cus6.add("음.. 핫, 아이스 모카라떼 하나씩요.");
                cus6.add("..........아 죄송해요.\n\n아이스 아메, 아이스 모카, 딸기 스무디요.");
                cus6.add("따뜻한 아이스 아메리카노요~! 앗 아이스 아메리카노요!!");
                cus6.add("초코 스무디 맛있어요...? 그럼 그걸로요.");
                cus6.add("뭐 마실거냐구? 아이스 아메리카노랑 아이스 모카라떼.");
                sharedPreference.setStringArrayPref(DayActivity.this,"list6",cus6);
                break;

                //29일 손님
            case 53: case 54: case 55: case 56: case 57: case 58: case 59: case 60: case 61: case 62: case 63: case 64: case 65:
                textView3.setText("7일차");
                break;

                //30일 손님
            case 66: case 67: case 68: case 69: case 70: case 71: case 72: case 73: case 74: case 75: case 76: case 77: case 78: case 79:
                textView3.setText("8일차");
                cus8 = new ArrayList<>();
                cus8.add("딸바? 와~ 딸바 하나요!");
                cus8.add("맛이 있으려나...? \n\n초코바나나랑, 아이스 모카라떼, 따뜻한 라떼요.");
                cus8.add("안녕하세요~ 딸기 바나나 주스 따뜻하게 되나요?\n\n안돼요? 그럼 그냥 따뜻한 모카라떼 주세요~");
                cus8.add("오~ 딸기 바나나 주스 얼마죠? 그걸로 줘봐요.");
                cus8.add("초코 바나나... 맛있어요...? 그럼... 그걸로...");
                cus8.add("여어! 초코 바나나 주스 한 잔 달라구~");
                cus8.add("핫 모카라떼랑 초코 바나나 주스 한 잔씩요.");
                cus8.add("저기.. 주문이요. 바나나 주스 종류 하나씩...");
                cus8.add("언니 언니! 딸기 들어간 것 중에 뭐 먹을까요?!\n\n그럼 딸기 바나나 주스 주세요!");
                cus8.add("뭐 먹지... 오 신메뉴라구요? 그럼 딸기 바나나랑 초코 바나나 주세요!");
                cus8.add("오늘 아침같이 상큼한 딸기 스무디요!");
                cus8.add("새로 나온 딸기 바나나 주스가 먹고 싶네요. 그걸로 주세요.");
                sharedPreference.setStringArrayPref(DayActivity.this,"list8",cus8);

                break;

                //31일 손님
            case 80: case 81: case 82: case 83: case 84: case 85: case 86: case 87: case 88: case 89: case 90: case 91: case 92: case 93: case 94:
                textView3.setText("9일차");
                break;

                //1월 1일 손님
            case 95: case 96: case 97: case 98: case 99: case 100: case 101: case 102: case 103: case 104: case 105: case 106: case 107: case 108: case 109: case 110:
                textView3.setText("마지막날");
                stopService(new Intent(getApplicationContext(), TotalMusic.class));
                startService(new Intent(getApplicationContext(), TotalMusic.class));
                //마지막 손님 추가
                cus10 = new ArrayList<>();
                cus10.add("초코바나나주스 맛있겠다! 그거로 주세요.");
                cus10.add("핫  모카라떼랑 딸기바나나주스 할게요.");
                cus10.add("핫 토피넛라떼 주세요.");
                cus10.add("헬로~ 아이스 토피넛라떼 한잔 마시고 싶네요~");
                cus10.add("핫 토피넛 라떼 한잔이랑 핫 초코라떼 한잔 주쇼");
                cus10.add("아메리카노, 모카라떼 아이스로 만들어주세요.");
                cus10.add("저...초코스무디랑 아이스 초코라떼요...");
                cus10.add("토피넛라떼랑 카페모카 뜨겁게 주세요.");
                cus10.add("달달하게~ 초코스무디 초코바나나주스 할게요!");
                cus10.add("아이스 토피넛, 핫 토피넛 마실게요~");
                cus10.add("안녕하세요~ 아메리카노, 바닐라라떼, 모카라떼, 토피넛라떼요. \n\n 아, 전부 아이스로요!");
                cus10.add("1월 1일인데 학원 가요...\n\n당 충전하게 초코 바나나 주스 주세요.");
                cus10.add("뭐가 좋을까...? \n\n토피넛 라떼 따뜻하게랑, 그냥 카페라떼 따뜻하게요.");
                cus10.add("해피 뉴 이어! 토피넛 라떼 핫으로요!");
                cus10.add("새해에 신 메뉴라... 전 아이스 토피넛 라떼로 한 잔이요.\n\n아 그리고 딸기바나나, 바닐라 라떼 아이스도요.");
                cus10.add("쿠키가 올라간다구요? 그럼 토피넛 라떼 아이스랑 따뜻하게요!");
                cus10.add("새해 복 많이 받으세요... ...아이스 토피넛 라떼... 주세요...");
                cus10.add("새해부터 수고하시네요! 토피넛 라떼 따뜻한 걸로 주세요.");
                sharedPreference.setStringArrayPref(DayActivity.this,"list10",cus10);

                break;
        }

    }
    //뒤로가기 눌렀을 때
    public void onBackPressed(){
        Intent intent = new Intent(DayActivity.this,EndGameActivity.class);
        startActivity(intent);
    }
}
