package com.example.examenpractico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.android.volley.AuthFailureError;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class Apipkm extends AppCompatActivity {

    ImageView imageView;
    TextView textViewNombre,tipo,id,height,weight;
    EditText editTextNombre;
    Button Butonget;
    RequestQueue queque;

    String Url = "https://pokeapi.co/api/v2/pokemon/" ;

    List<String> datos = new ArrayList<String>();
    ListView lstDatos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apipkm);


        imageView = findViewById(R.id.imageView);
        Butonget = findViewById(R.id.Butonget);
        editTextNombre = findViewById(R.id.editTextNombre);
        textViewNombre = findViewById(R.id.textViewNombre);

        id = findViewById(R.id.id);
        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);
        tipo = findViewById(R.id.tipos);

        Butonget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetAPiData();
            }
        });

        queque = Volley.newRequestQueue(getApplicationContext());

        System.out.println("MSG " + datos.size());

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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        queque.add(stringRequest);
        System.out.println("Consul " + queque);
    }

}
