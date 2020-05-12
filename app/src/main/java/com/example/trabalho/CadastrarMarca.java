package com.example.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CadastrarMarca extends AppCompatActivity {

    private DatabaseHelper helper = DatabaseHelper.getInstance(this);
    private Button addMarca;
    private EditText marca;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_marca);

        marca = findViewById(R.id.nomeMarca);
        addMarca = findViewById(R.id.addMarca);
            addMarca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        if (!marca.getText().toString().isEmpty()) {
                            Marca m = new Marca();
                            m.setMarca(marca.getText().toString());
                            helper.addMarca(m);
                            Toast.makeText(CadastrarMarca.this, "Marca cadastrada com Sucesso", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CadastrarMarca.this, MainActivity.class);
                            startActivity(intent);
                        } else
                            Toast.makeText(CadastrarMarca.this, "Insira o nome da Marca", Toast.LENGTH_SHORT).show();

                    }

            });




    }

}
