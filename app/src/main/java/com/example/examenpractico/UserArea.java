package com.example.examenpractico;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class UserArea extends ConnectedObject {

    TextView textViewUser;
    Button btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        textViewUser = findViewById(R.id.textViewUser);
        String strUserInfo = "";
        Intent intent = getIntent();
        strUserInfo += "\n"+intent.getStringExtra("id");
        strUserInfo += "\n"+intent.getStringExtra("nombre");
        strUserInfo += "\n"+intent.getStringExtra("correo");
        textViewUser.setText(strUserInfo);

        btnCerrarSesion = findViewById(R.id.buttonCerrarSesion);
        btnCerrarSesion.setOnClickListener(view -> cerrarSesion());
    }

    @Override
    public void onBackPressed(){
        //Previene que se pueda regresar al login con las opciones de navegacion
        moveTaskToBack(true);
    }


}