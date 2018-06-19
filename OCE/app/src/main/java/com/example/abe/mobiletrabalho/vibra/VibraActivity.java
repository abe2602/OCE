package com.example.abe.mobiletrabalho.vibra;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TabHost;

import com.example.abe.mobiletrabalho.R;


/*
* Falta:
* - Vibração com a música
*   na vibração, colocar bem pouco, para relaxar!
* */
public class VibraActivity extends AppCompatActivity {
    Switch on1, on2, on3;
    MediaPlayer music1, music2, music3;
    Vibrator vibrate;
    boolean check1, check2, check3;
    boolean auxCheck1, auxCheck2, auxCheck3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibra);

        vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        auxCheck1 = true;
        auxCheck2 = true;
        auxCheck3 = true;

        on1 = findViewById(R.id.switch_music_1);
        on2 = findViewById(R.id.switch_music_2);
        on3 = findViewById(R.id.switch_music_3);

        on1.setChecked(false);
        on2.setChecked(false);
        on3.setChecked(false);

        on1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    check1 = isChecked;
            }
        });

        on2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check2 = isChecked;
            }
        });

        on3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check3 = isChecked;
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (check1) {
            music1.stop();
            music1.release();
        }

        if (check2) {
            music2.stop();
            music2.release();
        }

        if (check3) {
            music3.stop();
            music3.release();

        }
    }
    public void startMusicClick(View view) {
        if(check1 && auxCheck1){
            music1 = (MediaPlayer.create(getApplicationContext(),R.raw.music_one));
            music1.start();
            auxCheck1 = false;
       }
        if(check2 && auxCheck2){
            music2 = (MediaPlayer.create(getApplicationContext(),R.raw.music_two));
            music2.start();
            auxCheck2 = false;
        }

        if(check3 && auxCheck3){
            music3 = (MediaPlayer.create(getApplicationContext(),R.raw.music_three));
            music3.start();
            auxCheck3 = false;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!auxCheck1 || !auxCheck2 || !auxCheck3){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrate.vibrate(VibrationEffect.createOneShot(1500,VibrationEffect.DEFAULT_AMPLITUDE));
                    }else{
                        //deprecated in API 26
                        vibrate.vibrate(1500);
                    }
                }
            }
        }).start();
    }

    public void stopMusicClick(View view) {
        if(!check1 && !auxCheck1){
            music1.stop();
            auxCheck1 = true;
        }
        if(!check2 && !auxCheck2){
            music2.stop();
            auxCheck2 = true;
        }

        if(!check3 && !auxCheck3){
            music3.stop();
            auxCheck3 = true;
        }
    }
}
