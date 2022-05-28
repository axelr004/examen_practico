package com.example.examenpractico;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText editTextId;
    EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextId = findViewById(R.id.editTextId);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    public void normalLogin(View view) {
        String strMensaje = "id: " + editTextId.getText().toString() + "\nContrasena :" +
                editTextPassword.getText().toString();
        Toast toast = Toast.makeText(getApplicationContext(), strMensaje, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void googleLogin(View view){
        String strMensaje = "Google Login";
        Toast toast = Toast.makeText(getApplicationContext(), strMensaje, Toast.LENGTH_SHORT);
        toast.show();
    }
}