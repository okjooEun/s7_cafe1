package com.example.s7_cafe;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class EndingMusic2Service extends Service {
    MediaPlayer mediaPlayer;

    public EndingMusic2Service() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mediaPlayer = MediaPlayer.create(this,R.raw.quit);
        mediaPlayer.setLooping(true); // 반복재생
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        mediaPlayer.stop();
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
