package com.test.myotherapplications;

import android.content.Context;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by tian on 2014/11/5.
 */
public class SensorProbe implements Probe {

    private Context context;
    private SensorManager mSensorManager;
    private SensorEventListener mSensorEventListener;

    public SensorProbe(Context context) {
        this.context = context;
    }

    @Override
    public void registerListener(DataListener listener) {

    }

    @Override
    public void unRegisterListener() {

    }

    @Override
    public void sendData() {

    }
}
