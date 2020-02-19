package com.diogopinto.listadetarefas.helper;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static String NAME_DB = "db_tarefas";
    public static String TABLE_NAME = "tarefas";
    private static int VERSION = 2;


    public DBHelper(Context context) {
        super(context, NAME_DB, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            String query = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+
                    " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nome TEXT NOT NULL);";

            sqLiteDatabase.execSQL(query);
            Log.i("INFO DB","Sucesso ao criar Tabela");
        }catch (SQLException e){
            Log.i("INFO DB","Erro ao criar Tabela"+e.getMessage());

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            String query = "DROP TABLE IF EXISTS "+TABLE_NAME +" ;";

            sqLiteDatabase.execSQL(query);
            onCreate(sqLiteDatabase);
            Log.i("INFO DB","Sucesso ao atualizar app");
        }catch (SQLException e){
            Log.i("INFO DB","Erro ao atualizar app"+e.getMessage());

        }
    }
}
