package com.example.ex;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.example.netConn.dataSave;
import com.example.netConn.netMsg;
import com.example.netConn.netThread;
import com.example.netConn.packageData;

import java.io.IOException;

public class ExampleActivity extends Activity implements SensorEventListener {

    protected String sendText;
    protected netMsg netConn;
    protected String recvText;
    protected boolean isHexModel;
    protected boolean isHexModelR;

    private SensorManager sensorManager;
    private Sensor sensor;

    //��Ӷ���Vibrator����
    private Vibrator vibrator;
    // ���μ���ʱ���� 5000����
    private static final int UPTATE_INTERVAL_TIME = 8000;
    private long lastShakeTime = System.currentTimeMillis();

    public ExampleActivity() {
        // TODO Auto-generated constructor stub
        sendText = "";
        netConn = new netMsg();
        isHexModel = false;
    }

    //��ActivityΪResumed״̬ʱ��ʼ���������йر���
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager = (SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(ExampleActivity.this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        //�ؼ�ID
        final TextView tv_hello = (TextView) findViewById(R.id.textView1);    //״̬��
        final Button bt_connect = (Button) findViewById(R.id.connect);        //���Ӱ�ť
        final Button bt_send = (Button) findViewById(R.id.send);                //���Ͱ�ť
        final EditText sendMsg = (EditText) findViewById(R.id.sendMsg);        //��Ϣ�༭��
        final Button bt_disConnect = (Button) findViewById(R.id.disConn);    //�Ͽ����Ӱ�ť
        final TextView tv_MsgShow = (TextView) findViewById(R.id.msgShow);    //��Ϣ��ʾ��
        final EditText ipAddr = (EditText) findViewById(R.id.ipAddress);        //IP��ַ�༭��
        final EditText port = (EditText) findViewById(R.id.port);            //�˿ڱ༭��
        final EditText macAddr = (EditText) findViewById(R.id.macAddress);    //MAC��ַ�༭��
        final CheckBox hexModel = (CheckBox) findViewById(R.id.checkBox1);    //����16����ģʽѡ���
        final CheckBox hexModelR = (CheckBox) findViewById(R.id.checkBox2);    //����16����ģʽѡ���

        //��ӡ��򿪡������رա���ť����
        final Button bt_open = (Button) findViewById(R.id.open);
        final Button bt_close = (Button) findViewById(R.id.close);


        //��ʼ���ռ�����
        findViewById(R.id.LinearLayout0).setBackgroundColor(Color.GRAY);
        findViewById(R.id.LinearLayout1).setBackgroundColor(Color.BLACK);
        findViewById(R.id.LinearLayout2).setBackgroundColor(Color.LTGRAY);
        findViewById(R.id.LinearLayout3).setBackgroundColor(Color.BLACK);
        findViewById(R.id.LinearLayout4).setBackgroundColor(Color.GRAY);

        tv_hello.setTextColor(Color.WHITE);

        //��ʼ���ؼ�״̬
        bt_send.setEnabled(false);
        bt_disConnect.setEnabled(false);

        //���open��close��ť��ʼ��״̬
        bt_open.setEnabled(false);
        bt_close.setEnabled(false);

        macAddr.setText("00 12 4B 00 02 18 EE B3");

        //�����ļ���ȡ
        try {
            this.openFileInput("config.ini").close();
        } catch (Exception e) {
            try {
                this.openFileOutput("config.ini", Activity.MODE_PRIVATE).close();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        try {
            //��ȡ�ϴ����ӵĵ�ַ
            if (new dataSave().getIpAdress(this.openFileInput("config.ini")).equals("")) {
                ipAddr.setText("192.168.1.105");
                port.setText("8888");
            } else {
                ipAddr.setText(new dataSave().getIpAdress(this.openFileInput("config.ini")));
                port.setText(new dataSave().getPort(this.openFileInput("config.ini")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        hexModel.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                isHexModel = hexModel.isChecked();
                netConn.isHexModel = isHexModel;
            }
        });
        hexModelR.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                // TODO Auto-generated method stub
                isHexModelR = hexModelR.isChecked();
                netConn.isHexModelR = isHexModelR;
            }
        });


        /**
         * �°�ť��Ӧ�¼��������ڴ˴�����
         * New Button Click Listener code setting here
         * �����ڽ�������������һ���µİ�ťbt_func
         * bt_func.setOnClickListener()(new OnClickListener() {

         public void onClick(View v) {
         // TODO Auto-generated method stub

         }
         });
         */

        /**
         * ���open��ť����¼�
         */
        bt_open.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                packageData pData = new packageData(macAddr.getText().toString());
                netConn.sendMsg(pData.winOpenData());
            }
        });

        /**
         * ���close��ť����¼�
         */
        bt_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                packageData pData = new packageData(macAddr.getText().toString());
                netConn.sendMsg(pData.winCloseData());
            }
        });

        /**
         * ���Ӱ�ť���¼���Ӧ����
         */
        bt_connect.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                tv_hello.setText("Connecting......");
                //tv_MsgShow.setText("");
                bt_connect.setEnabled(false);

                //��ʼ����������Ϣ
                int int_port = 0;
                String str_port = port.getText().toString();
                if (!str_port.equals("")) {
                    try {
                        int_port = Integer.parseInt(str_port);
                    } catch (Exception e) {
                        int_port = 0;
                    }
                }

                netConn = new netMsg(ipAddr.getText().toString(), int_port);
                //����ģʽ����
                netConn.isHexModel = isHexModel;
                netConn.isHexModelR = isHexModelR;

                //��ʼ���Ӻͽ��չ���
                netThread neth = new netThread(netConn, tv_MsgShow, tv_hello,
                        bt_send, bt_disConnect, bt_connect);
                neth.setServerInfo(ipAddr.getText().toString(), int_port);
                neth.start();
            }
        });

        /**
         * ���ݷ��Ͱ�ť�¼���Ӧ����
         */
        bt_disConnect.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                netConn.closeSock();
                tv_hello.setText("Connected close...");
                bt_disConnect.setEnabled(false);
                bt_connect.setEnabled(true);
                bt_send.setEnabled(false);

                /**
                 * �°�ť��Ч�����ڴ˴���д
                 * bt_func.setEnable(false);
                 */

            }
        });


        /**
         * �������ݰ�ť�¼���Ӧ
         */
        bt_send.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (netConn.isConnect()) {
                    String text_msg = sendMsg.getText().toString();
                    netConn.sendMsg(text_msg);
                    sendMsg.setText("");
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_example, menu);
        return true;
    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        if (!netConn.isConnect())
            try {
                netConn.sock_client.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        else
            netConn.closeSock();

        EditText ipAddr = (EditText) findViewById(R.id.ipAddress);
        EditText port = (EditText) findViewById(R.id.port);
        dataSave ds = new dataSave();

        //���浱ǰ������
        try {
            ds.saveServerInfo(ipAddr.getText().toString(), port.getText().toString(),
                    this.openFileOutput("config.ini", Activity.MODE_PRIVATE));
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        //���ڵ�ʱ��
        long currentTime = System.currentTimeMillis();
        //���μ���ʱ����
        long timeInterval = currentTime - lastShakeTime;

        EditText macAddr = (EditText) findViewById(R.id.macAddress);

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        if(sensorType == Sensor.TYPE_ACCELEROMETER && netConn.isConnect()){
            if (timeInterval < UPTATE_INTERVAL_TIME)
                return;
            if(Math.abs(x) > 12 || Math.abs(y) > 12 || Math.abs(z) > 12){
                //����lastShakeTimeΪ��ǰʱ��
                lastShakeTime = currentTime;
                AlertDialog.Builder builder = new AlertDialog.Builder(ExampleActivity.this);
                builder.setTitle("Test!");
                builder.setMessage("timeInterval: " + timeInterval);
                builder.setPositiveButton("Yes", null);
                builder.show();
                vibrator.vibrate(300);
                packageData pData = new packageData(macAddr.getText().toString());
                netConn.sendMsg(pData.winOpenData());
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
