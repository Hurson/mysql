package com.avit.ads.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: Hemeijin Date: 2011-7-19 Time: 8:55:34 To
 * change this template use File | Settings | File Templates.
 */
public class ParseMessageUtil {
	/**
	 * @param message
	 *            报文
	 * @return 获得报文的第一行内容
	 * @author hemeijin
	 */
	public static String getFirstRowContent(String message) {
		String resultContent = null;
		int index = 0;
		int index2 = 0;
		int index3 = 0;
		if (!StringHelper.isNotNull(message)) {
			return null;
		}
		index = message.trim().indexOf(ConstantsHelper.CRLF);
		index2 = message.trim().indexOf(ConstantsHelper.COLON, index);
		index3 = message.trim().lastIndexOf(ConstantsHelper.CRLF, index2);
		if (index > 0) {
			resultContent = message.substring(0, index3).trim();
		}
		return resultContent;
	}

	/**
	 * @param message
	 * @param key
	 * @return S1 接口 的第一行内容 ，根据key 得到 value
	 */
	public static String getFirstRowInfoForS1(String message, String key) {
		/*
		 * String value = null; if (!StringHelper.isNotNull(message) ||
		 * !StringHelper.isNotNull(key)) { return null; } String firstRowContent
		 * = getFirstRowContent(message.trim()); String[] contentArr =
		 * firstRowContent.split(ConstantsHelper.SEMICOLON); for (int i = 0; i <
		 * contentArr.length; i++) { if
		 * (contentArr[i].trim().startsWith(key.trim())) { value =
		 * contentArr[i].trim().split(ConstantsHelper.EQUATE)[1].trim(); break;
		 * } } return value;
		 */
		return getFirstRowInfoForS1IgnoreCast(message, key);
	}

	/**
	 * 可以忽略Key的大小写
	 * 
	 * @param message
	 * @param key
	 * @return S1 接口 的第一行内容 ，根据key 得到 value
	 */
	private static String getFirstRowInfoForS1IgnoreCast(String message,
			String key) {
		String value = null;
		if (!StringHelper.isNotNull(message) || !StringHelper.isNotNull(key)) {
			return null;
		}
		String firstRowContent = getFirstRowContent(message.trim());
		String[] contentArr = firstRowContent.split(ConstantsHelper.SEMICOLON);
		for (int i = 0; i < contentArr.length; i++) {
			if (contentArr[i].trim().toLowerCase()
					.startsWith(key.trim().toLowerCase())) {
				value = contentArr[i].trim().split(ConstantsHelper.EQUATE)[1]
						.trim();
				break;
			}
		}
		return value;
	}

	/**
	 * @param message
	 *            报文
	 * @param key
	 *            字段名
	 * @return 通过key 取得value，适合key-value在一行的。 比如传入OnDemandSessionId 得到
	 *         be074250cc5a11d98cd50800200c9a66
	 * @author hemeijin
	 */
	public static String getValueByKey(String message, String key) {
		/*
		 * String nodeText = null; if (!StringHelper.isNotNull(message) ||
		 * !StringHelper.isNotNull(key)) { return null; }
		 * 
		 * int index =
		 * message.toLowerCase().indexOf(key.trim().toLowerCase());//
		 * 使用返回从指定位置开始第一次搜索到指定字符串的索引的方法 // 再从第一次搜索到的地方再向后搜索 if (index > 0) {
		 * nodeText = (message.substring(index + key.trim().length() + 1,
		 * message.indexOf( ConstantsHelper.CRLF, index))).trim(); } return
		 * nodeText;
		 */
		return getValueByKeyIgnoreCast(message, key);
	}

