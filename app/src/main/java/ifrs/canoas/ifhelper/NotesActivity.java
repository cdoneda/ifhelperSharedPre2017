package ifrs.canoas.ifhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import ifrs.canoas.lib.NotesDbHelper;
import ifrs.canoas.model.Notes;

public class NotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
    }

    public void cadastrar(View v){
        NotesDbHelper db = new NotesDbHelper(this.getApplicationContext());

        EditText eTitle = (EditText) findViewById(R.id.edtTitle);
        EditText eText = (EditText) findViewById(R.id.edtText);
        EditText eDiscipline = (EditText) findViewById(R.id.edtDiscipline);
        Notes n = new Notes(eTitle.getText().toString(),eText.getText().toString(),eDiscipline.getText().toString());
        n.insert(db);
        Log.d("LOG", n.getAll(db).toString());
    }
}
