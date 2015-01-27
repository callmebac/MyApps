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

    //添加定义Vibrator变量
    private Vibrator vibrator;
    // 两次检测的时间间隔 5000毫秒
    private static final int UPTATE_INTERVAL_TIME = 8000;
    private long lastShakeTime = System.currentTimeMillis();

    public ExampleActivity() {
        // TODO Auto-generated constructor stub
        sendText = "";
        netConn = new netMsg();
        isHexModel = false;
    }

    //当Activity为Resumed状态时初始化传感器有关变量
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

        //控件ID
        final TextView tv_hello = (TextView) findViewById(R.id.textView1);    //状态字
        final Button bt_connect = (Button) findViewById(R.id.connect);        //连接按钮
        final Button bt_send = (Button) findViewById(R.id.send);                //发送按钮
        final EditText sendMsg = (EditText) findViewById(R.id.sendMsg);        //信息编辑框
        final Button bt_disConnect = (Button) findViewById(R.id.disConn);    //断开连接按钮
        final TextView tv_MsgShow = (TextView) findViewById(R.id.msgShow);    //信息显示框
        final EditText ipAddr = (EditText) findViewById(R.id.ipAddress);        //IP地址编辑框
        final EditText port = (EditText) findViewById(R.id.port);            //端口编辑框
        final EditText macAddr = (EditText) findViewById(R.id.macAddress);    //MAC地址编辑框
        final CheckBox hexModel = (CheckBox) findViewById(R.id.checkBox1);    //发送16进制模式选择框
        final CheckBox hexModelR = (CheckBox) findViewById(R.id.checkBox2);    //接收16进制模式选择框

        //添加“打开”、“关闭”按钮变量
        final Button bt_open = (Button) findViewById(R.id.open);
        final Button bt_close = (Button) findViewById(R.id.close);


        //初始化空间属性
        findViewById(R.id.LinearLayout0).setBackgroundColor(Color.GRAY);
        findViewById(R.id.LinearLayout1).setBackgroundColor(Color.BLACK);
        findViewById(R.id.LinearLayout2).setBackgroundColor(Color.LTGRAY);
        findViewById(R.id.LinearLayout3).setBackgroundColor(Color.BLACK);
        findViewById(R.id.LinearLayout4).setBackgroundColor(Color.GRAY);

        tv_hello.setTextColor(Color.WHITE);

        //初始化控件状态
        bt_send.setEnabled(false);
        bt_disConnect.setEnabled(false);

        //添加open、close按钮初始化状态
        bt_open.setEnabled(false);
        bt_close.setEnabled(false);

        macAddr.setText("00 12 4B 00 02 18 EE B3");

        //配置文件读取
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
            //读取上次链接的地址
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
         * 新按钮响应事件定义请在此处定义
         * New Button Click Listener code setting here
         * 例如在界面中脱拖入了一个新的按钮bt_func
         * bt_func.setOnClickListener()(new OnClickListener() {

         public void onClick(View v) {
         // TODO Auto-generated method stub

         }
         });
         */

        /**
         * 添加open按钮点击事件
         */
        bt_open.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                packageData pData = new packageData(macAddr.getText().toString());
                netConn.sendMsg(pData.winOpenData());
            }
        });

        /**
         * 添加close按钮点击事件
         */
        bt_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                packageData pData = new packageData(macAddr.getText().toString());
                netConn.sendMsg(pData.winCloseData());
            }
        });

        /**
         * 连接按钮的事件响应函数
         */
        bt_connect.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                tv_hello.setText("Connecting......");
                //tv_MsgShow.setText("");
                bt_connect.setEnabled(false);

                //初始化服务器信息
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
                //发送模式设置
                netConn.isHexModel = isHexModel;
                netConn.isHexModelR = isHexModelR;

                //开始连接和接收工作
                netThread neth = new netThread(netConn, tv_MsgShow, tv_hello,
                        bt_send, bt_disConnect, bt_connect);
                neth.setServerInfo(ipAddr.getText().toString(), int_port);
                neth.start();
            }
        });

        /**
         * 数据发送按钮事件响应函数
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
                 * 新按钮无效化请在此处编写
                 * bt_func.setEnable(false);
                 */

            }
        });


        /**
         * 发送数据按钮事件响应
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

        //保存当前的配置
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
        //现在的时间
        long currentTime = System.currentTimeMillis();
        //两次检测的时间间隔
        long timeInterval = currentTime - lastShakeTime;

        EditText macAddr = (EditText) findViewById(R.id.macAddress);

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        if(sensorType == Sensor.TYPE_ACCELEROMETER && netConn.isConnect()){
            if (timeInterval < UPTATE_INTERVAL_TIME)
                return;
            if(Math.abs(x) > 12 || Math.abs(y) > 12 || Math.abs(z) > 12){
                //更新lastShakeTime为当前时间
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
