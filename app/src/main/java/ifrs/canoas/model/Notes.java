package ifrs.canoas.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ifrs.canoas.lib.NotesContract;
import ifrs.canoas.lib.NotesDbHelper;

/**
 * Created by cassi on 18/09/2017.
 */

public class Notes {

    private int idNote;
    private String title;
    private String date;
    private String text;
    private String disciplene;

    public Notes(){}
    public Notes(String title, String text, String disciplene) {
        this.title = title;
        this.text = text;
        this.disciplene = disciplene;
    }
    public Notes(int idNote, String title, String date, String text, String disciplene) {
        this.idNote = idNote;
        this.title = title;
        this.date = date;
        this.text = text;
        this.disciplene = disciplene;
    }

    public int getIdNote() {
        return idNote;
    }

    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDisciplene() {
        return disciplene;
    }

    public void setDisciplene(String disciplene) {
        this.disciplene = disciplene;
    }

    public void insert(NotesDbHelper mdb) {
        SQLiteDatabase db = mdb.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(NotesContract.FeedEntry.COLUMN_NAME_TITLE, title);
        Date data = new Date();
        values.put(NotesContract.FeedEntry.COLUMN_NAME_DATE, data.getTime());

        values.put(NotesContract.FeedEntry.COLUMN_NAME_TEXT, text);
        values.put(NotesContract.FeedEntry.COLUMN_NAME_DISCIPLINE, disciplene);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(NotesContract.FeedEntry.TABLE_NAME, null, values);

    }

    public void update(NotesDbHelper mdb) {
        SQLiteDatabase db = mdb.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(NotesContract.FeedEntry.COLUMN_NAME_TITLE, title);
        values.put(NotesContract.FeedEntry.COLUMN_NAME_DATE, date);
        values.put(NotesContract.FeedEntry.COLUMN_NAME_TEXT, text);
        values.put(NotesContract.FeedEntry.COLUMN_NAME_DISCIPLINE, disciplene);

       Log.d("COLUNA", values.toString());
        // Which row to update, based on the title
        String selection = NotesContract.FeedEntry._ID+ " = ?";
        String[] selectionArgs = {String.valueOf(this.idNote)};

        int count = db.update(
                NotesContract.FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

    }

    public void delete(NotesDbHelper mdb) {
        SQLiteDatabase db = mdb.getWritableDatabase();
        String selection =  "_ID = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(this.idNote)};
        // Issue SQL statement.
        db.delete(NotesContract.FeedEntry.TABLE_NAME, selection, selectionArgs);

    }

    public void load(NotesDbHelper mdb, int id) {
        SQLiteDatabase db = mdb.getWritableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                NotesContract.FeedEntry._ID,
                NotesContract.FeedEntry.COLUMN_NAME_TITLE,
                NotesContract.FeedEntry.COLUMN_NAME_DATE,
                NotesContract.FeedEntry.COLUMN_NAME_TEXT,
                NotesContract.FeedEntry.COLUMN_NAME_DISCIPLINE
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = NotesContract.FeedEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                NotesContract.FeedEntry.COLUMN_NAME_TITLE + " DESC";

        Cursor c = db.query(
                NotesContract.FeedEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        c.moveToFirst();
        this.idNote = c.getInt(c.getColumnIndex(NotesContract.FeedEntry._ID));
        this.title = c.getString(c.getColumnIndex(NotesContract.FeedEntry.COLUMN_NAME_TITLE));
        this.date = c.getString(c.getColumnIndex(NotesContract.FeedEntry.COLUMN_NAME_DATE));
        this.text = c.getString(c.getColumnIndex(NotesContract.FeedEntry.COLUMN_NAME_TEXT));
        this.disciplene = c.getString(c.getColumnIndex(NotesContract.FeedEntry.COLUMN_NAME_DISCIPLINE));

    }

    public static List getAll(NotesDbHelper mdb) {
        ArrayList<Notes> l = new ArrayList<>();
        SQLiteDatabase db = mdb.getWritableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                NotesContract.FeedEntry._ID,
                NotesContract.FeedEntry.COLUMN_NAME_TITLE,
                NotesContract.FeedEntry.COLUMN_NAME_DATE,
                NotesContract.FeedEntry.COLUMN_NAME_TEXT,
                NotesContract.FeedEntry.COLUMN_NAME_DISCIPLINE
        };


        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                NotesContract.FeedEntry.COLUMN_NAME_TITLE + " ASC";

        Cursor c = db.query(
                NotesContract.FeedEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                l.add(new Notes(c.getInt(c.getColumnIndex(NotesContract.FeedEntry._ID)),
                        c.getString(c.getColumnIndex(NotesContract.FeedEntry.COLUMN_NAME_TITLE)),
                        c.getString(c.getColumnIndex(NotesContract.FeedEntry.COLUMN_NAME_DATE)),
                        c.getString(c.getColumnIndex(NotesContract.FeedEntry.COLUMN_NAME_TEXT)),
                        c.getString(c.getColumnIndex(NotesContract.FeedEntry.COLUMN_NAME_DISCIPLINE))
                ));
            } while (c.moveToNext());
        }

        return l;
    }


    @Override
    public String toString() {
        return "Notes{" +
                "idNote=" + idNote +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", text='" + text + '\'' +
                ", disciplene='" + disciplene + '\'' +
                '}';
    }
}
