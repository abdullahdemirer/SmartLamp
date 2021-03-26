package com.abdullahdemirer.smartlamp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.abdullahdemirer.smartlamp.HelperClasses.ConnectionHelper;

import java.io.IOException;

public class ControlLampWayOne extends AppCompatActivity {

    BluetoothSocket btSocket;
    ImageView imageView;
    SwitchCompat switchCompat;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_control_lamp_way_one);
        ConnectionHelper connectionHelper = ConnectionHelper.getInstance();
        btSocket = connectionHelper.getBtSocket();
        toolbar = findViewById(R.id.tbar);
        toolbar.setTitle("Way One");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        switchCompat = findViewById(R.id.switchButtonn);
        imageView = findViewById(R.id.lampImage);

        imageView.setImageDrawable(getResources().getDrawable(R.drawable.light_off));
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    try {
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.light_on));
                        btSocket.getOutputStream().write("1".toString().getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.light_off));
                        btSocket.getOutputStream().write("2".toString().getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}