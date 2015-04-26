package net.marcelkoch.playground.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by marcel on 22.04.15.
 */
public class SearchActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            String address = (String) b.get("address");
            Log.w("MK", "got address= " + address);
        }

        setContentView(R.layout.search);

    }
}