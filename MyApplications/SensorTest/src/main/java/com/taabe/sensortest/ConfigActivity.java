package com.taabe.sensortest;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
//private CheckBox mCheckBox_Version;
//public static final String VERSION = "com.taabe.sensortest.version";
//mCheckBox_Version = (CheckBox) findViewById(R.id.checkBox_version);
//mCheckBox_Version.setOnCheckedChangeListener(cbcListener);
public class ConfigActivity extends Activity {

    private static final String THETA = "\u0398";

    //定义获取界面上控件的引用的变量
    private CheckBox mCheckBox_Name;
    private CheckBox mCheckBox_Type;
    private CheckBox mCheckBox_MaxRange;
    private CheckBox mCheckBox_MinDelay;
    private CheckBox mCheckBox_Power;
    private CheckBox mCheckBox_Resolution;
    private CheckBox mCheckBox_Vendor;
    private CheckBox mCheckBox_Version;
    private CheckBox mCheckBox_Accuracy;
    private CheckBox mCheckBox_Timestamp;
    private CheckBox mCheckBox_SingleValue;
    private CheckBox mCheckBox_xAxis;
    private CheckBox mCheckBox_yAxis;
    private CheckBox mCheckBox_zAxis;
    private Button mOKBtn;
    private int mSensorType;

    public static final String EXTRA_SENSOR_TYPR = "com.taabe.sensortest.sensortype";
    public static final String EXTRA_NAME = "com.taabe.sensortest.name";
    public static final String EXTRA_TYPE = "com.taabe.sensortest.type";
    public static final String EXTRA_MAXRANGE = "com.taabe.sensortest.maxrange";
    public static final String EXTRA_MINDELAY = "com.taabe.sensortest.mindelay";
    public static final String EXTRA_POWER = "com.taabe.sensortest.power";
    public static final String EXTRA_RESOLUTION = "com.taabe.sensortest.resolution";
    public static final String EXTRA_VENDOR = "com.taabe.sensortest.vendor";
    public static final String EXTRA_VERSION = "com.taabe.sensortest.version:";
    public static final String EXTRA_ACCURACY = "com.taabe.sensortest.accruacy";
    public static final String EXTRA_TIMESTAMP = "com.taabe.sensortest.timestamp";
    public static final String EXTRA_SINGLEVALUE = "com.taabe.sensortest.singlevalue";
    public static final String EXTRA_XAXIS = "com.taabe.sensortest.xaxis";
    public static final String EXTRA_YAXIS = "com.taabe.sensortest.yaxis";
    public static final String EXTRA_ZAXIS = "com.taabe.sensortest.zaxis";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        mSensorType = getIntent().getIntExtra(EXTRA_SENSOR_TYPR, -1);

        mCheckBox_Name = (CheckBox) findViewById(R.id.checkBox_name);
        mCheckBox_Type = (CheckBox) findViewById(R.id.checkBox_type);
        mCheckBox_MaxRange = (CheckBox) findViewById(R.id.checkBox_maxRange);
        mCheckBox_MinDelay = (CheckBox) findViewById(R.id.checkBox_minDelay);
        mCheckBox_Power = (CheckBox) findViewById(R.id.checkBox_power);
        mCheckBox_Resolution = (CheckBox) findViewById(R.id.checkBox_resolution);
        mCheckBox_Vendor = (CheckBox) findViewById(R.id.checkBox_vendor);
        mCheckBox_Version = (CheckBox) findViewById(R.id.checkBox_version);
        mCheckBox_Accuracy = (CheckBox) findViewById(R.id.checkBox_accuracy);
        mCheckBox_Timestamp = (CheckBox) findViewById(R.id.checkBox_timestamp);
        mCheckBox_SingleValue = (CheckBox) findViewById(R.id.checkBox_singleValue);
        mCheckBox_xAxis = (CheckBox)findViewById(R.id.checkBox_xAxis);
        mCheckBox_yAxis = (CheckBox)findViewById(R.id.checkBox_yAxis);
        mCheckBox_zAxis = (CheckBox)findViewById(R.id.checkBox_zAxis);
        mOKBtn = (Button) findViewById(R.id.configOKBtn);

