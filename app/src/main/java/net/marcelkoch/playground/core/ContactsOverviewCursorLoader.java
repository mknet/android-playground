package net.marcelkoch.playground.core;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.ContactsContract;

public class ContactsOverviewCursorLoader /*extends CursorLoader*/ {

    private static final String[] PROJECTION = new String[]{"DISTINCT " + ContactsContract.Data._ID,
            ContactsContract.Data.DISPLAY_NAME/*, ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS*/};

    // Dear future me: please forgive me
    private static final String SELECTION = ""
            + ContactsContract.Data.DISPLAY_NAME + " NOTNULL AND "
            + ContactsContract.Data.DISPLAY_NAME + " != '' AND "
            + ContactsContract.Data.MIMETYPE + " = ?";
    private final Context context;


    public ContactsOverviewCursorLoader(Context context) {
        /*super(context, ContactsContract.Data.CONTENT_URI,
                PROJECTION, SELECTION, new String[]{ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE}, null);*/
        this.context = context;
    }

    public Loader<Cursor> getCursorLoader() {
//        return new CursorLoader(this.context) {
//            @Override
//            public Cursor loadInBackground() {
//                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
//                return builder.query(new MySQLiteOpenHelper().getReadableDatabase(),
//                        PROJECTION,
//                        SELECTION,
//                        new String[]{ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE},
//                        null,null,null);
//            }
//
//        };
        return new CursorLoader(context, ContactsContract.Data.CONTENT_URI,
                PROJECTION, SELECTION, new String[]{ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE}, null);
    }

    private class MySQLiteOpenHelper extends SQLiteOpenHelper {
        public MySQLiteOpenHelper() {
            super(ContactsOverviewCursorLoader.this.context, "data", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

        }
    }
}
