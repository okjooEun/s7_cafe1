package com.example.s7_cafe;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.s7_cafe.UserInputContract.UserTable.SQL_CREATE_USER_TABLE;
import static com.example.s7_cafe.UserInputContract.UserTable.SQL_DELETE_TABLE;
import static com.example.s7_cafe.UserInputContract.UserTable.TABLE_USER;

public class MainActivity extends AppCompatActivity {
    //카운터 답변 제한 시간 15초
    private CountDownTimer timer;
    String strColor = "#FF0000";

    MenuDbhelper userdbhelper = new MenuDbhelper(this);
    SQLiteDatabase db;
    private List<User_Input> userList;


    ImageView setting, guest, imageView16, calenderview;
    Button btnSelect1, btnSelect2, btnSelect3, goKitchen;
    LinearLayout linBtn, lin;
    TextView txtTalk, txtTalk2, guestname, datetext;
    FrameLayout frame;

    String name;

    Boolean touchonoff, savename;
    Boolean come = false;
    Random rand = new Random();
    int i, r, j, count;

    Drawable drawable;
    SharedPreferenceUtill sharedPreference;
    ArrayList<Drawable> drawables = new ArrayList<Drawable>();
    ArrayList<String> cuslist, cuslist2, cuslist3, cuslist4, cuslist6, cuslist8,cuslist10, blacklist;
    Handler handler = new Handler();

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    DatabaseReference databaseReference = firebaseDatabase.getReference();

    private static final String SETTINGS_PLAYER_JSON ="settings_item_json";

    ArrayList<String > list = new ArrayList<>();
    ArrayList<String > list2 = new ArrayList<>();
    SoundPool pool;
    int bip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stopService(new Intent(getApplicationContext(), MusicService.class));
        startService(new Intent(getApplicationContext(), TotalMusic.class));

        db = userdbhelper.getWritableDatabase();

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


        setting = (ImageView) findViewById(R.id.setting);
        guest = (ImageView) findViewById(R.id.guest);
        calenderview = (ImageView) findViewById(R.id.calenderview);
        imageView16 = (ImageView) findViewById(R.id.imageView16);


        lin = (LinearLayout) findViewById(R.id.lin);
        linBtn = (LinearLayout) findViewById(R.id.linBtn);
        frame = (FrameLayout) findViewById(R.id.frame);

        txtTalk = (TextView) findViewById(R.id.txtTalk);
        txtTalk2 = (TextView) findViewById(R.id.txtTalk2);
        guestname = (TextView) findViewById(R.id.guestname);
        datetext = (TextView) findViewById(R.id.datetext);

        btnSelect1 = (Button) findViewById(R.id.btnSelect1);
        btnSelect2 = (Button) findViewById(R.id.btnSelect2);
        btnSelect3 = (Button) findViewById(R.id.btnSelect3);
        goKitchen = (Button) findViewById(R.id.goKitchen);
        guest = (ImageView) findViewById(R.id.guest);
        Resources res1 = getResources();

        drawable = res1.getDrawable(R.drawable.speech);
        sharedPreference = new SharedPreferenceUtill();
        count = sharedPreference.getInt(this, "count");

        //세팅값 1초마다 가져오기
        TimerTask myTask = new TimerTask() {
            @Override
            public void run() {
                touchonoff = sharedPreference.getBoolean(MainActivity.this,"touch");
            }
        };
        Timer timer1 = new Timer();
        timer1.schedule(myTask, 1000,1000);


        savename = sharedPreference.getBoolean(this, "savename");

        come = sharedPreference.getBoolean(this,"come");


