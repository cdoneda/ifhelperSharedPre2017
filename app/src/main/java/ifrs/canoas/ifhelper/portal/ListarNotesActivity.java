package ifrs.canoas.ifhelper.portal;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import ifrs.canoas.ifhelper.DefaultActivity;
import ifrs.canoas.ifhelper.NewNoteActivity;
import ifrs.canoas.ifhelper.NotesActivity;
import ifrs.canoas.ifhelper.R;
import ifrs.canoas.ifhelper.geral.LoginActivity;
import ifrs.canoas.lib.MensagemAdapter;
import ifrs.canoas.lib.NotesAdapter;
import ifrs.canoas.lib.NotesDbHelper;
import ifrs.canoas.lib.SharedPreferenceHelper;
import ifrs.canoas.lib.WebServiceUtil;
import ifrs.canoas.model.Notes;
import ifrs.canoas.model.portal.Mensagem;
import ifrs.canoas.model.portal.User;

public class ListarNotesActivity extends DefaultActivity implements Serializable {

    NotesDbHelper db;
    private ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_notes);
        setToolbar();
        db = new NotesDbHelper(getApplicationContext());
        geraLista(Notes.getAll(db));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        trataFloatButton();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        geraLista(Notes.getAll(db));
    }

    private void trataFloatButton() {

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context contexto = getApplicationContext();

                Intent objIntent = new Intent(contexto, NewNoteActivity.class);
                objIntent.putExtra("edicao",false);
                startActivity(objIntent);
            }
        });

    }
    public void cadastrar(View v) {


        EditText eTitle = (EditText) findViewById(R.id.edtTitle);
        EditText eText = (EditText) findViewById(R.id.edtText);
        EditText eDiscipline = (EditText) findViewById(R.id.edtDiscipline);
        Notes n = new Notes(eTitle.getText().toString(), eText.getText().toString(), eDiscipline.getText().toString());
        n.insert(db);
        Log.d("LOG", n.getAll(db).toString());

        geraLista(Notes.getAll(db));
    }

    /**
     *
     */


    private void geraLista(List<Notes> lista) {
        list = (ListView) findViewById(R.id.NotesListView);
        NotesAdapter ad = new NotesAdapter(getApplicationContext(), lista, db);
        list.setAdapter(ad);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(), "Ver Mensagem completa", Toast.LENGTH_SHORT).show();
            }

        });
    }





}


