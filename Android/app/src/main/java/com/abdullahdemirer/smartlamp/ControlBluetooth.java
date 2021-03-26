package com.abdullahdemirer.smartlamp;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.abdullahdemirer.smartlamp.HelperClasses.AlertDialogMessage;
import com.abdullahdemirer.smartlamp.HelperClasses.ConnectionHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ControlBluetooth extends AppCompatActivity {

    Toolbar toolbar;

    BluetoothAdapter myBluetoothAdapter;
    private boolean isBtConnected = false;
    BluetoothAdapter myBluetooth = null;

    String address;
    Button button, button1,button2,button3;


    SweetAlertDialog alertDialog;
    AlertDialog dialog;
    String deviceName;
    private final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_control_bluetooth);
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        toolbar = findViewById(R.id.tbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Home");


        button = findViewById(R.id.button1);
        button1 = findViewById(R.id.button2);
        button2 = findViewById(R.id.button3);
        button3= findViewById(R.id.button4);

        if(!isBtConnected){
            button.setClickable(false);
            button1.setClickable(false);
            button2.setClickable(false);
            button3.setClickable(false);
        }



        int control = controlBluetoothOpen();
        if(control == 1){
            AlertDialogMessage.showWarningAlert(this,"Information","If you want to use this applicaiton " +
                    "you have to open your bluetooth","Okey");

        }else if( control == 3){
            AlertDialogMessage.showErrorAlert(this,"Error!","You can't use this application " +
                    "beacuse of your phone does not have bluetooth");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    System.exit(0);
                }
            },2500);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu,menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.show){
          showPairs();
        }else if(id == R.id.bluetooth){
            openBluetooth();
        }
        return  true;
    }

    private void showPairs() {
        int control = controlBluetoothOpen();
        if(control == 1){
            AlertDialogMessage.showWarningAlert(this,"Information","If you want to use this applicaiton " +
                    "you have to open your bluetooth","Okey");
        } else{
            ListView listView = new ListView(this);
            ArrayList<String> devices = findPairedDevices();

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,devices);
            listView.setAdapter(adapter);
            listView.setBackgroundColor(this.getResources().getColor(R.color.Thistle));
            ColorDrawable color = new ColorDrawable(this.getResources().getColor(R.color.black));
            listView.setDivider(color);
            listView.setDividerHeight(2);

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Select Your Device");
            alert.setView(listView);

            dialog = alert.create();
            dialog.show();
            if(!devices.get(0).equals("No device paired")){
                listView.setOnItemClickListener(selectDevice);
            }
        }


    }

    private int controlBluetoothOpen() {
        if(myBluetoothAdapter != null){
            if(!myBluetoothAdapter.isEnabled()){
                return 1;
            }else {
                return 2;
            }
        }else {
           return 3;
        }

    }

    private ArrayList<String> findPairedDevices() {
        Set<BluetoothDevice> devices = myBluetoothAdapter.getBondedDevices();
        ArrayList<String> devicesName = new ArrayList<>();
        if(devices.size()> 0){
            for (BluetoothDevice a : devices){
                devicesName.add(a.getName() + "\n"+ a.getAddress());
            }
        }
        if(devicesName.size() == 0){
            devicesName.add("No device paired");
        }
        return devicesName;
    }

    private void openBluetooth() {
        int control = controlBluetoothOpen();
        if(control == 1){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, 0);
        }else{
            AlertDialogMessage.showWarningAlert(this,"WARNING!","Your phone's bluetooth is already turned on","Okey");
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 0:
                if(resultCode == RESULT_OK){
                    AlertDialogMessage.showSuccesAlert(this,"Your phone's bluetooth is turned on");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public AdapterView.OnItemClickListener selectDevice = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            dialog.dismiss();
            String info = ((TextView) view).getText().toString();
            deviceName = info.substring(0,info.length()-17);
            address = info.substring(info.length()-17);
            ConnectionHelper connectionHelper = ConnectionHelper.getInstance();
            new BTbaglan(connectionHelper.getBtSocket()).execute();

        }
    };

    private  class BTbaglan extends AsyncTask<Void,Void,Void> {

        private boolean ConnectSuccess = true;
        private  BluetoothSocket btSocket;

        public BTbaglan(BluetoothSocket btSocket) {
            this.btSocket = btSocket;
        }

        @Override
        protected void onPreExecute() {
            alertDialog = new SweetAlertDialog(ControlBluetooth.this,SweetAlertDialog.PROGRESS_TYPE);
            alertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            alertDialog.setTitleText("Connecting to "+deviceName);
            alertDialog.setCancelable(false);
            alertDialog.show();


        }
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                if(btSocket == null ||! isBtConnected){
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice cihaz = myBluetooth.getRemoteDevice(address);
                    btSocket = cihaz.createInsecureRfcommSocketToServiceRecord(myUUID);
                    btSocket.connect();
                    ConnectionHelper connectionHelper = ConnectionHelper.getInstance();
                    connectionHelper.setBtSocket(btSocket);
                }

            } catch (IOException e) {
                ConnectSuccess = false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (!ConnectSuccess) {
                // msg("Baglantı Hatası, Lütfen Tekrar Deneyin");
                Toast.makeText(getApplicationContext(),"Couldn't connect to "+deviceName,Toast.LENGTH_SHORT).show();
            } else {
                //   msg("Baglantı Basarılı");
                Toast.makeText(getApplicationContext(),"Connected to "+deviceName,Toast.LENGTH_SHORT).show();

                isBtConnected = true;
                button.setClickable(true);
                button1.setClickable(true);
                button2.setClickable(true);
                button3.setClickable(true);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ControlBluetooth.this,ControlLampWayOne.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    }
                });
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ControlBluetooth.this,ControlLampWayTwo.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    }
                });
            }
            alertDialog.dismiss();
        }
    }



}