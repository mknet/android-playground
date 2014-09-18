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

    private static final String[] PROJECTION = new String[]{ContactsContract.Data._ID,
            ContactsContract.Data.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS,
            ContactsContract.CommonDataKinds.Identity.CONTACT_ID
    };

    private static final String SELECTION = ""
            + ContactsContract.Data.DISPLAY_NAME + " NOTNULL AND "
            + ContactsContract.Data.DISPLAY_NAME + " != '' AND "
            + ContactsContract.Data.MIMETYPE + " = ?";
    private final Context context;


    public ContactsOverviewCursorLoader(Context context) {
        this.context = context;
    }

    public Loader<Cursor> getCursorLoader() {
        return new CursorLoader(context, ContactsContract.Data.CONTENT_URI,
                PROJECTION, SELECTION,
                new String[]{ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE},
                ContactsContract.Data.DISPLAY_NAME
                );
    }
}
