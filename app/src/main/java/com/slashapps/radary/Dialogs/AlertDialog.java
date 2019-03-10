package com.slashapps.radary.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.slashapps.radary.R;

public class AlertDialog extends Dialog {
    public static MediaPlayer mediaPlayer;
    TextView messageTv;
    Button button;

    public AlertDialog(@NonNull Context context,String mesage) {
        super(context);
        setContentView(R.layout.dialog_layout);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        button=findViewById(R.id.dismiss_bttn);
        messageTv=findViewById(R.id.message);
        messageTv.setText(mesage);
        mediaPlayer = MediaPlayer.create(context, R.raw.dangeralarm);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100,100);
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }

        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                dismiss();
            }
        });
    }
}
