package csu.example.netConn;

import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class netThread extends Thread {
	
	protected netMsg netConn;
	//��Ҫ���ƵĿؼ�
	protected TextView tv_MsgShow;
	protected Button bt_send;
	protected Button bt_disConnect;
	protected Button bt_connect;
	protected TextView tv_hello;
	
	//�°�ť���߿ؼ����ڴ˴����
	/**
	 * protected Button bt_func;
	 * �ڴ˴���Ϊ�°�ť��Ӷ������ú���
	 * public void setButton(Button bt_func)
	 * {
	 * 		this.bt_func = bt_func;
	 * }
	 */
	
	//socket��������Ϣ
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
			
			//���ӳɹ�
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
				
				//���ӳɹ���Ҫ����ť��Ч��
				//�ڴ˴�����
				
				/**
				 * �ڴ˴�����°�ť��״̬����
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
					//��ȡ��Ϣ
					final String recvText = netConn.readMsg();
					
					//�ڽ�������ʾ��Ϣ
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
					//����״̬�仯
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
			       
					
					//�˳��������ӣ���Ҫ����ť��Ч��
					//�ڴ˴�����
					
					/**
					 * �ڴ˴�����°�ť��״̬����
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
