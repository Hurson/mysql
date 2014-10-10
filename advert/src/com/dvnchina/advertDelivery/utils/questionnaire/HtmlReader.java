package com.dvnchina.advertDelivery.utils.questionnaire;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;

import com.dvnchina.advertDelivery.constant.TemplateConstant;

public class HtmlReader {
	public HtmlReader() {
	}

	/**
	 * @param filePath
	 *            文件路径
	 * @return 获得html的全部内容
	 */
	public static String readHtml(String filePath) {
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					filePath), TemplateConstant.ENCODING));
			String temp = null;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
				sb.append("\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	/**
	 * @param inputStream
	 *            文件路径
	 * @return 获得html的全部内容
	 */
	public static String readHtml(InputStream inputStream) {
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
			br = new BufferedReader(new InputStreamReader(inputStream, TemplateConstant.ENCODING));
			String temp = null;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
				sb.append("\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	/**
	 * @param path
	 * @param encoding
	 * @return 获得html的全部内容 
	 */
	public static String readHtml(String path ,String encoding){
		String result = null;
		BufferedReader reader = null;
		String line = null;
		StringBuffer buffer = new StringBuffer();
		File file2 = new File(path);
		if(file2.exists()){
			try {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(file2), encoding));
				line = reader.readLine();
				while(line != null){
					buffer.append(line).append("\r\n");
					line = reader.readLine();
				}
				reader.close();
				result = buffer.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}
		return null;
	}

	/**
	 * @param filePath
	 *            文件路径
	 * @return 获得的findTag标签内容
	 */
	public static String replaceTextFromHtmlByTag(String filePath,
			String findTag, String replaceTagContent) {
		// 得到body标签中的内容
		String str = readHtml(filePath);
		StringBuffer buff = new StringBuffer();
		String beginTag = "<" + findTag + ">";
		String endTag = "</" + findTag + ">";
		String findContent = str.substring(str.indexOf(beginTag), str
				.indexOf(endTag)
				+ endTag.length());
		str = StringUtils.replace(str, findContent, replaceTagContent);
		return str;
	}

	public static String findTextFromHtmlByTag(String str, String findTag) {
		String beginTag = "<" + findTag;
		String endTag = "</" + findTag + ">";
		int beginTagIndex = str.indexOf(beginTag);
		int endTagIndex = str.indexOf(endTag);
		String findContent = str.substring(beginTagIndex, endTagIndex
				+ endTag.length());
		return findContent;
	}

	public static String findTextFromHtml(String str, String start, String end) {
		int beginTagIndex = str.indexOf(start) + start.length();
		int endTagIndex = str.indexOf(end);
		String findContent = str.substring(beginTagIndex, endTagIndex);
		return findContent;
	}

	public static String findTextFromHtmlByTag(String str, String start,
			String end) {
		int beginTagIndex = str.indexOf(start);
		int endTagIndex = str.indexOf(end);
		String findContent = str.substring(beginTagIndex, endTagIndex
				+ end.length());
		return findContent;
	}

	public static String findTextFromHtmlByTagWithOutTag(String str,
			String findTag) {
		String beginTag = "<" + findTag;
		String endTag = "</" + findTag + ">";
		int beginTagIndex = str.indexOf(beginTag);
		int endTagIndex = str.indexOf(endTag);
		int startTagEndIndex = str.indexOf(">", beginTagIndex) + 1;
		String findContent = str.substring(startTagEndIndex, endTagIndex);
		return findContent;
	}

	public static String[] findAttributeFromTag(String str, String findTag) {
		String beginTag = "<" + findTag;
		String endTag = ">";
		int beginTagIndex = str.indexOf(beginTag);
		int endTagIndex = str.indexOf(endTag, beginTagIndex);
		String findContent = str.substring(beginTagIndex + beginTag.length(),
				endTagIndex);
		String[] atts = null;

		if (StringUtils.isNotBlank(findContent)) {
			String a = StringUtils.trim(findContent);
			String[] res = StringUtils.split(a, "\"");
			for (int i = 0; i < res.length; i += 2) {
				res[i] = StringUtils.removeEnd(res[i], "=").trim();
				res[i + 1] = res[i + 1].trim();
			}
			return res;
		}
		return null;
	}

	public static String findAttributeFromTag(String str, String findTag,
			String findAttr) {
		String beginTag = "<" + findTag;
		String endTag = ">";
		int beginTagIndex = str.indexOf(beginTag);
		int endTagIndex = str.indexOf(endTag, beginTagIndex);
		String findContent = str.substring(beginTagIndex + beginTag.length(),
				endTagIndex);
		if (StringUtils.isNotBlank(findContent)) {
			String a = StringUtils.trim(findContent);
			String[] res = StringUtils.split(a, "\"");
			if (res.length >= 2 && res[0].contains(findAttr))
				return res[1];
		}
		return null;
	}

	public static String findString(String str, String findString) {
		String beginTag = findString;
		int beginTagIndex = str.indexOf(beginTag) + beginTag.length();
		return str.substring(beginTagIndex, str.indexOf("_", beginTagIndex));

	}

	public static String findSize(String str, String findString) {
		String copyStr=str;
		int index = str.indexOf(findString) + findString.length();
		copyStr=str.substring(index);
		if (str.length() > index) {
			String x = str.substring(index, index + 1);
			if (x.equals("_")) {
				if (str.length() > index + 2) {
					int e = str.substring(index + 2).indexOf("_");
					if (str.length() > index + 1 + e + 1) {
						return str.substring(index + 1, index + 1
								+ e + 1);
					}
				}
			}
		}
		return "";
	}

	public static void main(String[] args) {
	}

}