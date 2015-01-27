package csu.example.sharedata;


import java.util.ArrayList;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.R.id;
import android.R.string;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShareActivity extends Activity implements
        SensorEventListener {
    private final String info = "请选择您的姓名！";
    private SensorManager sensorManager;
    private Sensor sensor;
    private Vibrator vibrator;
    private ArrayList<DataPoint> dataPoints = new ArrayList<DataPoint>();
    private float AccelX = 0;
    private float AccelY = 0;
    private float AccelZ = 0;
    private int n = 0;

    private Button start, button1, button2, button3;
    private TextView textView1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        // 初始化各按钮
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        start = (Button) findViewById(R.id.button4);

        textView1 = (TextView) findViewById(R.id.textView1);

        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        // 1、得到sensorManager对象
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        // 2、得到sensor
        sensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // 3、得到感应事件监听，通过注册实现

        // 建立我们自定义的helper类的实例
        final mySqliteOpenHelper mySQLiteHelper = new mySqliteOpenHelper(
                ShareActivity.this, "sqliteDB", null, 1);

        // 各个按钮的工作
        start.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                textView1.setText("稍等……");
                dataPoints.clear();
            }
        });

        button1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (textView1.getText().equals(info)) {

                    for (int i = 0; i < dataPoints.size(); i++) {
                        SQLiteDatabase myDB = mySQLiteHelper
                                .getWritableDatabase();
                        //确保只有一组点，如果点需要多组的时候，可以删除下面两行
                        String[] whereStrings = {String.valueOf(i)};
                        myDB.delete("personA", "id = ?", whereStrings);

                        ContentValues contentValues = new ContentValues();
                        contentValues.put("id", dataPoints.get(i).getId());
                        Log.i("personA", "" + dataPoints.get(i).getId());
                        Log.i("personA", "" + dataPoints.get(i).getDimension()[0]);
                        Log.i("personA", "" + dataPoints.get(i).getDimension()[1]);
                        Log.i("personA", "" + dataPoints.get(i).getDimension()[2]);
                        contentValues.put("x",
                                dataPoints.get(i).getDimension()[0]);
                        contentValues.put("y",
                                dataPoints.get(i).getDimension()[1]);
                        contentValues.put("z",
                                dataPoints.get(i).getDimension()[2]);
                        myDB.insert("personA", null, contentValues);
                        myDB.close();
                    }
                    textView1.setText("完成！");

                    Toast.makeText(ShareActivity.this, "数据存储完成!",
                            Toast.LENGTH_LONG).show();

                }

            }

        });

        button2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (textView1.getText().equals(info)) {

                    for (int i = 0; i < dataPoints.size(); i++) {
                        SQLiteDatabase myDB = mySQLiteHelper
                                .getWritableDatabase();
                        String[] whereStrings = {String.valueOf(i)};
                        myDB.delete("personB", "id = ?", whereStrings);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("id", dataPoints.get(i).getId());
                        Log.i("personB", "" + dataPoints.get(i).getId());
                        Log.i("personB", "" + dataPoints.get(i).getDimension()[0]);
                        Log.i("personB", "" + dataPoints.get(i).getDimension()[1]);
                        Log.i("personB", "" + dataPoints.get(i).getDimension()[2]);
                        contentValues.put("x",
                                dataPoints.get(i).getDimension()[0]);
                        contentValues.put("y",
                                dataPoints.get(i).getDimension()[1]);
                        contentValues.put("z",
                                dataPoints.get(i).getDimension()[2]);
                        myDB.insert("personB", null, contentValues);
                        myDB.close();
                    }
                    textView1.setText("完成！");

                    Toast.makeText(ShareActivity.this, "数据存储完成!",
                            Toast.LENGTH_LONG).show();

                }

            }

        });

        button3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (textView1.getText().equals(info)) {
                    for (int i = 0; i < dataPoints.size(); i++) {
                        SQLiteDatabase myDB = mySQLiteHelper
                                .getWritableDatabase();
                        String[] whereStrings = {String.valueOf(i)};
                        myDB.delete("personC", "id = ?", whereStrings);
                        ContentValues contentValues = new ContentValues();

                        contentValues.put("id", dataPoints.get(i).getId());
                        Log.i("personC", "" + dataPoints.get(i).getId());
                        Log.i("personC", "" + dataPoints.get(i).getDimension()[0]);
                        Log.i("personC", "" + dataPoints.get(i).getDimension()[1]);
                        Log.i("personC", "" + dataPoints.get(i).getDimension()[2]);
                        contentValues.put("x",
                                dataPoints.get(i).getDimension()[0]);
                        contentValues.put("y",
                                dataPoints.get(i).getDimension()[1]);
                        contentValues.put("z",
                                dataPoints.get(i).getDimension()[2]);
                        myDB.insert("personC", null, contentValues);
                        myDB.close();
                    }
                    textView1.setText("完成！");

                    Toast.makeText(ShareActivity.this, "数据存储完成!",
                            Toast.LENGTH_LONG).show();
//					String clu [] = new String []{"id","x","y","z"}; 
//					Uri urii=Uri.parse("content://com.example.sharedata.myProvider/run");
//getContentResolver().query(urii, clu, null, null, null);					

                }

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_share, menu);
        return true;

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
//		sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        sensorManager.registerListener(this, sensor,
                sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(final SensorEvent event) {
        // TODO Auto-generated method stub

        if (textView1.getText().equals("稍等……") && (dataPoints.size() < 20)) {

            AccelX = event.values[0];
            AccelY = event.values[1];
            AccelZ = event.values[2];
/*            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/
            if (Math.abs(AccelX) > 14 || Math.abs(AccelY) > 14 || Math.abs(AccelZ) > 14) {
                Log.i("MD", " " + n);
                Log.i("MD---x" + n, " " + AccelX);
                Log.i("MD---y" + n, " " + AccelY);
                Log.i("MD---z" + n, " " + AccelZ);
                double dimension[] = {AccelX, AccelY, AccelZ};
                DataPoint dpDataPoint = new DataPoint(dimension);
                dpDataPoint.setId(n);
                dataPoints.add(dpDataPoint);
                n++;
            }
        } else if (!(dataPoints.size() < 20)) {
            sensorManager.unregisterListener(this);
            textView1.setText(info);
            vibrator.vibrate(300);
        }
    }


}
