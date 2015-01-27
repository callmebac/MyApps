package csu.example.myknn;

import android.os.Bundle;
import android.app.Activity;
import android.os.Vibrator;
import android.view.Menu;


import java.util.ArrayList;

import android.R.integer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MyKnnActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    private Vibrator vibrator;

    private ArrayList<MyKnnNode> KnnPoints = new ArrayList<MyKnnNode>();
    private float AccelX = 0;
    private float AccelY = 0;
    private float AccelZ = 0;
    private int n = 0;
    private Button button1;
    private TextView textView1;
    private MyKnnCluster knnclusters[] = new MyKnnCluster[3];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_knn);

        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        //1、得到sensorManager对象
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        //2、得到sensor
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //3、得到感应事件监听，通过注册实现
        textView1 = (TextView) findViewById(R.id.textView1);
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                textView1.setText("稍等……");

                //取各个点装入分类器中

                //PersonA的点
                ArrayList<MyKnnNode> knnPersonANodes= new ArrayList<MyKnnNode>();
                Uri uriPersonA = Uri.parse("content://csu.example.sharedata.myProvider/personA");
                Cursor curPersonA = MyKnnActivity.this.getContentResolver().query(
                        uriPersonA, null, null, null, null);
                if ((curPersonA != null) && curPersonA.getCount() > 0) {
                    while (curPersonA.moveToNext()) {
                        String idString = curPersonA.getString(0);
                        Log.i("PersonA: ", idString);
                        int ind = Integer.parseInt(idString);
                        String AccelX = curPersonA.getString(1);
                        String AccelY = curPersonA.getString(2);
                        String AccelZ = curPersonA.getString(3);
                        Log.i("PersonA: ", AccelX);
                        Log.i("PersonA: ", AccelY);
                        Log.i("PersonA: ", AccelZ);
                        double accX = Double.parseDouble(AccelX);
                        double accY = Double.parseDouble(AccelY);
                        double accZ = Double.parseDouble(AccelZ);
                        double dimension[] = {accX, accY, accZ};
                        MyKnnNode KnnPoint = new MyKnnNode(ind, dimension);
                        knnPersonANodes.add(KnnPoint);
                    }
                }
                knnclusters[0] = new MyKnnCluster("personA", knnPersonANodes);


                //PersonB的点

                ArrayList<MyKnnNode> knnPersonBNodes = new ArrayList<MyKnnNode>();
                Uri uriPersonB = Uri.parse("content://csu.example.sharedata.myProvider/personB");
                Cursor curPersonB = MyKnnActivity.this.getContentResolver().query(
                        uriPersonB, null, null, null, null);
                if ((curPersonB != null) && curPersonB.getCount() > 0) {
                    while (curPersonB.moveToNext()) {
                        String idString = curPersonB.getString(0);
                        Log.i("personB: ", idString);
                        int ind = Integer.parseInt(idString);
                        String AccelX = curPersonB.getString(1);
                        String AccelY = curPersonB.getString(2);
                        String AccelZ = curPersonB.getString(3);
                        Log.i("personB： ", AccelX);
                        Log.i("personB: ", AccelY);
                        Log.i("personB: ", AccelZ);
                        double accX = Double.parseDouble(AccelX);
                        double accY = Double.parseDouble(AccelY);
                        double accZ = Double.parseDouble(AccelZ);
                        double dimension[] = {accX, accY, accZ};
                        MyKnnNode KnnPoint = new MyKnnNode(ind, dimension);
                        knnPersonBNodes.add(KnnPoint);
                    }
                }
                knnclusters[1] = new MyKnnCluster("personB", knnPersonBNodes);


                //PersonC的点

                ArrayList<MyKnnNode> knnPersonCNodes = new ArrayList<MyKnnNode>();
                Uri uriPersonC = Uri.parse("content://csu.example.sharedata.myProvider/personC");
                Cursor curPersonC = MyKnnActivity.this.getContentResolver().query(
                        uriPersonC, null, null, null, null);
                if ((curPersonC != null) && curPersonC.getCount() > 0) {
                    while (curPersonC.moveToNext()) {
                        String idString = curPersonC.getString(0);
                        Log.i("personC: ", idString);
                        int ind = Integer.parseInt(idString);
                        String AccelX = curPersonC.getString(1);
                        String AccelY = curPersonC.getString(2);
                        String AccelZ = curPersonC.getString(3);
                        Log.i("personC: ", AccelX);
                        Log.i("personC: ", AccelY);
                        Log.i("personC: ", AccelZ);
                        double accX = Double.parseDouble(AccelX);
                        double accY = Double.parseDouble(AccelY);
                        double accZ = Double.parseDouble(AccelZ);
                        double dimension[] = {accX, accY, accZ};
                        MyKnnNode KnnPoint = new MyKnnNode(ind, dimension);
                        knnPersonCNodes.add(KnnPoint);
                    }
                }
                knnclusters[2] = new MyKnnCluster("personC", knnPersonCNodes);


                Log.i("i", knnclusters[0].getClusterName());
                Log.i("i", knnclusters[1].getClusterName());
                Log.i("i", knnclusters[2].getClusterName());

            }
        });


    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        sensorManager.registerListener(this, sensor, sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_my_knn, menu);
        return true;
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        if (textView1.getText().equals("稍等……") && (KnnPoints.size() < 20)) {
            AccelX = event.values[0];
            AccelY = event.values[1];
            AccelZ = event.values[2];
            Log.i("MD---x" + n, " " + AccelX);
            Log.i("MD---y" + n, " " + AccelY);
            Log.i("MD---z" + n, " " + AccelZ);
            if (Math.abs(AccelX) > 14 || Math.abs(AccelY) > 14 || Math.abs(AccelZ) > 14) {
                n++;
                double dimension[] = {AccelX, AccelY, AccelZ};
                MyKnnNode knNnode = new MyKnnNode(n, dimension);
                KnnPoints.add(knNnode);
            }
        } else if (!(KnnPoints.size() < 20)) {
            sensorManager.unregisterListener(this);
            textView1.setText("正在分析您的身份！");
            if (textView1.getText().equals("正在分析您的身份！")) {
                MyKnnClassify knnClassify = new MyKnnClassify(knnclusters, KnnPoints);
                knnClassify.calEuclideanDistanceSum();
                int personA = 0, personB = 0, personC = 0;
                for (int j = 0; j < KnnPoints.size(); j++) {
                    Log.i(" 距离是：", " " + KnnPoints.get(j).getInstance());
                    Log.i(" 类别是：", " " + KnnPoints.get(j).getClassification());
                    if ((KnnPoints.get(j).getClassification()).equals("personA")) {
                        personA++;
                    } else if ((KnnPoints.get(j).getClassification()).equals("personB")) {
                        personB++;
                    } else {
                        personC++;
                    }
                }

                Log.i("personA", " " + personA);
                Log.i("personB", " " + personB);
                Log.i("personC", " " + personC);
                if (personC <= personA && personB <= personA) {
                    textView1.setText("您是："+ "Tian");
                    vibrator.vibrate(300);
                } else if (personA <= personB && personC <= personB) {
                    textView1.setText("您是："+ "Guo");
                    vibrator.vibrate(300);
                } else if (personA <= personC && personB <= personC) {
                    textView1.setText("您是："+ "Mesfin");
                    vibrator.vibrate(300);
                } else {
                    textView1.setText("无法判断您的身份！");
                }
            }
        }

    }


}
