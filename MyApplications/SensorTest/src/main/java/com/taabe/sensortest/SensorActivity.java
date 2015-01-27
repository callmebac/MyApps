package com.taabe.sensortest;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
//android:layout_toRightOf="@id/versionLabel"
public class SensorActivity extends Activity implements SensorEventListener {

    private static final String TAG = "SensorActivity";
    private static final String KEY_INDEX = "index";
    private static final String THETA = "\u0398";
    private static final String ACCELERATION_UNITS = "m/s\u00B2";

    private SensorManager sensorManager;
    private Sensor sensor;
    //private Intent resultIntent;
    //private float gravity[] = new float[3];
    //private float linear_acceleration[] = new float[3];
    private Button mStartBtn;
    private Button mStopBtn;
    private TextView mInfoTextView;
    private Button mNextBtn;
    private Button mPrveBtn;
    private Button mConfigBtn;
    private List<Sensor> allSensors;

    private TextView nameLabel;
    private TextView name;
    private TextView typeLabel;
    private TextView type;
    private TextView maxRangeLabel;
    private TextView maxRange;
    private TextView minDelayLabel;
    private TextView minDelay;
    private TextView powerLabel;
    private TextView power;
    private TextView resolutionLabel;
    private TextView resolution;
    private TextView vendorLabel;
    private TextView vendor;
    private TextView versionLabel;
    private TextView version;
    private TextView accuracyLabel;
    private TextView accuracy;
    private TextView timestampLabel;
    private TextView timestamp;
    private TextView timestampUnits;
    private TextView dataLabel;
    private TextView dataUnits;
    private TextView xAxis;
    private TextView xAxisLabel;
    private TextView yAxis;
    private TextView yAxisLabel;
    private TextView zAxis;
    private TextView zAxisLabel;
    private TextView singleValue;
    private TextView cosLabel;
    private TextView cos;

    private Sensors[] mSensorsBank = new Sensors[]{
            new Sensors(R.string.sensor_accelerometer),
            new Sensors(R.string.sensor_gyroscope),
            new Sensors(R.string.sensor_light),
            new Sensors(R.string.sensor_magnetic_field),
            new Sensors(R.string.sensor_orientation),
            new Sensors(R.string.sensor_pressure),
            new Sensors(R.string.sensor_proximity),
            new Sensors(R.string.sensor_temperature),
    };

    /*    private void updateSensor(){
        int sSensor = mSensorsBank[mCurrentIndex].getSensor();
        mInfoTextView.setText(sSensor);
    }*/

    private int mCurrentIndex = 0;

/*    private Sensor[] mAllSensors = new Sensor[allSensors.size()];
    //Log.d(TAG, "Updating sensor text for sensor #" + mCurrentIndex, new Exception());
    private void updateSensor(){
        if (mAllSensors[0] == null){
            int i = 0;
            for (Sensor s : allSensors)
                mAllSensors[i++] = s;
        }
        String sSensor = mAllSensors[mCurrentIndex].getName();
        mInfoTextView.setText(sSensor + "'s information:");
    }*/

    private ArrayList<Sensor> mAllSensors = new ArrayList<Sensor>();

    private void updateSensor(){
        String sSensor = allSensors.get(mCurrentIndex).getName();
        mInfoTextView.setText(sSensor + "'s information:");
    }

