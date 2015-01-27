package csu.example.netConn;

import java.io.FileInputStream;
import java.io.FileOutputStream;


public class dataSave {
		
	public dataSave()
	{
	}
	
	public String getIpAdress(FileInputStream is)
	{
		String ipAddress = "";
		
		try
		{
			byte[] buffer = new byte[100];
			int num = is.read(buffer);
			String i = new String(buffer, 0, num);
			num = i.indexOf("\n");
			ipAddress = new String(buffer, 0, num);
			is.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return ipAddress;
	}
	
	public String getPort(FileInputStream is)
	{
		String strPort = "";
		
		try
		{
			byte[] buffer = new byte[100];
			int num1 = is.read(buffer);
			String str = new String(buffer, 0, num1);
			int num2 = str.indexOf("\n");
			strPort = new String(buffer, num2+1, num1-num2-2);
			
			is.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return strPort;
	}
	
	public void saveServerInfo(String ipAddress, String strPort, FileOutputStream fos)
	{
		try {
			
			fos.write((ipAddress+"\n").getBytes());
			fos.write((strPort+"\n").getBytes());
			
			fos.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
