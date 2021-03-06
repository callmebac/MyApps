package com.test.myapplications;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.test.myapplications.Probe.DataListener;
import com.test.myotherapplications.R;

public class ProbeActivityBak extends ActionBarActivity implements DataListener {

    private static final String TAG = "ProbeActivityBak";

    LocationProbe locationProbe = new LocationProbe(getApplicationContext());

    TextView mInfo;
    TextView mData;
    Button mStartBtn;
    Button mStopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_probe);

        mInfo = (TextView) findViewById(R.id.textView);
        mData = (TextView) findViewById(R.id.data);
        mStartBtn = (Button) findViewById(R.id.startBtn);
        mStopBtn = (Button) findViewById(R.id.stopBtn);

        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationProbe.registerListener(ProbeActivityBak.this);
                locationProbe.sendData();
            }
        });

        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationProbe.unRegisterListener();
                updateUI();
            }
        });
    }

    private void updateUI() {
        mData.setText("");
    }

    @Override
    public void onDataReceived(String data) {
        mData.setText(data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.location, menu);
        return true;
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
