package net.marcelkoch.playground.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by marcel on 18.09.14.
 */
public class ContactListAdapter extends SimpleAdapter {

    public ContactListAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }
}
