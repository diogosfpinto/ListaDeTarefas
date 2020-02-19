package com.diogopinto.listadetarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.diogopinto.listadetarefas.R;
import com.diogopinto.listadetarefas.helper.ITarefaDAO;
import com.diogopinto.listadetarefas.helper.TarefaDAO;
import com.diogopinto.listadetarefas.model.Tarefa;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarTarefaActivity extends AppCompatActivity {


    private TextInputEditText textTarefa;
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        textTarefa = findViewById(R.id.textTarefa);

        //Recuperar tarefa, caso seja edição
        tarefaAtual =(Tarefa)getIntent().getSerializableExtra("tarefaSelecionada");

        //configurar tarefa na caixa de texto
        if (tarefaAtual !=null ){
            textTarefa.setText(tarefaAtual.getNomeTarefa());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_salvar:
                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                Tarefa tarefa = new Tarefa();

                if (tarefaAtual != null) {//edição

                    tarefa.setNomeTarefa(textTarefa.getText().toString());
                    if (!tarefa.getNomeTarefa().isEmpty()){
                        tarefa.setId(tarefaAtual.getId());
                        tarefa.setNomeTarefa(tarefa.getNomeTarefa());
                        if (tarefaDAO.atualizar(tarefa)){
                            finish();
                            Toast.makeText(getApplicationContext(), "Sucesso ao atualizar tarefa!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Falha ao atuallizar tarefa!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                } else { //salvar
                    // Executa ação para o item salvar
                    tarefa.setNomeTarefa(textTarefa.getText().toString());
                    if (!tarefa.getNomeTarefa().isEmpty()){
                        if (tarefaDAO.salvar(tarefa)){
                            finish();
                            Toast.makeText(getApplicationContext(), "Sucesso ao salvar tarefa!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Falha ao salvar tarefa!",
                                    Toast.LENGTH_SHORT).show();
                        }

                        break;
                    }
                }

        }
        return super.onOptionsItemSelected(item);
    }
}
