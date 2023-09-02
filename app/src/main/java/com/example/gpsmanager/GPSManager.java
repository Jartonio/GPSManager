package com.example.gpsmanager;




import static android.content.Context.LOCATION_SERVICE;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GnssAntennaInfo;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;

import java.util.List;


public class GPSManager {

    private final Context mContext;
    private final LocationManager mLocationManager;
    private final LocationListener mLocationListener;

    public GPSManager(Context context) {
        mContext = context;
        mLocationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @RequiresApi(api = Build.VERSION_CODES.S)
            @Override
            public void onLocationChanged(Location location) {
                // Acción a realizar cuando la ubicación cambia
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                MainActivity mainActivity = (MainActivity) context;
                TextView miDisplay = mainActivity.findViewById(R.id.display);
                miDisplay.setText(mainActivity.getString(R.string.latitud) + latitude + "\n"+mainActivity.getString(R.string.longitud) + longitude +"\nPrecisión: "+  location.getAccuracy());
                Toast.makeText(mContext, "Obtenidos datos", Toast.LENGTH_LONG).show();
                Log.d("prueba", ""+R.string.latitud+ latitude + " - "+  R.string.longitud +" - "+ longitude+ " - "+ location.getAccuracy());



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