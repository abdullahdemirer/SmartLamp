package com.abdullahdemirer.smartlamp.HelperClasses;

import android.bluetooth.BluetoothSocket;

public class ConnectionHelper {
    BluetoothSocket btSocket;

    public static  ConnectionHelper connectionHelper;

    public BluetoothSocket getBtSocket() {
        return btSocket;
    }

    public void setBtSocket(BluetoothSocket btSocket) {
        this.btSocket = btSocket;
    }

    public  static  ConnectionHelper getInstance(){
        if(connectionHelper == null){
            connectionHelper = new ConnectionHelper();
        }
        return  connectionHelper;
    }
}
