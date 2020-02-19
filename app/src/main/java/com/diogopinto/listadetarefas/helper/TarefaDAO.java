package com.diogopinto.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.diogopinto.listadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements ITarefaDAO{

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public TarefaDAO(Context ctx) {
        DBHelper db = new DBHelper(ctx);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());
        try{
            escreve.insert(DBHelper.TABLE_NAME, null, cv);
            Log.e("INFO", "Tarefa salva com sucesso ");
        } catch (Exception e){
            Log.e("INFO", "Erro ao salvar tarefa "+e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {
        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());
        boolean result = false;
        try{
            int i = escreve.update(DBHelper.TABLE_NAME,cv,"id=?", new String[]{tarefa.getId() + ""});
            if (i != -1) {
                Log.e("INFO", "Tarefa atualizada com sucesso ");
                result = true;
            }
        } catch (Exception e){
            Log.e("INFO", "Erro ao atualizar tarefa "+e.getMessage());
            return false;
        }
        return result;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {
        try{
            escreve.delete(DBHelper.TABLE_NAME, "id=?", new String[]{tarefa.getId() + ""});
            Log.e("INFO", "Tarefa removida com sucesso ");
        } catch (Exception e){
            Log.e("INFO", "Erro ao remover tarefa "+e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Tarefa> listar() {

        List<Tarefa> tarefas = new ArrayList<>();

        String query = "SELECT * FROM "+DBHelper.TABLE_NAME+";";
        Cursor cursor = le.rawQuery(query, null);

        cursor.moveToFirst();
        while (cursor.moveToNext()){
            Tarefa tarefa = new Tarefa();
            tarefa.setId(cursor.getLong(cursor.getColumnIndex("id")));
            tarefa.setNomeTarefa(cursor.getString(cursor.getColumnIndex("nome")));
            tarefas.add(tarefa);
        }
        return tarefas;
    }
}
