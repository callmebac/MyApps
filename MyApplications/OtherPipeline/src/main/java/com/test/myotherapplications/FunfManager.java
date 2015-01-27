package com.test.myotherapplications;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.test.myotherapplications.Probe.DataListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tian on 2014/11/20.
 */
public class FunfManager extends Service {

    private Probe mLocationProbe;
    private Pipeline mBasicPipeline;
    private Map<String, Pipeline> pipelines;

    @Override
    public IBinder onBind(Intent intent) {
        return new LocalBinder();
    }

    public class LocalBinder extends Binder {
        public FunfManager getFunfManager() {
            return FunfManager.this;
        }
    }

    public void registerPipeline(Probe probe, Pipeline pipeline) {
        this.mLocationProbe = probe;
        this.mBasicPipeline = pipeline;
        pipeline.onCreate(this);
    }

    class DataRequestInfo {
        private DataListener listener;
    }

    Map<String, List<DataRequestInfo>> dataRequests;

    public void requestData(DataListener listener, String probeName) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener cannot be null");
        }
        if (probeName == null) {
            throw new IllegalArgumentException("Probe cannot be null");
        }

        DataRequestInfo newDataRequest = new DataRequestInfo();
        newDataRequest.listener = listener;
        List<DataRequestInfo> requests = dataRequests.get(probeName);
        if (requests == null) {
            requests = new ArrayList<DataRequestInfo>();
            dataRequests.put(probeName, requests);
        }
        requests.add(newDataRequest);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("FunfManager.java", "FunfManager onCreate()");
        this.dataRequests = new HashMap<String, List<DataRequestInfo>>();
        this.pipelines = new HashMap<String, Pipeline>();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("FunfManager.java", "FunfManager onStartCommand()");
        if (mLocationProbe != null && mBasicPipeline != null) {
            mLocationProbe.registerListener((OtherPipeline) mBasicPipeline);
        }
        return START_STICKY;
    }

}