	/**
	 * 可以忽略Key 的大小写
	 * 
	 * @param message
	 *            报文
	 * @param key
	 *            字段名
	 * @return 通过key 取得value，适合key-value在一行的。 比如传入OnDemandSessionId 得到
	 *         be074250cc5a11d98cd50800200c9a66
	 * @author hemeijin
	 */
	private static String getValueByKeyIgnoreCast(String message, String key) {
		String nodeText = null;
		if (!StringHelper.isNotNull(message) || !StringHelper.isNotNull(key)) {
			return null;
		}
		if (ConstantsHelper.SESSION.equalsIgnoreCase(key.trim())) {
			key = key + ":";
			int index = message.toLowerCase().indexOf(key.trim().toLowerCase());// 使用返回从指定位置开始第一次搜索到指定字符串的索引的方法
			// 再从第一次搜索到的地方再向后搜索
			if (index > 0) {
				nodeText = (message.substring(index + key.trim().length(),
						message.indexOf(ConstantsHelper.CRLF, index))).trim();
			}
		} else {
			int index = message.toLowerCase().indexOf(key.trim().toLowerCase());// 使用返回从指定位置开始第一次搜索到指定字符串的索引的方法
			// 再从第一次搜索到的地方再向后搜索
			if (index > 0) {
				nodeText = (message.substring(index + key.trim().length() + 1,
						message.indexOf(ConstantsHelper.CRLF, index))).trim();
			}
		}
		return nodeText;
	}

	/**
	 * @param message
	 *            报文
	 * @param key
	 *            字段名
	 * @return 通过key 取得value，适合key-value在一行的,key相同的，value拼接成窜，默认用逗号分割。
	 * @author hemeijin
	 */
	public static String getGroupValueByKey(String message, String key) {
		/*
		 * String nodeText = null; int nextIndex = 0; int countNum = 0; if
		 * (!StringHelper.isNotNull(message) || !StringHelper.isNotNull(key)) {
		 * return null; } while (nextIndex < message.trim().length()) { int
		 * index = message.indexOf(key.trim(), nextIndex);//
		 * 使用返回从指定位置开始第一次搜索到指定字符串的索引的方法 // 再从第一次搜索到的地方再向后搜索 if (index == -1) {
		 * break; } else { if (countNum == 0) { nodeText =
		 * (message.substring(index + key.trim().length() + 1, message.indexOf(
		 * ConstantsHelper.CRLF, index))).trim(); } else { nodeText = nodeText +
		 * ConstantsHelper.COMMA.trim() + (message.substring(index +
		 * key.trim().length() + 1, message.indexOf( ConstantsHelper.CRLF,
		 * index))).trim(); } nextIndex = index + key.trim().length();//
		 * 找到的是出现该字符串的首位置，所以还得加上要搜索的字符串的长度 countNum++; } } return nodeText;
		 */
		return getGroupValueByKeyIgnoreCast(message, key);
	}

	/**
	 * 可以忽略Key 的大小写
	 * 
	 * @param message
	 *            报文
	 * @param key
	 *            字段名
	 * @return 通过key 取得value，适合key-value在一行的,key相同的，value拼接成窜，默认用逗号分割。
	 * @author hemeijin
	 */
	private static String getGroupValueByKeyIgnoreCast(String message,
			String key) {
		String nodeText = null;
		int nextIndex = 0;
		int countNum = 0;
		if (!StringHelper.isNotNull(message) || !StringHelper.isNotNull(key)) {
			return null;
		}
		while (nextIndex < message.trim().length()) {
			int index = message.toLowerCase().indexOf(key.trim().toLowerCase(),
					nextIndex);// 使用返回从指定位置开始第一次搜索到指定字符串的索引的方法
			// 再从第一次搜索到的地方再向后搜索
			if (index == -1) {
				break;
			} else {
				if (countNum == 0) {
					nodeText = (message.substring(index + key.trim().length()
							+ 1, message.indexOf(ConstantsHelper.CRLF, index)))
							.trim();
				} else {
					nodeText = nodeText
							+ ConstantsHelper.COMMA.trim()
							+ (message.substring(index + key.trim().length()
									+ 1, message.indexOf(ConstantsHelper.CRLF,
									index))).trim();
				}
				nextIndex = index + key.trim().length();// 找到的是出现该字符串的首位置，所以还得加上要搜索的字符串的长度
				countNum++;
			}
		}
		return nodeText;
	}

	/**
	 * @param message
	 *            报文
	 * @return 获取InbandMarker的内容，适合key-value在一行的和不在一行的。
	 * @author hemeijin
	 */
	public static String getInbandMarkerContent(String message) {
		return getMultiContent(message, ConstantsHelper.INBANDMARKER,
				ConstantsHelper.COLON);
	}

