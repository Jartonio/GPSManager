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
        //https://f5len.org/tools/locator/ enci,a del numero 21 del portal  IN73BI64Wv
        Log.d("grid", calcularGrid(43.35383448790477,-5.85973083972931));


    }

    public String calcularGrid(double latitude, double longitude) {

        double lonIndex = ((longitude + 180) );
        double latIndex =  ((latitude + 90) );

        char[] grid = new char[6];

        double lo,la,lor,lar;

        lo=lonIndex/20;
        lor=lonIndex%20;
        la=latIndex/10;
        lar=latIndex%10;

        grid[0] = (char) ('A' + (int)lo);
        grid[1] = (char) ('A' + (int)la);

        lo=lor/2;
        lor=lo%2;
        la=lar/1;
        lar=la%1;
        grid[2]= (char)('0' + (int)lo);
        grid[3]= (char)('0' + (int)la);


        lo=lor/0.08333333;
        lor=lo%0.08333333;
        Log.d("grid", ""+lo);
        la=lar/0.04166666;
        lar=la%0.04166666;
        grid[4]= (char)('a' + (int)lo);
        grid[5]= (char)('a' + (int)la);









        return new String(grid);
    }
}