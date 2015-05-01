package net.marcelkoch.playground.geo;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class GeoLocationWatcher extends Service implements LocationListener {

    private final String DEBUG_TAG = "GeoLocationWatcher";
    private boolean xmlSuccessful = false;
    private boolean locationTimeExpired = false;

    private LocationManager lm;
    private double latitude;
    private double longitude;
    private double accuracy;

    //Intent Action
    String ACTION_FILTER = "net.marcelkoch.playground.proximityalert";

    @Override
    public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        Log.d(DEBUG_TAG, "onLocationChanged "+ latitude +"  -  "+  longitude);

        accuracy = location.getAccuracy();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(DEBUG_TAG, "onProviderDisabled");
        Toast.makeText(
                getApplicationContext(),
                "Attempted to ping your location, and GPS was disabled.",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(DEBUG_TAG, "onProviderEnabled");
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10f, this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(DEBUG_TAG, "onStatusChanged");

    }

    @Override
    public void onCreate() {
        Log.d(DEBUG_TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        Log.d(DEBUG_TAG, "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(DEBUG_TAG, "onBind");

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(DEBUG_TAG, "onStartCommand");

        //i'm registering my Receiver First
        registerReceiver(new ProximityReceiver(), new IntentFilter(ACTION_FILTER));

        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        //Setting up My Broadcast Intent
        Intent i= new Intent(ACTION_FILTER);
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), -1, i, 0);

        //setting up proximituMethod
        lm.addProximityAlert(
                50.09990, 8.68534, 1000, 100000000, pi);

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 10f, this);
        //lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000,
        //        300f, this);

        return super.onStartCommand(intent,flags,startId);

    }


    public class ProximityReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            // Key for determining whether user is leaving or entering
            String key = LocationManager.KEY_PROXIMITY_ENTERING;

            //Gives whether the user is entering or leaving in boolean form
            boolean state = intent.getBooleanExtra(key, false);

            if(state){
                // Call the Notification Service or anything else that you would like to do here
                Log.d("MyTag", "Welcome to my Area");
                Toast.makeText(context, "Welcome to my Area", Toast.LENGTH_SHORT).show();
            }else{
                //Other custom Notification
                Log.d("MyTag", "Thank you for visiting my Area,come back again !!");
                Toast.makeText(context, "Thank you for visiting my Area,come back again !!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}