	/**
	 * @param message
	 *            报文
	 * @return 获取Transport的内容，适合key-value在一行的和不在一行的。
	 * @author hemeijin
	 */
	public static String getTransportContent(String message) {
		return getMultiContent(message, ConstantsHelper.TRANSPORT,
				ConstantsHelper.COLON);
	}

	/**
	 * @param message
	 *            报文
	 * @return 获取Transport的内容，适合key-value在一行的和不在一行的,并且多个Transport的情行，用UDP,QAM标识。
	 *         如获取某个 Transport 的内容
	 * @author hemeijin
	 */
	public static String getTransportContentBuyUDP(String message) {
		return getMultiContentBuyMarker(message, ConstantsHelper.TRANSPORT,
				ConstantsHelper.COLON, ConstantsHelper.UDP);
	}

	/**
	 * @param message
	 *            报文
	 * @return 获取Transport的内容，适合key-value在一行的和不在一行的,并且多个Transport的情行，用UDP,QAM标识。
	 *         如获取某个 Transport 的内容
	 * @author hemeijin
	 */
	public static String getTransportContentBuyQAM(String message) {
		return getMultiContentBuyMarker(message, ConstantsHelper.TRANSPORT,
				ConstantsHelper.COLON, ConstantsHelper.QAM);
	}

	/**
	 * @param message
	 *            报文
	 * @param key
	 *            字段名
	 * @param reg
	 *            拆分符 如："," ":" ";" 等
	 * @return 通过key 取得多行内容，适合key-value在一行的和不在一行的。 如获取：Transport ,InbandMarker
	 *         的内容
	 * @author hemeijin
	 */
	public static String getMultiContent(String message, String key, String reg) {
		String resultContent = null;
		int index1 = 0;
		int index2 = 0;
		int index3 = 0;
		if (!StringHelper.isNotNull(message) || !StringHelper.isNotNull(key)
				|| !StringHelper.isNotNull(reg)) {
			return null;
		}
		if (message.toLowerCase().indexOf(key.trim().toLowerCase()) > 0) {
			index1 = message.toLowerCase().indexOf(key.trim().toLowerCase())
					+ key.trim().length() + 1;
			index2 = message.indexOf(reg.trim(), index1);
			index3 = message.lastIndexOf(ConstantsHelper.CRLF, index2);
			if (index2 <= 0) {
				index3 = message.lastIndexOf(ConstantsHelper.CRLF);
			}
			resultContent = message.substring(index1, index3).trim();
		}
		return resultContent;
	}

	/**
	 * @param message
	 *            报文
	 * @param key
	 *            字段名
	 * @param reg
	 *            拆分符 如："," ":" ";" 等
	 * @return 通过key 取得多行内容，如：适合多个Transport的情行，用UDP,QAM标识。 如获取某个 Transport 的内容
	 * @author hemeijin
	 */
	private static String getMultiContentBuyMarker(String message, String key,
			String reg, String marker) {
		String resultContent = null;
		int index1 = 0;
		int index2 = 0;
		int index3 = 0;
		if (!StringHelper.isNotNull(message) || !StringHelper.isNotNull(key)
				|| !StringHelper.isNotNull(reg)) {
			return null;
		}
		while (true) {
			if (message.trim().toLowerCase()
					.indexOf(key.trim().toLowerCase(), index1) > 0) {
				index1 = message.trim().toLowerCase()
						.indexOf(key.trim().toLowerCase(), index1)
						+ key.trim().length() + 1;
				index2 = message.trim().indexOf(reg.trim(), index1);
				index3 = message.trim().lastIndexOf(ConstantsHelper.CRLF,
						index2);
				String content = message.substring(index1, index3).trim();
				if (content.indexOf(marker) > 0) {
					return content;
				} else {
					index1 = index3;
				}
			} else {
				break;
			}
		}
		return resultContent;
	}

