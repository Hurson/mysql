package com.avit.ads.dtmb.des;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.avit.ads.util.InitConfig;


public class Des {
	/** 加密工具 */
	private Cipher encryptCipher = null;

	/** 解密工具 */
	private Cipher decryptCipher = null;

        private void initialize_encryptKey(String keyValue) throws Exception{
	        Key key = getKey(keyValue.getBytes());
		encryptCipher = Cipher.getInstance("DES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);
	}

	public void initalize_dencryptkey(String keyValue) throws Exception {
                Key key = getKey(keyValue.getBytes());
		decryptCipher = Cipher.getInstance("DES");
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
	}

	/**
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 * 
	 * @param arrBTmp
	 *            构成该字符串的字节数组
	 * @return 生成的密钥
	 * @throws java.lang.Exception
	 */
	private Key getKey(byte[] arrBTmp) throws Exception {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];

		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		// 生成密钥
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
		return key;
	}

	/**
	 * 加密字节数组
	 * 
	 * @param arrB
	 *            需加密的字节数组
	 * @return 加密后的字节数组
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] arrB) throws Exception {
		return encryptCipher.doFinal(arrB);
	}

	/**
	 * 解密字节数组
	 * 
	 * @param arrB
	 *            需解密的字节数组
	 * @return 解密后的字节数组
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] arrB) throws Exception {
		return decryptCipher.doFinal(arrB);
	}

	/**
	 * 文件file进行加密并保存目标文件destFile中
	 * 
	 * @param file
	 *            要加密的文件 如c:/test/srcFile.txt
	 * @param destFile
	 *            加密后存放的文件名 如c:/加密后文件.txt
	 */
	public void encrypt(String sourceFileName, String diminationFileName) throws Exception {
		InputStream is = new FileInputStream(new File(sourceFileName));
		OutputStream out = new FileOutputStream(new File(diminationFileName));
		CipherInputStream cis = new CipherInputStream(is, encryptCipher);
		byte[] buffer = new byte[1024];
		int r;
		while ((r = cis.read(buffer)) > 0) {
			out.write(buffer, 0, r);
		}
		cis.close();
		is.close();
		out.close();
	}

	/**
	 * 文件采用DES算法解密文件
	 * 
	 * @param file
	 *            已加密的文件 如c:/加密后文件.txt * @param destFile 解密后存放的文件名 如c:/
	 *            test/解密后文件.txt
	 */
	public void decrypt(String sourceFileName, String diminationFileName) throws Exception {
		InputStream is = new FileInputStream(sourceFileName);
		OutputStream out = new FileOutputStream(diminationFileName);
		CipherOutputStream cos = new CipherOutputStream(out, decryptCipher);
		byte[] buffer = new byte[1024];
		int r;
		while ((r = is.read(buffer)) >= 0) {
			cos.write(buffer, 0, r);
		}
		cos.close();
		out.close();
		is.close();
	}
	/**
	 * @author hudongyu
	 * @param sourceFileName--需加密文件绝对地址
	 * @param diminationFileName--加密后文件输出绝对地址
	 * @throws Exception
	 */
	public void DesCryptFile(String sourceFileName, String diminationFileName) throws Exception{
		try{
			Des d = new Des();
			String k = InitConfig.getConfigMap().get("des.key");
			d.initialize_encryptKey(k);
			d.encrypt(sourceFileName,diminationFileName);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * @author hudongyu
	 * 将要转换的字符串输入,返回加密后的字符串。
	 * @param String s
	 * @return String r
	 * @throws Exception
	 */
	public String DesCryptString(String s) throws Exception{
		BASE64Encoder enc = new BASE64Encoder();
		BASE64Decoder dec = new BASE64Decoder();
		byte[] b = s.getBytes();
		String k = "12345678";
		initialize_encryptKey(k);
		byte[] c = encrypt(b);
		String r = enc.encode(c);
		return r;
	}
	
	
	
	public static void main(String[] args) throws Exception {
		BASE64Encoder enc = new BASE64Encoder();
		BASE64Decoder dec = new BASE64Decoder();
		Des t = new Des();
		
		String s = "佳创视讯";
		byte[] b = s.getBytes();
		for(byte i :b){
			System.out.print(i + " ");
		}
		System.out.println("\n===============");
		t.initialize_encryptKey("12345678");
		byte[] d = t.encrypt(b);
		for(byte i : d){
			System.out.print(i + " ");
		}
		System.out.println();
		String e = enc.encodeBuffer(d);
		System.out.println(e);
		t.initalize_dencryptkey("12345678");
		byte[] p = dec.decodeBuffer(e);
		byte[] f = t.decrypt(p);
		for(byte i : f){
			System.out.print(i + " ");
		}
		System.out.println();
		String g = new String(f);
		System.out.println("解密后————   " + g);
	}

}
