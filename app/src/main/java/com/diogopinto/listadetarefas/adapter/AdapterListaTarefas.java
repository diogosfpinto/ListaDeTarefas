package com.diogopinto.listadetarefas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diogopinto.listadetarefas.R;
import com.diogopinto.listadetarefas.model.Tarefa;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterListaTarefas extends RecyclerView.Adapter<AdapterListaTarefas.MyViewHolder> {

    private List<Tarefa> listaTarefa;

    public AdapterListaTarefas(List<Tarefa>  lista) {
        this.listaTarefa = lista;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_tarefa_adapter, parent, false);


        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Tarefa tarefa = listaTarefa.get(position);
        holder.tarefa.setText(tarefa.getNomeTarefa());
    }

    @Override
    public int getItemCount() {
        return this.listaTarefa.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tarefa;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tarefa = itemView.findViewById(R.id.textNome);
        }
    }
}
