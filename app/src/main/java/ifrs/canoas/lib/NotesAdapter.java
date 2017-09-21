package ifrs.canoas.lib;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import ifrs.canoas.ifhelper.NewNoteActivity;
import ifrs.canoas.ifhelper.R;
import ifrs.canoas.model.Notes;
import ifrs.canoas.model.portal.Curso;

/**
 * Classe Adapter personalizada o android fornece alguns adapters mas que graça tem
 */
public class NotesAdapter extends BaseAdapter implements Serializable {

    private Context context;//Contexto da aplicação preciso para poder usar os recursos do android
    private List<Notes> list; //Minha Lista
    private NotesDbHelper db;

    /**
     * Construtor forçando a bara para ter o contexto e uma lista
     *
     * @param context
     * @param list
     */
    public NotesAdapter(Context context, List<Notes> list,  NotesDbHelper db ) {
        this.context = context;
        this.list = list;
        this.db = db;
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public Object getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return list.get(arg0).getIdNote();
    }

    @Override
    public View getView(int position, View elemento, ViewGroup pai) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.note_elemento, null);

        TextView titulo = (TextView) layout.findViewById(R.id.titleNote);
        final TextView mensagem = (TextView) layout.findViewById(R.id.textMensagem);

        final Notes note = (Notes) this.getItem(position);
        titulo.setText(note.getTitle());

        mensagem.setText(note.getText());

        Button btDel = (Button) layout.findViewById(R.id.btExcluir);
        Button btEditar = (Button) layout.findViewById(R.id.btEditar);

        btDel.setOnClickListener(
                new View.OnClickListener() {


                    @Override
                    public void onClick(View view) {

                       note.delete(db);
                        Toast.makeText(context,
                                "the note has been deleted.", Toast.LENGTH_SHORT).show();

                       Notes.getAll(db);
                        layout.setVisibility(View.GONE);
                    }
        });

        btEditar.setOnClickListener(
                new View.OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        Intent objIntent = new Intent(context, NewNoteActivity.class);
                        objIntent.putExtra("edicao", true);
                        objIntent.putExtra("id", note.getIdNote());
                        objIntent.putExtra("title", note.getTitle());
                        objIntent.putExtra("text", note.getText());
                        objIntent.putExtra("date", note.getDate());
                        objIntent.putExtra("discipline", note.getDisciplene());
                        context.startActivity(objIntent);
                    }
                });

        return layout;
    }

}
