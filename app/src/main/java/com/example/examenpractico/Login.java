package com.example.examenpractico;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class Login extends AppCompatActivity {

    EditText editTextId;
    EditText editTextPassword;

    Intent serviceIntent;

    String strId;
    String strCorreo;
    String strNombre;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceIntent = new Intent(this, NetworkChecker.class);
        startService(serviceIntent);

        setContentView(R.layout.activity_main);
        editTextId = findViewById(R.id.editTextId);
        editTextPassword = findViewById(R.id.editTextPassword);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(serviceIntent);
        System.out.println("APPMSG: Destroying service");
    }

    public void normalLogin(View view) {
        String strMensaje = "id: " + editTextId.getText().toString() + "\nContrasena :" +
                editTextPassword.getText().toString();
        Toast toast = Toast.makeText(getApplicationContext(), strMensaje, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void googleLogin(View view){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult.launch(signInIntent);
    }

    ActivityResultLauncher<Intent> startActivityForResult = registerForActivityResult(
            new StartActivityForResult(),
            result -> {
                if (result.getResultCode() == AppCompatActivity.RESULT_OK) {
                    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                    if(account != null){
                        strId = account.getId();
                        strCorreo = account.getEmail();
                        strNombre = account.getDisplayName();
                        goToUserArea();
                    }
                }
            }
    );

    public void goToUserArea(){
        String strMensaje = "id: "+strId+"\nNombre: "+strNombre+"\nEmail: "+strCorreo;
        Toast.makeText(getApplicationContext(), strMensaje, Toast.LENGTH_SHORT).show();
        Intent userAreaIntent = new Intent(this, Apipkm.class);
        userAreaIntent.putExtra("id", strId);
        userAreaIntent.putExtra("nombre", strNombre);
        userAreaIntent.putExtra("correo", strCorreo);
        startActivity(userAreaIntent);
    }

}