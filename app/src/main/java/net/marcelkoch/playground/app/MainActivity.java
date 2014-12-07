package net.marcelkoch.playground.app;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;

import net.marcelkoch.playground.adapter.ContactListAdapter;
import net.marcelkoch.playground.core.ContactsOverviewCursorLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


public class MainActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    // This is the Adapter being used to display the list's data
    SimpleCursorAdapter mAdapter;

    ContactListAdapter contactListAdapter;

    // These are the Contacts rows that we will retrieve

    // This is the select criteria

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a progress bar to display while the list loads
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        progressBar.setIndeterminate(true);
        getListView().setEmptyView(progressBar);

        // Must add the progress bar to the root of the layout
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        root.addView(progressBar);


        // Create an empty adapter we will use to display the loaded data.
        // We pass null for the cursor, then update it in onLoadFinished()
//        mAdapter = new SimpleCursorAdapter(this,
//                android.R.layout.simple_list_item_1, null,
//                fromColumns, toViews, 0);
//        setListAdapter(mAdapter);



        getLoaderManager().initLoader(0, null, this);
    }

    // Called when a new Loader needs to be created
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new ContactsOverviewCursorLoader(this).getCursorLoader();
    }

    // Called when a previously created loader has finished loading
    public void onLoadFinished(Loader<Cursor> loader, Cursor cur) {

        Map<String, Map<String, String>> data = new HashMap();

        while (cur.moveToNext()) {
            String id = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Identity.CONTACT_ID));
            String name = cur.getString(cur.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
            String address = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS));


            if (!data.containsKey(id)) {
                Map entry = new HashMap<String, String>();
                entry.put("name", name);
                entry.put("address", address);
                data.put(id, entry);
            }
        }

        // For the cursor adapter, specify which columns go into which views
        String[] fromColumns = {"name"};
        int[] toViews = {android.R.id.text1}; // The TextView in simple_list_item_1

        contactListAdapter = new ContactListAdapter(this, new ArrayList(data.values()),android.R.layout.simple_list_item_1,fromColumns,toViews);
        setListAdapter(contactListAdapter);


        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        //mAdapter.swapCursor(cur);

        getNameEmailDetails();
    }

    // Called when a previously created loader is reset, making the data unavailable
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        mAdapter.swapCursor(null);
    }

//    public void addWatchJob(double longitude, double latitude, float radius) {
//        LocationManager mgr =
//                (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//
//        mgr.addProximityAlert(longitude, latitude, radius,100000,);
//    }
//
//    public void sendMessage(String text) {
//        SmsManager manager = SmsManager.getDefault();
//    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Cursor cursor = (Cursor) l.getItemAtPosition(position);
        String name = cursor.getString(1);
        String address = cursor.getString(2);
        startActivity(new Intent(this, MapActivity.class).putExtra("address", address));
    }

    public ArrayList<String> getNameEmailDetails() {
        ArrayList<String> emlRecs = new ArrayList<String>();
        HashSet<String> emlRecsHS = new HashSet<String>();
        Context context = this;
        ContentResolver cr = context.getContentResolver();
        String[] PROJECTION = new String[]{ContactsContract.RawContacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_ID,
                ContactsContract.CommonDataKinds.Email.DATA,
                ContactsContract.CommonDataKinds.Photo.CONTACT_ID};
        String order = "CASE WHEN "
                + ContactsContract.Contacts.DISPLAY_NAME
                + " NOT LIKE '%@%' THEN 1 ELSE 2 END, "
                + ContactsContract.Contacts.DISPLAY_NAME
                + ", "
                + ContactsContract.CommonDataKinds.Email.DATA
                + " COLLATE NOCASE";
        String filter = ContactsContract.CommonDataKinds.Email.DATA + " NOT LIKE ''";
        Cursor cur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, PROJECTION, filter, null, order);
        if (cur.moveToFirst()) {
            do {
                // names comes in hand sometimes
                String name = cur.getString(1);
                String emlAddr = cur.getString(3);

                // keep unique only
                if (emlRecsHS.add(emlAddr.toLowerCase())) {
                    emlRecs.add(emlAddr);
                }
            } while (cur.moveToNext());
        }

        cur.close();
        return emlRecs;
    }

}