	/**
	 * @param inbandMarkerContent
	 *            getInbandMarkerContent方法的返回值。InbandMarker的内容
	 * @return 获取InbandMarker几条记录 封装成一个list返回
	 * @author hemeijin
	 */
	public static List<Map<String, String>> getInbandMarkerContentList(
			String inbandMarkerContent) {
		return getMultiContentList(inbandMarkerContent);
	}

	/**
	 * @param transportContent
	 *            getTransportContent方法的返回值。Transport的内容
	 * @return 获取Transport几条记录 封装成一个list返回
	 * @author hemeijin
	 */
	public static List<Map<String, String>> getTransportContentList(
			String transportContent) {
		return getMultiContentList(transportContent);
	}

	/**
	 * @param multiContent
	 *            比如说Transport的内容
	 * @return 获取Transport几条记录 返回一个list ，list里面的对象是map
	 * @author hemeijin
	 */
	private static List<Map<String, String>> getMultiContentList(
			String multiContent) {
		List<Map<String, String>> multiContentList = new ArrayList<Map<String, String>>();
		if (!StringHelper.isNotNull(multiContent)) {
			return null;
		}
		String[] multiContentArr = multiContent.split(ConstantsHelper.COMMA);
		for (int i = 0; i < multiContentArr.length; i++) {
			Map<String, String> multiContentMap = new HashMap<String, String>();
			String[] keyValues = multiContentArr[i].trim().split(
					ConstantsHelper.SEMICOLON);
			for (int j = 0; j < keyValues.length; j++) {
				String[] keyValue = keyValues[j].trim().split(
						ConstantsHelper.EQUATE);
				if (keyValue.length != 2) {
					multiContentMap.put(keyValue[0].trim(), keyValue[0].trim());
				} else {
					multiContentMap.put(keyValue[0].trim(), keyValue[1].trim());
				}
			}
			multiContentList.add(multiContentMap);
		}
		return multiContentList;
	}

	/**
	 * @param inbandMarkerContentList
	 *            getInbandMarkerContentList方法的返回值。根据多行内容按逗号分隔获得的list,
	 *            里面的每个元素是Map
	 * @param innerKey
	 *            字段名 如：bandwidth
	 * @param index
	 *            List的第几个元素 如：0，代表List的第一个元素
	 * @return 根据innerKey，index 获取value 比如获取Transport的第几个元素的bandwidth的值
	 * @author hemeijin
	 */
	public static String getInbandMarkerContentListInfo(
			List<Map<String, String>> inbandMarkerContentList, String innerKey,
			Integer index) {
		return getMultiContentListInfo(inbandMarkerContentList, innerKey, index);
	}

	/**
	 * @param transportContentList
	 *            getTransportContentList方法的返回值。根据多行内容按逗号分隔获得的list,里面的每个元素是Map
	 * @param innerKey
	 *            字段名 如：bandwidth
	 * @param index
	 *            List的第几个元素 如：0，代表List的第一个元素
	 * @return 根据innerKey，index 获取value 比如获取Transport的第几个元素的bandwidth的值
	 * @author hemeijin
	 */
	public static String getTransportListInfo(
			List<Map<String, String>> transportContentList, String innerKey,
			Integer index) {
		return getMultiContentListInfo(transportContentList, innerKey, index);
	}

	/**
	 * @param multiContentList
	 *            根据多行内容按逗号分隔获得的list,里面的每个元素是Map
	 * @param innerKey
	 *            字段名 如：bandwidth
	 * @param index
	 *            List的第几个元素 如：0，代表List的第一个元素
	 * @return 根据innerKey，index 获取value
	 *         比如根据key：bandwidth获取Transport的第index+1个元素的bandwidth的值
	 * @author hemeijin
	 */
	private static String getMultiContentListInfo(
			List<Map<String, String>> multiContentList, String innerKey,
			Integer index) {
		Map<String, String> transportDataMap = multiContentList.get(index);
		return transportDataMap.get(innerKey);
	}