    private void displayText()
    {
        nameLabel.setVisibility(View.VISIBLE);
        name.setVisibility(View.VISIBLE);
        int sensorType = allSensors.get(mCurrentIndex).getType();
        typeLabel.setVisibility(View.VISIBLE);
        type.setVisibility(View.VISIBLE);
        maxRangeLabel.setVisibility(View.VISIBLE);
        maxRange.setVisibility(View.VISIBLE);
        minDelayLabel.setVisibility(View.VISIBLE);
        minDelay.setVisibility(View.VISIBLE);
        powerLabel.setVisibility(View.VISIBLE);
        power.setVisibility(View.VISIBLE);
        resolutionLabel.setVisibility(View.VISIBLE);
        resolution.setVisibility(View.VISIBLE);
        vendorLabel.setVisibility(View.VISIBLE);
        vendor.setVisibility(View.VISIBLE);
        versionLabel.setVisibility(View.VISIBLE);
        version.setVisibility(View.VISIBLE);
        accuracyLabel.setVisibility(View.VISIBLE);
        accuracy.setVisibility(View.VISIBLE);
        timestamp.setVisibility(View.VISIBLE);
        timestampLabel.setVisibility(View.VISIBLE);
        timestampUnits.setVisibility(View.VISIBLE);
        if( sensorType == Sensor.TYPE_LIGHT || sensorType == Sensor.TYPE_PROXIMITY )
        {
            dataLabel.setVisibility(View.INVISIBLE);
            dataUnits.setVisibility(View.INVISIBLE);
            xAxis.setVisibility(View.INVISIBLE);
            yAxis.setVisibility(View.INVISIBLE);
            zAxis.setVisibility(View.INVISIBLE);
            singleValue.setVisibility(View.VISIBLE);
        }else {
            dataLabel.setVisibility(View.VISIBLE);
            dataUnits.setVisibility(View.VISIBLE);
            xAxis.setVisibility(xAxis.VISIBLE);
            xAxisLabel.setText("X Axis: ");
            yAxis.setVisibility(yAxis.VISIBLE);
            yAxisLabel.setText("Y Axis: ");
            zAxis.setVisibility(zAxis.VISIBLE);
            zAxisLabel.setText("Z Axis: ");
            singleValue.setVisibility(View.INVISIBLE);
        }
    }

    private void noDisplayText()
    {
        name.setVisibility(View.INVISIBLE);
        type.setVisibility(View.INVISIBLE);
        maxRange.setVisibility(View.INVISIBLE);
        minDelay.setVisibility(View.INVISIBLE);
        power.setVisibility(View.INVISIBLE);
        resolution.setVisibility(View.INVISIBLE);
        vendor.setVisibility(View.INVISIBLE);
        version.setVisibility(View.INVISIBLE);
        accuracy.setVisibility(View.INVISIBLE);
        timestampLabel.setVisibility(View.GONE);
        timestamp.setVisibility(View.GONE);
        timestampUnits.setVisibility(View.GONE);
        dataLabel.setVisibility(View.GONE);
        dataUnits.setVisibility(View.GONE);
        xAxisLabel.setVisibility(View.GONE);
        yAxisLabel.setVisibility(View.GONE);
        zAxisLabel.setVisibility(View.GONE);
        xAxis.setVisibility(View.GONE);
        yAxis.setVisibility(View.GONE);
        zAxis.setVisibility(View.GONE);
        singleValue.setText(null);
        singleValue.setVisibility(View.GONE);
        cosLabel.setVisibility(View.GONE);
        cos.setVisibility(View.GONE);
    }

