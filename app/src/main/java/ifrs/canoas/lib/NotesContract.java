package ifrs.canoas.lib;

import android.provider.BaseColumns;

/**
 * Created by cassi on 18/09/2017.
 */

public class NotesContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_TEXT + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_DISCIPLINE + TEXT_TYPE + " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    private NotesContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TEXT = "text";
        public static final String COLUMN_NAME_DISCIPLINE = "disciplene";

    }
}
