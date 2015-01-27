package com.test.myotherapplications;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


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

    private Set<DataListener> dataListeners = Collections.synchronizedSet(new HashSet<DataListener>());

    @Override
    public void registerListener(DataListener... listeners) {
        if (listeners != null) {
            for (DataListener listener : listeners)
                dataListeners.add(listener);
        }
    }

    @Override
    public void unRegisterListener() {
        DataListener[] listeners;
        synchronized (dataListeners) {
            listeners = new DataListener[dataListeners.size()];
            dataListeners.toArray(listeners);
            for (DataListener listener : listeners)
                dataListeners.remove(listener);
        }
    }

/*    @Override
    public void unRegisterListener() {
        DataListener[] listeners = null;
        synchronized (dataListeners) {
            listeners = new DataListener[dataListeners.size()];
            dataListeners.toArray(listeners);
        }
        if(listeners != null) {
            for (DataListener listener : listeners)
                dataListeners.remove(listener);
        }
    }*/

    @Override
    public void sendData() {
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (mLocationManager.getProvider(GPS_PROVIDER) != null &&
                mLocationManager.isProviderEnabled(GPS_PROVIDER))
            provider = GPS_PROVIDER;
        else if (mLocationManager.getProvider(TEST_PROVIDER) != null &&
                mLocationManager.isProviderEnabled(TEST_PROVIDER))
            provider = TEST_PROVIDER;
        else if (mLocationManager.getProvider(NETWORK_PROVIDER) != null &&
                mLocationManager.isProviderEnabled(NETWORK_PROVIDER))
            provider = NETWORK_PROVIDER;

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Date date = new Date(location.getTime());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                data = "\n"
                        + "Latitude: " + String.valueOf(location.getLatitude()) + "\n"
                        + "Longitude: " + String.valueOf(location.getLongitude()) + "\n"
                        + "Altitude: " + String.valueOf(location.getAltitude()) + "\n"
                        + "Time: " + formatter.format(date) + "\n";
                if (dataListeners != null) {
                    for (DataListener listener : dataListeners)
                        listener.onDataReceived(data);
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
    }
}














































