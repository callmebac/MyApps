package com.example.netConn;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.widget.Button;
import android.widget.TextView;

public class netThread extends Thread {
	
	protected netMsg netConn;
	//需要控制的控件
	protected TextView tv_MsgShow;
	protected Button bt_send;
	protected Button bt_disConnect;
	protected Button bt_connect;
	protected TextView tv_hello;
	
	//新按钮或者控件请在此处添加
	/**
	 * protected Button bt_func;
	 * 在此处再为新按钮添加对象设置函数
	 * public void setButton(Button bt_func)
	 * {
	 * 		this.bt_func = bt_func;
	 * }
	 */
	
	//socket服务器信息
	protected String ipAddress;
	protected int port;
	

	
	
	public netThread(netMsg mns, TextView tv_MsgShow, TextView tv_hello,
			Button bt_send, Button bt_disConnect, Button bt_connect)
	{
		this.netConn = mns;	
		this.tv_MsgShow = tv_MsgShow;
		this.bt_connect = bt_connect;
		this.bt_disConnect = bt_disConnect;
		this.bt_send = bt_send;
		this.tv_hello = tv_hello;
		
		ipAddress = "";
		port = 0;
	}
	
	public void setServerInfo(String ipAddress, int port)
	{
		this.ipAddress = ipAddress;
		this.port = port;
		
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try
		{
			//start connect
			if(!netConn.startConnect())
			{
				tv_hello.post(new Runnable() {
					
					public void run() {
						// TODO Auto-generated method stub
						tv_hello.setText("connect fail!, Please check Ip and Port...");
					}
				});
				bt_connect.post(new Runnable() {
					
					public void run() {
						// TODO Auto-generated method stub
						bt_connect.setEnabled(true);
					}
				});
				
				return ;
			}
			
			//连接成功
			if(netConn.isConnect())
			{
				
				tv_hello.post(new Runnable() {
					
					public void run() {
						// TODO Auto-generated method stub
						tv_hello.setText("connect success!");
					}
				});
				bt_send.post(new Runnable() {
					
					public void run() {
						// TODO Auto-generated method stub
						bt_send.setEnabled(true);
					}
				});
				
				bt_disConnect.post(new Runnable() {
					
					public void run() {
						// TODO Auto-generated method stub
						bt_disConnect.setEnabled(true);
					}
				});
				
				//连接成功需要将按钮有效化
				//在此粗设置
				
				/**
				 * 在此处添加新按钮的状态设置
				 * bt_func.post(new Runnable() {
				 * 		public void run() {
				 * 			bt_func.setEnable(true);
				 * 		}
				 * });
				 */
				
			}
			
			
			while(true)
			{
				if(netConn.isConnect() && !netConn.isDisConn)
				{
					//读取消息
					final String recvText = netConn.readMsg();
					
					//在界面上显示消息
					tv_MsgShow.post(new Runnable() {
						public void run() {
							// TODO Auto-generated method stub
							//get system time
							SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");   
					        Date date = new Date();   
					        String data = sdf.format(date) + ":  ";   
							String str = tv_MsgShow.getText().toString();
							
							data += recvText + "\n";
							str += data;
							tv_MsgShow.setText(str);
						}
					} );
					
				}
				else
				{
					//界面状态变化
					tv_hello.post(new Runnable() {
						
						public void run() {
							// TODO Auto-generated method stub
							tv_hello.setText("server shutting down!");
						}
					});
					bt_send.post(new Runnable() {
						
						public void run() {
							// TODO Auto-generated method stub
							bt_send.setEnabled(false);
						}
					});
					
					bt_disConnect.post(new Runnable() {
						
						public void run() {
							// TODO Auto-generated method stub
							bt_disConnect.setEnabled(false);
						}
					});
					
					
					
					bt_connect.post(new Runnable() {
						
						public void run() {
							// TODO Auto-generated method stub
							bt_connect.setEnabled(true);
						}
					});
			       
					
					//退出网络连接，需要将按钮无效化
					//在此粗设置
					
					/**
					 * 在此处添加新按钮的状态设置
					 * bt_func.post(new Runnable() {
					 * 		public void run() {
					 * 			bt_func.setEnable(false);
					 * 		}
					 * });
					 */
					
					
			        if(!netConn.isClosed())
			        	netConn.closeSock();
			        return;
				}
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
}
