package com.example.gpsmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private GPSManager mGPSManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView miText = findViewById(R.id.display);
        miText.setText("Esperando datos del GPS....");

        mGPSManager = new GPSManager(this);
        Location location = mGPSManager.getCurrentLocation();


    }

}