package com.avit.ads.util;

import java.io.File;
import java.security.MessageDigest;

public class Md5Util {
       public static byte[] encryptMD5(byte[] data) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return md5.digest(data);
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        if (b == null) {
            return hs;
        }
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs;
    }

    public static void main(String[] args) throws Exception {

        String fileName = "C010032010113016430100";
       //String fileSize="38";
        File file = new File("F:\\ftp\\back_93\\receive\\20120822\\C010032010113016430100.DAT");
        String n="\n" ;
      // logger.info(file.length());
        fileName=fileName+file.length()+n;
        String s = byte2hex(encryptMD5(fileName.getBytes()));
      //  logger.info(s);
        System.out.println(s);

//        new GetBossData().run();


//    	String str = "A010022010060318130142\n";
//		String md5str = Md5Util.byte2hex(Md5Util.encryptMD5(str.getBytes()));
//		logger.info(md5str);
//		
//		
//		String str2 = "35cf3ca5f0b170aca610066fd7a874eb";
//		
//		byte[] byts = str2.getBytes();
//		logger.info("byts.length=" + byts.length);
//		for(int i = 0; i < byts.length; i++){
//			logger.info(byts[i]+" ");
//		}
		
		
		
		
    }
}