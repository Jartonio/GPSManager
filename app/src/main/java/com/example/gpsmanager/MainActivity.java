package com.example.gpsmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    private GPSManager mGPSManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mGPSManager = new GPSManager(this);
        Location location = mGPSManager.getCurrentLocation();

    }


}