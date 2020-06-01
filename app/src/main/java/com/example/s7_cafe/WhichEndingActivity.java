package com.example.s7_cafe;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WhichEndingActivity extends AppCompatActivity {

    LinearLayout end, linBtn;
    ImageView setting, guest;
    Button btnSelect1, btnSelect2;
    TextView txtTalk, txtTalk2, guestname, datetext;
    FrameLayout frame;
    Boolean onoff;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whichending);
        stopService(new Intent(getApplicationContext(), ChristmasMusicService.class));
        startService(new Intent(getApplicationContext(), GameMusicService.class));

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



        end = (LinearLayout) findViewById(R.id.end);
        setting = (ImageView) findViewById(R.id.setting);
        guest = (ImageView) findViewById(R.id.guest);

        linBtn = (LinearLayout) findViewById(R.id.linBtn);
        frame = (FrameLayout) findViewById(R.id.frame);

        txtTalk = (TextView) findViewById(R.id.txtTalk);
        txtTalk2 = (TextView) findViewById(R.id.txtTalk2);
        guestname = (TextView) findViewById(R.id.guestname);
        datetext = (TextView) findViewById(R.id.datetext);

        btnSelect1 = (Button) findViewById(R.id.btnSelect1);
        btnSelect2 = (Button) findViewById(R.id.btnSelect2);

        guest = (ImageView) findViewById(R.id.guest);

        Glide.with(this).load(R.raw.boss_normal).into(guest);

        guestname.setText("사장님");
        txtTalk.setText("오늘 마감도 수고했어요. 계속 함께 일할래요?");
        btnSelect1.setText("네! 계속 여기서 일하고 싶습니다. 앞으로도 잘 부탁드립니다!");
        btnSelect2.setText("죄송합니다... 그래도 여러모로 많은 것을 배웠습니다. 그동안 감사합니다.");

        SharedPreferenceUtill sharedPreference = new SharedPreferenceUtill();
        onoff = sharedPreference.getBoolean(this,"sound");

        if(onoff == true){
            startService(new Intent(getApplicationContext(),GameMusicService.class));
        }
        if(onoff == false){
            stopService(new Intent(getApplicationContext(),GameMusicService.class));
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        final DatabaseReference uidRef = databaseReference.child(uid);
        uidRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int total = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    int value = ds.getValue(Integer.class);
                    total += value;
                }
                uidRef.child("total").setValue(total);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtTalk.setVisibility(View.GONE);
                linBtn.setVisibility(View.VISIBLE);
                guestname.setVisibility(View.INVISIBLE);
                frame.setClickable(false);
                frame.setBackground(null);


                btnSelect1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = user.getUid();
                        final DatabaseReference totalRef = databaseReference.child(uid).child("total");
                        totalRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String s = "";
                                s = dataSnapshot.getValue().toString();

                                final int sum = Integer.parseInt(s);

                                if (sum < 1){
                                    Intent intent = new Intent(WhichEndingActivity.this, Ending1Activity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(WhichEndingActivity.this, Ending2Activity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });


                    }
                });

                btnSelect2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = user.getUid();
                        final DatabaseReference totalRef = databaseReference.child(uid).child("total");
                        totalRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String s = "";
                                s = dataSnapshot.getValue().toString();

                                final int sum = Integer.parseInt(s);

                                if (sum < 1){
                                    Intent intent = new Intent(WhichEndingActivity.this, Ending3Activity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(WhichEndingActivity.this, FinishActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }
                });
            }
        });



        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WhichEndingActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

    }

    public void onBackPressed(){
        Intent intent = new Intent(WhichEndingActivity.this,EndGameActivity.class);
        startActivity(intent);
    }

}