package com.abdullahdemirer.smartlamp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView txtLamp,txtDeliver;
    RelativeLayout relativeLayout;
    Animation txtAnimation, layoutAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        txtAnimation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.fall_down);
        layoutAnimation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.bottom_to_top);


        txtLamp = findViewById(R.id.txtLamp);
        txtDeliver = findViewById(R.id.txtDeliver);
        relativeLayout = findViewById(R.id.realMain);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                relativeLayout.setVisibility(View.VISIBLE);
                relativeLayout.setAnimation(layoutAnimation);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txtLamp.setVisibility(View.VISIBLE);
                        txtDeliver.setVisibility(View.VISIBLE);

                        txtLamp.setAnimation(txtAnimation);
                        txtLamp.setAnimation(txtAnimation);
                    }
                },500);
            }
        },1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,ControlBluetooth.class);
                startActivity(intent);
                finish();
            }
        },6000);
    }


}