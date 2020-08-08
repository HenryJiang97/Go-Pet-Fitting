package com.example.gopetfitting;

import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.IOException;

public class MultiaudioActivity extends AppCompatActivity {
    private MediaPlayer mp;
    private ImageView imageView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sing_animation);
    }
    public void sweetSong(View view) throws IOException {
       // try {
        imageView = findViewById(R.id.dog_guitar);
        /*from raw folder*/
        Glide.with(this)
                .load(R.raw.guitar_sweet)
                .into(imageView);
        if(mp == null) {
            System.out.println(" sweetsweet");
            mp = MediaPlayer.create(this, R.raw.sweet);
            System.out.println("create");
            mp.start();
        } else if (mp.isPlaying()) {
            System.out.println("mediaPlayer is playing");
            mp.stop();
            mp = MediaPlayer.create(this, R.raw.sweet);
            System.out.println("run");
            mp.start();
            System.out.println("mediaPlayer after create");
        }
       // } catch (Exception e) {
//            e.printStackTrace();
//        }

        // stop playing music
//        if (mp != null && mp.isPlaying()) {
//            mp.stop();
//        }
    }

    public void bluesSong(View view) throws IOException {
        // try {
        imageView = findViewById(R.id.dog_guitar);
        /*from raw folder*/
        Glide.with(this)
                .load(R.raw.guitar_blur)
                .into(imageView);
        if(mp == null) {
            System.out.println(" blues");
            mp = MediaPlayer.create(this, R.raw.blues);
            System.out.println("create");
            mp.start();
        } else if (mp.isPlaying()) {
            System.out.println("mediaPlayer is playing");
            mp.stop();
            mp = MediaPlayer.create(this, R.raw.blues);
            System.out.println("run");
            mp.start();
            System.out.println("mediaPlayer after create");
        }
        // } catch (Exception e) {
//            e.printStackTrace();
//        }

        // stop playing music
//        if (mp != null && mp.isPlaying()) {
//            mp.stop();
//        }
    }

}

