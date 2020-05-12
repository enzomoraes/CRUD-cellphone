package com.example.trabalho;

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

public class ListarCelulares extends AppCompatActivity {

    private DatabaseHelper helper = DatabaseHelper.getInstance(this);
    private ListView listaDeCelulares;
    ArrayAdapter<Celular> arrayAdapter;
    Celular celular;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_celulares);

        preencheLista();

        listaDeCelulares.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                celular = arrayAdapter.getItem(position);
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
                helper.excluirCelular(celular.getIdCel());
                preencheLista();
                return false;
            }
        });

        matualiza.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = new Intent(ListarCelulares.this, AtualizarCelular.class);
                intent.putExtra("modelo", celular.getModelo());
                intent.putExtra("cel_id", celular.getIdCel());
                intent.putExtra("id_marca", celular.getMarca().getIdMarca());
                startActivity(intent);

                return false;
            }
        });
    }

    public void preencheLista(){
        listaDeCelulares = findViewById(R.id.listaDeCelulares);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, helper.listarCelulares());
        listaDeCelulares.setAdapter(arrayAdapter);
        registerForContextMenu(listaDeCelulares);
    }

}
