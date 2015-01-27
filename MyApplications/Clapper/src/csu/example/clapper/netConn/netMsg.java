package csu.example.clapper.netConn;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * this class is used to connect with the server
 * @author Administrator
 *
 */

public class netMsg {
	
	public Socket sock_client;	//socket varliable
	protected InetSocketAddress hostAddr;
	protected String dstIpAddr;
	public String readData;	//reciving data from the server
	protected int dstPort;		
	public boolean readIsReady;	//recive ready
	public boolean isDisConn;
	
	public boolean isHexModel;	//hex model setting
	public boolean isHexModelR;	//Hex model showing
	
	/**
	 * construct function
	 */
	public netMsg(){
		sock_client = new Socket();
		hostAddr = new InetSocketAddress("192.168.1.102", 12000);
		this.dstIpAddr = "192.168.1.102";
		this.dstPort = 12000;
		readIsReady = false;
		isDisConn = true;
		readData = "";
		isHexModel = false;
		isHexModelR = false;
	}
	
	/**
	 * @param hostAddress Ip Address
	 * @param hostPort	
	 */
	public netMsg(String hostAddress, int hostPort)
	{
		sock_client = new Socket();
		hostAddr = new InetSocketAddress(hostAddress, hostPort);
		this.dstIpAddr = hostAddress;
		this.dstPort = hostPort;
		isHexModel = false;
		isHexModelR = false;
	}
	
	/*
	 * start connectting
	 */
	public boolean startConnect()
	{
		try
		{
			//set timeout 10 second
			sock_client.connect(new InetSocketAddress(dstIpAddr, dstPort), 10000);
			if(!sock_client.isConnected())
				return false;
			isDisConn = false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * send msg to the server
	 * @param msg
	 * @return true: send msg success; false send msg fail;
	 */
	public boolean sendMsg(String msg)
	{
		try
		{
			//16�����ַ���ת��
			hexCode hc = new hexCode();
			byte[] sendMsg = msg.getBytes();
			if(isHexModel)
			{
				sendMsg = hc.toHexData(msg);
			}
			
			
			if(!sock_client.isConnected())
			{
				System.out.println("Is not Connected");
				return false;
			}
			//start send msg
			OutputStream os = sock_client.getOutputStream();
			os.write(sendMsg);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * send msg to the server
	 * @param msg
	 * @return true: send msg success; false send msg fail;
	 */
	public boolean sendMsg(byte[] msg)
	{
		try
		{
			//16�����ַ���ת��
			if(!sock_client.isConnected())
			{
				System.out.println("Is not Connected");
				return false;
			}
			//start send msg
			OutputStream os = sock_client.getOutputStream();
			os.write(msg);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	/**
	 * read msg from the server port
	 * �������Ҫ�����ݽ��н�����ʹ�øú���
	 * ��Ҫ����16������ʾ���⣺������isHexModelR����
	 * @return
	 */
	public String readMsg()
	{
		try
		{
			if(!sock_client.isConnected())
			{
				return "";
			}
			
			//start read form the stream
			InputStream is = sock_client.getInputStream();
			
			String buf = "";
			byte[] buffer = new byte[1024];
			int readNum = 0;
			do
			{
				readNum = is.read(buffer);
				String medStr = "";
				if(readNum >= 0)
					medStr = new String(buffer,0, readNum, "ISO_8859_1");
				else
					isDisConn = true;
				
				buf += medStr;
			}
			while(readNum == 1024);
			
			//isHexModelRΪtrueΪ16����ģʽ��ʾ����Ҫ���и�ʽת��
			if(isHexModelR)
			{
				hexCode hc = new hexCode();
				buf = hc.hexToString(buf);
			}
			
			return  buf;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * read msg from the server port
	 * @return
	 */
	public String readMsg(boolean aa)
	{
		try
		{
			if(!sock_client.isConnected())
			{
				return "";
			}
			
			//start read form the stream
			InputStream is = sock_client.getInputStream();
			
			byte[] buffer = new byte[1024];
			int readNum = 0;
			//������Ĭ�Ͻ������ݲ��ᳬ��1024�ֽ�
			do
			{
				readNum = is.read(buffer);
				if(readNum < 0)
					isDisConn = true;
			}
			while(readNum == 1024);
			
			String strRe = "";
			
			//����Э�����
			if(buffer[13] == 0x06)
			{
				hexCode hc = new hexCode();
				strRe += "ͨ��" + hc.hexToString(new String(buffer, 14, 1, "ISO_8859_1"));
				
				if(buffer[17] == 0x00)
					strRe += "�����Ѵ�";
				else
					strRe += "�����ѹر�";
			}
			
			return  strRe;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * test wheather connecting is alive
	 * @return
	 */
	public boolean isConnect()
	{
		if(sock_client.isClosed())
			return false;
		if(!sock_client.isConnected())
			return false;
		return true;
	}
	
	
	public boolean isClosed()
	{
		return sock_client.isClosed();
	}
	
	public boolean isReciving()
	{
		return !sock_client.isInputShutdown();
	}
	/**
	 * close client socket connect
	 * @return
	 */
	public boolean closeSock()
	{
		try
		{
			sock_client.shutdownInput();
			//sock_client.shutdownOutput();
			sock_client.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
