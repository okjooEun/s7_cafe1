package com.example.s7_cafe;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.s7_cafe.UserInputContract.UserTable.SQL_CREATE_USER_TABLE;
import static com.example.s7_cafe.UserInputContract.UserTable.SQL_DELETE_TABLE;
import static com.example.s7_cafe.UserInputContract.UserTable.TABLE_USER;
import static com.example.s7_cafe.UserInputContract.UserTable.SQL_DELETE_TABLE;

public class KitchenActivity extends AppCompatActivity {

    SQLiteDatabase db;
    SQLiteDatabase db2;
    SQLiteDatabase db3;
    SQLiteDatabase menu;

    final MenuDbhelper userDbhelper = new MenuDbhelper(this);
    final MenuDbhelper menuDbhelper = new MenuDbhelper(this);

    AnimThread thread1 = new AnimThread();
    AnimThread thread2 = new AnimThread();
    AnimThread thread3 = new AnimThread();
    AnimThread thread4 = new AnimThread();

    Dialog dialog;

    SoundPool pool;
    int bip, tada, waste, recipe;

    private List<Menu> menuList;
    private List<User_Input> userList;
    boolean running = false;

    Button iceCup, iceWater, iceMilk, hotCup, hotWater, hotMilk, blender, recipeBook, btnMake, trash;
    ImageView icon0, icon1, icon2, icon3, icon4, icon5, icon6, icon7, icon8;
    ImageView selectCup, selectWM, selectIng, selectIng2, selectIng3, selectIng4, selectBlen, imageView15, setting, helpb, correct1,correct2, correct3,calenderview;
    TextView bil1, bil2, bil3, bil4, datetext;
    int ice, cup, coffee, ingredient1, ingredient2, ingredient3, base;
    String strColor = "#FF0000";
    int count;
    String a,b,c,d;

    private CountDownTimer ktimer1, ktimer2, ktimer3, ktimer4;

    Boolean checkimg = true;
    Boolean touchonoff;

    SharedPreferenceUtill sharedPreference = new SharedPreferenceUtill();