        //根据不同传感器显示不同的checkbox
        switch(mSensorType)
        {
            case Sensor.TYPE_ROTATION_VECTOR:
                mCheckBox_xAxis.setVisibility(View.VISIBLE);
                mCheckBox_yAxis.setVisibility(View.VISIBLE);
                mCheckBox_zAxis.setVisibility(View.VISIBLE);
                mCheckBox_xAxis.setText("x*sin(" + THETA + "/2):");
                mCheckBox_yAxis.setText("y*sin(" + THETA + "/2):");
                mCheckBox_zAxis.setText("z*sin(" + THETA + "/2):");
                break;
            case Sensor.TYPE_ORIENTATION:
                mCheckBox_xAxis.setVisibility(View.VISIBLE);
                mCheckBox_yAxis.setVisibility(View.VISIBLE);
                mCheckBox_zAxis.setVisibility(View.VISIBLE);
                mCheckBox_xAxis.setText("Azimuth(Z Axis):");
                mCheckBox_yAxis.setText("Pitch(X Axis):");
                mCheckBox_zAxis.setText("Roll(Y Axis):");
                break;
            case Sensor.TYPE_ACCELEROMETER:
            case Sensor.TYPE_LINEAR_ACCELERATION:
            case Sensor.TYPE_MAGNETIC_FIELD:
            case Sensor.TYPE_GYROSCOPE:
            case Sensor.TYPE_GRAVITY:
                mCheckBox_xAxis.setVisibility(View.VISIBLE);
                mCheckBox_yAxis.setVisibility(View.VISIBLE);
                mCheckBox_zAxis.setVisibility(View.VISIBLE);
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                mCheckBox_SingleValue.setVisibility(View.VISIBLE);
                mCheckBox_SingleValue.setText("Ambient temperature:");
                break;
            case Sensor.TYPE_LIGHT:
                mCheckBox_SingleValue.setVisibility(View.VISIBLE);
                mCheckBox_SingleValue.setText("Ambient light:");
                break;
            case Sensor.TYPE_PRESSURE:
                mCheckBox_SingleValue.setVisibility(View.VISIBLE);
                mCheckBox_SingleValue.setText("Atmospheric pressure");
                break;
            case Sensor.TYPE_PROXIMITY:
                mCheckBox_SingleValue.setVisibility(View.VISIBLE);
                mCheckBox_SingleValue.setText("Distance:");
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                mCheckBox_SingleValue.setText("Relative humidity:");
                mCheckBox_SingleValue.setVisibility(View.VISIBLE);
                break;
            default:
        }

        CheckBoxCheckedListener cbcListener = new CheckBoxCheckedListener();
        mCheckBox_Name.setOnCheckedChangeListener(cbcListener);
        mCheckBox_Type.setOnCheckedChangeListener(cbcListener);
        mCheckBox_MaxRange.setOnCheckedChangeListener(cbcListener);
        mCheckBox_MinDelay.setOnCheckedChangeListener(cbcListener);
        mCheckBox_Power.setOnCheckedChangeListener(cbcListener);
        mCheckBox_Resolution.setOnCheckedChangeListener(cbcListener);
        mCheckBox_Vendor.setOnCheckedChangeListener(cbcListener);
        mCheckBox_Version.setOnCheckedChangeListener(cbcListener);
        mCheckBox_Accuracy.setOnCheckedChangeListener(cbcListener);
        mCheckBox_Timestamp.setOnCheckedChangeListener(cbcListener);
        mCheckBox_SingleValue.setOnCheckedChangeListener(cbcListener);
        mCheckBox_xAxis.setOnCheckedChangeListener(cbcListener);
        mCheckBox_yAxis.setOnCheckedChangeListener(cbcListener);
        mCheckBox_zAxis.setOnCheckedChangeListener(cbcListener);

        mOKBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    //定义传递额外数据的Intent
    Intent data = new Intent();
    class CheckBoxCheckedListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int checkedId = buttonView.getId();
            if(isChecked) {
                switch (checkedId) {
                    case R.id.checkBox_name:
                        data.putExtra(EXTRA_NAME, "name");
                        break;
                    case R.id.checkBox_type:
                        data.putExtra(EXTRA_TYPE, "type");
                        break;
                    case R.id.checkBox_maxRange:
                        data.putExtra(EXTRA_MAXRANGE, "maxrange");
                        break;
                    case R.id.checkBox_minDelay:
                        data.putExtra(EXTRA_MINDELAY,"mindelay");
                        break;
                    case R.id.checkBox_power:
                        data.putExtra(EXTRA_POWER, "power");
                        break;
                    case R.id.checkBox_resolution:
                        data.putExtra(EXTRA_RESOLUTION, "resolution");
                        break;
                    case R.id.checkBox_vendor:
                        data.putExtra(EXTRA_VENDOR, "vendor");
                        break;
                    case R.id.checkBox_version:
                        data.putExtra(EXTRA_VERSION, "version");
                        break;
                    case R.id.checkBox_accuracy:
                        data.putExtra(EXTRA_ACCURACY, "accuracy");
                        break;
                    case R.id.checkBox_timestamp:
                        data.putExtra(EXTRA_TIMESTAMP, "timestamp");
                        break;
                    case R.id.checkBox_singleValue:
                        data.putExtra(EXTRA_SINGLEVALUE, "singleValue");
                        break;
                    case R.id.checkBox_xAxis:
                        data.putExtra(EXTRA_XAXIS, "xAxis");
                        break;
                    case R.id.checkBox_yAxis:
                        data.putExtra(EXTRA_YAXIS, "yAxis");
                        break;
                    case R.id.checkBox_zAxis:
                        data.putExtra(EXTRA_ZAXIS, "zAxis");
                        break;
                }
            }
        }
    }
}
