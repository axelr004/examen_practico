package com.example.examenpractico;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Apipkm extends ConnectedObject {

    ImageView imageView;
    TextView textViewNombre,tipo,id,height,weight;
    EditText editTextNombre;
    Button Butonget;
    Button btnInfo;
    RequestQueue queque;

    String Url = "https://pokeapi.co/api/v2/pokemon/" ;

    List<String> datos = new ArrayList<>();

    TextView textViewUser;
    Button btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apipkm);


        imageView = findViewById(R.id.imageView);
        Butonget = findViewById(R.id.Butonget);
        editTextNombre = findViewById(R.id.editTextNombre);
        textViewNombre = findViewById(R.id.nombre);

        id = findViewById(R.id.id);
        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);
        tipo = findViewById(R.id.tipos);

        Butonget.setOnClickListener(v -> GetAPiData());

        queque = Volley.newRequestQueue(getApplicationContext());

        System.out.println("MSG " + datos.size());

        textViewUser = findViewById(R.id.textViewUser);
        String strUserInfo = "";
        Intent intent = getIntent();
        strUserInfo += "\n"+intent.getStringExtra("id");
        strUserInfo += "\n"+intent.getStringExtra("nombre");
        strUserInfo += "\n"+intent.getStringExtra("correo");
        textViewUser.setText(strUserInfo);

        btnCerrarSesion = findViewById(R.id.buttonCerrarSesion);
        btnCerrarSesion.setOnClickListener(view -> cerrarSesion());

        btnInfo = findViewById(R.id.btnInfo);
        btnInfo.setOnClickListener(view -> {
            Intent infoIntent = new Intent(this, ComponentesIU.class);
            startActivity(infoIntent);
        });

    }

    public void GetAPiData() {

        String UrlFinal = Url+ editTextNombre.getText().toString().toLowerCase();

        System.out.println("URLs" + UrlFinal);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlFinal,
                response ->{
                    System.out.println("APPMSG: "+response);
                    try {
                        JSONObject json = new JSONObject(response);
                        id.setText(json.getString("id"));
                        editTextNombre.setText(json.getString("name"));
                        height.setText(json.getString("height"));
                        weight.setText(json.getString("weight"));
                        JSONArray tipox = json.getJSONArray("types");
                        JSONObject type = ((JSONObject) tipox.get(0)).getJSONObject("type");
                        System.out.println("TIP" + type);
                        tipo.setText(type.getString("name"));

                        Context context = imageView.getContext();

                        int ids = context.getResources().getIdentifier("p"+id.getText().toString(), "drawable", context.getPackageName());
                        System.out.println("IMAGEN "+ ids);
                        imageView.setImageResource(ids);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error ->{
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                    System.out.println("APPMSG: "+error.toString());
                }){
            @Override
            protected Map<String, String> getParams() {
                return new HashMap<>();
            }
        };
        queque.add(stringRequest);
        System.out.println("Consul " + queque);
    }

    @Override
    public void onBackPressed(){
        //Previene que se pueda regresar al login con las opciones de navegacion
        moveTaskToBack(true);
    }

}
