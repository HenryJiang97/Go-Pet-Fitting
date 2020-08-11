//package com.example.gopetfitting;
//
//import android.media.MediaPlayer;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class SingAnimationActivity extends AppCompatActivity {
//    Button blues;
//    Button sweet;
//    MediaPlayer mediaPlayer1;
//    MediaPlayer mediaPlayer2;
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.sing_animation);
//        blues = (Button)findViewById(R.id.blues);
//        sweet = (Button)findViewById(R.id.sweet);
//        mediaPlayer1=MediaPlayer.create(this, R.raw.sweet);
//        mediaPlayer2 = MediaPlayer.create(this, R.raw.blues);
//
//        sweet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Thread() {
//                    @Override
//                    public void run() {
//                        //Toast.makeText(SingAnimationActivity.this,"blues starts",Toast.LENGTH_SHORT).show();
//                        System.out.println("run sweet");
//                        mediaPlayer1.stop();
//                        System.out.println("sweet stop");
//                        //mediaPlayer2.prepareAsync();
//                        mediaPlayer2.start();
//                    }
//                }.start();
//            }
//        });
//
//        blues.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Thread() {
//                    @Override
//                    public void run() {
//                        //Toast.makeText(SingAnimationActivity.this,"blues starts",Toast.LENGTH_SHORT).show();
//                        System.out.println("run blues");
//                        mediaPlayer2.stop();
//                        mediaPlayer1.prepareAsync();
//                        System.out.println("blues stop");
//                        mediaPlayer1.start();
//                    }
//                }.start();
//            }
//        });
//    }
//}
