package com.avit.ads.pushads.ui;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
		List<String> types=new ArrayList<String>();
		List<String> files=new ArrayList<String>();;
		types.add("1");
		types.add("5");
		files.add("/65535.123.1231/init-a.iframe");
		files.add("/65535.123.1231/init-a.ts");
		
		byte bytes[] = new byte[200];// { 50, 0, -1, 28, -24 };
		short nid=0x1234;
		short len = 0x54;
		short looplen=(short)types.size();
		short filelen=0x23;
		String filename="/65535.123.1231/init-a.frame";
		short dataindex=0;
		bytes[dataindex++]=(byte)0xc0;
		bytes[dataindex++]=(byte)(len&0x00ff);
		bytes[dataindex++]=(byte)(nid>>8);
		bytes[dataindex++]=(byte)(nid&0x00ff);
		bytes[dataindex++]=(byte)0xff;
		bytes[dataindex++]=(byte)0xff;
		bytes[dataindex++]=(byte)(looplen&0x00ff);
		String tt;
		
		for (int i=0;i<types.size();i++)
		{
			bytes[dataindex++]=(byte)Integer.valueOf(types.get(i)).intValue();
			bytes[dataindex++]=(byte)Integer.valueOf(types.get(i)).intValue();
			
			byte filebytes[]= files.get(i).getBytes("ISO-8859-1");
			bytes[dataindex++]=(byte)filebytes.length;			
			for (int j=0;j<filebytes.length;j++)
			{
				bytes[dataindex++]=filebytes[j];
			}
			//tt= byte2HexStr(bytes);
			//bytes[9]=(byte)(filelen&0x00ff);
			//tt= byte2HexStr(bytes);
			//bytes= filename.getBytes("ISO-8859-1");
			//tt= byte2HexStr(bytes);
		}
		len = (short)(dataindex-1);
		bytes[1]=(byte)(len&0x00ff);	
		tt= byte2HexStr(bytes).substring(0,(len+1)*2);
		String isoString = new String(bytes, "ISO-8859-1");
		isoString=decode("C0A603E9FFFF05");
		//isoString ="C0A603E9FFFF05";
		
		byte[] isoret = isoString.getBytes("ISO-8859-1");
		isoString="";
		}
		catch(Exception e)
		{
			
		}
	}
	private static String hexString="0123456789ABCDEF";
	/*
	* 将字符串编码成16进制数字,适用于所有字符（包括中文）
	*/
	public static String encode(String str)
	{
	// 根据默认编码获取字节数组
	byte[] bytes=str.getBytes();
	StringBuilder sb=new StringBuilder(bytes.length*2);
	// 将字节数组中每个字节拆解成2位16进制整数
	for(int i=0;i<bytes.length;i++)
	{
	sb.append(hexString.charAt((bytes[i]&0xf0)>>4));
	sb.append(hexString.charAt((bytes[i]&0x0f)>>0));
	}
	return sb.toString();
	}
	
	/*
	* 将16进制数字解码成字符串,适用于所有字符（包括中文）
	*/
	public static String decode(String bytes)
	{
	ByteArrayOutputStream baos=new ByteArrayOutputStream(bytes.length()/2);
	// 将每2位16进制整数组装成一个字节
	for(int i=0;i<bytes.length();i+=2)
	baos.write((hexString.indexOf(bytes.charAt(i))<<4 |hexString.indexOf(bytes.charAt(i+1))));
	return new String(baos.toByteArray());
	}
	
	/** 
     * bytes转换成十六进制字符串 
     * @param byte[] b byte数组 
     * @return String 每个Byte值之间空格分隔 
     */  
    public static String byte2HexStr(byte[] b)  
    {  
        String stmp="";  
        StringBuilder sb = new StringBuilder("");  
        for (int n=0;n<b.length;n++)  
        {  
            stmp = Integer.toHexString(b[n] & 0xFF);  
            sb.append((stmp.length()==1)? "0"+stmp : stmp);  
            sb.append("");  
        }  
        return sb.toString().toUpperCase().trim();  
    }  
}
