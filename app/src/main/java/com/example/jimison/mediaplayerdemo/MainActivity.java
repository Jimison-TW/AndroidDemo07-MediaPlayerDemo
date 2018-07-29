package com.example.jimison.mediaplayerdemo;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

    ImageButton mediaPause, mediaGo, mediaStop;
    ToggleButton mediaRepeat;
    EditText edtGo;
    MediaPlayer mediaPlayer = null;
    boolean isBootReady = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaGo = findViewById(R.id.mediaGo);
        mediaPause = findViewById(R.id.mediaPause);
        mediaRepeat = findViewById(R.id.mediaRepeat);
        mediaStop = findViewById(R.id.mediaStop);
        edtGo = findViewById(R.id.edtGo);
        mediaPause.setOnClickListener(pauseListener);
        mediaStop.setOnClickListener(stopListener);
        mediaGo.setOnClickListener(goListener);
        mediaRepeat.setOnClickListener(repeatListen);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer = new MediaPlayer();
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.song);
        try {
            mediaPlayer.setDataSource(this, uri);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "播放檔錯誤", Toast.LENGTH_SHORT).show();
        }
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnCompletionListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mediaPause.setImageResource(android.R.drawable.ic_media_play);  //android.R是android的預設資源路徑，這邊的資料是你看不到的，為系統保留的資源
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mediaPlayer.release();
        mediaPlayer = null;
        Toast.makeText(getApplicationContext(), "播放錯誤", Toast.LENGTH_SHORT).show();
        return true;
    }

    private Button.OnClickListener pauseListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            if (isBootReady) {
                mediaPlayer.prepareAsync();
                isBootReady = false;
            } else {
                mediaPlayer.start();
            }
        }
        }
    };

    private Button.OnClickListener stopListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
        mediaPlayer.stop();
        isBootReady = true;
        }
    };

    private Button.OnClickListener goListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
        if (edtGo.getText().toString().equals("")) return;
        int second = Integer.parseInt(edtGo.getText().toString());
        mediaPlayer.seekTo(second * 1000);
        }
    };

    private Button.OnClickListener repeatListen = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
        if (((ToggleButton) v).isChecked()) {
            mediaPlayer.setLooping(true);
        } else {
            mediaPlayer.setLooping(false);
        }
        }
    };
}
