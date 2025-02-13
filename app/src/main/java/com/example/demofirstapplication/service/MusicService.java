package com.example.demofirstapplication.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import com.example.demofirstapplication.R;
import com.example.demofirstapplication.activity.MainActivity;

public class MusicService extends Service {
    MediaPlayer mediaPlayer;
    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.y2meta_koi_no_uta_320_kbps);
        mediaPlayer.setLooping(true);
        Toast.makeText(this, "Music Service is created", Toast.LENGTH_SHORT).show();
    }

    // Start the service
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
        else{
            mediaPlayer.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }
    
    // Stop the service
    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
}



























