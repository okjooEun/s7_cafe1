package com.example.s7_cafe;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class TotalMusic extends Service {
    SharedPreferenceUtill sharedPreference = new SharedPreferenceUtill();
    Boolean onoff;
    int count;
public void onCreate() {

    onoff = sharedPreference.getBoolean(TotalMusic.this,"sound");
    reset();

    count = sharedPreference.getInt(TotalMusic.this, "count");

    switch (count){
        case 1:
        case 2:
            if(onoff == true) {
                startService(new Intent(getApplicationContext(), GameMusicService.class));
            }
            if (onoff == false)
            { stopService(new Intent(getApplicationContext(), GameMusicService.class));}
            break;

        case 3:
        case 4:
        case 5:
            if(onoff == true) {
                stopService(new Intent(getApplicationContext(), GameMusicService.class));
                startService(new Intent(getApplicationContext(), ChristmasMusicService.class));
            }
            if (onoff == false)
            { stopService(new Intent(getApplicationContext(), GameMusicService.class));
                stopService(new Intent(getApplicationContext(), ChristmasMusicService.class));}
            break;
        case 6:
        case 7:
        case 8:
            if(onoff == true) {
                stopService(new Intent(getApplicationContext(), ChristmasMusicService.class));
                startService(new Intent(getApplicationContext(), GameMusicService.class));
            }
            if (onoff == false)
            { stopService(new Intent(getApplicationContext(), GameMusicService.class));
                stopService(new Intent(getApplicationContext(), ChristmasMusicService.class));}

    }

}

public void reset(){
    //세팅값 1초마다 가져오기
    TimerTask myTask = new TimerTask() {
        @Override
        public void run() {
            onoff = sharedPreference.getBoolean(TotalMusic.this,"sound");
        }
    };
    Timer timer = new Timer();
    timer.schedule(myTask, 1000,1000);
}
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
