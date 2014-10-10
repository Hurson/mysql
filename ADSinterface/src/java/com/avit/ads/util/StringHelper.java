package com.avit.ads.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by IntelliJ IDEA.
 * User: Hemeijin
 * Date: 2011-7-19
 * Time: 9:10:46
 * To change this template use File | Settings | File Templates.
 */
public class StringHelper {

    /**
     * 转换Html标记
     *
     * @param input
     * @return
     */
    public static String escapeHTMLTags(String input) {
        if (input == null || input.length() == 0) {
            return input;
        }

        StringBuffer buf = new StringBuffer(input.length() + 6);
        char ch = ' ';
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            if (ch == '<') {
                buf.append("&lt;");
            } else if (ch == '>') {
                buf.append("&gt;");
            } else if (ch == '"') {
                buf.append("&quot;");
            }
            // Convert single quote
            else if (ch == '\'') {
                buf.append("&#39;");
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

    /**
     * @param line
     * @param oldString
     * @param newString
     * @return
     */
    private static String replace(String line, String oldString, String newString) {
        if (line == null) {
            return null;
        }
        int i = 0;
        if ((i = line.indexOf(oldString, i)) >= 0) {
            char[] line2 = line.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i = line.indexOf(oldString, i)) > 0) {
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            return buf.toString();
        }

        return line;
    }

    /**
     * 实现原理<br>
     * 用getBytes(encoding)：返回字符串的一个byte数组<br>
     * 当b[0]为 63时，应该是转码错误<br>
     * A、不乱码的汉字字符串： <br>
     * 1、encoding用GB2312时，每byte是负数；<br>
     * 2、encoding用ISO8859_1时，b[i]全是63。<br>
     * B、乱码的汉字字符串：<br>
     * 1、encoding用ISO8859_1时，每byte也是负数；<br>
     * 2、encoding用GB2312时，b[i]大部分是63。<br>
     * C、英文字符串<br>
     * 1、encoding用ISO8859_1和GB2312时，每byte都大于0；<br>
     * <p/>
     * 总结：给定一个字符串，用getBytes("iso8859_1") <br>
     * 1、如果b[i]有63，不用转码； A-2<br>
     * 2、如果b[i]全大于0，那么为英文字符串，不用转码； B-1<br>
     * 3、如果b[i]有小于0的，那么已经乱码，要转码。 C-1 <br>
     */
    public static String toGBK(String source) {
        if (source == null) {
            return null;
        }

        if (source.trim().equals("")) {
            return source;
        }

        String retStr = source;

        try {
            byte b[] = source.getBytes("ISO8859_1");

            for (int i = 0; i < b.length; i++) {
                byte b1 = b[i];
                if (b1 == 63) {
                    break; // 1
                } else if (b1 > 0) {
                    continue;// 2
                } else if (b1 < 0) {
                    // 不可能为0，0为字符串结束符
                    retStr = new String(b, "GBK");
                    break;
                }
            }
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return retStr;
    }

    /**
     * 将一个字符数组转换为GBK编码
     *
     * @param source
     * @return
     */
    public static String[] toGBK(String[] source) {
        String[] ret = new String[source.length];
        for (int i = 0; i < source.length; i++) {
            ret[i] = toGBK(source[i]);
        }

        return ret;
    }

    /**
     * 判断字符串不为null同时不为""
     *
     * @param value
     * @return
     */
    public static boolean isNotNull(String value) {
        if (null != value && !"".equals(value.trim())) {
            return true;
        }

        return false;
    }

    /**
     * @param value
     * @return
     */
    public static boolean isNotNull(Object value) {
        // 如果对象不为空
        if (null != value) {
            // 如果对象是字符串，且对象为空格，返回 false，其他返回true
            if (value instanceof String && "".equals(((String) value).trim())) {
                return false;
            }

            return true;
        }

        return false;
    }

    /**
     * 判断字符串数组不为null同时不为空
     *
     * @param value
     * @return
     */
    public static boolean isNotNull(String[] value) {
        if (null != value && value.length != 0) {
            return true;
        }

        return false;
    }

    /**
     * 将字符串数组转为以arg分隔的字符串<br>
     *
     * @param args
     * @param arg
     * @return
     */
    public static String array2String(String[] args, String arg) {
        StringBuffer sb = new StringBuffer();

        // 数组为空,直接返回空字符串
        if (args == null || args.length <= 0) {
            return "";
        }

        for (int i = 0; i < args.length; i++) {
            // 数组元素为空或空字符串，则转换为空格
            if (args[i] == null || args[i].trim().equals("")) {
                sb.append(arg + "");
            } else {
                sb.append(arg + args[i]);
            }
        }

        // 去除第一个分隔符
        if (sb.length() > 0) {
            return sb.substring(arg.length());
        }

        return sb.toString();
    }

    /**
     * 截断字符串，中文字符按两个计算，截断后以指定字符代替
     *
     * @param source
     * @return
     */
    public static String truncateString(String source, int maxLength, String substitute) {
        if (StringHelper.isNotNull(source)) {
            char[] array = source.toCharArray();
            int length = 0;
            for (int i = 0; i < array.length; i++) {
                char c = array[i];
                if (c <= 0 || c >= 126) {
                    length += 2;
                } else {
                    length++;
                }
            }

            if (length > maxLength && source.length() > maxLength) {
                return source.substring(0, maxLength) + substitute;
            }
        }

        return source;
    }

    /**
     * 截断字符串，中文字符按两个计算，截断后以指定字符代替
     *
     * @param source
     * @return
     */
    public static String truncateString(String source, int maxLength) {
        return StringHelper.truncateString(source, maxLength, "");
    }

    /**
     * 删除字符串中所有HTML标记
     *
     * @param input
     * @return
     */
    public static String toPlainText(String input) {
        if (input == null) {
            return "";
        }

        input = input.replaceAll("</?[^>]+>", "");

        return input;
    }

    /**
     * 转换文本字符串为HTML字符串
     *
     * @param input
     */
    public static String toHtmlText(String input) {
        String ret = escapeHTMLTags(input);

        ret = replace(ret, "\r\n", "<br>");
        ret = replace(ret, "\n", "<br>");
        ret = replace(ret, "\r", "<br>");
        // ret = replace(ret, " ", "&nbsp;");
        // ret = replace(ret, "<", "&lt;");
        // ret = replace(ret, "<", "&gt;");

        return ret;
    }

    /**
     * 转换文本字符串为HTML字符串
     *
     * @param input
     */
    public static String textToHtml(String input) {
        return toHtmlText(input);
    }

    public static void main(String[] args) {
        try {
            String aa = "豆花";
            String aaISO = new String(aa.getBytes("GBK"), "ISO-8859-1");

            System.out.println(aa);
            System.out.println(aaISO);
            System.out.println(new String(aaISO.getBytes("ISO-8859-1"), "GBK"));
            System.out.println(URLDecoder.decode("%D7%BF%C8%F3%BF%C6%BC%BC", "ISO-8859-1"));

            System.out.println(URLEncoder.encode("%B6%B9%BB%A8", "ISO-8859-1"));

        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
