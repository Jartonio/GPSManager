package com.example.gpsmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView miText = findViewById(R.id.display);
        miText.setText(R.string.buscando_GPS);

        GPSManager mGPSManager = new GPSManager(this);

        //Location location =mGPSManager.getCurrentLocation();
        mGPSManager.getCurrentLocation();
    }
}