package com.ems.bdsqlitefull.crud;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.ems.bdsqlitefull.R;
import com.ems.bdsqlitefull.pojo.Aluno;

import java.util.ArrayList;

public class ListAll extends AppCompatActivity {
    ListView listViewAlunos;
    ArrayList<Aluno> alunos = new ArrayList<>();
    ArrayAdapter<Aluno> adaptador;
    SQLiteDatabase db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Mostra um botão na Barra Superior para voltar
        getSupportActionBar().setTitle("CRUD DB SQLite - Listagem Geral");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        // Abreo banco de dados existente
        db = openOrCreateDatabase("db_aluno", Context.MODE_PRIVATE, null);

        listViewAlunos = findViewById(R.id.listagem);

        // Carrega os registros em ordem alfabética no ArrayList para anexar ao adaptador
        alunos.clear();
        Cursor c = db.rawQuery("SELECT * FROM aluno ORDER BY nome ASC", null);
        while (c.moveToNext()) {
            alunos.add(new Aluno(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4)));
        }
        // Configura o adaptador
        adaptador = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                alunos);


        // Anexa o adaptador à ListView
        listViewAlunos.setAdapter(adaptador);

        listViewAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aluno aluno = (Aluno) listViewAlunos.getItemAtPosition(position);
                Intent itAluno = new Intent(getApplicationContext(), Details.class);
                itAluno.putExtra("objAluno", aluno);
                startActivity(itAluno);
            }
        });
    }

    // Configura o botão (seta) na ActionBar (Barra Superior)
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}