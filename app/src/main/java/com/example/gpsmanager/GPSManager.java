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

    private Boolean primerPaso=true;


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
                int precision= (int)location.getAccuracy();

                if (primerPaso){
                    primerPaso=false;
                    miDisplay.setText(R.string.buscando_GPS);
                }else {
                    if (precision < R.integer.precision_minima) {
                        miDisplay.setText(mainActivity.getString(R.string.latitud) + latitude + "\n" + mainActivity.getString(R.string.longitud) + longitude + mainActivity.getString(R.string.precision) + precision);
                        Toast.makeText(mContext, R.string.datos_gps_correctos, Toast.LENGTH_LONG).show();
                        miGrid.setText(""+calcularGrid(latitude,longitude));
                    } else {
                        miDisplay.setText(R.string.precision_mala);
                        miGrid.setText("");
                    }
                    Log.d("prueba", "Latitud: " + latitude + " Longuitud: " + longitude + " Precision: " + precision);
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
 /*
    int latIndex1 = (int) Math.floor((latitude + 90) / 10);
    int lonIndex1 = (int) Math.floor((longitude + 180) / 20);
    int latIndex2 = (int) Math.floor(((latitude + 90) % 10) / 2);
    int lonIndex2 = (int) Math.floor(((longitude + 180) % 20) / 2);
    int latIndex3 = (int) Math.floor(((latitude + 90) % 2) / (5.0 / 60));
    int lonIndex3 = (int) Math.floor(((longitude + 180) % 2) / (5.0 / 60));
    int latIndex4 = (int) Math.floor((((latitude + 90) % 2) % (5.0 / 60)) / (2.5 / 60));
    int lonIndex4 = (int) Math.floor((((longitude + 180) % 2) % (5.0 / 60)) / (5.0 / 60));
    char[] locator = new char[8];
    locator[0] = (char) ('A' + lonIndex1);
    locator[1] = (char) ('A' + latIndex1);
    locator[2] = (char) ('0' + lonIndex2);
    locator[3] = (char) ('0' + latIndex2);
    locator[4] = (char) ('a' + lonIndex3);
    locator[5] = (char) ('a' + latIndex3);
    locator[6] = (char) ('0' + lonIndex4);
    locator[7] = (char) ('0' + latIndex4);

    int latIndex1 = (int) Math.floor((latitude + 90) / 10);
    int lonIndex1 = (int) Math.floor((longitude + 180) / 20);
    int latIndex2 = (int) Math.floor(((latitude + 90) % 10) / 2);
    int lonIndex2 = (int) Math.floor(((longitude + 180) % 20) / 2);
    int latIndex3 = (int) Math.floor(((latitude + 90) % 2) / (5.0 / 60));
    int lonIndex3 = (int) Math.floor(((longitude + 180) % 2) / (5.0 / 60));
    int latIndex4 = (int) Math.floor((((latitude + 90) % 2) % (5.0 / 60)) / (2.5 / 60));
    int lonIndex4 = (int) Math.floor((((longitude + 180) % 2) % (5.0 / 60)) / (5.0 / 60));
    int latIndex5 = (int) Math.floor((((latitude + 90) % (5.0 / 60)) % (2.5 / 60)) / ((1.0 / 60)));
    int lonIndex5 = (int) Math.floor((((longitude + 180) % (5.0 / 60)) % (5.0 / 60)) / ((2.0 / 60)));
    char[] locator = new char[10];
    locator[0] = (char) ('A' + lonIndex1);
    locator[1] = (char) ('A' + latIndex1);
    locator[2] = (char) ('0' + lonIndex2);
    locator[3] = (char) ('0' + latIndex2);
    locator[4] = (char) ('a' + lonIndex3);
    locator[5] = (char) ('a' + latIndex3);
    locator[6] = (char) ('0' + lonIndex4);
    locator[7] = (char) ('0' + latIndex4);
    locator[8] = (char) ('A' + lonIndex5);
    locator[9] = (char) ('A' + latIndex5);

    int latIndex1 = (int) Math.floor((latitude + 90) / 10);
    int lonIndex1 = (int) Math.floor((longitude + 180) / 20);
    int latIndex2 = (int) Math.floor(((latitude + 90) % 10) / 2);
    int lonIndex2 = (int) Math.floor(((longitude + 180) % 20) / 2);
    int latIndex3 = (int) Math.floor(((latitude + 90) % 2) / (5.0 / 60));
    int lonIndex3 = (int) Math.floor(((longitude + 180) % 2) / (5.0 / 60));
    int latIndex4 = (int) Math.floor((((latitude + 90) % 2) % (5.0 / 60)) / (2.5 / 60));
    int lonIndex4 = (int) Math.floor((((longitude + 180) % 2) % (5.0 / 60)) / (5.0 / 60));
    int latIndex5 = (int) Math.floor((((latitude + 90) % (5.0 / 60)) % (2.5 / 60)) / ((1.0 / 60)));
    int lonIndex5 = (int) Math.floor((((longitude + 180) % (5.0 / 60)) % (5.0 / 60)) / ((2.0 / 60)));
    int latIndex6 = (int) Math.floor((((latitude + 90) % (1.0 / 60)) % ((1.0 / 24))) / ((1.0 / (24 * 10))));
    int lonIndex6 = (int) Math.floor((((longitude + 180) % (2.0 / 60)) % ((1.0 / 12))) / ((1.0 / (12 * 10))));
    char[] locator = new char[12];
    locator[0] = (char) ('A' + lonIndex1);
    locator[1] = (char) ('A' + latIndex1);
    locator[2] = (char) ('0' + lonIndex2);
    locator[3] = (char) ('0' + latIndex2);
    locator[4] = (char) ('a' + lonIndex3);
    locator[5] = (char) ('a' + latIndex3);
    locator[6] = (char) ('0' + lonIndex4);
    locator[7] = (char) ('0' + latIndex4);
    locator[8] = (char) ('A' + lonIndex5);
    locator[9] = (char) ('A' + latIndex5);
    locator[10] = (char) ('0' + lonIndex6);
    locator[11] = (char) ('0' + latIndex6);

*/
    int lonIndex = (int) ((longitude + 180) / 20);
    int latIndex = (int) ((latitude + 90) / 10);

    char[] locator = new char[6];
    locator[0] = (char) ('A' + lonIndex);
    locator[1] = (char) ('A' + latIndex);
    locator[2] = (char) ('0' + ((longitude + 180) % 20) / 2);
    locator[3] = (char) ('0' + (latitude + 90) % 10);
    locator[4] = (char) ('a' + ((longitude + 180) % 2) * 12);
    locator[5] = (char) ('a' + ((latitude + 90) % 1) * 24);
/*
    int lonIndex = (int) ((longitude + 180) / (360.0 / 10));
    int latIndex = (int) ((latitude + 90) / (180.0 / 10));

    char[] locator = new char[10];
    locator[0] = (char) ('A' + lonIndex / 10);
    locator[1] = (char) ('A' + latIndex / 10);
    locator[2] = (char) ('0' + (lonIndex % 10));
    locator[3] = (char) ('0' + (latIndex % 10));
    locator[4] = (char) ('a' + ((longitude + 180) % (360.0 / 10)) / (360.0 / 10) * 24);
    locator[5] = (char) ('a' + ((latitude + 90) % (180.0 / 10)) / (180.0 / 10) * 24);
    locator[6] = (char) ('0' + ((longitude + 180) % ((360.0 / 10) / 24)) / ((360.0 / 10) / 24) * 24);
    locator[7] = (char) ('0' + ((latitude + 90) % ((180.0 / 10) / 24)) / ((180.0 / 10) / 24) * 24);
    locator[8] = (char) ('a' + ((longitude + 180) % (((360.0 / 10) / 24) / 24)) / (((360.0 / 10) / 24) / 24) * 24);
    locator[9] = (char) ('a' + ((latitude + 90) % (((180.0 / 10) / 24) / 24)) / (((180.0 / 10) / 24) / 24) * 24);

*/




    return new String(locator);
    }

}

