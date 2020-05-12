package com.example.trabalho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListarMarcas extends AppCompatActivity {
    private DatabaseHelper helper = DatabaseHelper.getInstance(this);
    private ListView listaDeMarcas;
    ArrayAdapter<Marca> arrayAdapter;
    Marca marca;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_marcas);

        preencheLista();

        listaDeMarcas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                marca = arrayAdapter.getItem(position);
                return false;
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuItem mdelete = menu.add(Menu.NONE, 1, Menu.NONE, R.string.excluir);
        MenuItem matualiza = menu.add(Menu.NONE, 2, Menu.NONE, R.string.editar);

        mdelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                helper.excluirMarca(marca.getIdMarca());
                preencheLista();
                return false;
            }
        });
        matualiza.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = new Intent(ListarMarcas.this, AtualizarMarca.class);
                intent.putExtra("marca_id", marca.getIdMarca());
                intent.putExtra("marca", marca.getMarca());
                startActivity(intent);

                return false;
            }
        });
    }


    public void preencheLista(){
        listaDeMarcas = findViewById(R.id.listaDeMarcas);
        arrayAdapter = new ArrayAdapter<Marca>(this, android.R.layout.simple_expandable_list_item_1, helper.listarMarcas());
        listaDeMarcas.setAdapter(arrayAdapter);
        registerForContextMenu(listaDeMarcas);
    }
}