	/**
	 * @param message
	 *            报文
	 * @return 获取Content-Type: application/sdp 时，下面的内容
	 * @author hemeijin
	 */
	public static String getSDPContent(String message) {
		String resultContent = null;
		int index1 = 0;
		int index2 = 0;
		int index3 = 0;
		if (!StringHelper.isNotNull(message)) {
			return null;
		}
		index1 = message.trim().toLowerCase()
				.indexOf(ConstantsHelper.APPLICATION_SDP.trim().toLowerCase());
		if (index1 > 0) {
			index2 = message
					.trim()
					.toLowerCase()
					.indexOf(
							ConstantsHelper.CONTENT_LENGTH.trim().toLowerCase());
			index3 = message.trim().indexOf(ConstantsHelper.CRLF, index2);
			resultContent = message.trim().substring(index3).trim();
		}
		return resultContent;
	}

	/**
	 * @param message
	 *            报文
	 * @return 获取Content-Type: text/parameters 时，下面的内容
	 * @author hemeijin
	 */
	public static String getParametersContent(String message) {
		String resultContent = null;
		int index1 = 0;
		int index2 = 0;
		int index3 = 0;
		if (!StringHelper.isNotNull(message)) {
			return null;
		}
		index1 = message.trim().toLowerCase()
				.indexOf(ConstantsHelper.TEXT_PARAMETERS.trim().toLowerCase());
		if (index1 > 0) {
			index2 = message
					.trim()
					.toLowerCase()
					.indexOf(
							ConstantsHelper.CONTENT_LENGTH.trim().toLowerCase());
			index3 = message.trim().indexOf(ConstantsHelper.CRLF, index2);
			resultContent = message.substring(index3).trim();
		}
		return resultContent;
	}

	/**
	 * @param sdPContent
	 *            getSDPContent 方法的返回值
	 * @return 返回SDPContent拆分的內容封裝的Map
	 */
	public static Map<String, String> getSDPContentMap(String sdPContent) {
		Map<String, String> sdpContentMap = new HashMap<String, String>();
		if (!StringHelper.isNotNull(sdPContent)) {
			return null;
		}
		String[] sdpArr = sdPContent.split(ConstantsHelper.CRLF);
		for (int i = 0; i < sdpArr.length; i++) {
			String[] keyValue = sdpArr[i].trim().split(ConstantsHelper.EQUATE);
			if (keyValue.length != 2) {
				if (sdpArr[i].indexOf(ConstantsHelper.EQUATE) > 0) {
					sdpContentMap.put(keyValue[0].trim(), "");
				} else {
					String putkey = sdpArr[i - 1].split(ConstantsHelper.EQUATE)[0]
							.trim();
					String putvalue = sdpArr[i - 1]
							.split(ConstantsHelper.EQUATE)[1].trim()
							+ sdpArr[i].trim();
					sdpContentMap.put(putkey, putvalue);
				}
			} else {
				sdpContentMap.put(keyValue[0].trim(), keyValue[1].trim());
			}
		}
		return sdpContentMap;
	}

	/**
	 * @param sdpContent
	 *            getSDPContent方法返回
	 * @param innerKey
	 * @return
	 */
	public static String getSDPContentMapInfo(String sdpContent, String innerKey) {
		Map<String, String> sdpContentMap = getSDPContentMap(sdpContent);
		return sdpContentMap.get(innerKey);
	}

	/**
	 * 通过第一行的记录，取得状态码
	 * 
	 * @param firstRowContent
	 * @return
	 */
	public static int getCodeFromFirstRowContent(String firstRowContent) {

		if (firstRowContent == null) {
			return -1;
		}
		String[] ss = firstRowContent.trim().split(
				ConstantsHelper.SPACE + ConstantsHelper.PLUS);
		return Integer.parseInt(ss[1]);
	}

	/**
	 * 通过第一行的记录，和状态码，取得状态信息
	 * 
	 * @param code
	 *            状态码
	 * @param firstRowContent
	 * @return
	 */
	public static String getCodeMessageFromFirstRowContent(int code,
			String firstRowContent) {
		if (firstRowContent == null) {
			return null;
		}
		String codeMessage = firstRowContent.substring(firstRowContent
				.indexOf(ConstantsHelper.SPACE + code + ConstantsHelper.SPACE)
				+ (ConstantsHelper.SPACE + code + ConstantsHelper.SPACE)
						.length());
		return codeMessage.trim();
	}
}
