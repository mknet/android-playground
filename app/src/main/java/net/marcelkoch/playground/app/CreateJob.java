package net.marcelkoch.playground.app;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import net.marcelkoch.playground.data.Job;
import net.marcelkoch.playground.data.JobsOpenHelper;
import net.marcelkoch.playground.geo.GeoLocationWatcher;

import java.io.IOException;
import java.util.List;

/**
 * Created by mkoch on 25.04.2014.
 */
public class CreateJob extends FragmentActivity {

    private String addressString;
    private Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        String address = "Keine Adresse vorhanden";
        if (b != null)
        {
            address = (String) b.get("address");
            Log.w("MK", "got addressString= " + address);
        }

        setContentView(R.layout.createjob);

        TextView addressTextView = (TextView) findViewById(R.id.address);
        addressTextView.setText(address);
        this.addressString = address;

        this.address = getLocationFromAddress(address);

        TextView longitudeView = (TextView) findViewById(R.id.longitude);
        longitudeView.setText(String.valueOf((this.address.getLongitude())));
        TextView latitudeView = (TextView) findViewById(R.id.latitude);
        latitudeView.setText(String.valueOf((this.address.getLatitude())));

        Intent intent = new Intent(this, GeoLocationWatcher.class);
        startService(intent);

    }

    public Address getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;

        Address location = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            location = address.get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return location;
    }

    public void saveMessage(View view) {
        EditText messageEditText = (EditText) ((View)view.getParent()).findViewById(R.id.message);
        final String message = messageEditText.getText().toString();
        JobsOpenHelper jobsOpenHelper = new JobsOpenHelper(view.getContext());
        final Job job = new Job();
        job.address = this.addressString;
        job.message = message;

        jobsOpenHelper.save(job);
    }
}
