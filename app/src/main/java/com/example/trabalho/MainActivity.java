package com.example.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView addMarca;
    private ImageView addCel;

    private ImageView listMarca;
    private ImageView listCel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addMarca = findViewById(R.id.addMarca);
        addMarca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastrarMarca.class);
                startActivity(intent);
            }
        });

        addCel = findViewById(R.id.addCel);
        addCel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastrarCelular.class);
                startActivity(intent);
            }
        });

        listMarca = findViewById(R.id.listMarcas);
        listMarca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListarMarcas.class);
                startActivity(intent);
            }
        });
        listCel = findViewById(R.id.listCel);
        listCel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListarCelulares.class);
                startActivity(intent);
            }
        });
    }
}
