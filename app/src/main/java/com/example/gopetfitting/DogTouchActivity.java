package com.example.gopetfitting;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Callable;

public class DogTouchActivity extends AppCompatActivity {
    private ImageView iv;
    private AnimationDrawable anim;
    private CustomAnimation animation;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dog_touch);
        iv = (ImageView) findViewById(R.id.touch);
        iv.setImageResource(R.drawable.dog_touch_actions);
        anim = (AnimationDrawable) iv.getDrawable();
        animation = new CustomAnimation(anim);
        animation.setDuration(100);
        animation.setCallableAfterDelay(new Callable<Void>()
        {
            @Override
            public Void call()
            {
                iv.setBackgroundResource(R.drawable.dog_touch_background);
                return null;
            }
        });
        iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("onTouch starts");
                animation.animate();
                return false;
            }
        });
    }
}

/*
anim = (AnimationDrawable) iv.getDrawable();

        CustomAnimation animation = new CustomAnimation(anim);
        animation.setDuration(100);
        animation.setCallableAfterDelay(new Callable<Void>()
        {
            @Override
            public Void call()
            {
                iv.setBackgroundResource(R.drawable.dog_touch_background);
                return null;
            }
        });
        animation.animate();

*/
