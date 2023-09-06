package com.example.gpsmanager;


import static android.content.Context.LOCATION_SERVICE;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

public class GPSManager {

    private final Context mContext;
    private final LocationManager mLocationManager;
    private final LocationListener mLocationListener;
    private Boolean primerPaso = true;

    public GPSManager(Context context) {
        mContext = context;
        mLocationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);


        mLocationListener = new LocationListener() {
            @RequiresApi(api = Build.VERSION_CODES.S)
            @Override
            public void onLocationChanged(@NonNull Location location) {
                // Acción a realizar cuando la ubicación cambia
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                MainActivity mainActivity = (MainActivity) context;
                TextView miDisplay = mainActivity.findViewById(R.id.display);
                TextView miGrid = mainActivity.findViewById(R.id.grid);
                int precision = (int) location.getAccuracy();

                if (primerPaso) {
                    primerPaso = false;
                    miDisplay.setText(R.string.buscando_GPS);
                } else {
                    if (precision < R.integer.precision_minima) {
                        miDisplay.setText(mainActivity.getString(R.string.latitud) + latitude + "\n" + mainActivity.getString(R.string.longitud) + longitude + mainActivity.getString(R.string.precision) + precision);
                        Toast.makeText(mContext, R.string.datos_gps_correctos, Toast.LENGTH_LONG).show();

                        miGrid.setText("" + calcularGrid( latitude, longitude));
                        Log.d("grid", ""+miGrid.getText());
                    } else {
                        miDisplay.setText(R.string.precision_mala);
                        miGrid.setText("");
                    }
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // Acción a realizar cuando cambia el estado del proveedor de ubicación
            }


            @Override
            public void onProviderEnabled(@NonNull String provider) {
                // Acción a realizar cuando el proveedor de ubicación se habilita
                Toast.makeText(context, R.string.buscando_GPS, Toast.LENGTH_LONG).show();
                getCurrentLocation();
            }


            @Override
            public void onProviderDisabled(@NonNull String provider) {
                // Acción a realizar cuando el proveedor de ubicación se deshabilita
                Toast.makeText(context, R.string.GPS_desactivado, Toast.LENGTH_LONG).show();
            }
        };
    }

    public void getCurrentLocation() {
        //Location location = null;

        // Verificar si se tiene permiso para acceder al GPS
        if (mContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Obtener la última ubicación conocida
            //location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            // Escuchar cambios en la ubicación
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, mLocationListener);
        } else {
            MainActivity mainActivity = (MainActivity) this.mContext;
            TextView miDisplay = mainActivity.findViewById(R.id.display);
            miDisplay.setText(R.string.se_necesitan_permisos);
            Toast.makeText(mContext, R.string.no_permisos_GPS, Toast.LENGTH_LONG).show();
        }
        //return location;
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
        grid[0] = (char) ('A' + (int)lo);
        grid[1] = (char) ('A' + (int)la);

        lo=lor/2;
        lor=lor%2;
        la=lar/1;
        lar=lar%1;
        grid[2]= (char)('0' + (int)lo);
        grid[3]= (char)('0' + (int)la);

        lo=lor/0.083333333333;
        lor=lo%0.083333333333;
        la=lar/0.041666666666;
        lar=la%0.041666666666;
        grid[4]= (char)('A' + (int)lo);
        grid[5]= (char)('A' + (int)la);

        lo=lor/0.008333333333;
        lor=lo%0.008333333333;
        la=lar/0.004166666666;
        lar=la%0.004166666666;
        grid[6]= (char)('0' + (int)lo);
        grid[7]= (char)('0' + (int)la);

        lo=lor/0.000347222222;
        lor=lo%0.000347222222;
        la=lar/0.000173611111;
        lar=la%0.000173611111;
        grid[8]= (char)('A' + (int)lo);
        grid[9]= (char)('A' + (int)la);


        return new String(grid);
    }

}

