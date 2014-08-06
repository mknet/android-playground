package net.marcelkoch.playground.core;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.provider.ContactsContract;

public  class ContactsOverviewCursorLoader extends CursorLoader {

    protected ContactsOverviewCursorLoader(Context context, String[] projection, String selection) {
        super(context, ContactsContract.Data.CONTENT_URI,
                projection, selection, new String[]{ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE}, null);
    }
}
