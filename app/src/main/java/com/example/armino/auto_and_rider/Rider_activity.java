package com.example.armino.auto_and_rider;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import Adapter_class.GPS_track;

public class Rider_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    ListView auto_list;
    private GoogleMap mMap;
    Button menu_button, voice_button;
    Custom_listview cv;
    double latitude, longitude;
    EditText Search_editText;
    CoordinatorLayout coordinatorLayout;
    LinearLayout listView_layout;
    RelativeLayout map_layout,progressBar_layout;
    String city_name,state_name,place_name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


     //   ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatorLayout);
        listView_layout=(LinearLayout) findViewById(R.id.listView_layout_ID);
        auto_list = (ListView) findViewById(R.id.auto_listView_ID);
        menu_button = (Button) findViewById(R.id.menu_button);
        voice_button = (Button) findViewById(R.id.voice_nav_button);
        Search_editText=(EditText)findViewById(R.id.search_editText_ID) ;


        getLocation();







        backgroundLoadListView c = new backgroundLoadListView();
        c.execute();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitude, longitude);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Here"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setMinZoomPreference(16);
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title(place_name));


    }

    public class backgroundLoadListView extends
            AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            auto_list.setAdapter(cv);


            Toast.makeText(Rider_activity.this,
                    "onPostExecute \n: setListAdapter after bitmap preloaded",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            Toast.makeText(Rider_activity.this, "onPreExecute \n: preload bitmap in AsyncTask",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            //  preLoadSrcBitmap();
            cv = new Custom_listview(Rider_activity.this);

            return null;
        }

    }


    void getLocation() {


        GPS_track gpsTracker = new GPS_track(this);

        if (gpsTracker.getIsGPSTrackingEnabled()) {

            latitude = gpsTracker.latitude;


//            textview = (TextView)findViewById(R.id.fieldLatitude);
//            textview.setText(stringLatitude);

            longitude = gpsTracker.longitude;
//            textview = (TextView)findViewById(R.id.fieldLongitude);
//            textview.setText(stringLongitude);

         state_name = gpsTracker.getCountryName(this);
//            textview = (TextView)findViewById(R.id.fieldCountry);
//            textview.setText(country);

           city_name= gpsTracker.getLocality(this);
//            textview = (TextView)findViewById(R.id.fieldCity);
//            textview.setText(city);

            String postalCode = gpsTracker.getPostalCode(this);
//            textview = (TextView)findViewById(R.id.fieldPostalCode);
//            textview.setText(postalCode);

           place_name = gpsTracker.getAddressLine(this);
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map2);
            mapFragment.getMapAsync(Rider_activity.this);
            driverSearch_snackbar("Founded no driver nearby","REFRESH");

//            textview = (TextView)findViewById(R.id.fieldAddressLine);
//            textview.setText(addressLine);
            try {
               // Search_editText.setText("dddd");


            }catch (Exception e)
            {

            }

         //  Toast.makeText(Rider_activity.this, "City :" + city + "country" + country, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();

        }

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.

                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    void snackbar(String message,String button_title) {
        try {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(button_title, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //getLocation();
                            listView_layout.setVisibility(View.GONE);
                            driverSearch_snackbar("Founded no driver nearby","REFRESH");
//                            Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "AAA", Snackbar.LENGTH_SHORT);
//                            snackbar1.show();
                        }
                    });

            snackbar.show();
        }catch (Exception e)
        {
            e.getMessage();
        }
    }

    void driverSearch_snackbar(String message,String button_title) {
        try {
            final Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(button_title, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            listView_layout.setVisibility(View.VISIBLE);
                           snackbar("Found 2 driver","Back");

                        }
                    });

            snackbar.show();
        }catch (Exception e)
        {
            e.getMessage();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
       // snackbar("Update the location","Yes");
      //  getLocation();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        snackbar("Update the location","Yes");
        getLocation();
    }
}
