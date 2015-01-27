package com.taabe.locationtest.location;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LocationActivity extends Activity {
    private static final String TAG = "LocationActivity";
    private static final String TEST_PROVIDER = "TEST_PROVIDER";
    private static final String NETWORK_PROVIDER = "network";
    private static final String GPS_PROVIDER = "gps";

    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private String provider = GPS_PROVIDER;

    private Button mStartBtn;
    private Button mStopBtn;
    private TextView mLatitude;
    private TextView mLongitude;
    private TextView mAltitude;
    private TextView mTimestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Log.i(TAG, "onCreate() called!");
        mLatitude = (TextView) findViewById(R.id.latitude);
        mLongitude = (TextView) findViewById(R.id.longitude);
        mAltitude = (TextView) findViewById(R.id.altitude);
        mTimestamp = (TextView) findViewById(R.id.timestamp);
        mStartBtn = (Button) findViewById(R.id.startBtn);
        mStopBtn = (Button) findViewById(R.id.stopBtn);


        //获取LocationManager系统服务
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(mLocationManager.getProvider(GPS_PROVIDER) != null &&
                mLocationManager.isProviderEnabled(GPS_PROVIDER))
            provider = GPS_PROVIDER;
        else if(mLocationManager.getProvider(NETWORK_PROVIDER) != null &&
                mLocationManager.isProviderEnabled(NETWORK_PROVIDER))
            provider = NETWORK_PROVIDER;
        else if(mLocationManager.getProvider(TEST_PROVIDER) != null &&
                mLocationManager.isProviderEnabled(TEST_PROVIDER))
            provider = TEST_PROVIDER;
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i(TAG, "onLocationChanged() called");
                if(location != null) {
                    mLatitude.setText(String.valueOf(location.getLatitude()));
                    mLongitude.setText(String.valueOf(location.getLongitude()));
                    mAltitude.setText(String.valueOf(location.getAltitude()));
                    mTimestamp.setText(String.valueOf(location.getTime()));
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocationManager.requestLocationUpdates(provider, 0, 0, mLocationListener);
            }
        });

        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocationManager.removeUpdates(mLocationListener);
                updateUI();
            }
        });
    }

    private void updateUI() {
        mLatitude.setText("");
        mLongitude.setText("");
        mAltitude.setText("");
        mTimestamp.setText("");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i(TAG, "onPause() called!");
        mLocationManager.removeUpdates(mLocationListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.location, menu);
        return true;
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop() called!");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
