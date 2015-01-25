package com.test.myapplications;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.test.myotherapplications.R;

public class ProbeActivity extends ActionBarActivity implements Probe.DataListener {

    private static final String TAG = "ProbeActivity";
    TextView mInfo;
    TextView mData;
    Button mStartBtn;
    Button mStopBtn;
    Button mSaveBtn;
    private LocationProbe mLocationProbe;
    private BasicPipeline mBasicPipeline;
    private OtherPipeline mOtherPipeline;
    private FunfManager funfManager;
    private ServiceConnection funfManagerConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("ProbeActivity", "funfManagerConn");
            funfManager = ((FunfManager.LocalBinder) service).getFunfManager();
            funfManager.registerPipeline(mLocationProbe, mBasicPipeline);
            //funfManager.registerPipeline(mLocationProbe, mOtherPipeline);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            funfManager = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_probe);
        Log.i(TAG, "onCreate() called!");

        mLocationProbe = new LocationProbe(getApplicationContext());

        mBasicPipeline = new BasicPipeline(getApplicationContext());
        mOtherPipeline = new OtherPipeline(getApplicationContext());

        mInfo = (TextView) findViewById(R.id.textView);
        mData = (TextView) findViewById(R.id.data);
        mStartBtn = (Button) findViewById(R.id.startBtn);
        mStopBtn = (Button) findViewById(R.id.stopBtn);
        mSaveBtn = (Button) findViewById(R.id.saveBtn);

/*
        class ClickListener implements View.OnClickListener {
            @Override
            public void onClick(View v) {
            }
        }
        ClickListener clickListener = new ClickListener();
        mStartBtn.setOnClickListener(clickListener);
*/

        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocationProbe.registerListener(ProbeActivity.this);
                mLocationProbe.sendData();
                Toast.makeText(ProbeActivity.this, "started!", Toast.LENGTH_SHORT).show();
            }
        });

        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocationProbe.unRegisterListener();
                updateUI();
                Toast.makeText(ProbeActivity.this, "stopped!", Toast.LENGTH_SHORT).show();
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mLocationProbe.registerListener(mBasicPipeline);
                //bindService(new Intent(ProbeActivity.this, FunfManager.class), funfManagerConn, BIND_AUTO_CREATE);
                startService(new Intent(ProbeActivity.this, FunfManager.class));
                mLocationProbe.sendData();
                Toast.makeText(getBaseContext(), "informations have been saved to " +
                        getApplicationContext().getFilesDir() + "/", Toast.LENGTH_LONG).show();
            }
        });

        bindService(new Intent(this, FunfManager.class), funfManagerConn, BIND_AUTO_CREATE);

        //Bundle bundle = new Bundle();
        //bundle.putSerializable("mLocationProbe", mLocationProbe);
        //startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(funfManagerConn);
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
