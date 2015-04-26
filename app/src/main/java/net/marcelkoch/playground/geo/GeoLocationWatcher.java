package net.marcelkoch.playground.geo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
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

    @Override
    public void onLocationChanged(Location location) {
        Log.d(DEBUG_TAG, "onLocationChanged");

        latitude = location.getLatitude();
        longitude = location.getLongitude();

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

        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 10f, this);
        //lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000,
        //        300f, this);

        return super.onStartCommand(intent,flags,startId);

    }
}