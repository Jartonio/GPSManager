package com.example.gpsmanager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

public class GPSManager {

    private Context mContext;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    public GPSManager(Context context) {
        mContext = context;
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Acción a realizar cuando la ubicación cambia
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    Toast.makeText(context.getApplicationContext(), "Latitud: " + latitude + "\nLongitud: " + longitude, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context.getApplicationContext(), "No se pudo obtener la ubicación", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // Acción a realizar cuando cambia el estado del proveedor de ubicación

            }

            @Override
            public void onProviderEnabled(String provider) {
                // Acción a realizar cuando el proveedor de ubicación se habilita
            }

            @Override
            public void onProviderDisabled(String provider) {
                // Acción a realizar cuando el proveedor de ubicación se deshabilita
            }
        };
    }

    public Location getCurrentLocation() {
        Location location = null;

        // Verificar si se tiene permiso para acceder al GPS
        if (mContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Obtener la última ubicación conocida
            location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            // Escuchar cambios en la ubicación
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, mLocationListener);
        }else {
            Toast.makeText(mContext, "No tengo permiso de GPS", Toast.LENGTH_SHORT).show();
        }

        return location;
    }


}