package com.example.examenpractico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class UserArea extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    TextView textViewUser;
    Button btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        textViewUser = findViewById(R.id.textViewUser);
        String strUserInfo = "";
        Intent intent = getIntent();
        strUserInfo += "\n"+intent.getStringExtra("id");
        strUserInfo += "\n"+intent.getStringExtra("nombre");
        strUserInfo += "\n"+intent.getStringExtra("correo");
        textViewUser.setText(strUserInfo);

        btnCerrarSesion = findViewById(R.id.buttonCerrarSesion);
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarSesion();
            }
        });
    }

    private void cerrarSesion(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                finish();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gsc.signOut();
    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }



}