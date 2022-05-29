package com.example.examenpractico;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

    String strId;
    String strCorreo;
    String strNombre;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextId = findViewById(R.id.editTextId);
        editTextPassword = findViewById(R.id.editTextPassword);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
    }

    public void normalLogin(View view) {
        String strMensaje = "id: " + editTextId.getText().toString() + "\nContrasena :" +
                editTextPassword.getText().toString();
        Toast toast = Toast.makeText(getApplicationContext(), strMensaje, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void googleLogin(View view){
        Intent signInIntent = gsc.getSignInIntent();
        activityResultLauncher.launch(signInIntent);
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    try{
                        task.getResult(ApiException.class);
                        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                        if(account != null){
                            strId = account.getId();
                            strCorreo = account.getEmail();
                            strNombre = account.getDisplayName();
                            goToUserArea();
                        }
                    } catch (ApiException e) {
                        Toast.makeText(getApplicationContext(), "Error al tratar de iniciar sesion con google.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private void goToUserArea(){
        String strMensaje = "id: "+strId+"\nNombre: "+strNombre+"\nEmail: "+strCorreo;
        Toast.makeText(getApplicationContext(), strMensaje, Toast.LENGTH_SHORT).show();
        Intent userAreaIntent = new Intent(this, UserArea.class);
        userAreaIntent.putExtra("id", strId);
        userAreaIntent.putExtra("nombre", strNombre);
        userAreaIntent.putExtra("correo", strCorreo);
        startActivity(userAreaIntent);
        finish();
    }

}