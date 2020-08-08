package com.example.gopetfitting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.Timer;
import java.util.TimerTask;

public class PetHomeActivity extends AppCompatActivity {
    private ImageView iv;
    private ImageView iv2;
    private Timer mTimer1;
    private TimerTask mTt1;
    private Handler mTimerHandler = new Handler();
    private ProgressBar progressBar;
    private ProgressBar progressBar2;
    private ProgressBar progressBar3;
    private ProgressBar progressBar4;
    private TextView score;
    private Button myPage;
    int duration = Toast.LENGTH_SHORT;
    CharSequence text;
    Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_home);
        myPage = (Button) findViewById(R.id.button9);
        iv = (ImageView)findViewById(R.id.imageView8);
        iv2 = (ImageView)findViewById(R.id.imageView12);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar2 = (ProgressBar)findViewById(R.id.progressBar2);
        progressBar3 = (ProgressBar)findViewById(R.id.progressBar3);
        progressBar4 = (ProgressBar)findViewById(R.id.progressBar4);
        score = (TextView)findViewById(R.id.textView47);
    }
    private void stopTimer(){
        if(mTimer1 != null){
            mTimer1.cancel();
            mTimer1.purge();
        }
    }
    public void walk(View view) {
        Intent intent=new Intent();
        intent.setClass(PetHomeActivity.this, DogWalkActivity.class);
        startActivity(intent);
        Context context = getApplicationContext();
        text = "health +2";
        toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, -10, 10);
        toast.show();
        progressBar2.setProgress(progressBar2.getProgress()+2);
        //score.setText(String.valueOf(Integer.parseInt(score.getText().toString())+2*0.25));
    }
    public void touch(View view) {
        Intent intent=new Intent();
        intent.setClass(PetHomeActivity.this, DogTouchActivity.class);
        startActivity(intent);
        Context context = getApplicationContext();
        text = "happiness +3";
        toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, -10, 10);
        toast.show();
        progressBar4.setProgress(progressBar4.getProgress()+3);
        //score.setText(String.valueOf(Integer.parseInt(score.getText().toString())+3*0.25));
    }

    public void show(View view) {
        Intent intent=new Intent();
        intent.setClass(PetHomeActivity.this, MultiaudioActivity.class);
        startActivity(intent);
    }

    public void food(View view) {
        mTimer1 = new Timer();
        mTt1 = new TimerTask() {
            public void run() {
                mTimerHandler.post(new Runnable() {
                    public void run() {
                        iv.setImageResource(R.mipmap.dog_touch_round);
                    }
                });
            }
        };
        mTimer1.schedule(mTt1, 1, 5000);
        System.out.println("time ends!");
        Glide.with(this)
                .load(R.raw.dog_eat_eat)
                .into(iv);
        Context context = getApplicationContext();
        text = "hungry -1";
        toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, -10, 10);
        toast.show();
        progressBar.setProgress(progressBar.getProgress()-1);
        //score.setText(String.valueOf(Integer.parseInt(score.getText().toString())+1*0.25));
    }

    public void drink(View view) {
        mTimer1 = new Timer();
        mTt1 = new TimerTask() {
            public void run() {
                mTimerHandler.post(new Runnable() {
                    public void run() {
                        //water();
                        iv.setImageResource(R.mipmap.dog_touch_round);
                    }
                });
            }
        };
        mTimer1.schedule(mTt1, 1, 8000);
        Glide.with(this)
                .load(R.raw.dog_drink_drink)
                .into(iv);
        text = "health +3";
        Context context = getApplicationContext();
        toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, -10, 10);
        toast.show();
        progressBar2.setProgress(progressBar2.getProgress()+3);
        //score.setText(String.valueOf(Integer.parseInt(score.getText().toString())+3*0.25));
    }

    public void bath(View view) {
        mTimer1 = new Timer();
        mTt1 = new TimerTask() {
            public void run() {
                mTimerHandler.post(new Runnable() {
                    public void run() {
                        //water();
                        iv.setImageResource(R.mipmap.dog_touch_round);
                    }
                });
            }
        };
        mTimer1.schedule(mTt1, 1, 8000);
        Glide.with(this)
                .load(R.raw.dog_bath)
                .into(iv);
        text = "cleanness +3";
        Context context = getApplicationContext();
        toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, -10, 10);
        toast.show();
        progressBar3.setProgress(progressBar3.getProgress()+3);
        //score.setText(String.valueOf(Integer.parseInt(score.getText().toString())+3*0.25));
    }

    public void vac(View view) {
        mTimer1 = new Timer();
        mTt1 = new TimerTask() {
            public void run() {
                mTimerHandler.post(new Runnable() {
                    public void run() {
                        //water();
                        iv.setImageResource(R.mipmap.dog_touch_round);
                    }
                });
            }
        };
        mTimer1.schedule(mTt1, 1, 8000);
        Glide.with(this)
                .load(R.raw.dog_vac)
                .into(iv);
        text = "health +5";
        Context context = getApplicationContext();
        toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, -10, 10);
        toast.show();
        progressBar2.setProgress(progressBar2.getProgress()+5);
        //score.setText(String.valueOf(Integer.parseInt(score.getText().toString())+5*0.25));
    }

    public void play(View view) {
        mTimer1 = new Timer();
        mTt1 = new TimerTask() {
            public void run() {
                mTimerHandler.post(new Runnable() {
                    public void run() {
                        //water();
                        iv2.setImageResource(R.mipmap.dog_touch_round);
                    }
                });
            }
        };
        mTimer1.schedule(mTt1, 1, 5000);
        System.out.println("time ends!");
        Glide.with(this)
                .load(R.raw.play_play)
                .into(iv2);
        Context context = getApplicationContext();
        text = "happiness +5";
        toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, -10, 10);
        toast.show();
        progressBar4.setProgress(progressBar4.getProgress()+5);
        //score.setText(String.valueOf(Integer.parseInt(score.getText().toString())+5*0.25));
    }

    public void toPage(View view) {
        Intent intent=new Intent();
        intent.setClass(PetHomeActivity.this, UserPageActivity.class);
        startActivity(intent);
    }
//    public void food(View view) {
//        Glide.with(this)
//                .load(R.raw.guitar_blur)
//                .into(iv);
//        new CountDownTimer(5000, 5000) {
//
//            public void onTick(long millisUntilFinished) {
//                // You don't need to use this.
//            }
//            public void onFinish() {
//                // Put the code to stop the GIF here.
//
//            }
//
//        }.start();
//    }

}
