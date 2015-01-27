package csu.example.netConn;

import java.util.ArrayList;


public class packageData {
	
	private String macAddr;
	
	public packageData()
	{
		macAddr = "00 12 4B 00 02 18 EE B3";
	}
	
	public packageData(String macAddr)
	{
		this.macAddr = macAddr;
	}
	
	/**
	 * ��ô�������������
	 * @return ������16������������
	 */
	public byte[] winOpenData()
	{
		hexCode hc = new hexCode();
		
		ArrayList<Byte> data = new ArrayList<Byte>();
		//SOF
		data.add((byte) 0x44);
		data.add((byte) 0x55);
		//MAC
		byte[] mcStr = hc.toHexData(macAddr);
		for(int i = 0; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		//CF
		data.add((byte)0x04);
		data.add((byte)0x10);
		//LEN
		data.add((byte)0x00);
		data.add((byte)0x05);
		//Channel ID
		data.add((byte)0x00);
		//OpCode
		data.add((byte)0x01);
		data.add((byte)0x00);
		//CRC
		mcStr = hc.toHexData(RCR16(data.toArray(), data.toArray().length));
		for(int i = 0 ; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		
		byte[] result = new byte[data.toArray().length];
		for(int i = 0; i < result.length; i ++)
		{
			result[i] = (Byte)data.toArray()[i];
		}
		
		return result;
	}
	
	/**
	 * ��ô�������������
	 * @return ������16��������
	 */
	public byte[] winCloseData()
	{
		hexCode hc = new hexCode();

		ArrayList<Byte> data = new ArrayList<Byte>();
		//SOF
		data.add((byte) 0x44);
		data.add((byte) 0x55);
		//MAC
		byte[] mcStr = hc.toHexData(macAddr);
		
		for(int i = 0; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		//CF
		data.add((byte)0x04);
		data.add((byte)0x10);
		//LEN
		data.add((byte)0x00);
		data.add((byte)0x05);
		//Channel ID
		data.add((byte)0x00);
		//OpCode
		data.add((byte)0x01);
		data.add((byte)0x01);
		//CRC
		mcStr = hc.toHexData(RCR16(data.toArray(), data.toArray().length));
		for(int i = 0 ; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		
		byte[] result = new byte[data.toArray().length];
		for(int i = 0; i < result.length; i ++)
		{
			result[i] = (Byte)data.toArray()[i];
		}
		
		return result;
	}
	
	/**
	 * ��ȡ����ֹͣ����
	 * @return ����ֹͣ16��������
	 */
	public byte[] winStopData()
	{
		hexCode hc = new hexCode();

		ArrayList<Byte> data = new ArrayList<Byte>();
		//SOF
		data.add((byte) 0x44);
		data.add((byte) 0x55);
		//MAC
		byte[] mcStr = hc.toHexData(macAddr);
		for(int i = 0; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		//CF
		data.add((byte)0x04);
		data.add((byte)0x10);
		//LEN
		data.add((byte)0x00);
		data.add((byte)0x05);
		//CHannel ID
		data.add((byte)0x00);
		//OpCode
		data.add((byte)0x01);
		data.add((byte)0x02);
		//CRC
		mcStr = hc.toHexData(RCR16(data.toArray(), data.toArray().length));
		for(int i = 0 ; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		
		byte[] result = new byte[data.toArray().length];
		for(int i = 0; i < result.length; i ++)
		{
			result[i] = (Byte)data.toArray()[i];
		}
		
		return result;
	}
	
	
	/**
	 * ��ȡ25�ȣ����䣬�����Զ�����ɨ��
	 * @return ����
	 */
	public byte[] red25Cold()
	{
		hexCode hc = new hexCode();

		byte[] infrared_data = {0x00, 0x01, (byte)0xF4, 0x02, 0x14, (byte)0xFF, (byte)0xFF, (byte)0xC0, 
				0x11, 0x51, 0x55, 0x15, 0x15, 0x55, 0x54, 0x55, 0x54, 0x51, 0x51, 0x40, 0x00, 0x00, 0x00, 
				0x00, 0x2A, (byte)0xAA, (byte)0xAA, (byte)0xA8, (byte)0xAA, (byte)0xAA, (byte)0xAA, (byte)0xAA, 
				0x22, 0x20, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1F, (byte)0xFF, (byte)0xF8, 
				0x02, 0x2A, 0x2A, (byte)0xA2, (byte)0xA2, (byte)0xAA, (byte)0xAA, (byte)0x8A, (byte)0xAA, 
				(byte) 0x88, (byte) 0x8A, (byte) 0x8A, 0x00, 0x00, 0x00, 0x00, 0x01, 0x55, 0x55, 0x55, 0x55, 
				0x55, 0x55, 0x55, 0x51, 0x10};
		
		ArrayList<Byte> data = new ArrayList<Byte>();
		//SOF
		data.add((byte) 0x44);
		data.add((byte) 0x55);
		//MAC
		byte[] mcStr = hc.toHexData(macAddr);
		for(int i = 0; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		//CF
		data.add((byte)0x04);
		data.add((byte)0x10);
		//LEN
		int short_len = (int)(3 + infrared_data.length + 2);
        data.add((byte)(short_len >> 8));
        data.add((byte)short_len);
		
        //OpCode
		data.add((byte)0x00);
		data.add((byte)0x81);
		data.add((byte)0x00);
		
		//Data
		for(int i = 0 ; i < infrared_data.length; i ++)
			data.add(infrared_data[i]);
		
		//CRC
		mcStr = hc.toHexData(RCR16(data.toArray(), data.toArray().length));
		for(int i = 0 ; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		
		byte[] result = new byte[data.toArray().length];
		for(int i = 0; i < result.length; i ++)
		{
			result[i] = (Byte)data.toArray()[i];
		}
		
		return result;
	}
	
	/**
	 * 25������
	 * @return
	 */
	public byte[] red25Heat()
	{
		hexCode hc = new hexCode();

		byte[] infrared_data = { 0x00, 0x01, (byte) 0xF4, 0x02, 0x14, (byte) 0xFF, (byte) 0xFF, (byte) 0xC0, 0x15, 0x11, 0x55, 0x15, 0x15,
				0x55, 0x54, 0x55, 0x54, 0x51, 0x51, 0x40, 0x00, 0x00, 0x00, 0x00, 0x2A, (byte) 0xAA, (byte) 0xAA, (byte) 0xA8, (byte) 0xAA, 
				(byte) 0xAA, (byte) 0xAA, (byte) 0xA8, (byte) 0xAA, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, (byte) 0xFF, (byte) 0xFF,
				(byte) 0x80, 0x2A, 0x22, (byte) 0xAA, 0x2A, 0x2A, (byte) 0xAA, (byte) 0xA8, (byte) 0xAA, (byte) 0xA8, (byte) 0x88, (byte) 0xA8, (byte) 0xA0, 0x00, 0x00, 0x00,
				0x00, 0x15, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55, 0x51, 0x11, 0x10 };
		
		ArrayList<Byte> data = new ArrayList<Byte>();
		//SOF
		data.add((byte) 0x44);
		data.add((byte) 0x55);
		//MAC
		byte[] mcStr = hc.toHexData(macAddr);
		for(int i = 0; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		//CF
		data.add((byte)0x04);
		data.add((byte)0x10);
		//LEN
		int short_len = (int)(3 + infrared_data.length + 2);
        data.add((byte)(short_len >> 8));
        data.add((byte)short_len);
		
        //OpCode
		data.add((byte)0x00);
		data.add((byte)0x81);
		data.add((byte)0x00);
		
		//Data
		for(int i = 0 ; i < infrared_data.length; i ++)
			data.add(infrared_data[i]);
		
		//CRC
		mcStr = hc.toHexData(RCR16(data.toArray(), data.toArray().length));
		for(int i = 0 ; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		
		byte[] result = new byte[data.toArray().length];
		for(int i = 0; i < result.length; i ++)
		{
			result[i] = (Byte)data.toArray()[i];
		}
		
		return result;
	}
	
	/**
	 * ����ر�
	 * @return
	 */
	public byte[] redClose()
	{
		hexCode hc = new hexCode();

		byte[] infrared_data = { 0x00, 0x01, (byte) 0xF4, 0x02, 0x22, (byte) 0xFF, (byte) 0xFF, (byte) 0xC0, 
				0x11, 0x51, 0x11, 0x51, 0x51, 0x55, 0x55, 0x11, 0x55, 0x51, 0x45, 0x45, 0x00, 0x00, 0x00, 0x00,
				0x00, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xA2, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, 
				(byte) 0xA8, (byte) 0x88, (byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7F, 
				(byte) 0xFF, (byte) 0xE0, 0x08, (byte) 0xA8, (byte) 0x88, (byte) 0xA8, (byte) 0xA8, (byte) 0xAA, 
				(byte) 0xAA, (byte) 0x88, (byte) 0xAA, (byte) 0xA8, (byte) 0x88, (byte) 0xA8, (byte) 0xA0, 0x00, 
				0x00, 0x00, 0x00, 0x15, 0x55, 0x55, 0x55, 0x55, 0x51, 0x45, 0x55, 0x15, 0x40 };

		
		ArrayList<Byte> data = new ArrayList<Byte>();
		//SOF
		data.add((byte) 0x44);
		data.add((byte) 0x55);
		//MAC
		byte[] mcStr = hc.toHexData(macAddr);
		for(int i = 0; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		//CF
		data.add((byte)0x04);
		data.add((byte)0x10);
		//LEN
		int short_len = (int)(3 + infrared_data.length + 2);
        data.add((byte)(short_len >> 8));
        data.add((byte)short_len);
		
        //OpCode
		data.add((byte)0x00);
		data.add((byte)0x81);
		data.add((byte)0x00);
		
		//Data
		for(int i = 0 ; i < infrared_data.length; i ++)
			data.add(infrared_data[i]);
		
		//CRC
		mcStr = hc.toHexData(RCR16(data.toArray(), data.toArray().length));
		for(int i = 0 ; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		
		byte[] result = new byte[data.toArray().length];
		for(int i = 0; i < result.length; i ++)
		{
			result[i] = (Byte)data.toArray()[i];
		}
		
		return result;

	}
	
	/**
	 * 25��ǿЧ����
	 * @return
	 */
	public byte[] redHHCode25()
	{
		hexCode hc = new hexCode();

		byte[] infrared_data = { 0x00, 0x01, (byte) 0xF4, 0x02, 0x10, (byte) 0xFF,(byte)  0xFF, (byte) 0xC0, 0x15, 
				0x15, 0x54, 0x54, 0x55, 0x55, 0x51, 0x55, 0x51, 0x45, 0x45, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xAA, 
				(byte) 0xAA, (byte) 0xAA, (byte) 0xA2, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xA2, (byte) 0xA2, 
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, (byte) 0xFF, (byte) 0xFF, (byte) 0x80, 0x2A, 
				0x2A, (byte) 0xA8, (byte) 0xA8, (byte) 0xAA, (byte) 0xAA, (byte) 0xA2, (byte) 0xAA, (byte) 0xA2, 0x22, 
				(byte) 0xA2, (byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55, 0x44, 0x45, 0x00 };

		ArrayList<Byte> data = new ArrayList<Byte>();
		//SOF
		data.add((byte) 0x44);
		data.add((byte) 0x55);
		//MAC
		byte[] mcStr = hc.toHexData(macAddr);
		for(int i = 0; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		//CF
		data.add((byte)0x04);
		data.add((byte)0x10);
		//LEN
		int short_len = (int)(3 + infrared_data.length + 2);
        data.add((byte)(short_len >> 8));
        data.add((byte)short_len);
		
        //OpCode
		data.add((byte)0x00);
		data.add((byte)0x81);
		data.add((byte)0x00);
		
		//Data
		for(int i = 0 ; i < infrared_data.length; i ++)
			data.add(infrared_data[i]);
		
		//CRC
		mcStr = hc.toHexData(RCR16(data.toArray(), data.toArray().length));
		for(int i = 0 ; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		
		byte[] result = new byte[data.toArray().length];
		for(int i = 0; i < result.length; i ++)
		{
			result[i] = (Byte)data.toArray()[i];
		}
		
		return result;
	}
	
	/**
	 * 25��ǿЧ�䣬��ɨ�磬�ر�
	 * @return
	 */
	public byte[] redHCode25Close()
	{
		hexCode hc = new hexCode();

		byte[] infrared_data = { 0x00, 0x01, (byte)0xF4, 0x02, 0x1F, (byte)0xFF, (byte)0xFF, (byte)0xC0, 
				0x11, 0x54, 0x45, 0x45, 0x45, 0x55, 0x54, 0x45, 0x6A, (byte)0xA2, (byte)0x8A, (byte) 0x8A, 
				0x00, 0x00, 0x00, 0x00, 0x01, 0x55, 0x55, 0x55, 0x45, 0x55, 0x55, 0x55, 0x51, 0x14, 0x00, 
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03, (byte)0xFF, (byte)0xFF, 0x00, 0x45, 
				0x51, 0x15, 0x15, 0x15, 0x55, 0x51, 0x15, 0x55, 0x11, 0x15, 0x14, 0x00, 0x00, 0x00, 0x00, 0x02, 
				(byte)0xAA, (byte)0xAA, (byte)0xAA, (byte)0xAA, (byte)0xAA, 0x28, (byte)0xAA, (byte)0xA2, (byte)0xA2 };


		ArrayList<Byte> data = new ArrayList<Byte>();
		//SOF
		data.add((byte) 0x44);
		data.add((byte) 0x55);
		//MAC
		byte[] mcStr = hc.toHexData(macAddr);
		for(int i = 0; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		//CF
		data.add((byte)0x04);
		data.add((byte)0x10);
		//LEN
		int short_len = (int)(3 + infrared_data.length + 2);
        data.add((byte)(short_len >> 8));
        data.add((byte)short_len);
		
        //OpCode
		data.add((byte)0x00);
		data.add((byte)0x81);
		data.add((byte)0x00);
		
		//Data
		for(int i = 0 ; i < infrared_data.length; i ++)
			data.add(infrared_data[i]);
		
		//CRC
		mcStr = hc.toHexData(RCR16(data.toArray(), data.toArray().length));
		for(int i = 0 ; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		
		byte[] result = new byte[data.toArray().length];
		for(int i = 0; i < result.length; i ++)
		{
			result[i] = (Byte)data.toArray()[i];
		}
		
		return result;
	}
	
	/**
	 * ��ȡ���ƿ�������
	 * @param number: ���غ�
	 * @return ����ָ�����غŵ�����
	 */
	public String openSwitch(int number, boolean onOrClose)
	{
		hexCode hc = new hexCode();

		ArrayList<Byte> data = new ArrayList<Byte>();
		//SOF
		data.add((byte) 0x44);
		data.add((byte) 0x55);
		//MAC
		byte[] mcStr = hc.toHexData(macAddr);
		for(int i = 0; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		//CF
		data.add((byte)0x04);
		data.add((byte)0x10);
		//LEN
		data.add((byte)0x00);
		data.add((byte)0x05);
		//Channel ID
		switch(number)
		{
		case 1:	data.add((byte)0x01);break;
		case 2:	data.add((byte)0x02);break;
		case 3:	data.add((byte)0x03);break;
		default: data.add((byte)0x01); break;
		}
		//OpCode
		data.add((byte)0x01);
		if(onOrClose)
			data.add((byte)0x00);
		else
			data.add((byte)0x01);
		//CRC
		mcStr = hc.toHexData(RCR16(data.toArray(), data.toArray().length));
		for(int i = 0 ; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		
		byte[] result = new byte[data.toArray().length];
		for(int i = 0; i < result.length; i ++)
		{
			result[i] = (Byte)data.toArray()[i];
		}
		
		return new String(result);
	}
	
	/**
	 * ��ȡ��������
	 * @return ��������ʮ����������
	 */
	public byte[] openDoor()
	{
		hexCode hc = new hexCode();

		ArrayList<Byte> data = new ArrayList<Byte>();
		//SOF
		data.add((byte) 0x44);
		data.add((byte) 0x55);
		//MAC
		byte[] mcStr = hc.toHexData(macAddr);
		for(int i = 0; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		//CF
		data.add((byte)0x04);
		data.add((byte)0x10);
		//LEN
		data.add((byte)0x00);
		data.add((byte)0x05);
		//Channel ID
		data.add((byte)0x00);
		//OpCOde
		data.add((byte)0x00);
		data.add((byte)0x01);
		//CRC
		mcStr = hc.toHexData(RCR16(data.toArray(), data.toArray().length));
		for(int i = 0 ; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		
		byte[] result = new byte[data.toArray().length];
		for(int i = 0; i < result.length; i ++)
		{
			result[i] = (Byte)data.toArray()[i];
		}
		
		return result;
	}
	
	/**
	 * ��ȡ�Ž������ȡ����
	 * @return �Ž������ȡ����
	 */
	public String getPassWord()
	{
		hexCode hc = new hexCode();

		ArrayList<Byte> data = new ArrayList<Byte>();
		//SOF
		data.add((byte) 0x44);
		data.add((byte) 0x55);
		//MAC
		byte[] mcStr = hc.toHexData(macAddr);
		for(int i = 0; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		//CF
		data.add((byte)0x04);
		data.add((byte)0x10);
		//LEN
		data.add((byte)0x00);
		data.add((byte)0x05);
		//Channel ID
		data.add((byte)0x00);
		//OpCode
		data.add((byte)0x00);
		data.add((byte)0x02);
		//CRC
		mcStr = hc.toHexData(RCR16(data.toArray(), data.toArray().length));
		for(int i = 0 ; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		
		byte[] result = new byte[data.toArray().length];
		for(int i = 0; i < result.length; i ++)
		{
			result[i] = (Byte)data.toArray()[i];
		}
		
		return new String(result);
	}
	
	/**
	 * ��ȡ�޸���������
	 * @param passWord ����
	 * @return �޸���������
	 */
	public byte[] editPassWord(String passWord)
	{
		hexCode hc = new hexCode();

		ArrayList<Byte> data = new ArrayList<Byte>();
		//SOF
		data.add((byte) 0x44);
		data.add((byte) 0x55);
		//MAC
		byte[] mcStr = hc.toHexData(macAddr);
		for(int i = 0; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		//CF
		data.add((byte)0x04);
		data.add((byte)0x10);
		//len
		data.add((byte)0x00);
		data.add((byte)0x07);
		//channel Id
		data.add((byte)0x00);
		//Opcode
		data.add((byte)0x00);
		data.add((byte)0x03);
		//pw
		mcStr = hc.toHexData(passWord);
		for(int i = 0; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		//CRC16
		mcStr = hc.toHexData(RCR16(data.toArray(), data.toArray().length));
		for(int i = 0 ; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		
		byte[] result = new byte[data.toArray().length];
		for(int i = 0; i < result.length; i ++)
		{
			result[i] = (Byte)data.toArray()[i];
		}
		
		return result;
	}
	
	/**
	 * ��ȡ���ӻ���ɾ���Ž���������
	 * @param cardNum �Ž�����
	 * @param addOrDec true:���� false:ɾ��
	 * @return
	 */
	public byte[] dealCardNum(String cardNum, boolean addOrDec)
	{
		hexCode hc = new hexCode();

		ArrayList<Byte> data = new ArrayList<Byte>();
		//SOF
		data.add((byte) 0x44);
		data.add((byte) 0x55);
		//MAC
		byte[] mcStr = hc.toHexData(macAddr);
		for(int i = 0; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		//CF
		data.add((byte)0x04);
		data.add((byte)0x10);
		//len
		data.add((byte)0x00);
		data.add((byte)0x07);
		//channel Id
		data.add((byte)0x00);
		//Opcode
		data.add((byte)0x00);
		if(addOrDec)
			data.add((byte)0x04);
		else
			data.add((byte)0x05);
		//pw
		mcStr = hc.toHexData(cardNum);
		for(int i = 0; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		//CRC16
		mcStr = hc.toHexData(RCR16(data.toArray(), data.toArray().length));
		for(int i = 0 ; i < mcStr.length; i ++)
		{
			data.add(mcStr[i]);
		}
		
		byte[] result = new byte[data.toArray().length];
		for(int i = 0; i < result.length; i ++)
		{
			result[i] = (Byte)data.toArray()[i];
		}
		
		return result;
	}
	
	
	/**
	 * ��ȡRCRУ����
	 * @param data
	 * @return
	 */
	protected String RCR16(Object[] data, int len)
	{
		short crc =  (short) 0xffff;
        int i, j = 0;
        for (i = 0; i < len; i++)
        {
            for (j = 0; j < 8; j++)
            {
                boolean c15 = ((crc >> 15 & 1) == 1);
                boolean bit = (((Byte)data[i] >> (int)(7 - j) & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit)
                {
                    crc ^= 0x1021;
                }
            }
        }
        
        int intCrc = 0;
        if(crc < 0)
        {
        	intCrc = 0x7fff & crc;
        	intCrc = intCrc|0x8000;
        }
        else
        	intCrc = crc;
        
        return Integer.toHexString(intCrc);
	}

}
