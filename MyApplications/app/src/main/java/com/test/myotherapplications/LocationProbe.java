package com.test.myotherapplications;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


/**
 * Created by tian on 2014/11/5.
 */

public class LocationProbe implements Probe {
    private static final String NETWORK_PROVIDER = LocationManager.NETWORK_PROVIDER;
    private static final String GPS_PROVIDER = LocationManager.GPS_PROVIDER;
    private static final String TEST_PROVIDER = "TEST_PROVIDER";

    private Context context;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private String provider = GPS_PROVIDER;
    private String data;

    public LocationProbe() {
    }

    public LocationProbe(Context context) {
        this();
        this.context = context;
    }

    private DataListener dataListener ;

    @Override
    public void registerListener(DataListener listener) {
        if (listener != null) {
            dataListener = listener;
        }
    }

    @Override
    public void unRegisterListener() {
        dataListener = null;
    }

    //just for test
    @Override
    public String sendData() {
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (mLocationManager.getProvider(GPS_PROVIDER) != null &&
                mLocationManager.isProviderEnabled(GPS_PROVIDER))
            provider = GPS_PROVIDER;
        else if (mLocationManager.getProvider(NETWORK_PROVIDER) != null &&
                mLocationManager.isProviderEnabled(NETWORK_PROVIDER))
            provider = NETWORK_PROVIDER;

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                data = "Latitude: " + String.valueOf(location.getLatitude()) + "\n"
                        + "Longitude: " + String.valueOf(location.getLongitude()) + "\n"
                        + "Altitude: " + String.valueOf(location.getAltitude()) + "\n"
                        + "Time: " + String.valueOf(location.getTime());
            }
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }
            @Override
            public void onProviderEnabled(String s) {
            }
            @Override
            public void onProviderDisabled(String s) {
            }
        };
        mLocationManager.requestLocationUpdates(provider, 0, 0, mLocationListener);
        return data;
    }

   /* //@Override
    public void sendDataData() {
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (mLocationManager.getProvider(GPS_PROVIDER) != null &&
                mLocationManager.isProviderEnabled(GPS_PROVIDER))
            provider = GPS_PROVIDER;
        else if (mLocationManager.getProvider(NETWORK_PROVIDER) != null &&
                mLocationManager.isProviderEnabled(NETWORK_PROVIDER))
            provider = NETWORK_PROVIDER;

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                data = "Latitude: " + String.valueOf(location.getLatitude()) + "\n"
                        + "Longitude: " + String.valueOf(location.getLongitude()) + "\n"
                        + "Altitude: " + String.valueOf(location.getAltitude()) + "\n"
                        + "Time: " + String.valueOf(location.getTime());
                if (dataListener != null) {
                    dataListener.onDataReceived(data);
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
            }
        };
        mLocationManager.requestLocationUpdates(provider, 0, 0, mLocationListener);
    }*/
}














































