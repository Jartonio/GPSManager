package com.example.gpsmanager;

import static android.app.PendingIntent.getActivity;

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

import androidx.annotation.LongDef;
import androidx.annotation.RequiresApi;

public class GPSManager {

    private Context mContext;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    public GPSManager(Context context) {
        mContext = context;
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onLocationChanged(Location location) {
                // Acción a realizar cuando la ubicación cambia
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                MainActivity mainActivity = (MainActivity) context;
                TextView miDisplay = mainActivity.findViewById(R.id.display);
                miDisplay.setText("Latitud: " + latitude + "\nLongitud: " + longitude);
                Toast.makeText(mContext, "Latitud: " + latitude + "\nLongitud: " + longitude, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // Acción a realizar cuando cambia el estado del proveedor de ubicación
            }

            @Override
            public void onProviderEnabled(String provider) {
                // Acción a realizar cuando el proveedor de ubicación se habilita
                Toast.makeText(context, "Buscando señal GPS.....", Toast.LENGTH_LONG).show();
                getCurrentLocation();
            }

            @Override
            public void onProviderDisabled(String provider) {
                // Acción a realizar cuando el proveedor de ubicación se deshabilita
                Toast.makeText(context, "El GPS está desactivado.", Toast.LENGTH_LONG).show();
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
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, mLocationListener);
        } else {
            MainActivity mainActivity = (MainActivity) this.mContext;
            TextView miDisplay = mainActivity.findViewById(R.id.display);
            miDisplay.setText("SE NECESITAN PERMISOS DE GPS.\n\nActive el permiso de GPS\ny\nvuelva a iniciar la aplicación.");
            Toast.makeText(mContext, "NO TIENE PERMISOS GPS", Toast.LENGTH_LONG).show();
        }
        return location;
    }


}