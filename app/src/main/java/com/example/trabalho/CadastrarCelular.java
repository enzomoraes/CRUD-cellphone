package com.example.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CadastrarCelular extends AppCompatActivity {

    private DatabaseHelper helper = DatabaseHelper.getInstance(this);
    private Spinner spinner;
    private Button addCel;
    private EditText modelo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_celular);

        spinner = findViewById(R.id.spinnerMarcas);
        ArrayAdapter<Marca> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, helper.listarMarcas());
        spinner.setAdapter(arrayAdapter);

        addCel = findViewById(R.id.addCel);
        addCel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelo = findViewById(R.id.modeloCel);

                if (!modelo.getText().toString().isEmpty()){
                    Marca m = (Marca) spinner.getSelectedItem();
                    Celular celular = new Celular();
                    celular.setMarca(m);
                    celular.setModelo(modelo.getText().toString());
                    helper.addCelular(celular);
                    Toast.makeText(CadastrarCelular.this, "Celular Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CadastrarCelular.this, MainActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(CadastrarCelular.this, "Insira o modelo do celular", Toast.LENGTH_SHORT).show();


            }
        });
    }
}