        timer = new CountDownTimer(15 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                //엔딩 화면 바꿔야함
                sound();
                Intent intent = new Intent(MainActivity.this, TimeOverEndingActivity.class);
                startActivity(intent);
                finish();

            }
        };
        Resources resources = getResources();

        sharedPreference.setInt(MainActivity.this, "count", count + 1);

        drawables.add(resources.getDrawable(R.drawable.selec_bar5));
        drawables.add(resources.getDrawable(R.drawable.selec_bar4));
        drawables.add(resources.getDrawable(R.drawable.selec_bar3));
        drawables.add(resources.getDrawable(R.drawable.selec_bar2));
        drawables.add(resources.getDrawable(R.drawable.selec_bar1));
        drawables.add(resources.getDrawable(R.drawable.selec_bar0));
        blacklist = sharedPreference.getStringArrayPref(this,"black");
        cuslist = sharedPreference.getStringArrayPref(this, "list");
        cuslist2 = sharedPreference.getStringArrayPref(this,"list2");
        cuslist3 = sharedPreference.getStringArrayPref(this,"list3");
        cuslist4 = sharedPreference.getStringArrayPref(this, "list4");



        // 새로시작하기 눌렀을 때 손님 받기
        if(savename == true) {
    switch (count) {
        case 0: case 1: case 2: case 3: case 4: case 5:
            datetext.setText("23");
            imageView16.setVisibility(View.INVISIBLE);

            rand_23day();
            break;
        case 6: case 7: case 8: case 9: case 10: case 11:
            datetext.setText("24");
            imageView16.setVisibility(View.INVISIBLE);
            j = rand.nextInt(2);
            switch (j) {
                case 0:
                    rand_23day();
                    break;
                case 1:
                    rand_24day();
                    break;
            }
            break;
        case 12: case 13: case 14: case 15: case 16: case 17: case 18: case 19: case 20: case 21: case 22: case 23:
            datetext.setText("25");

            stopService(new Intent(getApplicationContext(), TotalMusic.class));
            startService(new Intent(getApplicationContext(), TotalMusic.class));
            Resources gu1 = getResources();
            Drawable dr2 = gu1.getDrawable(R.drawable.countback_cm);
            lin.setBackground(dr2);
            datetext.setTextColor(Color.parseColor(strColor));
            imageView16.setVisibility(View.INVISIBLE);
            j = rand.nextInt(4);
            switch (j) {
                case 0:
                    rand_23day();
                    break;
                case 1:
                    rand_24day();
                    break;
                case 2:
                    rand_25day();
                    break;
                case 3:
                    rand_bc();
                    break;
            }

            break;
        case 24: case 25: case 26: case 27: case 28: case 29: case 30: case 31: case 32:
            datetext.setText("26");
            imageView16.setVisibility(View.INVISIBLE);
            j = rand.nextInt(4);
            switch (j) {
                case 0:
                    rand_23day();
                    break;
                case 1:
                    rand_24day();
                    break;
                case 2:
                    rand_bc();
                    break;
            }

            break;
        case 33: case 34: case 35: case 36: case 37: case 38: case 39: case 40: case 41: case 42:
            datetext.setText("27");
            imageView16.setVisibility(View.INVISIBLE);
            j = rand.nextInt(4);
            switch (j) {
                case 0:
                    rand_23day();
                    break;
                case 1:
                    rand_24day();
                    break;
                case 2:
                    rand_bc();
                    break;
            }

            break;
        case 43: case 44: case 45: case 46: case 47: case 48: case 49: case 50: case 51: case 52:
            datetext.setText("28");
            imageView16.setVisibility(View.INVISIBLE);
            j = rand.nextInt(4);
            switch (j) {
                case 0:
                    rand_23day();
                    break;
                case 1:
                    rand_24day();
                    break;
                case 2:
                    rand_28day();
                    break;
                case 3:
                    rand_bc();
                    break;
            }

            break;
        case 53: case 54: case 55: case 56: case 57: case 58: case 59: case 60: case 61: case 62: case 63: case 64: case 65:
            datetext.setText("29");
            imageView16.setVisibility(View.INVISIBLE);
            j = rand.nextInt(4);
            switch (j) {
                case 0:
                    rand_23day();
                    break;
                case 1:
                    rand_24day();
                    break;
                case 2:
                    rand_28day();
                    break;
                case 3:
                    rand_bc();
                    break;
            }
            break;
        case 66: case 67: case 68: case 69: case 70: case 71: case 72: case 73: case 74: case 75: case 76: case 77: case 78: case 79:
            datetext.setText("30");
            imageView16.setVisibility(View.INVISIBLE);
            j = rand.nextInt(5);
            switch (j) {
                case 0:
                    rand_23day();
                    break;
                case 1:
                    rand_24day();
                    break;
                case 2:
                    rand_28day();
                    break;
                case 3:
                    rand_30day();
                    break;
                case 4:
                    rand_bc();
                    break;
            }
            break;
        case 80: case 81: case 82: case 83: case 84: case 85: case 86: case 87: case 88: case 89: case 90: case 91: case 92: case 93: case 94:
            datetext.setText("31일차");
            imageView16.setVisibility(View.INVISIBLE);
            j = rand.nextInt(5);
            switch (j) {
                case 0:
                    rand_23day();
                    break;
                case 1:
                    rand_24day();
                    break;
                case 2:
                    rand_28day();
                    break;
                case 3:
                    rand_30day();
                    break;
                case 4:
                    rand_bc();
                    break;
            }
            break;

        case 95: case 96: case 97: case 98: case 99: case 100: case 101: case 102: case 103: case 104: case 105: case 106: case 107: case 108: case 109:
            stopService(new Intent(getApplicationContext(), TotalMusic.class));
            startService(new Intent(getApplicationContext(), TotalMusic.class));
            Resources gu2 = getResources();
            Drawable dr3 = gu2.getDrawable(R.drawable.countback_new);
            lin.setBackground(dr3);
            Resources gu3 = getResources();
            Drawable dr4 = gu3.getDrawable(R.drawable.jcalendar);
            calenderview.setImageDrawable(dr4);
            datetext.setText(" 1");
            datetext.setTextColor(Color.parseColor(strColor));
            imageView16.setVisibility(View.INVISIBLE);
            j = rand.nextInt(6);
            switch (j) {
                case 0:
                    rand_23day();
                    break;
                case 1:
                    rand_24day();
                    break;
                case 2:
                    rand_28day();
                    break;
                case 3:
                    rand_30day();
                    break;
                case 4:
                    rand_1day();
                    break;
                case 5:
                    rand_bc();
                    break;
            }
            break;
        case 110:
            Resources gu4 = getResources();
            Drawable dr5 = gu4.getDrawable(R.drawable.countback_new);
            lin.setBackground(dr5);
            Resources gu5 = getResources();
            Drawable dr6 = gu5.getDrawable(R.drawable.jcalendar);
            calenderview.setImageDrawable(dr6);
            datetext.setText(" 1");
            datetext.setTextColor(Color.parseColor(strColor));
            imageView16.setVisibility(View.INVISIBLE);
            frame.setVisibility(View.INVISIBLE);
            toast();
            handler.postDelayed(new Runnable()  {
                public void run() {
                    // 시간 지난 후 실행할 코딩
                    txtTalk.setText(cuslist4.get(11));
                    frame.setVisibility(View.VISIBLE);
                    blackString();
                    bcus11();
                }
            }, 2700);

            break;
    }


    setting.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(MainActivity.this, SettingActivity2.class);
            startActivity(intent);

        }
    });


}
        // 이어하기 눌렀을 때 손님 받기
        if (savename == false) {
            name = sharedPreference.getString(MainActivity.this, "name");
            switch(count){
                case 1: case 2: case 3: case 4: case 5:
                    datetext.setText("23");
                    imageView16.setVisibility(View.INVISIBLE);
                    break;
                case 6: case 7: case 8: case 9: case 10: case 11:
                    datetext.setText("24");
                    imageView16.setVisibility(View.INVISIBLE);
                    break;
                case 12: case 13: case 14: case 15: case 16: case 17: case 18: case 19: case 20: case 21: case 22: case 23:
                    Resources gu1 = getResources();
                    Drawable dr2 = gu1.getDrawable(R.drawable.countback_cm);
                    lin.setBackground(dr2);
                    datetext.setText("25");
                    datetext.setTextColor(Color.parseColor(strColor));
                    imageView16.setVisibility(View.INVISIBLE);
                    break;
                case 24: case 25: case 26: case 27: case 28: case 29: case 30: case 31: case 32:
                    datetext.setText("26");
                    imageView16.setVisibility(View.INVISIBLE);
                    break;
                case 33: case 34: case 35: case 36: case 37: case 38: case 39: case 40: case 41: case 42:
                    datetext.setText("27");
                    imageView16.setVisibility(View.INVISIBLE);
                    break;
                case 43: case 44: case 45: case 46: case 47: case 48: case 49: case 50: case 51: case 52:
                    datetext.setText("28");
                    imageView16.setVisibility(View.INVISIBLE);
                    break;
                case 53: case 54: case 55: case 56: case 57: case 58: case 59: case 60: case 61: case 62: case 63: case 64: case 65:
                    datetext.setText("29");
                    imageView16.setVisibility(View.INVISIBLE);
                    break;
                case 66: case 67: case 68: case 69: case 70: case 71: case 72: case 73: case 74: case 75: case 76: case 77: case 78: case 79:
                    datetext.setText("30");
                    imageView16.setVisibility(View.INVISIBLE);
                    break;
                case 80: case 81: case 82: case 83: case 84: case 85: case 86: case 87: case 88: case 89: case 90: case 91: case 92: case 93: case 94:
                    datetext.setText("31");
                    imageView16.setVisibility(View.INVISIBLE);
                    break;
                case 95: case 96: case 97: case 98: case 99: case 100: case 101: case 102: case 103: case 104: case 105: case 106: case 107: case 108: case 109: case 110:
                    Resources gu2 = getResources();
                    Drawable dr3 = gu2.getDrawable(R.drawable.countback_new);
                    lin.setBackground(dr3);
                    Resources gu3 = getResources();
                    Drawable dr4 = gu3.getDrawable(R.drawable.jcalendar);
                    calenderview.setImageDrawable(dr4);
                    datetext.setText(" 1");
                    datetext.setTextColor(Color.parseColor(strColor));
                    imageView16.setVisibility(View.INVISIBLE);

                    break;
            }
            switch (name) {
                case "0":
                    txtTalk.setText(cuslist.get(0));
                    gcus0();
                    break;
                case "1":
                    txtTalk.setText(cuslist.get(1));
                    gcus1();
                    break;
                case "2":
                    txtTalk.setText(cuslist.get(2));
                    gcus2();
                    break;
                case "3":
                    txtTalk.setText(cuslist.get(3));
                    gcus3();
                    break;
                case "4":
                    txtTalk.setText(cuslist.get(4));
                    gcus4();
                    break;
                case "5":
                    txtTalk.setText(cuslist.get(5));
                    gcus5();
                    break;
                case "6":
                    txtTalk.setText(cuslist.get(6));
                    gcus6();
                    break;
                case "7":
                    txtTalk.setText(cuslist.get(7));
                    gcus7();
                    break;
                case "8":
                    txtTalk.setText(cuslist.get(8));
                    gcus8();
                    break;
                case "9":
                    txtTalk.setText(cuslist.get(9));
                    gcus9();
                    break;
                case "10":
                    txtTalk.setText(cuslist.get(10));
                    gcus10();
                    break;
                case "11":
                    txtTalk.setText(cuslist.get(11));
                    gcus11();
                    break;
                case "12":
                    txtTalk.setText(cuslist.get(12));
                    gcus12();
                    break;
                case "13":
                    txtTalk.setText(cuslist.get(13));
                    gcus13();
                    break;
                case "14":
                    txtTalk.setText(cuslist.get(14));
                    gcus14();
                    break;
                case "15":
                    txtTalk.setText(cuslist2.get(0));
                    gcus15();
                    break;
                case "16":
                    txtTalk.setText(cuslist2.get(1));
                    gcus16();
                    break;
                case "17":
                    txtTalk.setText(cuslist2.get(2));
                    gcus17();
                    break;
                case "18":
                    txtTalk.setText(cuslist2.get(3));
                    gcus18();
                    break;
                case "19":
                    txtTalk.setText(cuslist2.get(4));
                    gcus19();
                    break;
                case "20":
                    txtTalk.setText(cuslist3.get(0));
                    gcus20();
                    break;
                case "21":
                    txtTalk.setText(cuslist3.get(1));
                    gcus21();
                    break;
                case "22":
                    txtTalk.setText(cuslist3.get(2));
                    gcus22();
                    break;
                case "23":
                    txtTalk.setText(cuslist3.get(3));
                    gcus23();
                    break;
                case "24":
                    txtTalk.setText(cuslist3.get(4));
                    gcus24();
                    break;
                case "25":
                    txtTalk.setText(cuslist3.get(5));
                    gcus25();
                    break;

                case "26":
                    txtTalk.setText(cuslist3.get(6));
                    gcus26();
                    break;
                case "27":
                    txtTalk.setText(cuslist3.get(7));
                    gcus27();
                    break;
                case "28":
                    txtTalk.setText(cuslist3.get(8));
                    gcus28();
                    break;
                case "29":
                    txtTalk.setText(cuslist3.get(9));
                    gcus29();
                    break;
                case "b0":
                    txtTalk.setText(cuslist4.get(0));
                    blackString();
                    bcus0();
                    break;
                case "b1":
                    txtTalk.setText(cuslist4.get(1));
                    blackString();
                    bcus1();
                    break;
                case "b2":
                    txtTalk.setText(cuslist4.get(2));
                    blackString();
                    bcus2();
                    break;
                case "b3":
                    txtTalk.setText(cuslist4.get(3));
                    blackString();
                    bcus3();
                    break;
                case "b4":
                    txtTalk.setText(cuslist4.get(4));
                    blackString();
                    bcus4();
                    break;
                case "b5":
                    txtTalk.setText(cuslist4.get(5));
                    blackString();
                    bcus5();
                    break;
                case "b6":
                    txtTalk.setText(cuslist4.get(6));
                    blackString();
                    bcus6();
                    break;
                case "b7":
                    txtTalk.setText(cuslist4.get(7));
                    blackString();
                    bcus7();
                    break;
                case "b8":
                    txtTalk.setText(cuslist4.get(8));
                    frame.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            txtTalk.setVisibility(View.GONE);
                            linBtn.setVisibility(View.VISIBLE);
                            guestname.setVisibility(View.INVISIBLE);
                            imageView16.setVisibility(View.INVISIBLE);
                            setting.setVisibility(View.INVISIBLE);
                            frame.setClickable(false);
                        }
                    });
                    bcus8();
                    break;
                case "b9":
                    txtTalk.setText(cuslist4.get(9));
                    blackString();
                    bcus9();
                    break;
                case "b10":
                    txtTalk.setText(cuslist4.get(10));
                    blackString();
                    bcus10();
                    break;
                case "b11":
                    txtTalk.setText(cuslist4.get(11));
                    Resources gu4 = getResources();
                    Drawable dr5 = gu4.getDrawable(R.drawable.countback_new);
                    lin.setBackground(dr5);
                    Resources gu5 = getResources();
                    Drawable dr6 = gu5.getDrawable(R.drawable.jcalendar);
                    calenderview.setImageDrawable(dr6);
                    datetext.setText(" 1");
                    datetext.setTextColor(Color.parseColor(strColor));
                    imageView16.setVisibility(View.INVISIBLE);
                    blackString();
                    bcus11();

            }
        }
    }

    // 일반 손님
    //1일차
    public void gcus00(){
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m30_7).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "00");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n아메리카노");
                insertExtra(1);
                sharedPreference.setStringArrayPref(MainActivity.this,"list",cuslist);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus01(){
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m20_9).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "01");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n아메리카노");
                insertExtra(2);
                sharedPreference.setStringArrayPref(MainActivity.this,"list",cuslist);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus02(){
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m20_13).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "02");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n카페라떼");
                insertExtra(4);
                sharedPreference.setStringArrayPref(MainActivity.this,"list",cuslist);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus03(){
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m20_2).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "03");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n카페라떼");
                insertExtra(4);
                sharedPreference.setStringArrayPref(MainActivity.this,"list",cuslist);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus04(){
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m20_14).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "04");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n카페라떼");
                insertExtra(3);
                sharedPreference.setStringArrayPref(MainActivity.this,"list",cuslist);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus05(){
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m20_15).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "05");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n아메리카노");
                insertExtra(1);
                sharedPreference.setStringArrayPref(MainActivity.this,"list",cuslist);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus06(){
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m30_5).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "06");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n아메리카노");
                insertExtra(2);
                sharedPreference.setStringArrayPref(MainActivity.this,"list",cuslist);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus07(){
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m30_4).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "07");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n아메리카노");
                insertExtra(2);
                sharedPreference.setStringArrayPref(MainActivity.this,"list",cuslist);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus08(){
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f20_11).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "08");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n아메리카노");
                insertExtra(1);
                sharedPreference.setStringArrayPref(MainActivity.this,"list",cuslist);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus09(){
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f20_15).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "09");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n아메리카노");
                insertExtra(1);
                sharedPreference.setStringArrayPref(MainActivity.this,"list",cuslist);
                startActivity(intent);
                finish();
            }
        });
    }
    //2일차
    public void gcus0() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f60_1).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "0");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n아메리카노");
                insertExtra(1);
                sharedPreference.setStringArrayPref(MainActivity.this,"list2",cuslist2);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus1() {
       guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f10_9).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "1");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n 카페라떼");
                insertExtra(4);
                sharedPreference.setStringArrayPref(MainActivity.this,"list",cuslist);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus2() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m20_1).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "2");
        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);

                intent.putExtra("bil1", "아이스\n아메리카노");
                insertExtra(1);
                intent.putExtra("bil2", "핫\n 카페라떼");
                insertExtra(4);
                sharedPreference.setStringArrayPref(MainActivity.this,"list",cuslist);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus3() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f20_5).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "3");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n바닐라라떼");
                insertExtra(6);
                intent.putExtra("bil2","핫\n아메리카노");
                insertExtra(2);
                sharedPreference.setStringArrayPref(MainActivity.this,"list2",cuslist2);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus4() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m10_4).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "4");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n카페라떼");
                insertExtra(3);
                sharedPreference.setStringArrayPref(MainActivity.this,"list",cuslist);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus5() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m20_12).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "5");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "초코스무디");
                insertExtra(7);
                sharedPreference.setStringArrayPref(MainActivity.this,"list2",cuslist2);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus6() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f10_2).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "6");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "초코스무디");
                insertExtra(7);
                intent.putExtra("bil2", "딸기스무디");
                insertExtra(8);
                sharedPreference.setStringArrayPref(MainActivity.this,"list2",cuslist2);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus7() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f20_10).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "7");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n바닐라라떼");
                insertExtra(5);
                sharedPreference.setStringArrayPref(MainActivity.this,"list2",cuslist2);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus8() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m10_1).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "8");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "딸기스무디");
                insertExtra(8);
                sharedPreference.setStringArrayPref(MainActivity.this,"list2",cuslist2);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus9() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m60_1).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "9");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n바닐라라떼");
                insertExtra(6);
                sharedPreference.setStringArrayPref(MainActivity.this,"list2",cuslist2);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus10() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f60_2).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "10");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n아메리카노");
                insertExtra(1);
                intent.putExtra("bil2", "딸기스무디");
                insertExtra(8);
                sharedPreference.setStringArrayPref(MainActivity.this,"list2",cuslist2);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus11() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m60_4).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "11");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n카페라떼");
                insertExtra(3);
                intent.putExtra("bil2","아이스\n바닐라라떼");
                insertExtra(5);
                sharedPreference.setStringArrayPref(MainActivity.this,"list2",cuslist2);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus12() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f10_10).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "12");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "딸기스무디");
                insertExtra(8);
                sharedPreference.setStringArrayPref(MainActivity.this,"list2",cuslist2);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus13() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m30_8).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "13");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n아메리카노");
                insertExtra(2);
                sharedPreference.setStringArrayPref(MainActivity.this,"list2",cuslist2);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus14() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f20_13).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "14");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n카페라떼");
                insertExtra(4);
                intent.putExtra("bil2","아이스\n카페라떼");
                insertExtra(3);
                sharedPreference.setStringArrayPref(MainActivity.this,"list2",cuslist2);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus010(){
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f30_2).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "010");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "초코스무디");
                insertExtra(7);
                sharedPreference.setStringArrayPref(MainActivity.this,"list2",cuslist2);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus011(){
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f30_6).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "011");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n아메리카노");
                insertExtra(2);
                sharedPreference.setStringArrayPref(MainActivity.this,"list2",cuslist2);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus012(){
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f30_11).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "012");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "딸기스무디");
                insertExtra(8);
                sharedPreference.setStringArrayPref(MainActivity.this,"list2",cuslist2);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus013(){
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m30_9).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "013");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n아메리카노");
                insertExtra(1);
                sharedPreference.setStringArrayPref(MainActivity.this,"list",cuslist);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus014(){
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m30_6).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "014");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n아메리카노");
                insertExtra(2);
                sharedPreference.setStringArrayPref(MainActivity.this,"list",cuslist);
                startActivity(intent);
                finish();
            }
        });
    }
    //3일차
    public void gcus15() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f10_3).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "15");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "한정 핫\n초코라떼");
                insertExtra(10);
                sharedPreference.setStringArrayPref(MainActivity.this,"list3",cuslist3);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus16() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m30_1).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "16");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "한정 핫\n초코라떼");
                insertExtra(10);
                sharedPreference.setStringArrayPref(MainActivity.this,"list3",cuslist3);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus17() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m60_3).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "17");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "한정 아이스\n초코라떼");
                insertExtra(9);
                intent.putExtra("bil2","아이스\n아메리카노");
                insertExtra(1);
                sharedPreference.setStringArrayPref(MainActivity.this,"list3",cuslist3);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus18() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m10_3).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "18");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "한정 핫\n초코라떼");
                insertExtra(10);
                intent.putExtra("bil2","핫\n바닐라라떼");
                insertExtra(6);
                sharedPreference.setStringArrayPref(MainActivity.this,"list3",cuslist3);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus19() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f30_7).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "19");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "한정 핫\n초코라떼");
                insertExtra(10);
                sharedPreference.setStringArrayPref(MainActivity.this,"list3",cuslist3);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus015() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f20_8).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "015");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "한정 아이스\n초코라떼");
                insertExtra(9);
                sharedPreference.setStringArrayPref(MainActivity.this,"list3",cuslist3);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus016() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f30_14).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "016");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "한정 핫\n초코라떼");
                insertExtra(10);
                sharedPreference.setStringArrayPref(MainActivity.this,"list3",cuslist3);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus017() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f30_3).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "017");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "한정 핫\n초코라떼");
                insertExtra(10);
                sharedPreference.setStringArrayPref(MainActivity.this,"list3",cuslist3);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus018() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f30_13).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "018");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "한정 핫\n초코라떼");
                insertExtra(10);
                sharedPreference.setStringArrayPref(MainActivity.this,"list3",cuslist3);
                startActivity(intent);
                finish();
            }
        });
    }
    //4일차
    public void gcus019() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f10_8).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "019");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n초코라떼");
                insertExtra(12);
                sharedPreference.setStringArrayPref(MainActivity.this,"list4",cuslist4);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus020() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m20_11).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "020");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n아메리카노");
                insertExtra(1);
                sharedPreference.setStringArrayPref(MainActivity.this,"list4",cuslist4);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus021() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m20_16).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "021");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n초코라떼");
                insertExtra(12);
                sharedPreference.setStringArrayPref(MainActivity.this,"list4",cuslist4);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus022() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m30_3).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "022");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n카페라떼");
                insertExtra(3);
                sharedPreference.setStringArrayPref(MainActivity.this,"list4",cuslist4);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus0222() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f10_1).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "0222");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n초코라떼");
                insertExtra(11);
                sharedPreference.setStringArrayPref(MainActivity.this,"list4",cuslist4);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus023() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m20_10).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "023");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n바닐라라떼");
                insertExtra(5);
                sharedPreference.setStringArrayPref(MainActivity.this,"list4",cuslist4);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus024() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m30_2).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "024");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n카페라떼");
                insertExtra(3);
                sharedPreference.setStringArrayPref(MainActivity.this,"list4",cuslist4);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus025() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m20_8).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "025");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n아메리카노");
                insertExtra(1);
                sharedPreference.setStringArrayPref(MainActivity.this,"list4",cuslist4);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus026() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m60_5).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "026");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n카페라떼");
                insertExtra(4);
                sharedPreference.setStringArrayPref(MainActivity.this,"list4",cuslist4);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus027() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f20_2).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "027");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "딸기스무디");
                insertExtra(8);
                sharedPreference.setStringArrayPref(MainActivity.this,"list4",cuslist4);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus028() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f20_1).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "028");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n아메리카노");
                insertExtra(2);
                sharedPreference.setStringArrayPref(MainActivity.this,"list4",cuslist4);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus029() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f20_4).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "029");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n바닐라라떼");
                insertExtra(5);
                sharedPreference.setStringArrayPref(MainActivity.this,"list4",cuslist4);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus030() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f10_6).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "030");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "초코스무디");
                insertExtra(7);
                sharedPreference.setStringArrayPref(MainActivity.this,"list4",cuslist4);
                startActivity(intent);
                finish();
            }
        });
    }
    //6일차
    public void gcus031() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f10_11).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "031");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n 모카라떼");
                insertExtra(14);

                sharedPreference.setStringArrayPref(MainActivity.this,"list6",cuslist6);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus032() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m20_18).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "032");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n모카라떼");
                insertExtra(13);

                sharedPreference.setStringArrayPref(MainActivity.this,"list6",cuslist6);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus033() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f20_14).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "033");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n모카라떼");
                insertExtra(13);

                sharedPreference.setStringArrayPref(MainActivity.this,"list6",cuslist6);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus034() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f30_8).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "034");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n모카라떼");
                insertExtra(13);
                intent.putExtra("bil2", "핫\n 모카라떼");
                insertExtra(14);

                sharedPreference.setStringArrayPref(MainActivity.this,"list6",cuslist6);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus035() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m20_17).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "035");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n아메리카노");
                insertExtra(1);
                intent.putExtra("bil2", "아이스\n모카라떼");
                insertExtra(13);
                intent.putExtra("bil3", "딸기스무디");
                insertExtra(8);

                sharedPreference.setStringArrayPref(MainActivity.this,"list6",cuslist6);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus036() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f20_18).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "036");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n아메리카노");
                insertExtra(1);

                sharedPreference.setStringArrayPref(MainActivity.this,"list6",cuslist6);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus037() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m20_3).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "037");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "초코스무디");
                insertExtra(7);

                sharedPreference.setStringArrayPref(MainActivity.this,"list6",cuslist6);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus038() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m20_19).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "038");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n아메리카노");
                insertExtra(1);
                intent.putExtra("bil2", "아이스\n모카라떼");
                insertExtra(13);

                sharedPreference.setStringArrayPref(MainActivity.this,"list6",cuslist6);
                startActivity(intent);
                finish();
            }
        });
    }
    //8일차
    public void gcus039() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f20_9).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "039");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "딸기바나나\n주스");
                insertExtra(16);

                sharedPreference.setStringArrayPref(MainActivity.this,"list8",cuslist8);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus040() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f20_7).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "040");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "초코바나나\n주스");
                insertExtra(15);
                intent.putExtra("bil2", "아이스\n모카라떼");
                insertExtra(13);
                intent.putExtra("bil3", "핫\n 카페라떼");
                insertExtra(4);

                sharedPreference.setStringArrayPref(MainActivity.this,"list8",cuslist8);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus041() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f20_12).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "041");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n 모카라떼");
                insertExtra(14);

                sharedPreference.setStringArrayPref(MainActivity.this,"list8",cuslist);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus042() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m60_7).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "057");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "딸기바나나\n주스");
                insertExtra(16);

                sharedPreference.setStringArrayPref(MainActivity.this,"list8",cuslist8);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus043() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f20_3).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "043");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "초코바나나\n주스");
                insertExtra(15);

                sharedPreference.setStringArrayPref(MainActivity.this,"list8",cuslist8);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus044() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m20_4).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "044");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "초코바나나\n주스");
                insertExtra(15);

                sharedPreference.setStringArrayPref(MainActivity.this,"list8",cuslist8);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus045() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m20_7).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "045");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n 모카라떼");
                insertExtra(14);
                intent.putExtra("bil2", "초코바나나\n주스");
                insertExtra(15);

                sharedPreference.setStringArrayPref(MainActivity.this,"list8",cuslist8);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus046() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f30_10).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "046");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "초코바나나\n주스");
                insertExtra(15);
                intent.putExtra("bil2", "딸기바나나\n주스");
                insertExtra(16);

                sharedPreference.setStringArrayPref(MainActivity.this,"list8",cuslist8);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus047() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f30_1).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "047");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "딸기바나나\n주스");
                insertExtra(16);

                sharedPreference.setStringArrayPref(MainActivity.this,"list8",cuslist8);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus048() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f30_5).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "048");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "초코바나나\n주스");
                insertExtra(15);
                intent.putExtra("bil2", "딸기바나나\n주스");
                insertExtra(16);

                sharedPreference.setStringArrayPref(MainActivity.this,"list8",cuslist8);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus049() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f30_4).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "049");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "딸기스무디");
                insertExtra(8);

                sharedPreference.setStringArrayPref(MainActivity.this,"list8",cuslist8);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus050() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f30_15).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "050");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "딸기바나나\n주스");
                insertExtra(16);

                sharedPreference.setStringArrayPref(MainActivity.this,"list8",cuslist8);
                startActivity(intent);
                finish();
            }
        });
    }
    //10일차
    public void gcus20() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f30_12).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "20");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "초코바나나\n주스");
                insertExtra(15);
                sharedPreference.setStringArrayPref(MainActivity.this,"list10",cuslist10);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus21() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f60_3).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "21");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n 모카라떼");
                insertExtra(14);
                intent.putExtra("bil2","딸기바나나\n주스");
                insertExtra(16);
                sharedPreference.setStringArrayPref(MainActivity.this,"list10",cuslist10);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus22() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m60_2).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "22");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n토피넛라떼");
                insertExtra(18);
                sharedPreference.setStringArrayPref(MainActivity.this,"list10",cuslist10);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus23() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f60_4).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "23");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n토피넛라떼");
                insertExtra(17);
                sharedPreference.setStringArrayPref(MainActivity.this,"list10",cuslist10);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus24() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m60_5).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "24");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n토피넛라떼");
                insertExtra(18);
                intent.putExtra("bil2","핫\n초코라떼");
                insertExtra(12);
                sharedPreference.setStringArrayPref(MainActivity.this,"list10",cuslist10);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus25() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f10_7).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "25");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n아메리카노");
                insertExtra(1);
                intent.putExtra("bil2","아이스\n 모카라떼");
                insertExtra(13);
                sharedPreference.setStringArrayPref(MainActivity.this,"list10",cuslist10);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus26() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f30_18).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "26");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "초코스무디");
                insertExtra(7);
                intent.putExtra("bil2", "아이스\n초코라떼");
                insertExtra(11);
                sharedPreference.setStringArrayPref(MainActivity.this,"list10",cuslist10);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus27() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m10_2).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "27");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n토피넛라떼");
                insertExtra(18);
                intent.putExtra("bil2","핫\b 모카라떼");
                insertExtra(14);
                sharedPreference.setStringArrayPref(MainActivity.this,"list10",cuslist10);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus28() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f20_17).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "28");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "초코스무디");
                insertExtra(7);
                intent.putExtra("bil2","초코바나나\n주스");
                insertExtra(15);
                sharedPreference.setStringArrayPref(MainActivity.this,"list10",cuslist10);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus29() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m20_6).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "29");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n토피넛라떼");
                insertExtra(17);
                intent.putExtra("bil2", "핫\n토피넛라떼");
                insertExtra(18);
                sharedPreference.setStringArrayPref(MainActivity.this,"list10",cuslist10);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus051() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f30_16).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "051");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n토피넛라떼");
                insertExtra(18);
                sharedPreference.setStringArrayPref(MainActivity.this,"list10",cuslist10);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus052() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f10_4).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "052");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n토피넛라떼");
                insertExtra(17);
                sharedPreference.setStringArrayPref(MainActivity.this,"list10",cuslist10);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus053() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f30_9).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "053");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n토피넛라떼");
                insertExtra(17);
                intent.putExtra("bil2", "핫\n토피넛라떼");
                insertExtra(18);
                sharedPreference.setStringArrayPref(MainActivity.this,"list10",cuslist10);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus054() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.m20_5).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "054");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n토피넛라떼");
                insertExtra(17);
                intent.putExtra("bil2", "딸기바나나\n주스");
                insertExtra(16);
                intent.putExtra("bil3", "아이스\n바닐라라떼");
                insertExtra(5);

                sharedPreference.setStringArrayPref(MainActivity.this,"list10",cuslist10);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus055() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f30_17).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "055");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n토피넛라떼");
                insertExtra(18);
                sharedPreference.setStringArrayPref(MainActivity.this,"list10",cuslist10);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus056() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f20_6).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "056");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "핫\n토피넛라떼");
                insertExtra(18);
                intent.putExtra("bil2", "핫\n 카페라떼");
                insertExtra(4);

                sharedPreference.setStringArrayPref(MainActivity.this,"list10",cuslist10);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus057() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f10_5).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "057");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "초코바나나\n주스");
                insertExtra(15);

                sharedPreference.setStringArrayPref(MainActivity.this,"list10",cuslist10);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gcus058() {
        guestname.setText("손님");
        Glide.with(MainActivity.this).load(R.raw.f30_8).into(guest);
        goKitchen.setVisibility(View.VISIBLE);
        goKitchen.setClickable(true);
        sharedPreference.setString(MainActivity.this, "name", "058");

        goKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                intent.putExtra("bil1", "아이스\n아메리카노");
                insertExtra(1);
                intent.putExtra("bil2", "아이스\n바닐라라떼");
                insertExtra(5);
                intent.putExtra("bil3", "아이스\n모카라떼");
                insertExtra(13);
                intent.putExtra("bil4", "아이스\n토피넛라떼");
                insertExtra(17);

                sharedPreference.setStringArrayPref(MainActivity.this,"list10",cuslist10);
                startActivity(intent);
                finish();
            }
        });
    }


    // 처음 손님 왔을때 누르면 버튼 뜨게 하는거
    public void blackString() {
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtTalk.setVisibility(View.GONE);
                linBtn.setVisibility(View.VISIBLE);
                guestname.setVisibility(View.INVISIBLE);
                imageView16.setVisibility(View.VISIBLE);
                setting.setVisibility(View.INVISIBLE);
                frame.setClickable(false);
                frame.setBackground(null);

                timer.start();
                MAnimThread thread = new MAnimThread();
                thread.start();
            }
        });
    }

    //진상손님
    public void bcus0 () {
        final Resources gu = getResources();
        Glide.with(MainActivity.this).load(R.raw.bc1_1).into(guest);
        sharedPreference.setString(MainActivity.this, "name", "b0");
        guestname.setText("손님");
        goKitchen.setVisibility(View.INVISIBLE);
        btnSelect1.setText("주문 다시 확인하겠습니다. 따뜻한 아메리카노 주문하신 것 맞나요?");
        btnSelect2.setText("(따뜻한 아메리카노가 맞겠지...?)");
        btnSelect3.setText("주문하실 땐 좀 제대로 말씀해주실래요?");


        btnSelect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case1").setValue(0);
                imageView16.setVisibility(View.INVISIBLE);
                linBtn.setVisibility(View.INVISIBLE);
                Glide.with(MainActivity.this).load(R.raw.bc1_2).into(guest);
                txtTalk2.setVisibility(View.VISIBLE);
                txtTalk2.setText("아! 아뇨, 아이스로 주세요.");
                goKitchen.setVisibility(View.VISIBLE);
                goKitchen.setClickable(true);
                guestname.setVisibility(View.VISIBLE);
                frame.setClickable(false);
                frame.setBackground(drawable);
                goKitchen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                        intent.putExtra("bil1", "아이스\n아메리카노");
                        insertExtra(1);
                        startActivity(intent);
                        finish();
                    }
                });


            }
        });


        btnSelect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case6").setValue(1);
                linBtn.setVisibility(View.INVISIBLE);
                imageView16.setVisibility(View.INVISIBLE);
                guestname.setText("나");
                txtTalk2.setVisibility(View.VISIBLE);
                txtTalk2.setText("따뜻한 아메리카노 한 잔 나왔습니다!");
                Glide.with(MainActivity.this).load(R.raw.bc1_2).into(guest);
                frame.setClickable(true);
                guestname.setVisibility(View.VISIBLE);
                frame.setBackground(drawable);
                frame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        guestname.setVisibility(View.VISIBLE);
                        guestname.setText("손님");
                        txtTalk2.setText("전 아이스 시켰는데요?");
                        frame.setClickable(true);
                        frame.setBackground(drawable);
                        frame.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                guestname.setVisibility(View.VISIBLE);
                                guestname.setText("나");
                                txtTalk2.setText("네? 하지만 주문하실 때...");
                                frame.setClickable(true);
                                frame.setBackground(drawable);
                                frame.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        guestname.setVisibility(View.VISIBLE);
                                        guestname.setText("손님");
                                        txtTalk2.setText("전 아이스 시켰다니까요!");
                                        frame.setClickable(true);
                                        frame.setBackground(drawable);
                                        frame.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                guestname.setVisibility(View.VISIBLE);
                                                guestname.setText("나");
                                                txtTalk2.setText("...죄송합니다. 금방 다시 만들어 드릴게요.");
                                                frame.setClickable(false);
                                                frame.setBackground(drawable);
                                                goKitchen.setVisibility(View.VISIBLE);
                                                goKitchen.setClickable(true);
                                                goKitchen.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        timer.cancel();
                                                        Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                                                        intent.putExtra("bil1", " 아이스\n아메리카노");
                                                        insertExtra(1);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                                guestname.setVisibility(View.VISIBLE);
                                                frame.setClickable(false);
                                                frame.setBackground(drawable);
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });

        btnSelect3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case1").setValue(0);
                timer.cancel();
                sound();
                Intent intent = new Intent(MainActivity.this, WrongEndingActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
    public void bcus1 () {
        Glide.with(MainActivity.this).load(R.raw.bc2).into(guest);
        sharedPreference.setString(MainActivity.this, "name", "b1");
        goKitchen.setVisibility(View.INVISIBLE);
        guestname.setText("손님");
        btnSelect1.setText("할인이나 적립 둘 중 하나만 선택하세요.");
        btnSelect2.setText("저희 매장은 할인과 적립 동시 적용 가능 매장이 아니어서요. \n둘 중 하나만 이용 가능하신데, 어떤 걸로 하시겠어요?");
        btnSelect3.setText("(사장님께 전화해본다.)");


        btnSelect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case2").setValue(1);
                imageView16.setVisibility(View.INVISIBLE);
                linBtn.setVisibility(View.INVISIBLE);
                txtTalk2.setVisibility(View.VISIBLE);
                txtTalk2.setText("다른 곳은 다 되던데 왜 여기는 안 되는 거야! \n\n그리고 뭐 이리 불친절해?");
                goKitchen.setVisibility(View.VISIBLE);
                goKitchen.setClickable(true);
                guestname.setVisibility(View.VISIBLE);
                frame.setClickable(false);
                frame.setBackground(drawable);
                goKitchen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                        intent.putExtra("bil1", "아이스\n 카페라떼");
                        insertExtra(3);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        btnSelect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                linBtn.setVisibility(View.INVISIBLE);
                imageView16.setVisibility(View.INVISIBLE);
                txtTalk2.setVisibility(View.VISIBLE);
                txtTalk2.setText("그럼 할인만 해줘.");
                goKitchen.setVisibility(View.VISIBLE);
                goKitchen.setClickable(true);
                guestname.setVisibility(View.VISIBLE);
                frame.setClickable(false);
                frame.setBackground(drawable);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case2").setValue(0);

                goKitchen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                        intent.putExtra("bil1", "아이스\n 카페라떼");
                        insertExtra(3);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        btnSelect3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timer.cancel();
                linBtn.setVisibility(View.INVISIBLE);
                imageView16.setVisibility(View.INVISIBLE);
                txtTalk2.setVisibility(View.VISIBLE);
                guestname.setText("사장님");
                txtTalk2.setText("아직도 우리 매장 방침을 다 못 외우신 건가요? \n\n이런 걸로 나를 부르다니....");
                Glide.with(MainActivity.this).load(R.raw.boss_angry).into(guest);
                guestname.setVisibility(View.VISIBLE);
                frame.setClickable(false);
                frame.setBackground(drawable);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case2").setValue(0);
                frame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sound();
                        Intent intent = new Intent(MainActivity.this, WrongEndingActivity.class);
                        startActivity(intent);
                        finish();
                    }

                });
            }
        });
    }
    public void bcus2() {
        Resources gu = getResources();
        Glide.with(MainActivity.this).load(R.raw.bc3).into(guest);

        sharedPreference.setString(MainActivity.this, "name", "b2");
        goKitchen.setVisibility(View.INVISIBLE);
        guestname.setText("손님");
        btnSelect1.setText("네? 다 똑같이 생기셨는데…");
        btnSelect2.setText("네? 그냥 하나로 의견 모아서 주세요.");
        btnSelect3.setText("각자 한 잔씩 결제도 가능한데, 한 분씩 결제해드릴까요?");

        btnSelect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case3").setValue(0);
                timer.cancel();
                sound();
                Intent intent = new Intent(MainActivity.this, WrongEndingActivity.class);
                startActivity(intent);
                finish();

            }
        });

        btnSelect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                imageView16.setVisibility(View.INVISIBLE);
                linBtn.setVisibility(View.INVISIBLE);
                txtTalk2.setVisibility(View.VISIBLE);
                txtTalk2.setText("아... 네;; 단호박이시네...");
                goKitchen.setVisibility(View.VISIBLE);
                guestname.setVisibility(View.VISIBLE);
                frame.setClickable(false);
                frame.setBackground(drawable);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case3").setValue(1);
                goKitchen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                        intent.putExtra("bil1", "아이스\n아메리카노");
                        insertExtra(1);
                        intent.putExtra("bil2", "핫\n바닐라라떼");
                        insertExtra(6);
                        intent.putExtra("bil3", "핫\n아메리카노");
                        insertExtra(2);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        btnSelect3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                imageView16.setVisibility(View.INVISIBLE);
                linBtn.setVisibility(View.INVISIBLE);
                txtTalk2.setVisibility(View.VISIBLE);
                txtTalk2.setText("아... 네. 그렇게 해주세요...!");
                goKitchen.setVisibility(View.VISIBLE);
                guestname.setVisibility(View.VISIBLE);
                frame.setClickable(false);
                frame.setBackground(drawable);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case3").setValue(0);
                goKitchen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                        intent.putExtra("bil1", "아이스\n아메리카노");
                        insertExtra(1);
                        intent.putExtra("bil2", "핫\n바닐라라떼");
                        insertExtra(6);
                        intent.putExtra("bil3", "핫\n아메리카노");
                        insertExtra(2);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }
    public void bcus3() {
        Resources gu = getResources();
        Glide.with(MainActivity.this).load(R.raw.bc4).into(guest);

        sharedPreference.setString(MainActivity.this, "name", "b3");
        goKitchen.setVisibility(View.INVISIBLE);
        guestname.setText("손님");
        btnSelect1.setText("손님, 아이가 손으로 케잌을 만져서 판매가 어려워서\n이 케익까지 구매 해 주실 수 있을까요?");
        btnSelect2.setText("(못 본 척 한다.)");
        btnSelect3.setText("얘! 그걸 만지면 어떡하니!");

        btnSelect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView16.setVisibility(View.INVISIBLE);
                linBtn.setVisibility(View.INVISIBLE);
                txtTalk2.setVisibility(View.VISIBLE);
                txtTalk2.setText("아, 죄송해요. 애기가 만진 것까지 합쳐서 계산해주세요.");
                goKitchen.setVisibility(View.VISIBLE);
                guestname.setVisibility(View.VISIBLE);
                frame.setClickable(false);
                frame.setBackground(drawable);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case4").setValue(0);

                timer.cancel();

                goKitchen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        timer.cancel();
                        Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                        intent.putExtra("bil1", "아이스\n바닐라라떼");
                        insertExtra(5);
                        intent.putExtra("bil2", "딸기스무디");
                        insertExtra(8);
                        intent.putExtra("bil3", "핫\n아메리카노");
                        insertExtra(2);
                        startActivity(intent);
                        finish();
                    }
                });

            }
        });

        btnSelect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                imageView16.setVisibility(View.INVISIBLE);
                linBtn.setVisibility(View.INVISIBLE);
                txtTalk2.setVisibility(View.VISIBLE);
                txtTalk2.setText("(양심에 찔리지만 넘어갔다.)");
                goKitchen.setVisibility(View.VISIBLE);
                guestname.setVisibility(View.VISIBLE);
                frame.setClickable(false);
                frame.setBackground(drawable);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case4").setValue(1);
                goKitchen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                        intent.putExtra("bil1", "아이스\n바닐라라떼");
                        insertExtra(5);
                        intent.putExtra("bil2", "딸기스무디");
                        insertExtra(8);
                        intent.putExtra("bil3", "핫\n아메리카노");
                        insertExtra(2);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        btnSelect3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case4").setValue(0);
                timer.cancel();
                sound();
                Intent intent = new Intent(MainActivity.this, WrongEndingActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
    public void bcus4() {
        Resources gu = getResources();
        Glide.with(MainActivity.this).load(R.raw.bc5).into(guest);
        sharedPreference.setString(MainActivity.this, "name", "b4");
        goKitchen.setVisibility(View.INVISIBLE);
        guestname.setText("손님");
        btnSelect1.setText("죄송합니다! 바로 새 것으로 준비해드릴게요.");
        btnSelect2.setText("이 정도는 괜찮은 것 같은데요...");
        btnSelect3.setText("네...? 정 그러시면 새 걸로 드릴게요.");

        btnSelect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView16.setVisibility(View.INVISIBLE);
                linBtn.setVisibility(View.INVISIBLE);
                txtTalk2.setVisibility(View.VISIBLE);
                txtTalk2.setText("앞으론 좀 더 신경 쓰는 것이 좋겠어요.\n\n따뜻한 카페라떼도 줘요.");
                goKitchen.setVisibility(View.VISIBLE);
                guestname.setVisibility(View.VISIBLE);
                frame.setClickable(false);
                frame.setBackground(drawable);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case5").setValue(0);

                timer.cancel();

                goKitchen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        timer.cancel();
                        Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                        intent.putExtra("bil1", "핫\n 카페라떼");
                        insertExtra(4);
                        startActivity(intent);
                        finish();
                    }
                });

            }
        });

        btnSelect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case5").setValue(0);
                timer.cancel();
                sound();
                Intent intent = new Intent(MainActivity.this, WrongEndingActivity.class);
                startActivity(intent);
                finish();

            }
        });

        btnSelect3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                imageView16.setVisibility(View.INVISIBLE);
                linBtn.setVisibility(View.INVISIBLE);
                txtTalk2.setVisibility(View.VISIBLE);
                txtTalk2.setText("...케이크는 빼고 카페라떼 따뜻한 걸로 한 잔 줘요.");
                goKitchen.setVisibility(View.VISIBLE);
                guestname.setVisibility(View.VISIBLE);
                frame.setClickable(false);
                frame.setBackground(drawable);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case5").setValue(1);
                goKitchen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                        intent.putExtra("bil1", "핫\n 카페라떼");
                        insertExtra(4);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }
    public void bcus5() {
        guestname.setText("손님");
        Resources gu1 = getResources();
        Glide.with(MainActivity.this).load(R.raw.bc3n6).into(guest);
        sharedPreference.setString(MainActivity.this, "name", "b5");
        goKitchen.setVisibility(View.INVISIBLE);
        btnSelect1.setText("제가 궁예인 줄 아십니까?");
        btnSelect2.setText("죄송하지만 제가 기억이 안 나는데, 메뉴를 정확히 골라 주시겠어요?");
        btnSelect3.setText("네? 아메리카노?");

        btnSelect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case6").setValue(0);
                timer.cancel();
                sound();
                Intent intent = new Intent(MainActivity.this, WrongEndingActivity.class);
                startActivity(intent);
                finish();

            }
        });


        btnSelect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case6").setValue(0);
                timer.cancel();
                imageView16.setVisibility(View.INVISIBLE);
                linBtn.setVisibility(View.INVISIBLE);
                txtTalk2.setVisibility(View.VISIBLE);
                txtTalk2.setText("따뜻한 바닐라라떼 1잔 주세요...");
                goKitchen.setVisibility(View.VISIBLE);
                guestname.setVisibility(View.VISIBLE);
                frame.setBackground(drawable);
                frame.setClickable(false);

                goKitchen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                        intent.putExtra("bil1", "핫\n바닐라라떼");
                        insertExtra(6);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        btnSelect3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case6").setValue(1);
                linBtn.setVisibility(View.INVISIBLE);
                imageView16.setVisibility(View.INVISIBLE);
                txtTalk2.setVisibility(View.VISIBLE);
                txtTalk2.setText("아니 아니, 그거 말고 다른거 시켰잖아요");
                frame.setClickable(true);
                guestname.setVisibility(View.VISIBLE);
                frame.setBackground(drawable);
                frame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        guestname.setVisibility(View.VISIBLE);
                        guestname.setText("나");
                        txtTalk2.setText("네? 혹시 카페라떼..?");
                        frame.setClickable(true);
                        frame.setBackground(drawable);
                        frame.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                guestname.setVisibility(View.VISIBLE);
                                guestname.setText("손님");
                                txtTalk2.setText("아니이~ 그거 말고 달짝지근한!");
                                frame.setClickable(true);
                                frame.setBackground(drawable);
                                frame.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        guestname.setVisibility(View.VISIBLE);
                                        guestname.setText("나");
                                        txtTalk2.setText("잘 모르겠는데,,,");
                                        frame.setClickable(true);
                                        frame.setBackground(drawable);
                                        frame.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                guestname.setVisibility(View.VISIBLE);
                                                guestname.setText("손님");
                                                txtTalk2.setText("에이, 내가 힌트 줄게. ‘바’로 시작하는거!");
                                                frame.setClickable(true);
                                                frame.setBackground(drawable);
                                                frame.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        guestname.setVisibility(View.VISIBLE);
                                                        guestname.setText("다음 손님");
                                                        txtTalk2.setText("(짜증을 내며) 저기요, 저 빨리 주문하고 나가봐야 하거든요? \n\n언제까지 스무고개 하고 있을 거예요?");
                                                        frame.setClickable(true);
                                                        frame.setBackground(drawable);
                                                        frame.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                guestname.setVisibility(View.VISIBLE);
                                                                guestname.setText("나");
                                                                txtTalk2.setText("아, 따뜻한 바닐라라떼요! \n\n(다음 손님에게) 죄송합니다, 조금만 더 기다려주세요!");
                                                                goKitchen.setVisibility(View.VISIBLE);
                                                                frame.setClickable(false);
                                                                frame.setBackground(drawable);

                                                                goKitchen.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {
                                                                        timer.cancel();
                                                                        Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                                                                        intent.putExtra("bil1", " 핫\n바닐라라떼");
                                                                        insertExtra(6);
                                                                        startActivity(intent);
                                                                        finish();
                                                                    }
                                                                });
                                                                guestname.setVisibility(View.VISIBLE);

                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });

                            }
                        });
                    }
                });
            }
        });
    }
    public void bcus6 () {

        Glide.with(MainActivity.this).load(R.raw.bc7).into(guest);
        sharedPreference.setString(MainActivity.this, "name", "b6");
        goKitchen.setVisibility(View.INVISIBLE);
        guestname.setText("손님");
        btnSelect1.setText("죄송하지만 저희 매장은 1인 1메뉴가 원칙이어서요. \n음료 세 잔 더 주문해주실 수 있으신가요?");
        btnSelect2.setText("(그냥 넘어간다.)");
        btnSelect3.setText("두 잔으로 아주 뽕 제대로 뽑으시네요!");


        btnSelect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case7").setValue(0);
                imageView16.setVisibility(View.INVISIBLE);
                linBtn.setVisibility(View.INVISIBLE);
                txtTalk2.setVisibility(View.VISIBLE);
                txtTalk2.setText("아이고! 호호호~ 그럼 라떼랑 아메리카노로\n\n뜨뜻~한거랑 시원~한거 한 잔씩 줘요~");
                goKitchen.setVisibility(View.VISIBLE);
                guestname.setVisibility(View.VISIBLE);
                frame.setClickable(false);
                frame.setBackground(drawable);
                goKitchen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                        intent.putExtra("bil1", "핫\n 카페라떼");
                        insertExtra(4);
                        intent.putExtra("bil2", "핫\n아메리카노");
                        insertExtra(2);
                        intent.putExtra("bil3", "아이스\n 카페라떼");
                        insertExtra(3);
                        intent.putExtra("bil4", "아이스\n아메리카노");
                        insertExtra(1);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        btnSelect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                linBtn.setVisibility(View.INVISIBLE);
                imageView16.setVisibility(View.INVISIBLE);
                txtTalk2.setVisibility(View.VISIBLE);
                guestname.setText("사장님");
                txtTalk2.setText("알바씨, 우리 매장 1인 1메뉴인데... \n\n일단 결제했으니 이번만 넘어갈게. ");
                Glide.with(MainActivity.this).load(R.raw.boss_angry).into(guest);
                guestname.setVisibility(View.VISIBLE);
                frame.setClickable(false);
                frame.setBackground(drawable);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case7").setValue(1);
                goKitchen.setVisibility(View.VISIBLE);
                goKitchen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                        intent.putExtra("bil1", "핫\n아메리카노");
                        insertExtra(2);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        btnSelect3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case7").setValue(0);
                timer.cancel();
                sound();
                Intent intent = new Intent(MainActivity.this, WrongEndingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void bcus7 () {
        Glide.with(MainActivity.this).load(R.raw.bc8).into(guest);
        sharedPreference.setString(MainActivity.this, "name", "b7");
        goKitchen.setVisibility(View.INVISIBLE);
        guestname.setText("손님");
        btnSelect1.setText("죄송하지만 저희 매장에는 없는 메뉴입니다. \n얼음 갈린 음료 중에는 초코 스무디가 제일 잘 나가요!");
        btnSelect2.setText("그런 메뉴가 없어서요... 다른 걸로 시키세요.");
        btnSelect3.setText("그런 메뉴는 여기 말고 별다방으로 가세요.");


        btnSelect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case8").setValue(0);
                imageView16.setVisibility(View.INVISIBLE);
                linBtn.setVisibility(View.INVISIBLE);
                guestname.setText("손님");
                txtTalk2.setVisibility(View.VISIBLE);
                txtTalk2.setText("그럼 그걸로 주세요!");
                goKitchen.setVisibility(View.VISIBLE);
                guestname.setVisibility(View.VISIBLE);
                frame.setClickable(false);
                frame.setBackground(drawable);
                goKitchen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                        intent.putExtra("bil1", "초코스무디");
                        insertExtra(7);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        btnSelect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                linBtn.setVisibility(View.INVISIBLE);
                imageView16.setVisibility(View.INVISIBLE);
                frame.setClickable(false);
                frame.setBackground(drawable);
                guestname.setVisibility(View.VISIBLE);
                guestname.setText("손님");
                txtTalk2.setVisibility(View.VISIBLE);
                txtTalk2.setText("아... 네; 그럼... 음... 딸기 스무디 주세요.");
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case7").setValue(1);
                goKitchen.setVisibility(View.VISIBLE);
                goKitchen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                        intent.putExtra("bil1", "딸기스무디");
                        insertExtra(8);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        btnSelect3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case7").setValue(0);
                timer.cancel();
                sound();
                Intent intent = new Intent(MainActivity.this, WrongEndingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void bcus8 () {
        final Resources gu = getResources();
        Glide.with(MainActivity.this).load(R.raw.bc9).into(guest);
        sharedPreference.setString(MainActivity.this, "name", "b8");
        goKitchen.setVisibility(View.INVISIBLE);
        Random trandom = new Random();
        final int t = trandom.nextInt(2);

        guestname.setText("손님");
        btnSelect1.setText("???");
        btnSelect2.setText("???");
        btnSelect3.setText("???");

        btnSelect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linBtn.setVisibility(View.INVISIBLE);
                txtTalk2.setVisibility(View.VISIBLE);
                guest.setVisibility(View.INVISIBLE);
                guestname.setVisibility(View.VISIBLE);
                frame.setVisibility(View.VISIBLE);
                sound();
                Intent intent = new Intent(MainActivity.this, TrashEndingActivity.class);
                intent.putExtra("trash",t);
                startActivity(intent);
                guestname.setText("나");
                txtTalk2.setText("쓰레기,,,,,,,,,,우엑,,,,,,,윽,,,,");
            }
        });


        btnSelect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linBtn.setVisibility(View.INVISIBLE);
                txtTalk2.setVisibility(View.VISIBLE);
                guest.setVisibility(View.INVISIBLE);
                guestname.setVisibility(View.VISIBLE);
                frame.setVisibility(View.VISIBLE);
                sound();
                Intent intent = new Intent(MainActivity.this, TrashEndingActivity.class);
                intent.putExtra("trash",t);
                startActivity(intent);
                guestname.setText("나");
                txtTalk2.setText("쓰레기,,,,,,,,,,우엑,,,,,,,윽,,,,");
            }
        });

        btnSelect3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linBtn.setVisibility(View.INVISIBLE);
                txtTalk2.setVisibility(View.VISIBLE);
                guest.setVisibility(View.INVISIBLE);
                guestname.setVisibility(View.VISIBLE);
                frame.setVisibility(View.VISIBLE);
                sound();
                Intent intent = new Intent(MainActivity.this, TrashEndingActivity.class);
                intent.putExtra("trash",t);
                startActivity(intent);
                guestname.setText("나");
                txtTalk2.setText("쓰레기,,,,,,,,,,우엑,,,,,,,윽,,,,");


            }
        });
    }
    public void bcus9 () {
        Glide.with(MainActivity.this).load(R.raw.bc10).into(guest);
        sharedPreference.setString(MainActivity.this, "name", "b9");
        goKitchen.setVisibility(View.INVISIBLE);
        guestname.setText("손님");
        btnSelect1.setText("단 커피 종류를 찾으신다면, 모카라떼나 바닐라라떼는 어떠세요?");
        btnSelect2.setText("샷이 추가되면 음료가 달아지는 게 아니고 더 써져요.");
        btnSelect3.setText("(그냥 카페라떼 만들자...)");


        btnSelect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case10").setValue(0);
                imageView16.setVisibility(View.INVISIBLE);
                linBtn.setVisibility(View.INVISIBLE);
                txtTalk2.setVisibility(View.VISIBLE);
                txtTalk2.setText("그럼 따뜻한 모카라떼로 부탁해요~");
                goKitchen.setVisibility(View.VISIBLE);
                guestname.setVisibility(View.VISIBLE);
                guestname.setText("손님");
                frame.setClickable(false);
                frame.setBackground(drawable);
                goKitchen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                        intent.putExtra("bil1", "핫\n  모카라떼");
                        insertExtra(14);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        btnSelect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                linBtn.setVisibility(View.INVISIBLE);
                imageView16.setVisibility(View.INVISIBLE);
                guestname.setText("손님");
                guestname.setVisibility(View.VISIBLE);
                txtTalk2.setVisibility(View.VISIBLE);
                txtTalk2.setText("흠흠... 말투가 조금 까칠하네\n\n그럼 모카라떼 따뜻한 걸로 줘요~");
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                frame.setClickable(false);
                frame.setBackground(drawable);
                databaseReference.child(uid).child("case10").setValue(1);
                goKitchen.setVisibility(View.VISIBLE);
                goKitchen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                        intent.putExtra("bil1", "핫\n  모카라떼");
                        insertExtra(14);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        btnSelect3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                linBtn.setVisibility(View.INVISIBLE);
                imageView16.setVisibility(View.INVISIBLE);
                txtTalk2.setVisibility(View.VISIBLE);
                txtTalk2.setText("내가 원했던 건 이런 맛이 아닌데...?");
                guestname.setVisibility(View.VISIBLE);
                guestname.setText("손님");
                frame.setClickable(true);
                frame.setBackground(drawable);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case10").setValue(0);
                frame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sound();
                        Intent intent = new Intent(MainActivity.this, WrongEndingActivity.class);
                        startActivity(intent);
                        finish();
                    }

                });
            }
        });
    }
    public void bcus10 () {
        Glide.with(MainActivity.this).load(R.raw.bc11).into(guest);
        sharedPreference.setString(MainActivity.this, "name", "b10");
        goKitchen.setVisibility(View.INVISIBLE);
        guestname.setText("손님");
        btnSelect1.setText("가게마다 가격이 다를 수 있어서요. \n혹시 통신사 멤버십 갖고 계시면 할인해 드릴게요!");
        btnSelect2.setText("(그냥 내가 100원 채우자...)");
        btnSelect3.setText("그럼 다른 동네 가서 드세요.");


        btnSelect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case10").setValue(0);
                imageView16.setVisibility(View.INVISIBLE);
                linBtn.setVisibility(View.INVISIBLE);
                txtTalk2.setVisibility(View.VISIBLE);
                txtTalk2.setText("그럼 그렇게 해주던가!");
                goKitchen.setVisibility(View.VISIBLE);
                guestname.setVisibility(View.VISIBLE);
                frame.setClickable(false);
                frame.setBackground(drawable);
                goKitchen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                        intent.putExtra("bil1", "아이스\n아메리카노");
                        insertExtra(1);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        btnSelect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                imageView16.setVisibility(View.INVISIBLE);
                linBtn.setVisibility(View.INVISIBLE);
                guestname.setText("나");
                txtTalk2.setVisibility(View.VISIBLE);
                txtTalk2.setText("(에휴... 차라리 이게 마음 편하지...)");
                goKitchen.setVisibility(View.VISIBLE);
                guestname.setVisibility(View.VISIBLE);
                frame.setClickable(false);
                frame.setBackground(drawable);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case11").setValue(1);
                goKitchen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                        intent.putExtra("bil1", "아이스\n아메리카노");
                        insertExtra(1);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        btnSelect3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case11").setValue(0);
                timer.cancel();
                sound();
                Intent intent = new Intent(MainActivity.this, WrongEndingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void bcus11 () {
        Glide.with(MainActivity.this).load(R.raw.bc12).into(guest);
        guestname.setText("손님");
        goKitchen.setVisibility(View.INVISIBLE);
        sharedPreference.setString(MainActivity.this, "name", "b11");
        btnSelect1.setText("(영업시간 끝났으니 무시하자...)");
        btnSelect2.setText("죄송하지만 영업시간이 끝나서 주문을 받을 수 없습니다.\n다음에 방문해주세요!");
        btnSelect3.setText("영업 끝났어요~");

        btnSelect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                linBtn.setVisibility(View.INVISIBLE);
                imageView16.setVisibility(View.INVISIBLE);
                txtTalk2.setVisibility(View.VISIBLE);
                guestname.setText("손님");
                txtTalk2.setText("뭐야! 지금 나 무시하냐?");
                guestname.setVisibility(View.VISIBLE);
                frame.setClickable(false);
                frame.setBackground(drawable);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case12").setValue(0);
                frame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sound();
                        Intent intent = new Intent(MainActivity.this, WrongEndingActivity.class);
                        startActivity(intent);
                        finish();
                    }

                });
            }
        });

        btnSelect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                linBtn.setVisibility(View.INVISIBLE);
                imageView16.setVisibility(View.INVISIBLE);
                txtTalk2.setVisibility(View.VISIBLE);
                txtTalk2.setText("에잉... 그래, 일 열심히 해라!");
                guestname.setText("손님");
                guestname.setVisibility(View.VISIBLE);
                frame.setClickable(false);
                frame.setBackground(drawable);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case12").setValue(0);
                frame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, WhichEndingActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        btnSelect3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                linBtn.setVisibility(View.INVISIBLE);
                imageView16.setVisibility(View.INVISIBLE);
                txtTalk2.setVisibility(View.VISIBLE);
                txtTalk2.setText("에잉...");
                guestname.setText("손님");
                guestname.setVisibility(View.VISIBLE);
                frame.setClickable(false);
                frame.setBackground(drawable);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                databaseReference.child(uid).child("case12").setValue(1);
                frame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sound();
                        Intent intent = new Intent(MainActivity.this, WhichEndingActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }


         //23일 랜덤
    public void rand_23day(){
        i = rand.nextInt(16);
        while ((cuslist.get(i)).equals("빈칸")) {
            i--;
            if (i == -1) {
                i++;
                while ((cuslist.get(i)).equals("빈칸")) {
                    i++;
                }
            }
        }
        txtTalk.setText(cuslist.get(i));
        switch (i) {
            case 0: {
                gcus00();
                break;
            }
            case 1: {
                gcus01();
                break;
            }

            case 2: {
                gcus02();
                break;
            }
            case 3: {
                gcus03();
                break;
            }
            case 4: {
                gcus04();
                break;
            }
            case 5: {
                gcus05();
                break;
            }
            case 6: {
                gcus06();
                break;
            }
            case 7: {
                gcus07();
                break;
            }
            case 8: {
                gcus08();
                break;
            }
            case 9: { ;
                gcus09();
                break;
            }
            case 10: {
                gcus0();
                break;
            }
            case 11: {
                gcus1();
                break;
            }
            case 12: {
                gcus2();
                break;
            }
            case 13: {
                gcus4();
                break;
            }
            case 14: {
                gcus13();
                break;
            }
            case 15: {
                gcus14();
                break;
            }
        }
    }
         //24일 랜덤
        public void rand_24day() {
            i = rand.nextInt(14);
            while ((cuslist2.get(i)).equals("빈칸")) {
                i--;
                if (i == -1) {
                    i++;
                    while ((cuslist.get(i)).equals("빈칸")) {
                        i++;
                    }
                }
            }
            txtTalk.setText(cuslist2.get(i));
            switch (i) {
                case 0: {
                    gcus3();
                    break;
                }
                case 1: {
                    gcus5();
                    break;
                }

                case 2: {
                    gcus6();
                    break;
                }
                case 3: {
                    gcus7();
                    break;
                }
                case 4: {
                    gcus8();
                    break;
                }
                case 5: {
                    gcus9();
                    break;
                }
                case 6: {
                    gcus10();
                    break;
                }
                case 7: {
                    gcus11();
                    break;
                }
                case 8: {
                    gcus12();
                    break;
                }
                case 9: { ;
                    gcus010();
                    break;
                }
                case 10: {
                    gcus011();
                    break;
                }
                case 11: {
                    gcus012();
                    break;
                }
                case 12: {
                    gcus013();
                    break;
                }
                case 13: {
                    gcus014();
                    break;
                }
            }
        }
        //25일 랜덤
        public void rand_25day(){
            i = rand.nextInt(9);
            while ((cuslist3.get(i)).equals("빈칸")) {
                i--;
                if (i == -1) {
                    i++;
                    while ((cuslist3.get(i)).equals("빈칸")) {
                        i++;
                    }
                }
            }
            txtTalk.setText(cuslist2.get(i));
            switch (i) {
                case 0: {
                    gcus15();
                    break;
                }

                case 1: {
                    gcus16();
                    break;
                }

                case 2: {
                    gcus17();
                    break;
                }
                case 3: {
                    gcus18();
                    break;
                }
                case 4: {
                    gcus19();
                    break;
                }
                case 5: {
                    gcus015();
                    break;
                }
                case 6: {
                    gcus016();
                    break;
                }
                case 7: {
                    gcus017();
                    break;
                }
                case 8: {
                    gcus018();
                    break;
                }

            }
        }
        //26일 랜덤
    public void rand_26day(){
        i = rand.nextInt(13);
        while ((cuslist4.get(i)).equals("빈칸")) {
            i--;
            if (i == -1) {
                i++;
                while ((cuslist4.get(i)).equals("빈칸")) {
                    i++;
                }
            }
        }
        txtTalk.setText(cuslist2.get(i));
        switch (i) {
            case 0: {
                gcus019();
                break;
            }

            case 1: {
                gcus020();
                break;
            }

            case 2: {
                gcus021();
                break;
            }
            case 3: {
                gcus022();
                break;
            }
            case 4: {
                gcus0222();
                break;
            }
            case 5: {
                gcus023();
                break;
            }
            case 6: {
                gcus024();
                break;
            }
            case 7: {
                gcus025();
                break;
            }
            case 8: {
                gcus026();
                break;
            }
            case 9: {
                gcus027();
                break;
            }
            case 10: {
                gcus028();
                break;
            }
            case 11: {
                gcus029();
                break;
            }
            case 12: {
                gcus030();
                break;
            }
        }
    }
        //28일 랜덤
        public void rand_28day() {
        i = rand.nextInt(8);
        while(cuslist6.get(i).equals("빈칸")) {
            i--;
            if (i == -1) {
                i++;
                while(cuslist8.get(i).equals("빈칸")) {
                    i++;
                }
            }
        }
        txtTalk.setText(cuslist6.get(i));
        switch (i) {
            case 0: {
                gcus031();
                break;
            }
            case 1: {
                gcus032();
                break;
            }
            case 2: {
                gcus033();
                break;
            }
            case 3: {
                gcus034();
                break;
            }
            case 4: {
                gcus035();
                break;
            }
            case 5: {
                gcus036();
                break;
            }
            case 6: {
                gcus037();
                break;
            }
            case 7: {
                gcus038();
                break;
            }
        }
    }
        //30일 랜덤
        public void rand_30day(){
        i = rand.nextInt(12);
        while ((cuslist8.get(i)).equals("빈칸")) {
            i--;
            if (i == -1) {
                i++;
                while ((cuslist8.get(i)).equals("빈칸")) {
                    i++;
                }
            }
        }
        txtTalk.setText(cuslist8.get(i));
        switch (i) {
            case 0: {
                gcus039();
                break;
            }

            case 1: {
                gcus040();
                break;
            }

            case 2: {
                gcus041();
                break;
            }
            case 3: {
                gcus042();
                break;
            }
            case 4: {
                gcus043();
                break;
            }
            case 5: {
                gcus044();
                break;
            }
            case 6: {
                gcus045();
                break;
            }
            case 7: {
                gcus046();
                break;
            }
            case 8: {
                gcus047();
                break;
            }
            case 9: {
                gcus048();
                break;
            }
            case 10: {
                gcus049();
                break;
            }
            case 11: {
                gcus050();
                break;
            }
        }
    }
        //1월1일 메뉴
        public void rand_1day(){
            i = rand.nextInt(18);
            while ((cuslist10.get(i)).equals("빈칸")) {
                i--;
                if (i == -1) {
                    i++;
                    while ((cuslist10.get(i)).equals("빈칸")) {
                        i++;
                    }
                }
            }
            txtTalk.setText(cuslist10.get(i));
            switch (i) {
                case 0: {
                    gcus20();
                    break;
                }
                case 1: {
                    gcus21();
                    break;
                }
                case 2:{
                    gcus22();
                    break;
                }
                case 3:{
                    gcus23();
                    break;
                }
                case 4:{
                    gcus24();
                    break;
                }
                case 5:{
                    gcus25();
                    break;
                }
                case 6: {
                    gcus26();
                    break;
                }
                case 7: {
                    gcus27();
                    break;
                }
                case 8: {
                    gcus28();
                    break;
                }
                case 9: {
                    gcus29();
                    break;
                }
                case 10: {
                    gcus051();
                    break;
                }
                case 11: {
                    gcus052();
                    break;
                }
                case 12: {
                    gcus053();
                    break;
                }
                case 13: {
                    gcus054();
                    break;
                }
                case 14: {
                    gcus055();
                    break;
                }
                case 15: {
                    gcus056();
                    break;
                }
                case 16: {
                    gcus057();
                    break;
                }
                case 17: {
                    gcus058();
                    break;
                }
            }
        }

        //진상손님 랜덤
        public void rand_bc(){
        i = rand.nextInt(11);
        while ((cuslist4.get(i)).equals("빈칸")) {
            i--;
            if (i == -1) {
                i++;
                while ((cuslist4.get(i)).equals("빈칸")) {
                    i++;
                }
            }
        }
        switch (i) {
            case 0: {
                txtTalk.setText(cuslist4.get(0));
                blackString();
                bcus0();
                break;
            }
            case 1: {
                txtTalk.setText(cuslist4.get(1));
                blackString();
                bcus1();
                break;
            }
            case 2:{
                txtTalk.setText(cuslist4.get(2));
                blackString();
                bcus2();
                break;
            }
            case 3:{
                txtTalk.setText(cuslist4.get(3));
                blackString();
                bcus3();
                break;
            }
            case 4:{
                txtTalk.setText(cuslist4.get(4));
                blackString();
                bcus4();
                break;
            }
            case 5:{
                txtTalk.setText(cuslist4.get(5));
                blackString();
                bcus5();
                break;
            }
            case 6: {
                txtTalk.setText(cuslist4.get(6));
                blackString();
                bcus6();
                break;

            }
            case 7: {
                txtTalk.setText(cuslist4.get(7));
                blackString();
                bcus7();
                break;
            }
            case 8:{
                txtTalk.setText(cuslist4.get(8));
                frame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtTalk.setVisibility(View.GONE);
                        linBtn.setVisibility(View.VISIBLE);
                        guestname.setVisibility(View.INVISIBLE);
                        imageView16.setVisibility(View.INVISIBLE);
                        setting.setVisibility(View.INVISIBLE);
                        frame.setClickable(false);
                        frame.setVisibility(View.INVISIBLE);
                    }
                });
                bcus8();
                break;
            }
            case 9: {
                txtTalk.setText(cuslist4.get(9));
                blackString();
                bcus9();
                break;
            }
            case 10: {
                txtTalk.setText(cuslist4.get(10));
                blackString();
                bcus10();
                break;
            }
        }
    }

        public void insertExtra(int name) {
        ContentValues values = new ContentValues();

        int cup = 0;
        int base = 0;
        int ice = 0;
        int coffee = 0;
        int ingredient1 = 0;
        int ingredient2 = 0;
        int ingredient3 = 0;

        values.put("menu_name", name);

        values.put("cup", cup);
        values.put("base", base);
        values.put("ice", ice);
        values.put("coffee", coffee);
        values.put("ingredient1", ingredient1);
        values.put("ingredient2", ingredient2);
        values.put("ingredient3", ingredient3);

        db.insert(TABLE_USER, null, values);
    }

        public class MAnimThread extends Thread{
        public void run(){
            int index = 0 ;
            for(int i = 0; i < 6; i++){
                final Drawable drawable3 = drawables.get(index);
                index ++;

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView16.setImageDrawable(drawable3);
                    }
                });
                try{
                    Thread.sleep(2900);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void sound(){
        if (touchonoff == true) {
            pool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

            bip = pool.load(this, R.raw.wrong, 1);
        }
    }
    public void toast(){
        String txt = "마감 후 청소 중...";
        int time = Toast.LENGTH_SHORT;
        Toast toast = new Toast(MainActivity.this);

        View view = View.inflate(MainActivity.this,R.layout.activity_toast, null);
        TextView textView = view.findViewById(R.id.textView);
        textView.setText(txt);
        toast.setView(view);
        toast.setDuration(time);
        toast.setGravity(Gravity.CENTER,90,0);
        toast.show();
    }
    public void onBackPressed(){
        Intent intent = new Intent(MainActivity.this,EndGameActivity.class);
        startActivity(intent);
    }
}





