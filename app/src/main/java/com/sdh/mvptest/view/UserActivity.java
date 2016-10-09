package com.sdh.mvptest.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.sdh.mvptest.R;
import com.sdh.mvptest.SecondActivity;
import com.sdh.mvptest.ui.ErrorCircle;
import com.sdh.mvptest.ui.LoadingView;
import com.sdh.mvptest.ui.MyClock;
import com.sdh.mvptest.ui.WaveLoadingView;

public class UserActivity extends AppCompatActivity {


    private MyClock clock;
    private SeekBar seekBar;
    private ErrorCircle err;
    private LoadingView loadingView;
    private WaveLoadingView waveloading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seek);
        clock = (MyClock) findViewById(R.id.clock);
        err = (ErrorCircle) findViewById(R.id.err);
        loadingView= (LoadingView) findViewById(R.id.loading);
        waveloading = (WaveLoadingView) findViewById(R.id.waveloading);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                clock.setPercent(progress);
                clock.setText(progress+"%");
                waveloading.setPercent(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        err.SetOnStopListener(new ErrorCircle.OnStopListener() {
            @Override
            public void onStop( View view) {
                TranslateAnimation animation = new TranslateAnimation(0, -5, 0, 0);
                animation.setInterpolator(new OvershootInterpolator());
                animation.setDuration(100);
                animation.setRepeatCount(3);
                animation.setRepeatMode(Animation.REVERSE);
                animation.setAnimationListener(new My());
                view.startAnimation(animation);

            }
        });

    }

    class My implements Animation.AnimationListener{


        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            err.reset();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    public void click(View view){

//        loadingView.start();
        startActivity(new Intent(UserActivity.this, SecondActivity.class));
    }
}