    ArrayList<Drawable> drawables = new ArrayList<Drawable>();
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);
        startService(new Intent(getApplicationContext(), TotalMusic.class));

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

        ktimer1 = new CountDownTimer(15 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                //엔딩 화면 넣기
                Intent intent = new Intent(KitchenActivity.this, TimeOverEndingActivity.class);
                db.execSQL(SQL_DELETE_TABLE);
                db.execSQL(SQL_CREATE_USER_TABLE);
                selectIng.setTag("check1");
                selectIng2.setTag("check2");
                startActivity(intent);
                finish();
            }
        };

        ktimer2 = new CountDownTimer(15 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                //엔딩 화면 넣기
                Intent intent = new Intent(KitchenActivity.this, TimeOverEndingActivity.class);
                db.execSQL(SQL_DELETE_TABLE);
                db.execSQL(SQL_CREATE_USER_TABLE);
                selectIng.setTag("check1");
                selectIng2.setTag("check2");
                startActivity(intent);
                finish();
            }
        };

        ktimer3 = new CountDownTimer(15 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                //엔딩 화면 넣기
                Intent intent = new Intent(KitchenActivity.this, TimeOverEndingActivity.class);
                db.execSQL(SQL_DELETE_TABLE);
                db.execSQL(SQL_CREATE_USER_TABLE);
                selectIng.setTag("check1");
                selectIng2.setTag("check2");
                startActivity(intent);
                finish();
            }
        };

        ktimer4 = new CountDownTimer(15 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                //엔딩 화면 넣기
                Intent intent = new Intent(KitchenActivity.this, TimeOverEndingActivity.class);
                db.execSQL(SQL_DELETE_TABLE);
                db.execSQL(SQL_CREATE_USER_TABLE);
                selectIng.setTag("check1");
                selectIng2.setTag("check2");
                startActivity(intent);
                finish();
            }
        };

        Resources resources0 = getResources();
        drawables.add(resources0.getDrawable(R.drawable.bar5));
        drawables.add(resources0.getDrawable(R.drawable.bar4));
        drawables.add(resources0.getDrawable(R.drawable.bar3));
        drawables.add(resources0.getDrawable(R.drawable.bar2));
        drawables.add(resources0.getDrawable(R.drawable.bar1));
        drawables.add(resources0.getDrawable(R.drawable.bar0));


        final MenuDbhelper menuDbhelper = new MenuDbhelper(this);
        menuList = menuDbhelper.getAllMenu();

        final MenuDbhelper userDbhelper = new MenuDbhelper(this);
        userList = userDbhelper.getUserInput();


        db = userDbhelper.getWritableDatabase();


        icon0 = (ImageView) findViewById(R.id.icon0);
        icon1 = (ImageView) findViewById(R.id.icon1);
        icon2 = (ImageView) findViewById(R.id.icon2);
        icon3 = (ImageView) findViewById(R.id.icon3);
        icon4 = (ImageView) findViewById(R.id.icon4);
        icon5 = (ImageView) findViewById(R.id.icon5);
        icon6 = (ImageView) findViewById(R.id.icon6);
        icon7 = (ImageView) findViewById(R.id.icon7);
        icon8 = (ImageView) findViewById(R.id.icon8);
        setting = (ImageView) findViewById(R.id.setting);
        helpb = (ImageView) findViewById(R.id.helpb);


        iceCup = (Button) findViewById(R.id.iceCup);
        hotCup = (Button) findViewById(R.id.hotCup);
        iceWater = (Button) findViewById(R.id.iceWater);
        hotWater = (Button) findViewById(R.id.hotWater);
        iceMilk = (Button) findViewById(R.id.iceMilk);
        hotMilk = (Button) findViewById(R.id.hotMilk);
        blender = (Button) findViewById(R.id.blender);
        recipeBook = (Button) findViewById(R.id.recipeBook);
        btnMake = (Button) findViewById(R.id.btnMake);

        selectCup = (ImageView) findViewById(R.id.selectCup);
        selectWM = (ImageView) findViewById(R.id.selectWM);
        selectBlen = (ImageView) findViewById(R.id.selectBlen);
        selectIng = (ImageView) findViewById(R.id.selectIng);
        selectIng2 = (ImageView) findViewById(R.id.selectIng2);
        selectIng3 = (ImageView) findViewById(R.id.selectIng3);
        selectIng4 = (ImageView) findViewById(R.id.selectIng4);
        imageView15 = (ImageView) findViewById(R.id.imageView15);
        calenderview = (ImageView) findViewById(R.id.calenderview);

        bil1 = (TextView) findViewById(R.id.bil1);
        bil2 = (TextView) findViewById(R.id.bil2);
        bil3 = (TextView) findViewById(R.id.bil3);
        bil4 = (TextView) findViewById(R.id.bil4);
        datetext = (TextView) findViewById(R.id.datetext);

        correct1 = (ImageView) findViewById(R.id.correct1);
        correct2 = (ImageView) findViewById(R.id.correct2);
        correct3 =(ImageView) findViewById(R.id.correct3);


        //세팅값 1초마다 가져오기
        TimerTask myTask = new TimerTask() {
            @Override
            public void run() {
               reset();
            }
        };
        Timer timer = new Timer();
        timer.schedule(myTask, 1000,1000);

        count = sharedPreference.getInt(this, "count");


        pool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        bip = pool.load(this, R.raw.wrong, 1);
        tada = pool.load(this, R.raw.tada, 1);
        waste = pool.load(this, R.raw.waste, 1);
        recipe = pool.load(this, R.raw.recipe, 1);

        Intent intent = getIntent();

        a = intent.getExtras().getString("bil1");
        bil1.setText(a);

        b = intent.getExtras().getString("bil2");
        bil2.setText(b);

        c = intent.getExtras().getString("bil3");
        bil3.setText(c);

        d = intent.getExtras().getString("bil4");
        bil4.setText(d);

        switch (count) {
            case 1: case 2: case 3: case 4: case 5:
                datetext.setText("23");
                baseicon();
                icon3.setImageResource(R.drawable.choco_off);
                icon4.setImageResource(R.drawable.straw_off);
                icon5.setImageResource(R.drawable.banana_off);
                icon6.setImageResource(R.drawable.mash_off);
                icon7.setImageResource(R.drawable.toff_off);
                icon8.setImageResource(R.drawable.mouse_off);

                icon3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { toast();
                    }
                });
                icon4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { toast(); }
                });
                icon5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                icon6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                icon7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                icon8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                break;
            case 6: case 7: case 8: case 9: case 10: case 11:
                datetext.setText("24");
                baseicon();
                icon3.setImageResource(R.drawable.icon3);
                icon4.setImageResource(R.drawable.icon4);
                icon5.setImageResource(R.drawable.banana_off);
                icon6.setImageResource(R.drawable.mash_off);
                icon7.setImageResource(R.drawable.toff_off);
                icon8.setImageResource(R.drawable.mouse_off);

                icon3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources res = getResources();
                        final Drawable drawable = res.getDrawable(R.drawable.choco_on);

                        if (checkimg == true) {
                            selectIng.setImageDrawable(drawable);
                            checkimg = false;
                            selectIng.setTag("icon3");
                        } else if (checkimg == false) {
                            selectIng2.setVisibility(View.VISIBLE);
                            selectIng2.setImageDrawable(drawable);
                            checkimg = true;
                            selectIng2.setTag("icon3");
                        }

                    }
                });
                icon4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources res = getResources();
                        final Drawable drawable = res.getDrawable(R.drawable.straw_on);

                        if (checkimg == true) {
                            selectIng.setImageDrawable(drawable);
                            checkimg = false;
                            selectIng.setTag("icon4");
                        } else if (checkimg == false) {
                            selectIng2.setVisibility(View.VISIBLE);
                            selectIng2.setImageDrawable(drawable);
                            checkimg = true;
                            selectIng2.setTag("icon4");
                        }

                    }
                });
                icon5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                icon6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                icon7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                icon8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                break;
            case 12: case 13: case 14: case 15: case 16: case 17:
                datetext.setText("25");
                datetext.setTextColor(Color.parseColor(strColor));
                baseicon();
                icon3.setImageResource(R.drawable.icon3);
                icon4.setImageResource(R.drawable.icon4);
                icon5.setImageResource(R.drawable.banana_off);
                icon6.setImageResource(R.drawable.icon6);
                icon7.setImageResource(R.drawable.toff_off);
                icon8.setImageResource(R.drawable.mouse_off);
                icon3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources res = getResources();
                        final Drawable drawable = res.getDrawable(R.drawable.choco_on);

                        if (checkimg == true) {
                            selectIng.setImageDrawable(drawable);
                            checkimg = false;
                            selectIng.setTag("icon3");
                        } else if (checkimg == false) {
                            selectIng2.setVisibility(View.VISIBLE);
                            selectIng2.setImageDrawable(drawable);
                            checkimg = true;
                            selectIng2.setTag("icon3");
                        }

                    }
                });
                icon4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources res = getResources();
                        final Drawable drawable = res.getDrawable(R.drawable.straw_on);

                        if (checkimg == true) {
                            selectIng.setImageDrawable(drawable);
                            checkimg = false;
                            selectIng.setTag("icon4");
                        } else if (checkimg == false) {
                            selectIng2.setVisibility(View.VISIBLE);
                            selectIng2.setImageDrawable(drawable);
                            checkimg = true;
                            selectIng2.setTag("icon4");
                        }

                    }
                });
                icon5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(KitchenActivity.this, "이 재료는 사용할 수 없어요", Toast.LENGTH_SHORT).show();
                    }
                });
                icon6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources res = getResources();
                        final Drawable drawable = res.getDrawable(R.drawable.mash_on);

                        if (checkimg == true) {
                            selectIng.setImageDrawable(drawable);
                            checkimg = false;
                            selectIng.setTag("icon6");
                        } else if (checkimg == false) {
                            selectIng2.setVisibility(View.VISIBLE);
                            selectIng2.setImageDrawable(drawable);
                            checkimg = true;
                            selectIng2.setTag("icon6");
                        }
                    }
                });
                icon7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                icon8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                break;
            case 18: case 19: case 20: case 21: case 22: case 23: case 24:
                datetext.setText("26");
                baseicon();
                icon3.setImageResource(R.drawable.icon3);
                icon4.setImageResource(R.drawable.icon4);
                icon5.setImageResource(R.drawable.banana_off);
                icon6.setImageResource(R.drawable.mash_off);
                icon7.setImageResource(R.drawable.toff_off);
                icon8.setImageResource(R.drawable.mouse_off);
                icon3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources res = getResources();
                        final Drawable drawable = res.getDrawable(R.drawable.choco_on);

                        if (checkimg == true) {
                            selectIng.setImageDrawable(drawable);
                            checkimg = false;
                            selectIng.setTag("icon3");
                        } else if (checkimg == false) {
                            selectIng2.setVisibility(View.VISIBLE);
                            selectIng2.setImageDrawable(drawable);
                            checkimg = true;
                            selectIng2.setTag("icon3");
                        }

                    }
                });
                icon4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources res = getResources();
                        final Drawable drawable = res.getDrawable(R.drawable.straw_on);

                        if (checkimg == true) {
                            selectIng.setImageDrawable(drawable);
                            checkimg = false;
                            selectIng.setTag("icon4");
                        } else if (checkimg == false) {
                            selectIng2.setVisibility(View.VISIBLE);
                            selectIng2.setImageDrawable(drawable);
                            checkimg = true;
                            selectIng2.setTag("icon4");
                        }
                    }
                });
                icon5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                icon6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                icon7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                icon8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                break;
            case 25: case 26: case 27: case 28: case 29: case 30: case 31:
                datetext.setText("27");
                baseicon();
                icon3.setImageResource(R.drawable.icon3);
                icon4.setImageResource(R.drawable.icon4);
                icon5.setImageResource(R.drawable.banana_off);
                icon6.setImageResource(R.drawable.mash_off);
                icon7.setImageResource(R.drawable.toff_off);
                icon8.setImageResource(R.drawable.mouse_off);
                icon3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources res = getResources();
                        final Drawable drawable = res.getDrawable(R.drawable.choco_on);

                        if (checkimg == true) {
                            selectIng.setImageDrawable(drawable);
                            checkimg = false;
                            selectIng.setTag("icon3");
                        } else if (checkimg == false) {
                            selectIng2.setVisibility(View.VISIBLE);
                            selectIng2.setImageDrawable(drawable);
                            checkimg = true;
                            selectIng2.setTag("icon3");
                        }

                    }
                });
                icon4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources res = getResources();
                        final Drawable drawable = res.getDrawable(R.drawable.straw_on);

                        if (checkimg == true) {
                            selectIng.setImageDrawable(drawable);
                            checkimg = false;
                            selectIng.setTag("icon4");
                        } else if (checkimg == false) {
                            selectIng2.setVisibility(View.VISIBLE);
                            selectIng2.setImageDrawable(drawable);
                            checkimg = true;
                            selectIng2.setTag("icon4");
                        }
                    }
                });
                icon5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                icon6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                icon7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                icon8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                break;
            case 32: case 33: case 34: case 35: case 36: case 37: case 38: case 39:
                datetext.setText("28");
                baseicon();
                icon3.setImageResource(R.drawable.icon3);
                icon4.setImageResource(R.drawable.icon4);
                icon5.setImageResource(R.drawable.banana_off);
                icon6.setImageResource(R.drawable.mash_off);
                icon7.setImageResource(R.drawable.toff_off);
                icon8.setImageResource(R.drawable.mouse_off);
                icon3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources res = getResources();
                        final Drawable drawable = res.getDrawable(R.drawable.choco_on);

                        if (checkimg == true) {
                            selectIng.setImageDrawable(drawable);
                            checkimg = false;
                            selectIng.setTag("icon3");
                        } else if (checkimg == false) {
                            selectIng2.setVisibility(View.VISIBLE);
                            selectIng2.setImageDrawable(drawable);
                            checkimg = true;
                            selectIng2.setTag("icon3");
                        }

                    }
                });
                icon4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources res = getResources();
                        final Drawable drawable = res.getDrawable(R.drawable.straw_on);

                        if (checkimg == true) {
                            selectIng.setImageDrawable(drawable);
                            checkimg = false;
                            selectIng.setTag("icon4");
                        } else if (checkimg == false) {
                            selectIng2.setVisibility(View.VISIBLE);
                            selectIng2.setImageDrawable(drawable);
                            checkimg = true;
                            selectIng2.setTag("icon4");
                        }

                    }
                });
                icon5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                icon6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                icon7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                icon8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                break;
            case 40: case 41: case 42: case 43: case 44: case 45: case 46: case 47: case 48:
                datetext.setText("29");
                baseicon();
                icon3.setImageResource(R.drawable.icon3);
                icon4.setImageResource(R.drawable.icon4);
                icon5.setImageResource(R.drawable.banana_off);
                icon6.setImageResource(R.drawable.mash_off);
                icon7.setImageResource(R.drawable.toff_off);
                icon8.setImageResource(R.drawable.mouse_off);
                icon3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources res = getResources();
                        final Drawable drawable = res.getDrawable(R.drawable.choco_on);

                        if (checkimg == true) {
                            selectIng.setImageDrawable(drawable);
                            checkimg = false;
                            selectIng.setTag("icon3");
                        } else if (checkimg == false) {
                            selectIng2.setVisibility(View.VISIBLE);
                            selectIng2.setImageDrawable(drawable);
                            checkimg = true;
                            selectIng2.setTag("icon3");
                        }

                    }
                });
                icon4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources res = getResources();
                        final Drawable drawable = res.getDrawable(R.drawable.straw_on);

                        if (checkimg == true) {
                            selectIng.setImageDrawable(drawable);
                            checkimg = false;
                            selectIng.setTag("icon4");
                        } else if (checkimg == false) {
                            selectIng2.setVisibility(View.VISIBLE);
                            selectIng2.setImageDrawable(drawable);
                            checkimg = true;
                            selectIng2.setTag("icon4");
                        }

                    }
                });
                icon5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                icon6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                icon7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                icon8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                break;
            case 49: case 50: case 51: case 52: case 53: case 54: case 55: case 56: case 57: case 58:
                datetext.setText("30");
                baseicon();
                icon3.setImageResource(R.drawable.icon3);
                icon4.setImageResource(R.drawable.icon4);
                icon5.setImageResource(R.drawable.icon5);
                icon6.setImageResource(R.drawable.mash_off);
                icon7.setImageResource(R.drawable.toff_off);
                icon8.setImageResource(R.drawable.mouse_off);
                icon3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources res = getResources();
                        final Drawable drawable = res.getDrawable(R.drawable.choco_on);

                        if (checkimg == true) {
                            selectIng.setImageDrawable(drawable);
                            checkimg = false;
                            selectIng.setTag("icon3");
                        } else if (checkimg == false) {
                            selectIng2.setVisibility(View.VISIBLE);
                            selectIng2.setImageDrawable(drawable);
                            checkimg = true;
                            selectIng2.setTag("icon3");
                        }

                    }
                });
                icon4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources res = getResources();
                        final Drawable drawable = res.getDrawable(R.drawable.straw_on);

                        if (checkimg == true) {
                            selectIng.setImageDrawable(drawable);
                            checkimg = false;
                            selectIng.setTag("icon4");
                        } else if (checkimg == false) {
                            selectIng2.setVisibility(View.VISIBLE);
                            selectIng2.setImageDrawable(drawable);
                            checkimg = true;
                            selectIng2.setTag("icon4");
                        }

                    }
                });
                icon5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources res = getResources();
                        final Drawable drawable = res.getDrawable(R.drawable.banana_on);

                        if (checkimg == true) {
                            selectIng.setImageDrawable(drawable);
                            checkimg = false;
                            selectIng.setTag("icon5");
                        } else if (checkimg == false) {
                            selectIng2.setVisibility(View.VISIBLE);
                            selectIng2.setImageDrawable(drawable);
                            checkimg = true;
                            selectIng2.setTag("icon5");
                        }

                    }
                });
                icon6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                icon7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                icon8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                break;
            case 59: case 60: case 61: case 62: case 63: case 64: case 65: case 66: case 67: case 68:
                datetext.setText("31");
                baseicon();
                icon3.setImageResource(R.drawable.icon3);
                icon4.setImageResource(R.drawable.icon4);
                icon5.setImageResource(R.drawable.icon5);
                icon6.setImageResource(R.drawable.mash_off);
                icon7.setImageResource(R.drawable.toff_off);
                icon8.setImageResource(R.drawable.mouse_off);
                icon3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources res = getResources();
                        final Drawable drawable = res.getDrawable(R.drawable.choco_on);

                        if (checkimg == true) {
                            selectIng.setImageDrawable(drawable);
                            checkimg = false;
                            selectIng.setTag("icon3");
                        } else if (checkimg == false) {
                            selectIng2.setVisibility(View.VISIBLE);
                            selectIng2.setImageDrawable(drawable);
                            checkimg = true;
                            selectIng2.setTag("icon3");
                        }

                    }
                });
                icon4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources res = getResources();
                        final Drawable drawable = res.getDrawable(R.drawable.straw_on);

                        if (checkimg == true) {
                            selectIng.setImageDrawable(drawable);
                            checkimg = false;
                            selectIng.setTag("icon4");
                        } else if (checkimg == false) {
                            selectIng2.setVisibility(View.VISIBLE);
                            selectIng2.setImageDrawable(drawable);
                            checkimg = true;
                            selectIng2.setTag("icon4");
                        }

                    }
                });
                icon5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources res = getResources();
                        final Drawable drawable = res.getDrawable(R.drawable.banana_on);

                        if (checkimg == true) {
                            selectIng.setImageDrawable(drawable);
                            checkimg = false;
                            selectIng.setTag("icon5");
                        } else if (checkimg == false) {
                            selectIng2.setVisibility(View.VISIBLE);
                            selectIng2.setImageDrawable(drawable);
                            checkimg = true;
                            selectIng2.setTag("icon5");
                        }

                    }
                });
                icon6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                icon7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                icon8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast();
                    }
                });
                break;

            case 69: case 70: case 71: case 72: case 73: case 74: case 75: case 76: case 77: case 78: case 79:
                Resources res = getResources();
                final Drawable drawable = res.getDrawable(R.drawable.jcalendar);
                calenderview.setImageDrawable(drawable);
                datetext.setText(" 1");
                datetext.setTextColor(Color.parseColor(strColor));
                baseicon();
                icon3.setImageResource(R.drawable.icon3);
                icon4.setImageResource(R.drawable.icon4);
                icon5.setImageResource(R.drawable.banana_on);
                icon6.setImageResource(R.drawable.mash_off);
                icon7.setImageResource(R.drawable.toff_icon);
                icon8.setImageResource(R.drawable.mouse_icon);
                icon3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources res = getResources();
                        final Drawable drawable = res.getDrawable(R.drawable.choco_on);

                        if (checkimg == true) {
                            selectIng.setImageDrawable(drawable);
                            checkimg = false;
                            selectIng.setTag("icon3");
                        } else if (checkimg == false) {
                            selectIng2.setVisibility(View.VISIBLE);
                            selectIng2.setImageDrawable(drawable);
                            checkimg = true;
                            selectIng2.setTag("icon3");
                        }

                    }
                });
                icon4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources res = getResources();
                        final Drawable drawable = res.getDrawable(R.drawable.straw_on);

                        if (checkimg == true) {
                            selectIng.setImageDrawable(drawable);
                            checkimg = false;
                            selectIng.setTag("icon4");
                        } else if (checkimg == false) {
                            selectIng2.setVisibility(View.VISIBLE);
                            selectIng2.setImageDrawable(drawable);
                            checkimg = true;
                            selectIng2.setTag("icon4");
                        }

                    }
                });
                icon5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources res = getResources();
                        final Drawable drawable = res.getDrawable(R.drawable.banana_on);

                        if (checkimg == true) {
                            selectIng.setImageDrawable(drawable);
                            checkimg = false;
                            selectIng.setTag("icon5");
                        } else if (checkimg == false) {
                            selectIng2.setVisibility(View.VISIBLE);
                            selectIng2.setImageDrawable(drawable);
                            checkimg = true;
                            selectIng2.setTag("icon5");
                        }
                    }
                });
                icon6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       toast();
                    }
                });
                icon7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources res = getResources();
                        final Drawable drawable = res.getDrawable(R.drawable.toff_on);

                        if (checkimg == true) {
                            selectIng.setImageDrawable(drawable);
                            checkimg = false;
                            selectIng.setTag("icon7");
                        } else if (checkimg == false) {
                            selectIng2.setVisibility(View.VISIBLE);
                            selectIng2.setImageDrawable(drawable);
                            checkimg = true;
                            selectIng2.setTag("icon7");
                        }
                    }
                });
                icon8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources res = getResources();
                        final Drawable drawable = res.getDrawable(R.drawable.mouse_on);

                        selectIng4.setVisibility(View.VISIBLE);
                        selectIng4.setImageDrawable(drawable);
                        selectIng4.setTag("icon8");
                    }
                });
                break;
        }

        trash = (Button) findViewById(R.id.trash);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(KitchenActivity.this, SettingActivity2.class);
                        startActivity(intent);
                    }
                });
            }
        });

        iceCup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cup = 100;
                insertCup(cup);
                Resources res = getResources();
                final Drawable drawable = res.getDrawable(R.drawable.icecup_on);
                selectCup.setImageDrawable(drawable);
            }
        });

        hotCup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cup = 101;
                insertCup(cup);
                Resources res = getResources();
                final Drawable drawable = res.getDrawable(R.drawable.hotcup_on);
                selectCup.setImageDrawable(drawable);

            }
        });

        iceWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base = 102;
                insertBase(base);
                Resources res = getResources();

                final Drawable drawable = res.getDrawable(R.drawable.coldwater_on);
                selectWM.setImageDrawable(drawable);
            }
        });

        hotWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base = 103;
                insertBase(base);
                Resources res = getResources();

                final Drawable drawable = res.getDrawable(R.drawable.hotwater_on);
                selectWM.setImageDrawable(drawable);
            }
        });

        iceMilk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base = 106;
                insertBase(base);
                Resources res = getResources();
                final Drawable drawable = res.getDrawable(R.drawable.coldmilk_on);
                selectWM.setImageDrawable(drawable);
            }
        });

        hotMilk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base = 107;
                insertBase(base);
                Resources res = getResources();
                final Drawable drawable = res.getDrawable(R.drawable.hotmilk_on);
                selectWM.setImageDrawable(drawable);
            }
        });
        helpb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(KitchenActivity.this);


                dialog = adb.create();

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = 1920;
                lp.height = 1100;

                dialog.show();

                Window window = dialog.getWindow();
                window.setAttributes(lp);
                Resources res = getResources();
                final Drawable drawable = res.getDrawable(R.drawable.help);
                window.setBackgroundDrawable(drawable);
                handler.postDelayed(new Runnable()  {
                    public void run() {
                        dialog.cancel();
                    }
                }, 3000);




            }
        });
        blender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources res = getResources();
                ingredient3 = 110;
                insertIngredient3(ingredient3);
                final Drawable drawable = res.getDrawable(R.drawable.blender_on);
                selectBlen.setImageDrawable(drawable);
            }
        });

        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertCup(0);
                insertBase(0);
                insertIce(0);
                insertCoffee(0);
                insertIngredient1(0);
                insertIngredient2(0);
                insertIngredient3(0);

                if(touchonoff == true) {
                    pool.play(waste, 1, 1, 0, 0, 1);
                }

                selectCup.setImageDrawable(null);
                selectWM.setImageDrawable(null);
                selectIng.setImageDrawable(null);
                selectIng.setTag("check1");
                selectIng2.setImageDrawable(null);
                selectIng2.setTag("check2");
                selectIng3.setImageDrawable(null);
                selectIng3.setTag("check3");
                selectBlen.setImageDrawable(null);
                selectIng4.setImageDrawable(null);
                selectIng4.setTag("check4");
                selectIng3.setVisibility(View.GONE);
                selectIng2.setVisibility(View.GONE);
                selectIng4.setVisibility(View.GONE);

                checkimg = true;
            }
        });

        recipeBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(touchonoff == true) {
                    pool.play(recipe, 1, 1, 0, 0, 1);
                }
                Intent intent = new Intent(KitchenActivity.this, RecipebookActivity.class);
                startActivity(intent);

            }
        });

        btnMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ("icon0".equals(selectIng.getTag()) || "icon0".equals(selectIng2.getTag())||"icon0".equals(selectIng3.getTag()) ) {
                    ice = 104;
                    insertIce(ice);
                }
                if ("icon1".equals(selectIng.getTag()) || "icon1".equals(selectIng2.getTag())) {
                    coffee = 105;
                    insertCoffee(coffee);
                }
                if ("icon2".equals(selectIng.getTag()) || "icon2".equals(selectIng2.getTag())) {
                    ingredient1 = 108;
                    insertIngredient1(ingredient1);
                }
                if ("icon3".equals(selectIng.getTag()) || "icon3".equals(selectIng2.getTag())) {
                    ingredient1 = 109;
                    insertIngredient1(ingredient1);
                }
                if ("icon4".equals(selectIng.getTag()) || "icon4".equals(selectIng2.getTag())) {
                    ingredient1 = 111;
                    insertIngredient1(ingredient1);
                }
                if ("icon5".equals(selectIng.getTag()) || "icon5".equals(selectIng2.getTag())) {
                    ingredient2 = 113;
                    insertIngredient2(ingredient2);
                }
                if ("icon6".equals(selectIng.getTag()) || "icon6".equals(selectIng2.getTag())) {
                    ingredient3 = 112;
                    insertIngredient3(ingredient3);
                }
                if ("icon7".equals(selectIng.getTag()) || "icon7".equals(selectIng2.getTag())) {
                    ingredient2 = 114;
                    insertIngredient2(ingredient2);
                }
                if ("icon8".equals(selectIng4.getTag())) {
                    ingredient3 = 115;
                    insertIngredient3(ingredient3);
                }

                executeQuery();
                checkBill();

                insertCup(0);
                insertBase(0);
                insertIce(0);
                insertCoffee(0);
                insertIngredient1(0);
                insertIngredient2(0);
                insertIngredient3(0);


                selectCup.setImageDrawable(null);
                selectWM.setImageDrawable(null);
                selectIng.setImageDrawable(null);
                selectIng.setTag("check1");
                selectIng2.setImageDrawable(null);
                selectIng2.setTag("check2");
                selectIng3.setImageDrawable(null);
                selectIng3.setTag("check3");
                selectBlen.setImageDrawable(null);
                selectIng4.setImageDrawable(null);
                selectIng4.setTag("check4");
                selectIng3.setVisibility(View.GONE);
                selectIng2.setVisibility(View.GONE);
                selectIng4.setVisibility(View.GONE);

                //running = false;
                checkimg = true;

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        startTimer();
    }


    public class AnimThread extends Thread {
        @Override
        public void run() {
            running = true;
            int index = 0;
            for (int i = 0; i < 6 && running; i++){
                final Drawable drawable3 = drawables.get(index);
                index += 1;

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView15.setImageDrawable(drawable3);
                    }
                });
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().isInterrupted();
                }
            }
        }

    }

    public void insertCup(int cup) {
        ContentValues values = new ContentValues();
        values.put("cup", cup);
        db.update(TABLE_USER, values, null, null);
    }

    public void insertBase(int base) {
        ContentValues values = new ContentValues();
        values.put("base", base);
        db.update(TABLE_USER, values, null, null);
    }


    public void insertIce(int ice) {
        ContentValues values = new ContentValues();
        values.put("ice", ice);
        db.update(TABLE_USER, values, null, null);
    }

    public void insertCoffee(int coffee) {
        ContentValues values = new ContentValues();
        values.put("coffee", coffee);
        db.update(TABLE_USER, values, null, null);
    }

    public void insertIngredient1(int ingredient1) {
        ContentValues values = new ContentValues();
        values.put("ingredient1", ingredient1);
        db.update(TABLE_USER, values, null, null);
    }

    public void insertIngredient2(int ingredient2) {
        ContentValues values = new ContentValues();
        values.put("ingredient2", ingredient2);
        db.update(TABLE_USER, values, null, null);
    }

    public void insertIngredient3(int ingredient3) {
        ContentValues values = new ContentValues();
        values.put("ingredient3", ingredient3);
        db.update(TABLE_USER, values, null, null);
    }

    public void startTimer() {
        db3 =  userDbhelper.getReadableDatabase();
        String sql_check = "select _id from user_input";
        Cursor c = db3.rawQuery(sql_check, null);

        int getCount = c.getCount();
        for (int i = 0; i < getCount; i++) {
            c.moveToNext();
            int num = c.getInt(0);
        }
        c.moveToFirst();
        if (getCount == 1) {
            ktimer1.start();
            thread1.start();
        } else if (getCount == 2) {
            ktimer2.start();
            thread1.start();
        } else if (getCount == 3) {
            ktimer3.start();
            thread1.start();
        } else if (getCount == 4) {
            ktimer4.start();
            thread1.start();
        }
    }

    public void newTimer() {
        db3 =  userDbhelper.getReadableDatabase();
        String sql_check = "select _id from user_input";
        Cursor c = db3.rawQuery(sql_check, null);

        int getCount = c.getCount();
        for (int i = 0; i < getCount; i++) {
            c.moveToNext();
            int num = c.getInt(0);
        }
        c.moveToFirst();
        if (getCount == 0) {
            ktimer1.cancel();
            if(thread1.isAlive()){
                thread1.interrupt();
            }
            running = false;

        } else if (getCount == 1) {
            ktimer2.cancel();
            ktimer1.start();
            runningThread();
        } else if (getCount == 2) {
            ktimer3.cancel();
            ktimer2.start();
            runningThread();
        } else if (getCount == 3) {
            ktimer4.cancel();
            ktimer3.start();
            runningThread();
        }
    }

    public void runningThread() {

        if (thread1.isAlive()) {
            running = false;
            thread1.interrupt();
            drawables.clear();
        }

        Resources resources0 = getResources();
        drawables.add(resources0.getDrawable(R.drawable.bar5));
        drawables.add(resources0.getDrawable(R.drawable.bar4));
        drawables.add(resources0.getDrawable(R.drawable.bar3));
        drawables.add(resources0.getDrawable(R.drawable.bar2));
        drawables.add(resources0.getDrawable(R.drawable.bar1));
        drawables.add(resources0.getDrawable(R.drawable.bar0));

        thread1 = new AnimThread();
        thread1.start();

    }
    //빌지 체크
    public void checkBill() {
        db3 = userDbhelper.getReadableDatabase();

        String sql_check = "select _id from user_input";
        Cursor c = db3.rawQuery(sql_check, null);
        int getCount = c.getCount();
        for (int i = 0; i < getCount; i++) {
            c.moveToNext();
            int num = c.getInt(0);
        }

        c.moveToFirst();
        if (getCount >= 1) {
            for (int j = 0; j < getCount; j++) {

                c.moveToFirst();

                if (c.getInt(0) == 3) {
                    correct2.setVisibility(View.VISIBLE);
                    correct3.setVisibility(View.INVISIBLE);
                } else if (c.getInt(0) == 4) {
                    correct3.setVisibility(View.VISIBLE);

                } else if (c.getInt(0) == 2) {
                    correct1.setVisibility(View.VISIBLE);
                    correct2.setVisibility(View.INVISIBLE);
                    correct3.setVisibility(View.INVISIBLE);
                }
            }
        }

    }

    public void toast(){
        String txt = "이 재료는 사용할 수 없어요.";
        int time = Toast.LENGTH_SHORT;
        Toast toast = new Toast(KitchenActivity.this);

        View view = View.inflate(KitchenActivity.this,R.layout.custom_toast, null);
        TextView textView = view.findViewById(R.id.toast);
        textView.setText(txt);
        toast.setView(view);
        toast.setDuration(time);
        toast.setGravity(Gravity.CENTER,100,-10);
        toast.show();
    }

    //재료 비교하기
    public void executeQuery() {


        db2 = userDbhelper.getReadableDatabase();

        String sql_user = "select menu_name, cup, base, coffee, ice, ingredient1, ingredient2, ingredient3 from user_input";

        Cursor c2 = db2.rawQuery(sql_user, null);

        int userCount = c2.getCount();


        for (int i = 0; i < userCount; i++) {
            c2.moveToNext();
            int menu = c2.getInt(0);
            int cup2 = c2.getInt(1);
            int base = c2.getInt(2);
            int coffee = c2.getInt(3);
            int ice = c2.getInt(4);
            int ingredient1 = c2.getInt(5);
            int ingredient2 = c2.getInt(6);
            int ingredient3 = c2.getInt(7);
        }

        for (int j = 0; j < userCount; j++) {


            c2.moveToFirst();


            if (c2.getInt(0) == 1) {

                menu = menuDbhelper.getReadableDatabase();
                String sql_recipe = "select cup, base, coffee, ice, ingredient1, ingredient2, ingredient3 from menu_recipe where menu_name = 1 ";

                Cursor cursor = menu.rawQuery(sql_recipe, null);

                int recipeCount = cursor.getCount();

                for (int i = 0; i < recipeCount; i++) {
                    cursor.moveToNext();
                    int cup1 = cursor.getInt(0);
                    int base = cursor.getInt(1);
                    int coffee = cursor.getInt(2);
                    int ice = cursor.getInt(3);
                    int ingredient1 = cursor.getInt(4);
                    int ingredient2 = cursor.getInt(5);
                    int ingredient3 = cursor.getInt(6);
                }

                if (cursor.getInt(0) == c2.getInt(1) && cursor.getInt(1) == c2.getInt(2) && cursor.getInt(2) == c2.getInt(3) && cursor.getInt(3) == c2.getInt(4) && cursor.getInt(4) == c2.getInt(5) && cursor.getInt(5) == c2.getInt(6) && cursor.getInt(6) == c2.getInt(7)) {
                    if(touchonoff == true) {
                    pool.play(tada, 1,1,0,0,1);}
                    String sql = "delete from user_input where menu_name = 1";
                    db.execSQL(sql);
                    Intent intent = new Intent(KitchenActivity.this, MakeSuccessActivity.class);
                    intent.putExtra("menuname",1);
                    startActivity(intent);
                    //running = false;
                    newTimer();
                } else {
                    //경고 화면 띄우기
                    if(touchonoff == true) {
                    pool.play(bip, 1,1,0,0,1);}
                    Intent intent = new Intent(KitchenActivity.this, WrongKitchenActivity.class);
                    startActivity(intent);
                }
            } c2.moveToFirst();
            if (c2.getInt(0) == 2) {

                menu = menuDbhelper.getReadableDatabase();
                String sql_recipe = "select cup, base, coffee, ice, ingredient1, ingredient2, ingredient3 from menu_recipe where menu_name = 2 ";

                Cursor cursor = menu.rawQuery(sql_recipe, null);

                int recipeCount = cursor.getCount();

                for (int i = 0; i < recipeCount; i++) {
                    cursor.moveToNext();
                    int cup1 = cursor.getInt(0);
                    int base = cursor.getInt(1);
                    int coffee = cursor.getInt(2);
                    int ice = cursor.getInt(3);
                    int ingredient1 = cursor.getInt(4);
                    int ingredient2 = cursor.getInt(5);
                    int ingredient3 = cursor.getInt(6);

                }

                if (cursor.getInt(0) == c2.getInt(1) && cursor.getInt(1) == c2.getInt(2) && cursor.getInt(2) == c2.getInt(3) && cursor.getInt(3) == c2.getInt(4) && cursor.getInt(4) == c2.getInt(5) && cursor.getInt(5) == c2.getInt(6) && cursor.getInt(6) == c2.getInt(7)) {
                    if(touchonoff == true) {
                   pool.play(tada, 1,1,0,0,1);}
                    String sql = "delete from user_input where menu_name = 2";
                    db.execSQL(sql);
                    Intent intent = new Intent(KitchenActivity.this, MakeSuccessActivity.class);
                    intent.putExtra("menuname",2);
                    startActivity(intent);
                    //running = false;
                    newTimer();
                } else {
                    //경고 화면 띄우기
                    if(touchonoff == true) {
                    pool.play(bip, 1,1,0,0,1);}
                    Intent intent = new Intent(KitchenActivity.this, WrongKitchenActivity.class);
                    startActivity(intent);
                }
            } c2.moveToFirst();
            if (c2.getInt(0) == 3) {

                menu = menuDbhelper.getReadableDatabase();
                String sql_recipe = "select cup, base, coffee, ice, ingredient1, ingredient2, ingredient3 from menu_recipe where menu_name = 3 ";

                Cursor cursor = menu.rawQuery(sql_recipe, null);

                int recipeCount = cursor.getCount();

                for (int i = 0; i < recipeCount; i++) {
                    cursor.moveToNext();
                    int cup1 = cursor.getInt(0);
                    int base = cursor.getInt(1);
                    int coffee = cursor.getInt(2);
                    int ice = cursor.getInt(3);
                    int ingredient1 = cursor.getInt(4);
                    int ingredient2 = cursor.getInt(5);
                    int ingredient3 = cursor.getInt(6);
                }

                if (cursor.getInt(0) == c2.getInt(1) && cursor.getInt(1) == c2.getInt(2) && cursor.getInt(2) == c2.getInt(3) && cursor.getInt(3) == c2.getInt(4) && cursor.getInt(4) == c2.getInt(5) && cursor.getInt(5) == c2.getInt(6) && cursor.getInt(6) == c2.getInt(7)) {

                    if(touchonoff == true) {pool.play(tada, 1,1,0,0,1);}
                    String sql = "delete from user_input where menu_name = 3";
                    db.execSQL(sql);
                    Intent intent = new Intent(KitchenActivity.this, MakeSuccessActivity.class);
                    intent.putExtra("menuname",3);
                    startActivity(intent);
                    //running = false;
                    newTimer();
                } else {
                    //경고 화면 띄우기
                    if(touchonoff == true) { pool.play(bip, 1,1,0,0,1);}
                    Intent intent = new Intent(KitchenActivity.this, WrongKitchenActivity.class);
                    startActivity(intent);
                }
            } c2.moveToFirst();
            if (c2.getInt(0) == 4) {

                menu = menuDbhelper.getReadableDatabase();
                String sql_recipe = "select cup, base, coffee, ice, ingredient1, ingredient2, ingredient3 from menu_recipe where menu_name = 4 ";

                Cursor cursor = menu.rawQuery(sql_recipe, null);

                int recipeCount = cursor.getCount();

                for (int i = 0; i < recipeCount; i++) {
                    cursor.moveToNext();
                    int cup1 = cursor.getInt(0);
                    int base = cursor.getInt(1);
                    int coffee = cursor.getInt(2);
                    int ice = cursor.getInt(3);
                    int ingredient1 = cursor.getInt(4);
                    int ingredient2 = cursor.getInt(5);
                    int ingredient3 = cursor.getInt(6);
                }

                if (cursor.getInt(0) == c2.getInt(1) && cursor.getInt(1) == c2.getInt(2) && cursor.getInt(2) == c2.getInt(3) && cursor.getInt(3) == c2.getInt(4) && cursor.getInt(4) == c2.getInt(5) && cursor.getInt(5) == c2.getInt(6) && cursor.getInt(6) == c2.getInt(7)) {
                    if(touchonoff == true) {  pool.play(tada, 1,1,0,0,1);}
                    String sql = "delete from user_input where menu_name = 4";
                    db.execSQL(sql);
                    Intent intent = new Intent(KitchenActivity.this, MakeSuccessActivity.class);
                    intent.putExtra("menuname",4);
                    startActivity(intent);
                  //  running = false;
                    newTimer();
                } else {
                    //경고 화면 띄우기
                    if(touchonoff == true) {pool.play(bip, 1,1,0,0,1);}
                    Intent intent = new Intent(KitchenActivity.this, WrongKitchenActivity.class);
                    startActivity(intent);
                }
            } c2.moveToFirst();
            if (c2.getInt(0) == 5) {

                menu = menuDbhelper.getReadableDatabase();
                String sql_recipe = "select cup, base, coffee, ice, ingredient1, ingredient2, ingredient3 from menu_recipe where menu_name = 5 ";

                Cursor cursor = menu.rawQuery(sql_recipe, null);

                int recipeCount = cursor.getCount();

                for (int i = 0; i < recipeCount; i++) {
                    cursor.moveToNext();
                    int cup1 = cursor.getInt(0);
                    int base = cursor.getInt(1);
                    int coffee = cursor.getInt(2);
                    int ice = cursor.getInt(3);
                    int ingredient1 = cursor.getInt(4);
                    int ingredient2 = cursor.getInt(5);
                    int ingredient3 = cursor.getInt(6);
                }

                if (cursor.getInt(0) == c2.getInt(1) && cursor.getInt(1) == c2.getInt(2) && cursor.getInt(2) == c2.getInt(3) && cursor.getInt(3) == c2.getInt(4) && cursor.getInt(4) == c2.getInt(5) && cursor.getInt(5) == c2.getInt(6) && cursor.getInt(6) == c2.getInt(7)) {
                    if(touchonoff == true) { pool.play(tada, 1,1,0,0,1);}
                    String sql = "delete from user_input where menu_name = 5";
                    db.execSQL(sql);
                    Intent intent = new Intent(KitchenActivity.this, MakeSuccessActivity.class);
                    intent.putExtra("menuname",5);
                    startActivity(intent);
                   // running = false;
                    newTimer();
                } else {
                    //경고 화면 띄우기
                    if(touchonoff == true) {pool.play(bip, 1,1,0,0,1);}
                    Intent intent = new Intent(KitchenActivity.this, WrongKitchenActivity.class);
                    startActivity(intent);
                }
            } c2.moveToFirst();
            if (c2.getInt(0) == 6) {
                c2.moveToFirst();

                menu = menuDbhelper.getReadableDatabase();
                String sql_recipe = "select cup, base, coffee, ice, ingredient1, ingredient2, ingredient3 from menu_recipe where menu_name = 6 ";

                Cursor cursor = menu.rawQuery(sql_recipe, null);

                int recipeCount = cursor.getCount();

                for (int i = 0; i < recipeCount; i++) {
                    cursor.moveToNext();
                    int cup1 = cursor.getInt(0);
                    int base = cursor.getInt(1);
                    int coffee = cursor.getInt(2);
                    int ice = cursor.getInt(3);
                    int ingredient1 = cursor.getInt(4);
                    int ingredient2 = cursor.getInt(5);
                    int ingredient3 = cursor.getInt(6);
                }

                if (cursor.getInt(0) == c2.getInt(1) && cursor.getInt(1) == c2.getInt(2) && cursor.getInt(2) == c2.getInt(3) && cursor.getInt(3) == c2.getInt(4) && cursor.getInt(4) == c2.getInt(5) && cursor.getInt(5) == c2.getInt(6) && cursor.getInt(6) == c2.getInt(7)) {
                    if(touchonoff == true) { pool.play(tada, 1,1,0,0,1);}
                    String sql = "delete from user_input where menu_name = 6";
                    db.execSQL(sql);
                    Intent intent = new Intent(KitchenActivity.this, MakeSuccessActivity.class);
                    intent.putExtra("menuname",6);
                    startActivity(intent);
                   // running = false;
                    newTimer();
                } else {
                    //경고 화면 띄우기
                    if(touchonoff == true) {pool.play(bip, 1,1,0,0,1);}
                    Intent intent = new Intent(KitchenActivity.this, WrongKitchenActivity.class);
                    startActivity(intent);
                }
            } c2.moveToFirst();
            if (c2.getInt(0) == 7) {

                menu = menuDbhelper.getReadableDatabase();
                String sql_recipe = "select cup, base, coffee, ice, ingredient1, ingredient2, ingredient3 from menu_recipe where menu_name = 7 ";

                Cursor cursor = menu.rawQuery(sql_recipe, null);

                int recipeCount = cursor.getCount();

                for (int i = 0; i < recipeCount; i++) {
                    cursor.moveToNext();
                    int cup1 = cursor.getInt(0);
                    int base = cursor.getInt(1);
                    int coffee = cursor.getInt(2);
                    int ice = cursor.getInt(3);
                    int ingredient1 = cursor.getInt(4);
                    int ingredient2 = cursor.getInt(5);
                    int ingredient3 = cursor.getInt(6);

                }

                if (cursor.getInt(0) == c2.getInt(1) && cursor.getInt(1) == c2.getInt(2) && cursor.getInt(2) == c2.getInt(3) && cursor.getInt(3) == c2.getInt(4) && cursor.getInt(4) == c2.getInt(5) && cursor.getInt(5) == c2.getInt(6) && cursor.getInt(6) == c2.getInt(7)) {
                    if(touchonoff == true) {pool.play(tada, 1,1,0,0,1);}
                    String sql = "delete from user_input where menu_name = 7";
                    db.execSQL(sql);
                    Intent intent = new Intent(KitchenActivity.this, MakeSuccessActivity.class);
                    intent.putExtra("menuname",7);
                    startActivity(intent);
                   // running = false;
                    newTimer();
                } else {
                    //경고 화면 띄우기
                    if(touchonoff == true) { pool.play(bip, 1,1,0,0,1);}
                    Intent intent = new Intent(KitchenActivity.this, WrongKitchenActivity.class);
                    startActivity(intent);
                }
            } c2.moveToFirst();
            if (c2.getInt(0) == 8) {

                menu = menuDbhelper.getReadableDatabase();
                String sql_recipe = "select cup, base, coffee, ice, ingredient1, ingredient2, ingredient3 from menu_recipe where menu_name = 8 ";

                Cursor cursor = menu.rawQuery(sql_recipe, null);

                int recipeCount = cursor.getCount();

                for (int i = 0; i < recipeCount; i++) {
                    cursor.moveToNext();
                    int cup1 = cursor.getInt(0);
                    int base = cursor.getInt(1);
                    int coffee = cursor.getInt(2);
                    int ice = cursor.getInt(3);
                    int ingredient1 = cursor.getInt(4);
                    int ingredient2 = cursor.getInt(5);
                    int ingredient3 = cursor.getInt(6);
                }

                if (cursor.getInt(0) == c2.getInt(1) && cursor.getInt(1) == c2.getInt(2) && cursor.getInt(2) == c2.getInt(3) && cursor.getInt(3) == c2.getInt(4) && cursor.getInt(4) == c2.getInt(5) && cursor.getInt(5) == c2.getInt(6) && cursor.getInt(6) == c2.getInt(7)) {
                    if(touchonoff == true) { pool.play(tada, 1,1,0,0,1);}
                    String sql = "delete from user_input where menu_name = 8";
                    db.execSQL(sql);
                    Intent intent = new Intent(KitchenActivity.this, MakeSuccessActivity.class);
                    intent.putExtra("menuname",8);
                    startActivity(intent);
                   // running = false;
                    newTimer();
                } else {
                    //경고 화면 띄우기
                    if(touchonoff == true) { pool.play(bip, 1,1,0,0,1);}
                    Intent intent = new Intent(KitchenActivity.this, WrongKitchenActivity.class);
                    startActivity(intent);
                }
            } c2.moveToFirst();
            if (c2.getInt(0) == 9) {

                menu = menuDbhelper.getReadableDatabase();
                String sql_recipe = "select cup, base, coffee, ice, ingredient1, ingredient2, ingredient3 from menu_recipe where menu_name = 9 ";

                Cursor cursor = menu.rawQuery(sql_recipe, null);

                int recipeCount = cursor.getCount();

                for (int i = 0; i < recipeCount; i++) {
                    cursor.moveToNext();
                    int cup1 = cursor.getInt(0);
                    int base = cursor.getInt(1);
                    int coffee = cursor.getInt(2);
                    int ice = cursor.getInt(3);
                    int ingredient1 = cursor.getInt(4);
                    int ingredient2 = cursor.getInt(5);
                    int ingredient3 = cursor.getInt(6);
                }

                if (cursor.getInt(0) == c2.getInt(1) && cursor.getInt(1) == c2.getInt(2) && cursor.getInt(2) == c2.getInt(3) && cursor.getInt(3) == c2.getInt(4) && cursor.getInt(4) == c2.getInt(5) && cursor.getInt(5) == c2.getInt(6) && cursor.getInt(6) == c2.getInt(7)) {
                    if(touchonoff == true) {pool.play(tada, 1,1,0,0,1);}
                    String sql = "delete from user_input where menu_name = 9";
                    db.execSQL(sql);
                    Intent intent = new Intent(KitchenActivity.this, MakeSuccessActivity.class);
                    intent.putExtra("menuname",9);
                    startActivity(intent);
               //     running = false;
                    newTimer();

                } else {
                    //경고 화면 띄우기
                    if(touchonoff == true) {pool.play(bip, 1,1,0,0,1);}
                    Intent intent = new Intent(KitchenActivity.this, WrongKitchenActivity.class);
                    startActivity(intent);
                }
            } c2.moveToFirst();
            if (c2.getInt(0) == 10) {

                menu = menuDbhelper.getReadableDatabase();
                String sql_recipe = "select cup, base, coffee, ice, ingredient1, ingredient2, ingredient3 from menu_recipe where menu_name = 10 ";

                Cursor cursor = menu.rawQuery(sql_recipe, null);

                int recipeCount = cursor.getCount();

                for (int i = 0; i < recipeCount; i++) {
                    cursor.moveToNext();
                    int cup1 = cursor.getInt(0);
                    int base = cursor.getInt(1);
                    int coffee = cursor.getInt(2);
                    int ice = cursor.getInt(3);
                    int ingredient1 = cursor.getInt(4);
                    int ingredient2 = cursor.getInt(5);
                    int ingredient3 = cursor.getInt(6);
                }

                if (cursor.getInt(0) == c2.getInt(1) && cursor.getInt(1) == c2.getInt(2) && cursor.getInt(2) == c2.getInt(3) && cursor.getInt(3) == c2.getInt(4) && cursor.getInt(4) == c2.getInt(5) && cursor.getInt(5) == c2.getInt(6) && cursor.getInt(6) == c2.getInt(7)) {
                    if(touchonoff == true) {pool.play(tada, 1,1,0,0,1);}
                    String sql = "delete from user_input where menu_name = 10";
                    db.execSQL(sql);
                    Intent intent = new Intent(KitchenActivity.this, MakeSuccessActivity.class);
                    intent.putExtra("menuname",10);
                    startActivity(intent);
                   // running = false;
                    newTimer();
                } else {
                    //경고 화면 띄우기
                    if(touchonoff == true) {pool.play(bip, 1,1,0,0,1);}
                    Intent intent = new Intent(KitchenActivity.this, WrongKitchenActivity.class);
                    startActivity(intent);
                }
            } c2.moveToFirst();
            if (c2.getInt(0) == 11) {

                menu = menuDbhelper.getReadableDatabase();
                String sql_recipe = "select cup, base, coffee, ice, ingredient1, ingredient2, ingredient3 from menu_recipe where menu_name = 11 ";

                Cursor cursor = menu.rawQuery(sql_recipe, null);

                int recipeCount = cursor.getCount();

                for (int i = 0; i < recipeCount; i++) {
                    cursor.moveToNext();
                    int cup1 = cursor.getInt(0);
                    int base = cursor.getInt(1);
                    int coffee = cursor.getInt(2);
                    int ice = cursor.getInt(3);
                    int ingredient1 = cursor.getInt(4);
                    int ingredient2 = cursor.getInt(5);
                    int ingredient3 = cursor.getInt(6);
                }

                if (cursor.getInt(0) == c2.getInt(1) && cursor.getInt(1) == c2.getInt(2) && cursor.getInt(2) == c2.getInt(3) && cursor.getInt(3) == c2.getInt(4) && cursor.getInt(4) == c2.getInt(5) && cursor.getInt(5) == c2.getInt(6) && cursor.getInt(6) == c2.getInt(7)) {
                    if(touchonoff == true) { pool.play(tada, 1,1,0,0,1);}
                    String sql = "delete from user_input where menu_name = 11";
                    db.execSQL(sql);
                    Intent intent = new Intent(KitchenActivity.this, MakeSuccessActivity.class);
                    intent.putExtra("menuname",11);
                    startActivity(intent);
                    //newTimer();
                    //running = false;
                    newTimer();
                } else {
                    //경고 화면 띄우기
                    if(touchonoff == true) { pool.play(bip, 1,1,0,0,1);}
                    Intent intent = new Intent(KitchenActivity.this, WrongKitchenActivity.class);
                    startActivity(intent);
                }
            } c2.moveToFirst();
            if (c2.getInt(0) == 12) {

                menu = menuDbhelper.getReadableDatabase();
                String sql_recipe = "select cup, base, coffee, ice, ingredient1, ingredient2, ingredient3 from menu_recipe where menu_name = 12 ";

                Cursor cursor = menu.rawQuery(sql_recipe, null);

                int recipeCount = cursor.getCount();

                for (int i = 0; i < recipeCount; i++) {
                    cursor.moveToNext();
                    int cup1 = cursor.getInt(0);
                    int base = cursor.getInt(1);
                    int coffee = cursor.getInt(2);
                    int ice = cursor.getInt(3);
                    int ingredient1 = cursor.getInt(4);
                    int ingredient2 = cursor.getInt(5);
                    int ingredient3 = cursor.getInt(6);
                }

                if (cursor.getInt(0) == c2.getInt(1) && cursor.getInt(1) == c2.getInt(2) && cursor.getInt(2) == c2.getInt(3) && cursor.getInt(3) == c2.getInt(4) && cursor.getInt(4) == c2.getInt(5) && cursor.getInt(5) == c2.getInt(6) && cursor.getInt(6) == c2.getInt(7)) {
                    if(touchonoff == true) {pool.play(tada, 1,1,0,0,1);}
                    String sql = "delete from user_input where menu_name = 12";
                    db.execSQL(sql);
                    Intent intent = new Intent(KitchenActivity.this, MakeSuccessActivity.class);
                    intent.putExtra("menuname",12);
                    startActivity(intent);
                    //running = false;
                    newTimer();
                } else {
                    //경고 화면 띄우기
                    if(touchonoff == true) { pool.play(bip, 1,1,0,0,1);}
                    Intent intent = new Intent(KitchenActivity.this, WrongKitchenActivity.class);
                    startActivity(intent);
                }
            } c2.moveToFirst();
            if (c2.getInt(0) == 13) {

                menu = menuDbhelper.getReadableDatabase();
                String sql_recipe = "select cup, base, coffee, ice, ingredient1, ingredient2, ingredient3 from menu_recipe where menu_name = 13 ";

                Cursor cursor = menu.rawQuery(sql_recipe, null);

                int recipeCount = cursor.getCount();

                for (int i = 0; i < recipeCount; i++) {
                    cursor.moveToNext();
                    int cup1 = cursor.getInt(0);
                    int base = cursor.getInt(1);
                    int coffee = cursor.getInt(2);
                    int ice = cursor.getInt(3);
                    int ingredient1 = cursor.getInt(4);
                    int ingredient2 = cursor.getInt(5);
                    int ingredient3 = cursor.getInt(6);
                }
                if (cursor.getInt(0) == c2.getInt(1) && cursor.getInt(1) == c2.getInt(2) && cursor.getInt(2) == c2.getInt(3) && cursor.getInt(3) == c2.getInt(4) && cursor.getInt(4) == c2.getInt(5) && cursor.getInt(5) == c2.getInt(6) && cursor.getInt(6) == c2.getInt(7)) {
                    if(touchonoff == true) {pool.play(tada, 1,1,0,0,1);}
                    String sql = "delete from user_input where menu_name = 13";
                    db.execSQL(sql);
                    Intent intent = new Intent(KitchenActivity.this, MakeSuccessActivity.class);
                    intent.putExtra("menuname",13);
                    startActivity(intent);
                   // running = false;
                    newTimer();
                } else {
                    //경고 화면 띄우기
                    if(touchonoff == true) {   pool.play(bip, 1,1,0,0,1);}
                    Intent intent = new Intent(KitchenActivity.this, WrongKitchenActivity.class);
                    startActivity(intent);
                }
            } c2.moveToFirst();
            if (c2.getInt(0) == 14) {

                menu = menuDbhelper.getReadableDatabase();
                String sql_recipe = "select cup, base, coffee, ice, ingredient1, ingredient2, ingredient3 from menu_recipe where menu_name = 14 ";

                Cursor cursor = menu.rawQuery(sql_recipe, null);

                int recipeCount = cursor.getCount();

                for (int i = 0; i < recipeCount; i++) {
                    cursor.moveToNext();
                    int cup1 = cursor.getInt(0);
                    int base = cursor.getInt(1);
                    int coffee = cursor.getInt(2);
                    int ice = cursor.getInt(3);
                    int ingredient1 = cursor.getInt(4);
                    int ingredient2 = cursor.getInt(5);
                    int ingredient3 = cursor.getInt(6);
                }
                if (cursor.getInt(0) == c2.getInt(1) && cursor.getInt(1) == c2.getInt(2) && cursor.getInt(2) == c2.getInt(3) && cursor.getInt(3) == c2.getInt(4) && cursor.getInt(4) == c2.getInt(5) && cursor.getInt(5) == c2.getInt(6) && cursor.getInt(6) == c2.getInt(7)) {
                    if(touchonoff == true) {pool.play(tada, 1,1,0,0,1);}
                    String sql = "delete from user_input where menu_name = 14";
                    db.execSQL(sql);
                    Intent intent = new Intent(KitchenActivity.this, MakeSuccessActivity.class);
                    intent.putExtra("menuname",14);
                    startActivity(intent);
                    //running = false;
                    newTimer();
                } else {
                    //경고 화면 띄우기
                    if(touchonoff == true) {pool.play(bip, 1,1,0,0,1);}
                    Intent intent = new Intent(KitchenActivity.this, WrongKitchenActivity.class);
                    startActivity(intent);
                }
            } c2.moveToFirst();
            if (c2.getInt(0) == 15) {

                menu = menuDbhelper.getReadableDatabase();
                String sql_recipe = "select cup, base, coffee, ice, ingredient1, ingredient2, ingredient3 from menu_recipe where menu_name = 15 ";

                Cursor cursor = menu.rawQuery(sql_recipe, null);

                int recipeCount = cursor.getCount();

                for (int i = 0; i < recipeCount; i++) {
                    cursor.moveToNext();
                    int cup1 = cursor.getInt(0);
                    int base = cursor.getInt(1);
                    int coffee = cursor.getInt(2);
                    int ice = cursor.getInt(3);
                    int ingredient1 = cursor.getInt(4);
                    int ingredient2 = cursor.getInt(5);
                    int ingredient3 = cursor.getInt(6);

                }

                if (cursor.getInt(0) == c2.getInt(1) && cursor.getInt(1) == c2.getInt(2) && cursor.getInt(2) == c2.getInt(3) && cursor.getInt(3) == c2.getInt(4) && cursor.getInt(4) == c2.getInt(5) && cursor.getInt(5) == c2.getInt(6) && cursor.getInt(6) == c2.getInt(7)) {
                    if(touchonoff == true) { pool.play(tada, 1,1,0,0,1);}
                    String sql = "delete from user_input where menu_name = 15";
                    db.execSQL(sql);
                    Intent intent = new Intent(KitchenActivity.this, MakeSuccessActivity.class);
                    intent.putExtra("menuname",15);
                    startActivity(intent);
                   // running = false;
                    newTimer();
                } else {
                    //경고 화면 띄우기
                    if(touchonoff == true) { pool.play(bip, 1,1,0,0,1);}
                    Intent intent = new Intent(KitchenActivity.this, WrongKitchenActivity.class);
                    startActivity(intent);
                }
            } c2.moveToFirst();
            if (c2.getInt(0) == 16) {

                menu = menuDbhelper.getReadableDatabase();
                String sql_recipe = "select cup, base, coffee, ice, ingredient1, ingredient2, ingredient3 from menu_recipe where menu_name = 16 ";

                Cursor cursor = menu.rawQuery(sql_recipe, null);

                int recipeCount = cursor.getCount();

                for (int i = 0; i < recipeCount; i++) {
                    cursor.moveToNext();
                    int cup1 = cursor.getInt(0);
                    int base = cursor.getInt(1);
                    int coffee = cursor.getInt(2);
                    int ice = cursor.getInt(3);
                    int ingredient1 = cursor.getInt(4);
                    int ingredient2 = cursor.getInt(5);
                    int ingredient3 = cursor.getInt(6);
                }
                if (cursor.getInt(0) == c2.getInt(1) && cursor.getInt(1) == c2.getInt(2) && cursor.getInt(2) == c2.getInt(3) && cursor.getInt(3) == c2.getInt(4) && cursor.getInt(4) == c2.getInt(5) && cursor.getInt(5) == c2.getInt(6) && cursor.getInt(6) == c2.getInt(7)) {
                    if(touchonoff == true) {  pool.play(tada, 1,1,0,0,1);}
                    String sql = "delete from user_input where menu_name = 16";
                    db.execSQL(sql);
                    Intent intent = new Intent(KitchenActivity.this, MakeSuccessActivity.class);
                    intent.putExtra("menuname",16);
                    startActivity(intent);
                  //  running = false;
                    newTimer();
                } else {
                    //경고 화면 띄우기
                    if(touchonoff == true) { pool.play(bip, 1,1,0,0,1);}
                    Intent intent = new Intent(KitchenActivity.this, WrongKitchenActivity.class);
                    startActivity(intent);
                }
            } c2.moveToFirst();
            if (c2.getInt(0) == 17) {

                menu = menuDbhelper.getReadableDatabase();
                String sql_recipe = "select cup, base, coffee, ice, ingredient1, ingredient2, ingredient3 from menu_recipe where menu_name = 17 ";

                Cursor cursor = menu.rawQuery(sql_recipe, null);

                int recipeCount = cursor.getCount();

                for (int i = 0; i < recipeCount; i++) {
                    cursor.moveToNext();
                    int cup1 = cursor.getInt(0);
                    int base = cursor.getInt(1);
                    int coffee = cursor.getInt(2);
                    int ice = cursor.getInt(3);
                    int ingredient1 = cursor.getInt(4);
                    int ingredient2 = cursor.getInt(5);
                    int ingredient3 = cursor.getInt(6);
                }
                if (cursor.getInt(0) == c2.getInt(1) && cursor.getInt(1) == c2.getInt(2) && cursor.getInt(2) == c2.getInt(3) && cursor.getInt(3) == c2.getInt(4) && cursor.getInt(4) == c2.getInt(5) && cursor.getInt(5) == c2.getInt(6) && cursor.getInt(6) == c2.getInt(7)) {
                    if(touchonoff == true) { pool.play(tada, 1,1,0,0,1);}
                    String sql = "delete from user_input where menu_name = 17";
                    db.execSQL(sql);
                    Intent intent = new Intent(KitchenActivity.this, MakeSuccessActivity.class);
                    intent.putExtra("menuname",17);
                    startActivity(intent);
                 //   running = false;
                    newTimer();
                } else {
                    //경고 화면 띄우기
                    if(touchonoff == true) {pool.play(bip, 1,1,0,0,1);}
                    Intent intent = new Intent(KitchenActivity.this, WrongKitchenActivity.class);
                    startActivity(intent);
                }
            } c2.moveToFirst();
            if (c2.getInt(0) == 18) {

                menu = menuDbhelper.getReadableDatabase();
                String sql_recipe = "select cup, base, coffee, ice, ingredient1, ingredient2, ingredient3 from menu_recipe where menu_name = 18 ";

                Cursor cursor = menu.rawQuery(sql_recipe, null);

                int recipeCount = cursor.getCount();

                for (int i = 0; i < recipeCount; i++) {
                    cursor.moveToNext();
                    int cup1 = cursor.getInt(0);
                    int base = cursor.getInt(1);
                    int coffee = cursor.getInt(2);
                    int ice = cursor.getInt(3);
                    int ingredient1 = cursor.getInt(4);
                    int ingredient2 = cursor.getInt(5);
                    int ingredient3 = cursor.getInt(6);
                }
                if (cursor.getInt(0) == c2.getInt(1) && cursor.getInt(1) == c2.getInt(2) && cursor.getInt(2) == c2.getInt(3) && cursor.getInt(3) == c2.getInt(4) && cursor.getInt(4) == c2.getInt(5) && cursor.getInt(5) == c2.getInt(6) && cursor.getInt(6) == c2.getInt(7)) {
                    if(touchonoff == true) { pool.play(tada, 1,1,0,0,1);}
                    String sql = "delete from user_input where menu_name = 18";
                    db.execSQL(sql);
                    Intent intent = new Intent(KitchenActivity.this, MakeSuccessActivity.class);
                    intent.putExtra("menuname",18);
                    startActivity(intent);
                 //   running = false;
                    newTimer();
                } else {
                    //경고 화면 띄우기
                    if(touchonoff == true) {pool.play(bip, 1,1,0,0,1);}
                    Intent intent = new Intent(KitchenActivity.this, WrongKitchenActivity.class);
                    startActivity(intent);
                }
            }

        }

    }

    public void reset(){
        touchonoff = sharedPreference.getBoolean(KitchenActivity.this,"touch");
    }

    public void baseicon(){
        icon0.setImageResource(R.drawable.icon0);
        icon1.setImageResource(R.drawable.icon1);
        icon2.setImageResource(R.drawable.icon2);

        icon0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources res = getResources();
                final Drawable drawable = res.getDrawable(R.drawable.ice_on);
                selectIng3.setVisibility(View.VISIBLE);
                selectIng3.setImageDrawable(drawable);
                selectIng3.setTag("icon0");

            }
        });
        icon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources res = getResources();
                final Drawable drawable = res.getDrawable(R.drawable.coffee_on);

                if (checkimg == true) {
                    selectIng.setImageDrawable(drawable);
                    checkimg = false;
                    selectIng.setTag("icon1");
                } else if (checkimg == false) {
                    selectIng2.setVisibility(View.VISIBLE);
                    selectIng2.setImageDrawable(drawable);
                    checkimg = true;
                    selectIng2.setTag("icon1");
                }

            }
        });
        icon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources res = getResources();
                final Drawable drawable = res.getDrawable(R.drawable.vanil_on);

                if (checkimg == true) {
                    selectIng.setImageDrawable(drawable);
                    checkimg = false;
                    selectIng.setTag("icon2");
                } else if (checkimg == false) {
                    selectIng2.setVisibility(View.VISIBLE);
                    selectIng2.setImageDrawable(drawable);
                    checkimg = true;
                    selectIng2.setTag("icon2");
                }

            }
        });
    }

    public void onBackPressed(){
        Intent intent = new Intent(KitchenActivity.this,EndGameActivity.class);
        startActivity(intent);
    }
}







