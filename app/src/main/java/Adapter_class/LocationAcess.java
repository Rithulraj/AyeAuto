package Adapter_class;

/**
 * Created by armino on 10/11/2017.
 */

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by 4264 on 14-10-2016.
 */

public class LocationAcess implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    private Location mlocation; // location
    private double latitude; // latitude
    private double longitude; // longitude
    private GoogleApiClient mGAC;
    private Context mContext;
    public static final String TAG = "GPSresource";
    private FusedLocationProviderApi fusedLocationProviderApi;
    private LocationRequest locationRequest;

    public LocationAcess(Context c) {
        mContext = c;
        try {
            buildGoogleApiClient();
            mGAC.connect();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    protected synchronized void buildGoogleApiClient() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        locationRequest.setInterval(1);
        locationRequest.setFastestInterval(1);
        mGAC = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    public double getLatitude() {
        if (mlocation != null) {
            latitude = mlocation.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */
    public double getLongitude() {
        if (mlocation != null) {
            longitude = mlocation.getLongitude();
        }

        // return longitude
        return longitude;
    }

    public Location GetLocationBlocking() throws InterruptedException {


        //      String lat=String.valueOf(moCurrentLocation.getLatitude());
        //    String longt=String.valueOf(moCurrentLocation.getLongitude());
//        Toast.makeText(oContext,"Lat"+lat+"long"+longt,Toast.LENGTH_SHORT).show();
        return mlocation;

    }


    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location oLocation = LocationServices.FusedLocationApi.getLastLocation(mGAC);
        if (mGAC != null) {

            mlocation = oLocation;
            getLatitude();
            getLongitude();
            if (oLocation != null){
                Log.d("lat",String.valueOf(mlocation.getLatitude()));
                Log.d("long",String.valueOf(mlocation.getLongitude()));
            }
            else{
                LocationServices.FusedLocationApi.requestLocationUpdates(mGAC, locationRequest, this);
            }   }}


    @Override
    public void onConnectionSuspended(int i) {

    }




    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mlocation=location;
    }
}