package com.example.currentsholat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class MuslimShalat extends AppCompatActivity {

    private static final String TAG = "tag";
    String url;
    String json_tag_obj = "json_obj_req";
    ProgressDialog pDialog;
    TextView mTvSubuh, mTvDzuhur, mTvAshar, mTvMagrib, mTvIsya, mTvLocation, mTvDate, textView;

    LocationManager locationManager;
    private static final int REQUEST_CODE = 101;
    String longitude, latitude;
    double lat, longi;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.muslimshalat);

        mTvSubuh = findViewById(R.id.textsbh);
        mTvDzuhur = findViewById(R.id.textdzh);
        mTvAshar = findViewById(R.id.textash);
        mTvMagrib = findViewById(R.id.textmgh);
        mTvIsya = findViewById(R.id.textisy);

        mTvLocation = findViewById(R.id.textloc);
        mTvDate = findViewById(R.id.textdate);
        textView = findViewById(R.id.btncity2);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(MuslimShalat.this, MainActivity.class));
                        break;
                    case R.id.sholat:
                        break;
                    case R.id.kiblat:
                        startActivity(new Intent(MuslimShalat.this, Compass.class));
                        break;
                }
                return false;
            }
        });

        searchLoc();

    }

    private void searchLoc() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Sabar Ini Cobaan");
        pDialog.show();

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (ActivityCompat.checkSelfPermission(
                MuslimShalat.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {

                lat = location.getLatitude();
                longi = location.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);

                try {
                    addresses = geocoder.getFromLocation(lat, longi, 1);

                    if (addresses != null && addresses.size() > 0) {

                        String city = addresses.get(0).getLocality();

                        url = "https://muslimsalat.com/"+city+".json?key=4f833da3e38b61e2099c8e7f67d2d654";
                        textView.setText(city);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String country = response.get("country").toString();
                            String state = response.get("state").toString();
                            String city = response.get("city").toString();
                            String location = country + ", " + state + ",  " + city;

                            String date = response.getJSONArray("items").getJSONObject(0).get("date_for").toString();

                            String subuh = response.getJSONArray("items").getJSONObject(0).get("fajr").toString();
                            String dzuhur = response.getJSONArray("items").getJSONObject(0).get("dhuhr").toString();
                            String ashar = response.getJSONArray("items").getJSONObject(0).get("asr").toString();
                            String maghrib = response.getJSONArray("items").getJSONObject(0).get("maghrib").toString();
                            String isya = response.getJSONArray("items").getJSONObject(0).get("isha").toString();

                            mTvSubuh.setText(subuh);
                            mTvDzuhur.setText(dzuhur);
                            mTvAshar.setText(ashar);
                            mTvMagrib.setText(maghrib);
                            mTvIsya.setText(isya);

                            mTvLocation.setText(location);
                            mTvDate.setText(date);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(MuslimShalat.this, "Error", Toast.LENGTH_SHORT).show();
                pDialog.hide();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest, json_tag_obj);

    }
}