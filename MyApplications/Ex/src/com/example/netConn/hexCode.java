package com.example.netConn;

public class hexCode {

	public hexCode()
	{
	}
	
	
	/**
	 * 16进制格式字符串转换为数据
	 * @param strLine 目标字符串
	 * @return 字节型数据
	 */
	public byte[] toHexData(String strLine)
	{
		//字符串转为小写
		strLine = strLine.toLowerCase();
		
		char[] charData = strLine.toCharArray();
		int[] data = new int[charData.length];
		int num = 0;
		//过滤输入合法信息
		for(int i = 0; i < charData.length; i ++)
		{
			if(charData[i] != ' ' 
					&& ((charData[i] <= '9' && charData[i] >= '0')
					|| (charData[i] <= 'f' && charData[i] >= 'a')))
			{
				if(charData[i] <= '9')
					data[num] = charData[i] - 0x30;
				else
					data[num] = charData[i] - 0x57;

				num ++;
			}
		}
		
		//得到最新数据
		byte[] charData1 = new byte[num/2];
		
		//开始转换
		for(int i = 0, j = 0; i < data.length && j < charData1.length; j ++)
		{
			byte ch = (byte)(data[i]*16 + data[i+1]);
			charData1[j] = ch;
			i += 2;
		}
		
		//获取最终16进制数据
		return charData1;
	}
	
	/**
	 * 字符串转换为16进制字符串
	 * @param strLine 目标字符串
	 * @return 16进制样式数据
	 */
	public String hexToString(String strLine)
	{
		String str="";
		
		for (int i=0;i<strLine.length();i++) 
		{
			int ch = (int)strLine.charAt(i); 
			String s4 = Integer.toHexString(ch); 
			str = str + s4 + " "; 
		} 
		
		System.out.println(str);
		return str;
	}


}
