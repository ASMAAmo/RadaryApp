package com.slashapps.radary.Activities;

import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.slashapps.radary.R;

public class AlertDialogActivity extends BaseActivity {
    public static MediaPlayer mediaPlayer;
    TextView messageTv;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        button=findViewById(R.id.dismiss_bttn);
        messageTv=findViewById(R.id.message);
        messageTv.setText(getIntent().getStringExtra("message_res"));
        mediaPlayer = MediaPlayer.create(this, R.raw.dangeralarm);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100,100);
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                AlertDialogActivity.this.finish();
            }
        });




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
    }
}
