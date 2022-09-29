package com.bit.balade.GpsActivity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bit.balade.LoginActivity.MemberVO;
import com.bit.balade.R;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GpsActivity extends FragmentActivity implements OnMapReadyCallback {


    private Retrofit retrofit;
    private ServiceApi service;
    private void setRetrofitInit(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://3.34.1.154:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ServiceApi.class);


    }


    private void sendLatLng(Double a, Double b){
        String lat = a.toString();
        String lng = b.toString();


        Call<GpsVo> call = service.sendLatLng(lng, lat);

        call.enqueue(new Callback<GpsVo>() {
            @Override
            public void onResponse(Call<GpsVo> call, Response<GpsVo> response) {
                GpsVo gpsVo = response.body();
            }

            @Override
            public void onFailure(Call<GpsVo> call, Throwable t) {
            }
        });
    }

    private void sendStart(String s){
        String user_no = s;
        Call<GpsVo> call = service.sendStart(user_no);
        call.enqueue(new Callback<GpsVo>() {
            @Override
            public void onResponse(Call<GpsVo> call, Response<GpsVo> response) {
                GpsVo gpsVo = response.body();
            }

            @Override
            public void onFailure(Call<GpsVo> call, Throwable t) {

            }
        });
    }

    private void sendEnd(String s, float f){
        String user_no = s;
        String distance = String.valueOf(f);
        Call<GpsVo> call = service.sendEnd(user_no, distance);
        call.enqueue(new Callback<GpsVo>() {
            @Override
            public void onResponse(Call<GpsVo> call, Response<GpsVo> response) {
                GpsVo gpsVo = response.body();
            }

            @Override
            public void onFailure(Call<GpsVo> call, Throwable t) {

            }
        });
    }

    private static final int PRIORITY_HIGH_ACCURACY = 100;
    private GoogleMap mMap;
    private static final int PERMISSIONS_FINE_LOCATION = 99;

    Location loc;
    boolean state = false;
    private ArrayList<Location> myPath = new ArrayList<Location>();

    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;




    Button rstart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setRetrofitInit();

        locationRequest = LocationRequest.create()
                .setInterval(8000)
                .setPriority(PRIORITY_HIGH_ACCURACY)
                .setSmallestDisplacement(5);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(GpsActivity.this);

        Intent intent = getIntent();
        MemberVO memberVO = intent.getParcelableExtra("memberVO");
        String userNo = memberVO.getUser_no();



        rstart = findViewById(R.id.rstart);
        rstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(userNo);
            }
        });
        Log.d(TAG, "askforPermission");
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "OnMapReady");
        askforPermission();
        mMap = googleMap;
        if(checkPermission()) {
            mMap.setMyLocationEnabled(true);
        }
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

    }

    // Methods

    private void askforPermission(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){

            fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null);
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);

            }
        }
    }

    private boolean checkPermission() {
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED ) {
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSIONS_FINE_LOCATION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    askforPermission();
                }else{
                    Toast.makeText(this,"저희 앱은 위지 정보 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    public void setCurrentLocation(Location location) {

        LatLng startLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(startLatLng, 15);
        mMap.moveCamera(cameraUpdate);
        //mMap.addMarker(new MarkerOptions().position(currentLatLng));  //확인용
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);
            List<Location> savedLocation = locationResult.getLocations();
            if(savedLocation.size() > 0){
                loc = savedLocation.get(savedLocation.size() - 1);
                setCurrentLocation(loc);
            }
        }
    };
    LocationCallback polyCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);

            List<Location> savedLocation = locationResult.getLocations();
            if(savedLocation.size() > 0){
                loc = savedLocation.get(savedLocation.size() - 1);
                setCurrentLocation(loc);
                double sendLat = loc.getLatitude();
                double sendLng = loc.getLongitude();
                sendLatLng(sendLat, sendLng);

                // Stackflow AagitoEx source
                myPath.add(loc);
                updateMap();
            }
        }
    };

    float totalDistance = 0;
    public void getDistance(){
        Location lastLoc;
        Location curLoc;
        if (myPath.size() > 2){
            for(int i=1 ; i < myPath.size() ; i++){
              lastLoc = myPath.get(i-1);
              curLoc = myPath.get(i);

              Log.d("Value of LastLoc", String.valueOf(lastLoc));
              Log.d("Value of curLoc", String.valueOf(curLoc));

              float distanceMeters = lastLoc.distanceTo(curLoc);
              float distanceKm = distanceMeters / 1000f;

              totalDistance = totalDistance+distanceKm;
            }
        }
    }

    //stackflow aagitoEx source
    private  LatLng polyLatLng;
    Polyline polyline;
    public void updateMap(){
        PolylineOptions polylineOptions = new PolylineOptions();
        for(int i=0 ; i < myPath.size() ; i++){
            loc = myPath.get(i);
            polyLatLng = new LatLng(loc.getLatitude(), loc.getLongitude());
            polylineOptions.add(polyLatLng);

        }
        polylineOptions.width(10f)
                .color(Color.RED)
                .geodesic(true);
        if(mMap != null) {
          polyline =  mMap.addPolyline(polylineOptions);
        }
    }

    public void changeState(String s) {
        if(!state){
            mMap.clear();
            if(myPath != null){
                myPath.clear();
            }
            state = true;
            sendStart(s);
            if(state && checkPermission()){
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, polyCallback, Looper.myLooper());
                addMarkerSingle();
            }
            rstart.setText("종료");
        }else{
            state = false;
            if(!state) {
                addMarkerSingle();
                fusedLocationProviderClient.removeLocationUpdates(polyCallback);
            }
            getDistance();
            sendEnd(s, totalDistance);
            rstart.setText("시작");
        }
    }

    public void addMarkerSingle(){
        if(checkPermission()){
            Log.d(TAG, "100");
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    Log.d(TAG, String.valueOf(location));
                    double markerLat = location.getLatitude();
                    double markerLong = location.getLongitude();
                    LatLng startEnd = new LatLng(markerLat, markerLong);

                    mMap.addMarker(new MarkerOptions().position(startEnd));
                }
            });
        }
    }
}