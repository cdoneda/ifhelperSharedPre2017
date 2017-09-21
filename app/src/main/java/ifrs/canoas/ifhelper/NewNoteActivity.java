package ifrs.canoas.ifhelper;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

import ifrs.canoas.lib.NotesDbHelper;
import ifrs.canoas.model.Notes;

public class NewNoteActivity extends AppCompatActivity implements Serializable {

    NotesDbHelper db;
    Notes noteEdicao = new Notes();
    EditText eTitle;
    EditText eText;
    EditText eDiscipline ;
    Boolean modoEdicao = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new NotesDbHelper(this.getApplicationContext());

        EditText   eTitle = (EditText) findViewById(R.id.edtTitle);
        EditText eText = (EditText) findViewById(R.id.edtText);
        EditText eDiscipline = (EditText) findViewById(R.id.edtDiscipline);

        modoEdicao =  getIntent().getExtras().getBoolean("edicao");
        if (modoEdicao) {

            noteEdicao.setIdNote(getIntent().getExtras().getInt("id"));
            noteEdicao.setTitle(getIntent().getExtras().getString("title"));
            noteEdicao.setText(getIntent().getExtras().getString("text"));
            noteEdicao.setDate(getIntent().getExtras().getString("date"));
            noteEdicao.setDisciplene(getIntent().getExtras().getString("discipline"));
            atualizaCamps();
        } else {

            eTitle.setText("");
            eText.setText("");
            eDiscipline.setText("");
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acao();
            }
        });
    }

    public void acao() {
        if (modoEdicao) {
            Toast.makeText(getApplicationContext(), "edicao", Toast.LENGTH_SHORT).show();
            editar();

        } else {
            Toast.makeText(getApplicationContext(), "cadastro", Toast.LENGTH_SHORT).show();
            cadastrar();
        }
    }

    public void cadastrar() {

        Notes n = new Notes(eTitle.getText().toString(), eText.getText().toString(), eDiscipline.getText().toString());
        n.insert(db);
        Log.d("LOG", n.getAll(db).toString());

        finish();
    }
    public void editar() {
        EditText   eTitle = (EditText) findViewById(R.id.edtTitle);
        EditText eText = (EditText) findViewById(R.id.edtText);
        EditText eDiscipline = (EditText) findViewById(R.id.edtDiscipline);

        noteEdicao.setTitle( eTitle.getText().toString());
        noteEdicao.setText(eText.getText().toString());

        noteEdicao.setDisciplene(eDiscipline.getText().toString());

        noteEdicao.update(db);
        finish();
    }

    public void atualizaCamps() {
        EditText   eTitle = (EditText) findViewById(R.id.edtTitle);
        EditText eText = (EditText) findViewById(R.id.edtText);
        EditText eDiscipline = (EditText) findViewById(R.id.edtDiscipline);

        eTitle.setText(noteEdicao.getTitle().toString());
        eText.setText(noteEdicao.getText().toString());
        eDiscipline.setText(noteEdicao.getDisciplene().toString());

    }
}
