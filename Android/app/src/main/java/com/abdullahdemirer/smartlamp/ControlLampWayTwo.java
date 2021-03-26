package com.abdullahdemirer.smartlamp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.abdullahdemirer.smartlamp.HelperClasses.ConnectionHelper;

import java.io.IOException;

public class ControlLampWayTwo extends AppCompatActivity implements View.OnTouchListener {
    BluetoothSocket btSocket;
    Button turnOn;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_control_lamp_way_two);
        ConnectionHelper connectionHelper = ConnectionHelper.getInstance();
        btSocket = connectionHelper.getBtSocket();
        toolbar = findViewById(R.id.tbar);
        toolbar.setTitle("Way Two");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        turnOn = findViewById(R.id.lamptwo);
        turnOn.setOnTouchListener(this);


    }
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        System.out.println(event.getAction());
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                try {
                    btSocket.getOutputStream().write("1".toString().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case MotionEvent.ACTION_UP:
                try {
                    btSocket.getOutputStream().write("2".toString().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        return true;
    }
}