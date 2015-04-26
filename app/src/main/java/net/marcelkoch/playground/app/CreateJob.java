package net.marcelkoch.playground.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import net.marcelkoch.playground.data.JobsOpenHelper;
import net.marcelkoch.playground.geo.GeoLocationWatcher;

/**
 * Created by mkoch on 25.04.2014.
 */
public class CreateJob extends FragmentActivity {

    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        String address = "Keine Adresse vorhanden";
        if (b != null)
        {
            address = (String) b.get("address");
            Log.w("MK", "got address= " + address);
        }

        setContentView(R.layout.createjob);

        TextView addressTextView = (TextView) findViewById(R.id.address);
        addressTextView.setText(address);
        this.address = address;

        Intent intent = new Intent(this, GeoLocationWatcher.class);
        startService(intent);

    }

    public void saveMessage(View view) {
        EditText messageEditText = (EditText) ((View)view.getParent()).findViewById(R.id.message);
        final String message = messageEditText.getText().toString();
        JobsOpenHelper jobsOpenHelper = new JobsOpenHelper(view.getContext());
        jobsOpenHelper.saveJob(message,this.address);
    }
}
