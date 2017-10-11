package com.example.armino.auto_and_rider;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.view.ViewGroup.LayoutParams;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Adapter_class.Distance_calculator;
import Adapter_class.DriversList;
import Adapter_class.GPS_track;
import Adapter_class.GetData;
import Adapter_class.LocationAcess;

public class Rider_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback,
         LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{




    ListView auto_list;
    EditText search_destination;
    private GoogleMap mMap;
    Button menu_button, voice_button;
    Custom_listview cv;
    double latitude, longitude;
    AutoCompleteTextView Search_editText;
    CoordinatorLayout coordinatorLayout;
    LinearLayout listView_layout;
    RelativeLayout map_layout, progressBar_layout;
    String city_name, state_name, place_name;

    Distance_calculator distance_calculator;
    int n = 0, mapSet = 0;

    StringBuilder sb = new StringBuilder();
    String resultAddress;

    LayoutParams list;
    ListView mListView;
    static public Activity RIDER_CONTEXT;


    private static final String TAG = "RiderActivity";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    Button btnFusedLocation;
    TextView tvLocation;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        setContentView(R.layout.activity_rider_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        RIDER_CONTEXT=this;



        //   ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        progressBar_layout = (RelativeLayout) findViewById(R.id.progressbar_layout_ID);
        map_layout = (RelativeLayout) findViewById(R.id.content_map_layout);
        // listView_layout = (LinearLayout) findViewById(R.id.listView_layout_ID);
        auto_list = (ListView) findViewById(R.id.auto_listView_ID);

        voice_button = (Button) findViewById(R.id.voice_nav_button);
        search_destination  = (EditText) findViewById(R.id.search_editText_ID);;
        Search_editText = (AutoCompleteTextView) findViewById(R.id.search_editText_ID);
        snackbar_search("Update the location", "Yes");
        try {


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Rider_activity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                ActivityCompat.requestPermissions(Rider_activity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
                Toast.makeText(Rider_activity.this, "permissin not granted", Toast.LENGTH_SHORT).show();

            } else {
                //Toast.makeText(Rider_activity.this, "permission already granted", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.getMessage();
        }


        //  backgroundLoadListView c = new backgroundLoadListView();
        // c.execute();
        cv = new Custom_listview(Rider_activity.this);
        //auto_list.setAdapter(cv);

         mListView = (ListView) findViewById(R.id.auto_listView_ID);
        list = (LayoutParams) mListView.getLayoutParams();
        //  mListView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);





        cv.notifyDataSetChanged();
        //mListView.setLayoutParams();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



 /*     auto_list.setOnItemClickListener(new AdapterView.OnItemClickListener()

       {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int position, long arg3) {
                System.out.println("posit................:" + position);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+ 121));//change the number
               if (ActivityCompat.checkSelfPermission(Rider_activity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                }
                startActivity(callIntent);

            }
        }); */
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rider_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;
        map_layout.setVisibility(View.VISIBLE);


        // Add a marker in Sydney and move the camera
     /*   if(latitude==0 && longitude==0)
        {
            latitude = 11.279343;
            longitude = 75.784348;
            Toast.makeText(Rider_activity.this,"Location not accurate",Toast.LENGTH_LONG).show();
        }

        LatLng mylocation = new LatLng(latitude, longitude);
        LatLng sydney2 = new LatLng(latitude+.001,longitude);
        LatLng sydney3 = new LatLng(latitude+.005,longitude);
        LatLng sydney4 = new LatLng(latitude,longitude+.001);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Here"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
        mMap.setMinZoomPreference(16);
        Marker marker = mMap.addMarker(new MarkerOptions().position(mylocation).title(place_name+"\n"+city_name));

        mMap.addMarker(new MarkerOptions().position(sydney2).title("Driver-1"));
        mMap.addMarker(new MarkerOptions().position(sydney3).title("Driver-2"));
        mMap.addMarker(new MarkerOptions().position(sydney4).title("Driver-3"));

        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.demo_marker2));

        //distance_calculator=new Distance_calculator();
      // double dis= distance_calculator.CalculationByDistance(mylocation,sydney2);//......................................
        Toast.makeText(Rider_activity.this,"longitude:"+longitude+"latitude:"+latitude,Toast.LENGTH_LONG).show();
        progressBar_layout.setVisibility(View.GONE); */


    }

    public class backgroundLoadListView extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub


//           // Toast.makeText(Rider_activity.this,
//                    "onPostExecute \n: setListAdapter after bitmap preloaded",
//                    Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
//            Toast.makeText(Rider_activity.this, "onPreExecute \n: preload bitmap in AsyncTask",
//                    Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            //  preLoadSrcBitmap();


            return null;
        }

    }


    void getLocation() {


        GPS_track gpsTracker = new GPS_track(this);

        if (gpsTracker.getIsGPSTrackingEnabled()) {
try {

    latitude = gpsTracker.latitude;
    longitude = gpsTracker.longitude;

    state_name = gpsTracker.getCountryName(this);
    city_name = gpsTracker.getLocality(this);

    place_name = gpsTracker.getAddressLine(this);

    String[] addr = place_name.split(",");
    int size = addr.length;
    for (int i = 0; i < size; i++) {
        sb.append(addr[i]).append("\n");
    }
    resultAddress = sb.toString();

    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    mapFragment.getMapAsync(Rider_activity.this);

    //  driverSearch_snackbar("Founded no driver nearby","REFRESH");
}
catch (Exception e)
{
    Log.d("Rider Activity",e.getMessage());
}

        } else {

            gpsTracker.showSettingsAlert();

        }

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // Toast.makeText(Rider_activity.this,"permission 1 granted",Toast.LENGTH_LONG).show();
                return;
            }

        }
    }

    void snackbar(String message, String button_title) {


        try {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(button_title, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            auto_list.setVisibility(View.GONE);
                            driverSearch_snackbar("Founded no driver nearby", "REFRESH");

                        }
                    });

            snackbar.show();


        } catch (Exception e) {
            e.getMessage();
        }
    }
