package com.example.examenpractico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
        String strId = editTextId.getText().toString();
        String strContrasena = editTextPassword.getText().toString();
        RequestQueue requestQueue;
        String url  = "https://examenpractico2022.000webhostapp.com";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response ->{
                    System.out.println("APPMSG: API RESPONSE "+response);
                    try {
                        JSONObject json = new JSONObject(response);
                        String strTipoRespuesta = json.getString("type");
                        if(strTipoRespuesta.equals("success")){
                            this.strId = json.getJSONObject("data").getString("id");
                            this.strCorreo = json.getJSONObject("data").getString("correo");
                            this.strNombre = json.getJSONObject("data").getString("nombre");
                            goToUserArea();
                        } else {
                            String feedback = json.getString("data");
                            Toast.makeText(getApplicationContext(), feedback, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error ->{
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                    System.out.println("APPMSG: "+error.toString());
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", strId);
                params.put("contrasena", strContrasena);
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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