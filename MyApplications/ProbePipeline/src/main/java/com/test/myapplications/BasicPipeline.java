package com.test.myapplications;

import android.content.Context;

import com.test.myapplications.Probe.DataListener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tian on 2014/11/12.
 */
public class BasicPipeline implements Pipeline, DataListener {

    protected List<String> data = new ArrayList<String>();
    private Context mContext;
    private FunfManager manager;

    public BasicPipeline(Context context) {
        mContext = context;
    }

    public BasicPipeline(FunfManager manager) {
        this.manager = manager;
    }

    public void onCreate(FunfManager manager) {
        for (String dataRequest : data) {
            manager.requestData(this, dataRequest);
        }
    }

    @Override
    public void onDataReceived(String data) {
        writeData(data);
    }

    protected void writeData(String data) {
        String fileName = "BasicPipeline";
        File file = new File(mContext.getFilesDir(), fileName);
        FileOutputStream outputStream;
        try {
            outputStream = mContext.openFileOutput(fileName, Context.MODE_APPEND);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRun(String action) {

    }
}