public  void mapUpdate()
{
    getLocation();
//   LocationAcess la=new LocationAcess(this);
//  latitude= la.getLatitude();
//   longitude= la.getLongitude();

    if (latitude == 0 && longitude == 0) {
        latitude = 11.279343;
        longitude = 75.784348;
        Toast.makeText(Rider_activity.this, "Location not accurate", Toast.LENGTH_LONG).show();

    }
    else {
        Toast.makeText(Rider_activity.this, " accurate", Toast.LENGTH_LONG).show();

        mapSet = 1;
    }
    LatLng mylocation = new LatLng(latitude, longitude);

    LatLng driverLoc1 = new LatLng(latitude + .001, longitude + .001);

    mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
    mMap.setMinZoomPreference(16);
    Marker marker = mMap.addMarker(new MarkerOptions().position(mylocation).title("My Location").snippet(place_name));
    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.demo_marker2));

    Marker marker1 = mMap.addMarker(new MarkerOptions().position(driverLoc1));
    marker1.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ayeauto_marker));
    Toast.makeText(Rider_activity.this, "longitude:" + longitude + "latitude:" + latitude, Toast.LENGTH_LONG).show();

}
    void snackbar_search(String message, String button_title) {

        try {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(button_title, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           updateUI();

                            auto_list.setVisibility(View.GONE);
                            driverSearch_snackbar("Founded no driver nearby", "REFRESH");

                              //  snackbar("Searching....", "cancel");


                        }
                    });
            snackbar.show();

            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                @Override
                public View getInfoWindow(Marker arg0) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    LinearLayout info = new LinearLayout(Rider_activity.this);
                    info.setOrientation(LinearLayout.VERTICAL);

                    TextView title = new TextView(Rider_activity.this);
                    title.setTextColor(Color.BLACK);
                    title.setGravity(Gravity.CENTER);
                    title.setTypeface(null, Typeface.BOLD);
                    title.setText(marker.getTitle());

                    TextView snippet = new TextView(Rider_activity.this);
                    snippet.setTextColor(Color.GRAY);
                    snippet.setText(marker.getSnippet());

                    info.addView(title);
                    info.addView(snippet);

                    return info;
                }
            });

            snackbar.show();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    void driverSearch_snackbar(String message, String button_title) {
        try {
            final Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(button_title, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                   mapUpdate();
//                           new GetData().execute();
//                            n = GetData.al.size();

                            new DriversList().getList();
                            n = DriversList.al1.size();
                            Toast.makeText(getApplicationContext(), "List size:"+n, Toast.LENGTH_SHORT).show();
                            if (n <= 5 && n!=0) {
                                list.height = (n) * 100 + 100;
                            }
                             else if(n>5){
                                list.height = 5 * 100 + 100;
                            }
                            else
                            {
                                list.height = 0;
                            }
                            mListView.setAdapter(cv);
                            auto_list.setVisibility(View.VISIBLE);
                            snackbar("Found " + n + " driver", "Back");


                        }
                    });

            snackbar.show();
        } catch (Exception e) {
            e.getMessage();
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
            Log.d(TAG, "Location update resumed .....................");
        }
       snackbar_search("Update the location","Yes");
        //  getLocation();

    }
    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }




    @Override
    protected void onRestart() {
        super.onRestart();
        snackbar_search("Update the location","Yes");
       // getLocation();
    }
    public void search(View v){  // must use same name as in XML
          String search_dest=search_destination.getText().toString();
        System.out.println("Returned "+search_dest);
        updateUI();


    }
    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart fired ..............");
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop fired ..............");
        mGoogleApiClient.disconnect();
        Log.d(TAG, "isConnected ...............: " + mGoogleApiClient.isConnected());
    }



    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        Log.d(TAG, "Location update started ..............: ");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: " + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Firing onLocationChanged..............................................");
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        updateUI();
    }

    private void updateUI()  {
        Log.d(TAG, "UI update initiated .............");
         mMap.clear();
        if (null != mCurrentLocation) {


             double lat = mCurrentLocation.getLatitude();
             double lng = mCurrentLocation.getLongitude();

            String addressStr = "";
            try{
             Geocoder myLocatio = new Geocoder(this, Locale.getDefault());
             List<Address> myList = myLocatio.getFromLocation(lat, lng, 1);
             Address address = (Address) myList.get(0);
           //   addressStr=  address.getLocality();
              //  addressStr=address.getAdminArea();
                addressStr += address.getAddressLine(0) + " ";
            // addressStr += address.getAddressLine(1) + ", ";
           //  addressStr += address.getAddressLine(1);

                Toast toast= Toast.makeText(getApplicationContext(), ""+addressStr, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
            catch (Exception e)
            {
                Toast.makeText(this,""+e.getMessage() , Toast.LENGTH_LONG).show();
            }

             LatLng mylocation = new LatLng(lat, lng);

             LatLng driverLoc1 = new LatLng(lat + .001, lng + .001);

             mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
             mMap.setMinZoomPreference(16);
             Marker marker = mMap.addMarker(new MarkerOptions().position(mylocation).title("My Location").snippet(addressStr));
             marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.demo_marker2));

             Marker marker1 = mMap.addMarker(new MarkerOptions().position(driverLoc1));
             marker1.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ayeauto_marker));



        } else {
            Log.d(TAG, "location is null ...............");
        }

    }
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        Log.d(TAG, "Location update stopped .......................");
    }
}
