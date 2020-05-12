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

public class AtualizarCelular extends AppCompatActivity {

    private DatabaseHelper helper = DatabaseHelper.getInstance(this);
    private Spinner spinner;
    private Button attCel;
    private EditText modelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_celular);


        spinner = findViewById(R.id.spinnerMarcas);
        ArrayAdapter<Marca> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, helper.listarMarcas());
        spinner.setAdapter(arrayAdapter);

        // Setando o spinner na marca que ele ja era anteriormente
        Marca marcaDoSpinner = helper.findMarca(getIntent().getExtras().getInt("id_marca"));
        int i = -1;
        do{
            spinner.setSelection(++i);
        } while (!spinner.getSelectedItem().toString().equals(marcaDoSpinner.toString()));



        modelo = findViewById(R.id.modeloCel);
        modelo.setText(getIntent().getExtras().getString("modelo"));

        attCel = findViewById(R.id.attCel);
        attCel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelo = findViewById(R.id.modeloCel);

                if (!modelo.getText().toString().isEmpty()){
                   Marca m = (Marca) spinner.getSelectedItem();
                   Celular celular = new Celular();
                   celular.setIdCel(getIntent().getExtras().getInt("cel_id"));
                   celular.setModelo(getIntent().getExtras().getString("modelo"));
                   celular.setMarca(m);
                   helper.atualizarCelular(celular);
                   Toast.makeText(AtualizarCelular.this, "Celular Atualizado com sucesso", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(AtualizarCelular.this, MainActivity.class);
                   startActivity(intent);

                }
                else
                    Toast.makeText(AtualizarCelular.this, "Celular não pôde ser Atualizado com sucesso", Toast.LENGTH_SHORT).show();


            }
        });

    }
}
