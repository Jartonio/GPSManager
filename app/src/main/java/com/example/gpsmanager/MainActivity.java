package com.example.gpsmanager;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

        //43.442402454187174, -5.854524152416852


        String grid =calcularGrid(43.442402454187174,-5.854524152416852);
        Log.d("prueba", "Coordenadas enviadas: 43.442402454187174, -5.854524152416852");
        Log.d("prueba", "grid: "+grid);

        double longitud=calcularLongitud(grid);
        double latitud=calcularLatitud(grid);

    }
    public double calcularLongitud(String grid){

         char[] miGrid = grid.toCharArray();
         double lo0,lo2,lo4,lo6,lo8;


         double longitud,latitud;

        lo0=(miGrid[0] - 'A')*20;
        lo2=(miGrid[2] - '0')*2;
        lo4=(miGrid[4] - 'A')*0.083333333333;
        lo6=(miGrid[6] - '0')*0.008333333333;
        lo8=(miGrid[8] - 'A')*0.000347222222;
        longitud=lo0+lo2+lo4+lo6+lo8-180;


        Log.d("prueba", "Longitud calculada: "+longitud);
        return longitud;

    }
    public double calcularLatitud (String grid){

        char[] miGrid = grid.toCharArray();
        double la1,la3,la5,la7,la9;
        double latitud;

        la1=(miGrid[1] - 'A')*10;
        la3=(miGrid[3] - '0')*1;
        la5=(miGrid[5] - 'A')*0.041666666666;
        la7=(miGrid[7] - '0')*0.004166666666;
        la9=(miGrid[9] - 'A')*0.000173611111;
        latitud=la1+la3+la5+la7+la9-90;
        Log.d("prueba", "Latitud calculada: "+latitud);

        return (latitud);
    }
    public String calcularGrid(double latitude, double longitude) {


        double lonIndex = longitude + 180;
        double latIndex = latitude + 90;

        char[] grid = new char[10];

        double lo,la,lor,lar;


        lo=lonIndex/20;
        lor=lonIndex%20;
        la=latIndex/10;
        lar=latIndex%10;
        grid[0] = (char) ('A' + lo);
        grid[1] = (char) ('A' + la);

        lo=lor/2;
        lor=lor%2;
        la=lar/1;
        lar=lar%1;
        grid[2]= (char)('0' + lo);
        grid[3]= (char)('0' + la);

        lo=lor/0.083333333333;
        lor=lo%0.083333333333;
        la=lar/0.041666666666;
        lar=la%0.041666666666;
        grid[4]= (char)('A' + lo);
        grid[5]= (char)('A' + la);

        lo=lor/0.008333333333;
        lor=lo%0.008333333333;
        la=lar/0.004166666666;
        lar=la%0.004166666666;
        grid[6]= (char)('0' + lo);
        grid[7]= (char)('0' + la);

        lo=lor/0.000347222222;
        lor=lo%0.000347222222;
        la=lar/0.000173611111;
        lar=la%0.000173611111;
        grid[8]= (char)('A' + lo);
        grid[9]= (char)('A' + la);

        return new String(grid);
    }
}