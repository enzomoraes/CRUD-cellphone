package com.example.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AtualizarMarca extends AppCompatActivity {

    private DatabaseHelper helper = DatabaseHelper.getInstance(this);
    private Button attMarca;
    private EditText marca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_marca);

        marca = findViewById(R.id.nomeMarca);
        marca.setText(getIntent().getExtras().getString("marca"));
        attMarca = findViewById(R.id.attMarca);
        attMarca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!marca.getText().toString().isEmpty()) {

                    Marca m = new Marca();
                    m.setIdMarca(getIntent().getExtras().getInt("marca_id"));
                    m.setMarca(marca.getText().toString());
                    helper.atualizaMarca(m);
                    Toast.makeText(AtualizarMarca.this, "Marca atualizada com Sucesso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AtualizarMarca.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(AtualizarMarca.this, "Marca não pôde ser atualizada com Sucesso", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}
