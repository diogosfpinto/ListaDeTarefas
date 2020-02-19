package com.diogopinto.listadetarefas.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;

import com.diogopinto.listadetarefas.R;
import com.diogopinto.listadetarefas.adapter.AdapterListaTarefas;
import com.diogopinto.listadetarefas.helper.DBHelper;
import com.diogopinto.listadetarefas.helper.RecyclerItemClickListener;
import com.diogopinto.listadetarefas.helper.TarefaDAO;
import com.diogopinto.listadetarefas.model.Tarefa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterListaTarefas tarefaAdapter;
    private List<Tarefa> listaTarefas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //configurarRecycler
        recyclerView = findViewById(R.id.recyclerView);


        DBHelper dbHelper = new DBHelper(getApplicationContext());

        //adicionarEvento de clique
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //Recuperar tarefa para edição
                                Tarefa tarefaSelecionada = listaTarefas.get(position);
                                //Enviar para tarefa para AdicionarTarefa
                                Intent intent = new Intent(MainActivity.this,
                                        AdicionarTarefaActivity.class);
                                intent.putExtra("tarefaSelecionada", tarefaSelecionada);
                                startActivity(intent);

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Log.i("clique", "onLongItemClick");
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(getApplicationContext(), AdicionarTarefaActivity.class);
               startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart(){
        carregarListaTarefas();
        super.onStart();
    }

    public void carregarListaTarefas(){
        //Listar tarefas
        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        listaTarefas = tarefaDAO.listar();
        //configurar um adapter

        tarefaAdapter = new AdapterListaTarefas(listaTarefas);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(tarefaAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
