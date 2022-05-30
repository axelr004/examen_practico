package com.example.examenpractico;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class ComponentesIU extends AppCompatActivity {
    TextView informacion;
    CheckBox checkBox;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componentes_iu);
        informacion = findViewById(R.id.informacion);
        checkBox = findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
               buscar ();
            }
        });
        String[] nombres = {"David","Alexander","Nicolas","Cristhian"};
        Spinner Snombres = (Spinner) findViewById(R.id.spinner);
        Snombres.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,nombres));
        Snombres.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                TextView textView = (TextView)selectedItemView;
                result = textView.getText().toString();
                buscar();
            }
                 ;
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }
    public  void buscar(){
        String plan = "3743";
        String nombre = "";
        if(result.equals("David")){
            nombre = "German David Estrada Holguin - 2013122";
        } else if(result.equals("Nicolas")){
            nombre = "Nicolas Felipe Victoria Rodriguez - 1767315";
        } else if(result.equals("Cristhian")){
            nombre = "Cristhian Camilo Lozano - 2067818";
        } else if(result.equals("Alexander")){
            nombre = "Alexander Ramirez Bohorquez - 1556236";
            plan = "2711";
        }
        String adicional = "";

        if(checkBox.isChecked()){
            adicional = "\nPlan: "+plan+"\nUniversidad del Valle";
        }
        informacion.setText(nombre + adicional);

    }
}