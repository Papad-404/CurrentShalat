package com.example.currentsholat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MuslimShalat extends AppCompatActivity {

    private static final String TAG = "tag";
    String url;
    String json_tag_obj = "json_obj_req";
    ProgressDialog pDialog;
    TextView mTvSubuh, mTvDzuhur, mTvAshar, mTvMagrib, mTvIsya, mTvLocation, mTvDate, textView;
    Button mSrc;
    String mEdi;


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
        textView = findViewById(R.id.edtxt);

        Intent intent = getIntent();
        textView.setText(intent.getStringExtra("mycity"));
        mEdi = String.valueOf(intent.getStringExtra("mycity"));


        url = "https://muslimsalat.com/"+mEdi+".json?key=4f833da3e38b61e2099c8e7f67d2d654";
        searchLoc();

    }

    private void searchLoc() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Apaan ini weh");
        pDialog.show();

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