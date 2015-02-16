package com.avit.ads.pushads.ocg;


public class GenerateFileJni {

	private static GenerateFileJni instance;
	static{
		System.loadLibrary("CreateUNT");
	}
	public native boolean geneTSFile(String xmlData, int tableVersion, int descVersion, String destPath, String logPath);
	
	public static GenerateFileJni getInstance(){
		if(instance == null){
			instance = new GenerateFileJni();
		}
		return instance;
	}
	
	/*	public static void main(String[] args){
		
		System.out.println("begin generateTs.....");
		String xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
				"<sendUNTMessage sendType=\"6\"><channelSubtitle><channelSubtitleElement>" +
				"<tvn_target caUserLevel=\"0\" caIndustryType=\"0\" tvn=\"1\" tvnType=\"2\" serviceID=\"100\"/>" +
				"<subtitleInfo uiID=\"a\" word=\"hello\" showFrequency=\"2\" backgroundColor=\"2\" backgroundHeight=\"1\" backgroundWidth=\"1\" backgroundY=\"1\" backgroundX=\"1\" fontColor=\"1\" fontSize=\"1\" timeout=\"5\" actionType=\"1\"/>" +
				"</channelSubtitleElement><channelSubtitleElement>" +
				"<tvn_target caUserLevel=\"0\" caIndustryType=\"0\" tvn=\"1\" tvnType=\"2\" serviceID=\"101\"/>" +
				"<subtitleInfo uiID=\"a\" word=\"world\" showFrequency=\"2\" backgroundColor=\"2\" " +
				"backgroundHeight=\"1\" backgroundWidth=\"1\" backgroundY=\"1\" backgroundX=\"1\" fontColor=\"1\" fontSize=\"1\" timeout=\"5\" actionType=\"1\"/>" +
				"</channelSubtitleElement><channelSubtitleElement><tvn_target caUserLevel=\"0\" caIndustryType=\"0\" tvn=\"1\" tvnType=\"2\" serviceID=\"102\"/>" +
				"<subtitleInfo uiID=\"a\" word=\"world\" showFrequency=\"2\" backgroundColor=\"2\" backgroundHeight=\"1\" backgroundWidth=\"1\" backgroundY=\"1\" backgroundX=\"1\" " +
				"fontColor=\"1\" fontSize=\"1\" timeout=\"5\" actionType=\"1\"/></channelSubtitleElement></channelSubtitle></sendUNTMessage>";
		//System.out.println(xml);
		//System.out.println(System.getProperty("java.library.path"));
		GenerateFileJni.getInstance().geneTSFile(xml, 1, 1, "/tmp", "/tmp/1.log");
	}
	*/
		/*public static String initIntefaceDebug(){
		
		// sendFile
//		OcgPlayMsg ocgPalyMsg = new OcgPlayMsg();
//		ocgPalyMsg.setSendPath("ftp://abc:abc@192.168.128.128/ui");
//		ocgPalyMsg.setSendType("1");
		
		// sendUIMessage
//		UiUpdateMsg uiMsg = new UiUpdateMsg();
//		uiMsg.setUpdateType("1:initPic-c.iframe,5:0");
//		uiMsg.setNetworkID("6280");
//		uiMsg.setServicesID("0");
//		uiMsg.setTsID("10086");

		//UNTMessage uNTMsg = initUNTMsg(1);
		//UNTMessage uNTMsg = initUNTMsg(2);
		//UNTMessage uNTMsg = initUNTMsg(3);
		//UNTMessage uNTMsg = initUNTMsg(4);
		//UNTMessage uNTMsg = initUNTMsg(5);
		//UNTMessage uNTMsg = initUNTMsg(6);
		
		//JaxbXmlObjectConvertor jaxbhelper = JaxbXmlObjectConvertor.getInstance();
        //String ocgXml = jaxbhelper.toXML(ocgPalyMsg);
        //String ocgXml = jaxbhelper.toXML(uiMsg);
        //String ocgXml = jaxbhelper.toXML(uNTMsg);
        //return ocgXml;
		
		TvnTarget tvnTarget1 = new TvnTarget();
		tvnTarget1.setServiceID("100");
		tvnTarget1.setTvnType("2");
		tvnTarget1.setTvn("1");
		tvnTarget1.setCaIndustryType("0");
		tvnTarget1.setCaUserLevel("0");
		
		MsubtitleInfo subtitle1 = new MsubtitleInfo();
		subtitle1.setUiId("a");
		subtitle1.setActionType("1");
		subtitle1.setTimeout("5");
		subtitle1.setFontColor("1");
		subtitle1.setFontSize("1");
		subtitle1.setBackgroundX("1");
		subtitle1.setBackgroundY("1");
		subtitle1.setBackgroundWidth("1");
		subtitle1.setBackgroundHeight("1");
		subtitle1.setBackgroundColor("2");
		subtitle1.setShowFrequency("2");
		subtitle1.setWord("hello");
		
		ChannelSubtitleElement elem1 = new ChannelSubtitleElement();
		elem1.setTvnTarget(tvnTarget1);
		elem1.setSubtitleInfo(subtitle1);
		
		
		TvnTarget tvnTarget2 = new TvnTarget();
		tvnTarget2.setServiceID("101");
		tvnTarget2.setTvnType("2");
		tvnTarget2.setTvn("1");
		tvnTarget2.setCaIndustryType("0");
		tvnTarget2.setCaUserLevel("0");
		
		MsubtitleInfo subtitle2 = new MsubtitleInfo();
		subtitle2.setUiId("a");
		subtitle2.setActionType("1");
		subtitle2.setTimeout("5");
		subtitle2.setFontColor("1");
		subtitle2.setFontSize("1");
		subtitle2.setBackgroundX("1");
		subtitle2.setBackgroundY("1");
		subtitle2.setBackgroundWidth("1");
		subtitle2.setBackgroundHeight("1");
		subtitle2.setBackgroundColor("2");
		subtitle2.setShowFrequency("2");
		subtitle2.setWord("world");
		
		ChannelSubtitleElement elem2 = new ChannelSubtitleElement();
		elem2.setTvnTarget(tvnTarget2);
		elem2.setSubtitleInfo(subtitle2);
		
		TvnTarget tvnTarget3 = new TvnTarget();
		tvnTarget3.setServiceID("102");
		tvnTarget3.setTvnType("2");
		tvnTarget3.setTvn("1");
		tvnTarget3.setCaIndustryType("0");
		tvnTarget3.setCaUserLevel("0");
		
		MsubtitleInfo subtitle3 = new MsubtitleInfo();
		subtitle3.setUiId("a");
		subtitle3.setActionType("1");
		subtitle3.setTimeout("5");
		subtitle3.setFontColor("1");
		subtitle3.setFontSize("1");
		subtitle3.setBackgroundX("1");
		subtitle3.setBackgroundY("1");
		subtitle3.setBackgroundWidth("1");
		subtitle3.setBackgroundHeight("1");
		subtitle3.setBackgroundColor("2");
		subtitle3.setShowFrequency("2");
		subtitle3.setWord("world");
		
		ChannelSubtitleElement elem3 = new ChannelSubtitleElement();
		elem3.setTvnTarget(tvnTarget3);
		elem3.setSubtitleInfo(subtitle3);
		
		ChannelSubtitle channelSubtitle = new ChannelSubtitle();
		List<ChannelSubtitleElement> list = new ArrayList<ChannelSubtitleElement>();
		list.add(elem1);
		list.add(elem2);
		list.add(elem3);
		channelSubtitle.setChannelSubtitleElemList(list);
		
		UNTMessage untMsg = new UNTMessage();
		untMsg.setSendType("6");
		untMsg.setChannelSubtitle(channelSubtitle);
		
		JaxbXmlObjectConvertor jaxbhelper = JaxbXmlObjectConvertor.getInstance();
       
        String ocgXml = jaxbhelper.toXML(untMsg);
        return ocgXml;
	}*/
}
