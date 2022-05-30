package com.example.examenpractico;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.IBinder;

import java.util.ArrayList;

public class NetworkChecker extends Service {

    private boolean run = true;
    private final IBinder binder = new LocalBinder();
    private final ArrayList<ServiceCallBacks> serviceCallbacks = new ArrayList<>();

    public class LocalBinder extends Binder {
        NetworkChecker getService() {
            return NetworkChecker.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        run = false;
    }

    public void setCallbacks(ServiceCallBacks callbacks) {

        serviceCallbacks.add(callbacks);
    }

    @SuppressWarnings("BusyWait")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(
                () -> {
                    int i = 0;
                    while(run){
                        System.out.println("APPMSG: checking connection "+i);
                        i++;
                        try {
                            Thread.sleep(1000 );
                            ConnectivityManager connectivityManager
                                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                            if(activeNetworkInfo == null || !activeNetworkInfo.isConnected()){
                                System.out.println("APPMSG: Proceding to make callbacks");
                                System.out.println("APPMSG: Trying to peform callback");
                                makeCallbacks();
                            }
                        } catch (InterruptedException e) {
                            System.out.println("APPMSG ERROR: "+e.getMessage());
                        }
                    }
                }
        ).start();
        return super.onStartCommand(intent, flags, startId);
    }

    private void makeCallbacks(){
        for (int i = 0; i < serviceCallbacks.size(); i++) {
            ServiceCallBacks scb =serviceCallbacks.get(i);
            if(scb != null){
                scb.notifyDisconnection();
            }
        }
        serviceCallbacks.clear();
        System.out.println("APPMSG callbacks made");
    }

}