package com.example.examenpractico;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class ConnectedObject extends AppCompatActivity implements ServiceCallBacks{

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
    }

    public void cerrarSesion(){
        gsc.signOut().addOnCompleteListener(task -> finish());
    }

    //SERVICE //////////////////////////////////////////////////////////////////////////////

    private NetworkChecker networkChecker;
    private boolean bound = false;

    @Override
    protected void onStart() {
        super.onStart();
        // bind to Service
        Intent intent = new Intent(this, NetworkChecker.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from service
        if (bound) {
            networkChecker.setCallbacks(null); // unregister
            unbindService(serviceConnection);
            bound = false;
        }
    }

    ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // cast the IBinder and get MyService instance
            NetworkChecker.LocalBinder binder = (NetworkChecker.LocalBinder) service;
            networkChecker = binder.getService();
            bound = true;
            networkChecker.setCallbacks(ConnectedObject.this); // register
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    };

    @Override
    public void notifyDisconnection() {
        System.out.println("APPMSG: Disconected");
        finish();
    }

}
