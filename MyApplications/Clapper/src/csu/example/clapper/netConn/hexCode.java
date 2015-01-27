package csu.example.clapper.netConn;

public class hexCode {

	public hexCode()
	{
	}
	
	
	/**
	 * 16���Ƹ�ʽ�ַ���ת��Ϊ����
	 * @param strLine Ŀ���ַ���
	 * @return �ֽ�������
	 */
	public byte[] toHexData(String strLine)
	{
		//�ַ���תΪСд
		strLine = strLine.toLowerCase();
		
		char[] charData = strLine.toCharArray();
		int[] data = new int[charData.length];
		int num = 0;
		//��������Ϸ���Ϣ
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
		
		//�õ���������
		byte[] charData1 = new byte[num/2];
		
		//��ʼת��
		for(int i = 0, j = 0; i < data.length && j < charData1.length; j ++)
		{
			byte ch = (byte)(data[i]*16 + data[i+1]);
			charData1[j] = ch;
			i += 2;
		}
		
		//��ȡ����16��������
		return charData1;
	}
	
	/**
	 * �ַ���ת��Ϊ16�����ַ���
	 * @param strLine Ŀ���ַ���
	 * @return 16������ʽ����
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
