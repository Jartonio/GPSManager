package com.example.gpsmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private GPSManager mGPSManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGPSManager = new GPSManager(this);
        Location location = mGPSManager.getCurrentLocation();

        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Toast.makeText(this, "Latitud: " + latitude + "\nLongitud: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "No se pudo obtener la ubicación", Toast.LENGTH_LONG).show();
        }
    }
}