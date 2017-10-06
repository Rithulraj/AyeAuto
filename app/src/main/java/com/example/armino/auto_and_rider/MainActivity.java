package com.example.armino.auto_and_rider;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.view.ViewGroup.LayoutParams;

import Adapter_class.GPSTracker;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ListView auto_list;
    Button demo_up_button;
    RelativeLayout button_layout,title_layout;
    private boolean backpressLog=false;
    private boolean fabLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);
        auto_list=(ListView)findViewById(R.id.auto_listView);
        //demo_up_button=(Button)findViewById(R.id.button_1) ;
       // button_layout=(RelativeLayout)findViewById(R.id.relativeLayout) ;

        Custom_listview cv=new Custom_listview(this);


        auto_list.setAdapter(cv);

        ListView mListView = (ListView)   findViewById(R.id.auto_listView);
        LayoutParams list = (LayoutParams) mListView.getLayoutParams();
        list.height = 200;//like int  200
        GPSTracker gps=new GPSTracker(this);
        // Location loc= t.getLocation();

        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();

        // \n is for new line
        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

        // mListView.setLayoutParams(list);

//        button_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Animation bottomUp = AnimationUtils.loadAnimation(MainActivity.this,R.anim.bottom_up);
////
//              ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.hiden_layout_ID);
//
//
//                //fab_layout.startAnimation(bottomUp1);
//                // fab_layout.animate().translationY(0).start();
//                //fab_layout.animate().translationY(-300).start();
//                if(!backpressLog)
//
//                {
//                    //fab.setImageResource(R.drawable.ic_menu_send);
//                    //posting();
//                    backpressLog=true;
////                    bottomUp.setDuration(100);
////
////                    hiddenPanel.startAnimation(bottomUp);
//                    hiddenPanel.setVisibility(View.VISIBLE);
//                    button_layout.setVisibility(View.GONE);
//
//
//                    fabLog=false;
//                }
////                else
////                {
////                    Animation bottom_down = AnimationUtils.loadAnimation(MainActivity.this,
////                            R.anim.bottom_down);
////                    // ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.hiden_layout_ID);
////                    hiddenPanel.startAnimation(bottom_down);
////                    hiddenPanel.setVisibility(View.INVISIBLE);
////
////                    //Animation bottomUp1 = AnimationUtils.loadAnimation(Admis_voice.this,R.anim.fab_button_down);
////
////                    //fab_layout.startAnimation(bottomUp1);
////                    //fab_layout.animate().translationY(0).start();
////                    // fab.setImageResource(R.mipmap.mail_icon);
////                    fabLog=true;
////
////                    backpressLog=true;
////                }
//
//
//
//            }
//        });




//        Button gt_button=(Button)findViewById(R.id.map_direction_button_ID);
//        gt_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                double latitude = 11.2726;
//                double longitude = 75.7800;
//                String label = "Divor";
//                String uriBegin = "geo:" + latitude + "," + longitude;
//                String query = latitude + "," + longitude + "(" + label + ")";
//                String encodedQuery = Uri.encode(query);
//                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
//                Uri uri = Uri.parse(uriString);
//                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
//                startActivity(intent);
//            }
//        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(11.2726, 75.7800);
       // mMap.addMarker(new MarkerOptions().position(sydney).title("Here"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setMinZoomPreference(16);
        Marker marker=mMap.addMarker(new MarkerOptions()
        .position(sydney)
                .title("my position"));



    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {

//        if(backpressLog)
//        {
//
//            Animation bottom_down = AnimationUtils.loadAnimation(MainActivity.this,
//                    R.anim.bottom_down);
//
//
//            ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.hiden_layout_ID);
//            hiddenPanel.startAnimation(bottom_down);
//            hiddenPanel.setVisibility(View.INVISIBLE);
//
//
//            fabLog=true;
//
//            backpressLog=false;
//            button_layout.setVisibility(View.VISIBLE);
//        }
//        else {
            super.onBackPressed();

    }
}
