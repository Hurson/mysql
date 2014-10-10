package cn.com.avit.ads.syndata;

import java.util.Date;

public class SynDataJob {
	public void synData() {
		
		SynMMSPData mmspdata= new 	SynMMSPData();
		mmspdata.synData();
//		SynBsmpData bsmpdata= new 	SynBsmpData();
//		SynCpsData cpsdata = new SynCpsData();
//		SynNpvrData npvrdata = new SynNpvrData();
		SynEpgData epgdata = new SynEpgData();
//	
//		System.out.println("npvrdata begin "+new Date());
//		npvrdata.synNpvrChannel();
//		System.out.println("npvrdata end "+new Date());
//		System.out.println("epgdata begin "+new Date());
//		epgdata.synEPGChannel();
//		System.out.println("epgdata end "+new Date());
//		System.out.println("bsmpdata begin "+new Date());
//		bsmpdata.synData();
//		System.out.println("bsmpdata end "+new Date());
//		System.out.println("cpsdata begin "+new Date());
//		cpsdata.synCPSData();
//		System.out.println("cpsdata end "+new Date());

	}
	public static void main(String args[]) {
		SynDataJob syn = new SynDataJob();
		syn.synData();
	}
	

}