    private void displaySensor(Sensor sensor) {
        name.setText(sensor.getName());
        type.setText(String.valueOf(sensor.getType()));
        maxRange.setText(String.valueOf(sensor.getMaximumRange()));
        minDelay.setText(String.valueOf(sensor.getMinDelay())); // Call requires API level 9
        power.setText(String.valueOf(sensor.getPower()));
        resolution.setText(String.valueOf(sensor.getResolution()));
        vendor.setText(String.valueOf(sensor.getVendor()));
        version.setText(String.valueOf(sensor.getVersion()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called");
        Sensor sensor;
        try {
            //sensor = mAllSensors.get(mCurrentIndex);
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            // Log a message at "error" log level, along with an exception
            Log.e(TAG, "Index was out of bounds", ex);
        }

        setContentView(R.layout.activity_sensor);

        mInfoTextView = (TextView)findViewById(R.id.textView);
        nameLabel = (TextView)findViewById(R.id.nameLabel);
        name = (TextView) findViewById(R.id.name);
        typeLabel = (TextView) findViewById(R.id.typeLabel);
        type = (TextView) findViewById(R.id.type);
        maxRangeLabel = (TextView) findViewById(R.id.maxRangeLabel);
        maxRange = (TextView) findViewById(R.id.maxRange);
        minDelayLabel = (TextView) findViewById(R.id.minDelayLabel);
        minDelay = (TextView) findViewById(R.id.minDelay);
        powerLabel = (TextView) findViewById(R.id.powerLabel);
        power = (TextView) findViewById(R.id.power);
        resolutionLabel = (TextView) findViewById(R.id.resolutionLabel);
        resolution = (TextView) findViewById(R.id.resolution);
        vendorLabel = (TextView) findViewById(R.id.vendorLabel);
        vendor = (TextView) findViewById(R.id.vendor);
        versionLabel = (TextView) findViewById(R.id.versionLabel);
        version = (TextView) findViewById(R.id.version);
        accuracyLabel = (TextView) findViewById(R.id.accuracyLabel);
        accuracy = (TextView) findViewById(R.id.accuracy);
        timestampLabel = (TextView) findViewById(R.id.timestampLabel);
        timestamp = (TextView) findViewById(R.id.timestamp);
        timestampUnits = (TextView) findViewById(R.id.timestampUnits);
        dataLabel = (TextView) findViewById(R.id.dataLabel);
        dataUnits = (TextView) findViewById(R.id.dataUnits);
        xAxisLabel = (TextView) findViewById(R.id.xAxisLabel);
        xAxis = (TextView) findViewById(R.id.xAxis);
        yAxisLabel = (TextView) findViewById(R.id.yAxisLabel);
        yAxis = (TextView) findViewById(R.id.yAxis);
        zAxisLabel = (TextView) findViewById(R.id.zAxisLabel);
        zAxis = (TextView) findViewById(R.id.zAxis);
        singleValue = (TextView) findViewById(R.id.singleValue);
        cosLabel = (TextView) findViewById(R.id.cosLabel);
        cos = (TextView) findViewById(R.id.cos);

        mNextBtn = (Button) findViewById(R.id.next_btn);
        mPrveBtn = (Button) findViewById(R.id.prev_btn);

        mStartBtn = (Button) findViewById(R.id.startBtn);
        mStopBtn = (Button) findViewById(R.id.stopBtn);

//        mConfigBtn = (Button) findViewById(R.id.configBtn);
        /*int sSensor = mSensorsBank[mCurrentIndex].getSenor();
        mInfoTextView.setText(sSensor);*/
/*        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mSensorsBank.length;
                int sSensor = mSensorsBank[mCurrentIndex].getSenor();
                mInfoTextView.setText(sSensor);
                updateSensor();
            }
        });*/

        //查看savedInstanceState是否为空，不为空则取出存储在其中的值
        if(savedInstanceState != null)
        {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        //为Next按钮设置点击事件监听器
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % allSensors.size();
                sensorManager.unregisterListener(SensorActivity.this);
                noDisplayText();
                updateSensor();
                resultIntent = null;
            }
        });

        //为Prve按钮设置点击事件监听器
        mPrveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + allSensors.size() - 1) % allSensors.size();
                sensorManager.unregisterListener(SensorActivity.this);
                noDisplayText();
                updateSensor();
                resultIntent = null;
                }
            });

        //初始化时隐藏不需要显示的文本控件
        noDisplayText();

        // 1、得到sensorManager对象
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        // 2、得到sensor
        //sensor = sensorManager
        //        .getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER);
        // 3、得到感应事件监听，通过注册实现

        //通过系统服务SENSOR_SERVICE取得sensorManager对象
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        //传入TYPE_ALL参数用来获取所有的传感器
        allSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayText();
                displaySensor(allSensors.get(mCurrentIndex));
                sensorManager.registerListener(SensorActivity.this, allSensors.get(mCurrentIndex),
                        SensorManager.SENSOR_DELAY_NORMAL);
                Toast.makeText(getBaseContext(), "started!", Toast.LENGTH_SHORT).show();
            }
        });

        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "stopped!", Toast.LENGTH_SHORT).show();
                sensorManager.unregisterListener(SensorActivity.this);
                noDisplayText();
                resultIntent = null;
            }
        });

        mConfigBtn = (Button) findViewById(R.id.configBtn);
        mConfigBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SensorActivity.this, ConfigActivity.class);
                int sensorType = allSensors.get(mCurrentIndex).getType();
                intent.putExtra(ConfigActivity.EXTRA_SENSOR_TYPR, sensorType);
                //startActivity(intent);
                startActivityForResult(intent, 0);
            }
        });
        updateSensor();
    }
    private Intent resultIntent;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        displayText();
        if(resultCode == RESULT_OK) {
            //resultIntent保存从ConfigActivity中传过来的Intent
            resultIntent = data;
            if (data.getStringExtra(ConfigActivity.EXTRA_NAME) != null &&
                    data.getStringExtra(ConfigActivity.EXTRA_NAME).equals("name")){
                nameLabel.setVisibility(View.GONE);
                name.setVisibility(View.GONE);
            }
            if (data.getStringExtra(ConfigActivity.EXTRA_TYPE) != null &&
                    data.getStringExtra(ConfigActivity.EXTRA_TYPE).equals("type")) {
                typeLabel.setVisibility(View.GONE);
                type.setVisibility(View.GONE);
            }
            if (data.getStringExtra(ConfigActivity.EXTRA_MAXRANGE) != null &&
                    data.getStringExtra(ConfigActivity.EXTRA_MAXRANGE).equals("maxrange")) {
                maxRangeLabel.setVisibility(View.GONE);
                maxRange.setVisibility(View.GONE);
            }
            if (data.getStringExtra(ConfigActivity.EXTRA_MINDELAY) != null &&
                    data.getStringExtra(ConfigActivity.EXTRA_MINDELAY).equals("mindelay")) {
                minDelayLabel.setVisibility(View.GONE);
                minDelay.setVisibility(View.GONE);
            }
            if (data.getStringExtra(ConfigActivity.EXTRA_RESOLUTION) != null &&
                    data.getStringExtra(ConfigActivity.EXTRA_RESOLUTION).equals("resolution")) {
                resolutionLabel.setVisibility(View.GONE);
                resolution.setVisibility(View.GONE);
            }
            if (data.getStringExtra(ConfigActivity.EXTRA_POWER) != null &&
                    data.getStringExtra(ConfigActivity.EXTRA_POWER).equals("power")) {
                powerLabel.setVisibility(View.GONE);
                power.setVisibility(View.GONE);
            }
            if (data.getStringExtra(ConfigActivity.EXTRA_VENDOR) != null &&
                    data.getStringExtra(ConfigActivity.EXTRA_VENDOR).equals("vendor")) {
                vendorLabel.setVisibility(View.GONE);
                vendor.setVisibility(View.GONE);
            }
            if (data.getStringExtra(ConfigActivity.EXTRA_VERSION) != null &&
                    data.getStringExtra(ConfigActivity.EXTRA_VERSION).equals("version")) {
                versionLabel.setVisibility(View.GONE);
                version.setVisibility(View.GONE);
            }
            if (data.getStringExtra(ConfigActivity.EXTRA_ACCURACY) != null &&
                    data.getStringExtra(ConfigActivity.EXTRA_ACCURACY).equals("accuracy")) {
                accuracyLabel.setVisibility(View.GONE);
                accuracy.setVisibility(View.GONE);
            }
        }
        displaySensor(allSensors.get(mCurrentIndex));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sensor, menu);
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

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called!");
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.d(TAG, "onResume() called!");
        sensorManager.registerListener(this, allSensors.get(mCurrentIndex),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called!");
        sensorManager.unregisterListener(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called!");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Log.d(TAG, "onRestart() called!");
    }
    /*        if(resultIntent != null &&
                    resultIntent.getStringExtra(ConfigActivity.EXTRA_TIMESTAMP) != null &&
                    resultIntent.getStringExtra(ConfigActivity.EXTRA_TIMESTAMP).equals("timestamp")){
                timestampLabel.setVisibility(View.GONE);
                timestamp.setVisibility(View.GONE);
                timestampUnits.setVisibility(View.GONE);
            }*/
    @Override
    public void onSensorChanged(SensorEvent event) {
        timestamp.setText(String.valueOf(event.timestamp));
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                showEventData("Accelerometer - gravity on axis",
                        ACCELERATION_UNITS,
                        event.values[0],
                        event.values[1],
                        event.values[2]);
                break;

            case Sensor.TYPE_MAGNETIC_FIELD:
                showEventData("Abient Magnetic Field",
                        "uT",
                        event.values[0],
                        event.values[1],
                        event.values[2]);
                break;

            case Sensor.TYPE_GYROSCOPE:
                showEventData("Angular speed around axis",
                        "radians/sec",
                        event.values[0],
                        event.values[1],
                        event.values[2]);
                break;

            case Sensor.TYPE_LIGHT:
                showEventData("Ambient light",
                        "lux",
                        event.values[0]);
                break;

            case Sensor.TYPE_PRESSURE:
                showEventData("Atmospheric pressure",
                        "hPa",
                        event.values[0]);
                break;

            case Sensor.TYPE_PROXIMITY:
                showEventData("Distance",
                        "cm",
                        event.values[0]);
                break;

            case Sensor.TYPE_GRAVITY:
                showEventData("Gravity",
                        ACCELERATION_UNITS,
                        event.values[0],
                        event.values[1],
                        event.values[2]);
                break;

            case Sensor.TYPE_LINEAR_ACCELERATION:
                showEventData("Acceleration (not including gravity)",
                        ACCELERATION_UNITS,
                        event.values[0],
                        event.values[1],
                        event.values[2]);
                break;

            case Sensor.TYPE_ROTATION_VECTOR:
                showEventData("Rotation Vector",
                        null,
                        event.values[0],
                        event.values[1],
                        event.values[2]);
                xAxisLabel.setText("x*sin(" + THETA + "/2): ");
                yAxisLabel.setText("y*sin(" + THETA + "/2): ");
                zAxisLabel.setText("z*sin(" + THETA + "/2): ");

                if( event.values.length == 4)
                {
                    cosLabel.setVisibility(View.VISIBLE);
                    cos.setVisibility(View.VISIBLE);
                    cosLabel.setText(String.valueOf(event.values[3]));
                }
                break;

            case Sensor.TYPE_ORIENTATION:
                showEventData("Angle",
                        "Degrees",
                        event.values[0],
                        event.values[1],
                        event.values[2]);
                xAxisLabel.setText("Azimuth(Z Axis):");
                yAxisLabel.setText("Pitch(X Axis):");
                zAxisLabel.setText("Roll(Y Axis):");
                break;

            case Sensor.TYPE_RELATIVE_HUMIDITY:
                showEventData("Relatice ambient air humidity",
                        "%",
                        event.values[0]);
                break;

            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                showEventData("Ambient temperature",
                        "degree Celcius",
                        event.values[0]);
                break;

            default:showEventData();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        switch (accuracy)
        {
            case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
                this.accuracy.setText("SENSOR_STATUS_ACCURACY_HIGH");
                break;
            case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
                this.accuracy.setText("SENSOR_STATUS_ACCURACY_MEDIUM");
                break;
            case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
                this.accuracy.setText("SENSOR_STATUS_ACCURACY_LOW");
                break;
            case SensorManager.SENSOR_STATUS_UNRELIABLE :
                this.accuracy.setText("SENSOR_STATUS_UNRELIABLE");
                break;
        }
    }

    private void showEventData()
    {
        dataLabel.setVisibility(View.GONE);
        dataUnits.setVisibility(View.GONE);
        singleValue.setVisibility(View.GONE);
        xAxisLabel.setVisibility(View.GONE);
        xAxis.setVisibility(View.GONE);
        yAxisLabel.setVisibility(View.GONE);
        yAxis.setVisibility(View.GONE);
        zAxisLabel.setVisibility(View.GONE);
        zAxis.setVisibility(View.GONE);
    }

    private void showEventData(String label, String units, float x, float y, float z){
        int count = 0;
        dataLabel.setVisibility(View.VISIBLE);
        dataLabel.setText(label);

        if(units == null)
            dataUnits.setVisibility(View.GONE);
        else
        {
            dataUnits.setVisibility(View.VISIBLE);
            dataUnits.setText("(" + units +"):");
        }

        singleValue.setVisibility(View.GONE);

        if(resultIntent != null &&
                resultIntent.getStringExtra(ConfigActivity.EXTRA_XAXIS) != null &&
                resultIntent.getStringExtra(ConfigActivity.EXTRA_XAXIS).equals("xAxis")){
            xAxisLabel.setVisibility(View.GONE);
            xAxis.setVisibility(View.GONE);
            count++;
        }else {
            xAxisLabel.setVisibility(View.VISIBLE);
            xAxis.setText(String.valueOf(x));
        }

        if(resultIntent != null &&
                resultIntent.getStringExtra(ConfigActivity.EXTRA_YAXIS) != null &&
                resultIntent.getStringExtra(ConfigActivity.EXTRA_YAXIS).equals("yAxis")){
            yAxisLabel.setVisibility(View.GONE);
            yAxis.setVisibility(View.GONE);
            count++;
        }else {
            yAxisLabel.setVisibility(View.VISIBLE);
            yAxis.setText(String.valueOf(y));
        }

        if(resultIntent != null &&
                resultIntent.getStringExtra(ConfigActivity.EXTRA_ZAXIS) != null &&
                resultIntent.getStringExtra(ConfigActivity.EXTRA_ZAXIS).equals("zAxis")){
            zAxisLabel.setVisibility(View.GONE);
            zAxis.setVisibility(View.GONE);
            count++;
        }else {
            zAxisLabel.setVisibility(View.VISIBLE);
            zAxis.setText(String.valueOf(z));
        }

        if(count == 3){
            dataLabel.setVisibility(View.GONE);
            dataUnits.setVisibility(View.GONE);
        }

/*        xAxisLabel.setVisibility(View.VISIBLE);
        xAxis.setText(String.valueOf(x));
        yAxisLabel.setVisibility(View.VISIBLE);
        yAxis.setText(String.valueOf(y));
        zAxisLabel.setVisibility(View.VISIBLE);
        zAxis.setText(String.valueOf(z));*/
    }

    private void showEventData(String label, String units, float value)
    {
        if(resultIntent != null &&
                resultIntent.getStringExtra(ConfigActivity.EXTRA_SINGLEVALUE) != null &&
                resultIntent.getStringExtra(ConfigActivity.EXTRA_SINGLEVALUE).equals("singleValue")){
            dataLabel.setVisibility(View.GONE);
            dataUnits.setVisibility(View.GONE);
            singleValue.setVisibility(View.GONE);
        }else {
            dataLabel.setVisibility(View.VISIBLE);
            dataLabel.setText(label);

            dataUnits.setVisibility(View.VISIBLE);
            dataUnits.setText("(" + units +"):");

            singleValue.setVisibility(View.VISIBLE);
            singleValue.setText(String.valueOf(value));
        }
        xAxisLabel.setVisibility(View.GONE);
        xAxis.setVisibility(View.GONE);
        yAxisLabel.setVisibility(View.GONE);
        yAxis.setVisibility(View.GONE);
        zAxisLabel.setVisibility(View.GONE);
        zAxis.setVisibility(View.GONE);
    }
}
