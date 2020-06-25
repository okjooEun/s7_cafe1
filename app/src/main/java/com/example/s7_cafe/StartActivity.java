package com.example.s7_cafe;

import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.s7_cafe.UserInputContract.UserTable.SQL_CREATE_USER_TABLE;
import static com.example.s7_cafe.UserInputContract.UserTable.SQL_DELETE_TABLE;

public class StartActivity extends AppCompatActivity {


    private static final String TAG = "AnnoymousAuth";
    private FirebaseAuth mAuth;
    ImageView back, setting;
    Button startbtn, continuebtn;
    ArrayList<String> cus1;

    SQLiteDatabase db,db2;

    SharedPreferenceUtill sharedPreferenceUtill = new SharedPreferenceUtill();
    final MenuDbhelper userDbhelper = new MenuDbhelper(this);

    int count;
    Boolean sound, touch, savename;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

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

        db = userDbhelper.getReadableDatabase();
        db2 = userDbhelper.getWritableDatabase();
        mAuth = FirebaseAuth.getInstance();

        back = (ImageView) findViewById(R.id.back);
        setting = (ImageView) findViewById(R.id.setting);
        startbtn = (Button) findViewById(R.id.startbtn);
        continuebtn = (Button) findViewById(R.id.continuebtn);

        count = sharedPreferenceUtill.getInt(StartActivity.this, "count");
        sound = sharedPreferenceUtill.getBoolean(StartActivity.this, "sound");
        touch = sharedPreferenceUtill.getBoolean(StartActivity.this, "touch");
        savename = sharedPreferenceUtill.getBoolean(StartActivity.this, "savename");



        mAuth = FirebaseAuth.getInstance();

        Glide.with(this).load(R.raw.start_page).into(back);

       startService(new Intent(getApplicationContext(), MusicService.class));
        stopService(new Intent(getApplicationContext(), TotalMusic.class));

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StartActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.execSQL(SQL_DELETE_TABLE);
                db2.execSQL(SQL_CREATE_USER_TABLE);
                signOut();
                signInAnonymously();

                Resources res1 = getResources();
                Drawable drawable = res1.getDrawable(R.drawable.start_btn_off);
                startbtn.setBackground(drawable);
                sharedPreferenceUtill.setInt(StartActivity.this, "count", 0);
                sharedPreferenceUtill.setBoolean(StartActivity.this, "savename", true);
                if (sound == false) {
                    sharedPreferenceUtill.setBoolean(StartActivity.this, "sound", true);
                }
                if (touch == false) {
                    sharedPreferenceUtill.setBoolean(StartActivity.this, "touch", true);
                }


                Intent intent = new Intent(StartActivity.this, PrologueActivity.class);
                startActivity(intent);
                finish();
            }
        });
        if (count <= 1) {
            Resources res1 = getResources();
            Drawable drawable = res1.getDrawable(R.drawable.continue_btn_off);
            continuebtn.setBackground(drawable);
            continuebtn.setClickable(false);

            if (sound == false) {
                sharedPreferenceUtill.setBoolean(StartActivity.this, "sound", true);
            }
            if (touch == false) {
                sharedPreferenceUtill.setBoolean(StartActivity.this, "touch", true);
            }
        } else {
            continuebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.execSQL(SQL_DELETE_TABLE);
                    db2.execSQL(SQL_CREATE_USER_TABLE);
                    Resources res1 = getResources();
                    Drawable drawable = res1.getDrawable(R.drawable.continue_btn_off);

                    continuebtn.setBackground(drawable);


                    sharedPreferenceUtill.setInt(StartActivity.this, "count", count - 1);
                    sharedPreferenceUtill.setBoolean(StartActivity.this, "savename", false);
                    sharedPreferenceUtill.setBoolean(StartActivity.this, "sound", sound);
                    sharedPreferenceUtill.setBoolean(StartActivity.this, "touch", touch);
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
            });
        }
    }

    private void signInAnonymously() {
        mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "signInAnonymously : success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);

                } else {
                    Log.w(TAG, "signInAnonymously : failure", task.getException());
                    Toast.makeText(StartActivity.this, "인터넷 연결 확인해주세요.\n 이후 게임 진행에 큰 문제 발생!", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }

            }
        });
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }


    private void updateUI(FirebaseUser user) {
        boolean isSignedIn = (user != null);

        if (isSignedIn) {

            String uid = user.getUid();
            UserInfo userinfo = new UserInfo() {
                @NonNull
                @Override
                public String getUid() {
                    return null;
                }

                @NonNull
                @Override
                public String getProviderId() {
                    return null;
                }

                @Nullable
                @Override
                public String getDisplayName() {
                    return null;
                }

                @Nullable
                @Override
                public Uri getPhotoUrl() {
                    return null;
                }

                @Nullable
                @Override
                public String getEmail() {
                    return null;
                }

                @Nullable
                @Override
                public String getPhoneNumber() {
                    return null;
                }

                @Override
                public boolean isEmailVerified() {
                    return false;
                }
            };

        }
        else
        {
            Log.d("AnonymousAuth", "user null");
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(StartActivity.this,EndGameActivity.class);
        startActivity(intent);

    }
}