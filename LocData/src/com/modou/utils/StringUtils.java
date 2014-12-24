package com.modou.utils;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

/**
 * 字符串工具类
 */
public class StringUtils {

	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.equalsIgnoreCase("null")
				|| str.trim().length() == 0)
			return true;
		else
			return false;
	}
	
	/** * 去除特殊字符或将所有中文标号替换为英文标号
     * @param str
     * @return
     */
	public static String StringFilter(String str) {
	    str=str.replaceAll("【","[").replaceAll("】","]").replaceAll("！","!");//替换中文标号
	    String regEx="[『』]"; // 清除掉特殊字符
	    Pattern p = Pattern.compile(regEx);
	    Matcher m = p.matcher(str);
	 return m.replaceAll("").trim();
	}
     
     /*** 半角转换为全角 
     *
     * @param input     
     * @return      
    */      
	public static String ToDBC(String input) {
		   /*char[] c = input.toCharArray();
		   for (int i = 0; i< c.length; i++) {
		       if (c[i] == 12288) {
		         c[i] = (char) 32;
		         continue;
		       }if (c[i]> 65280&& c[i]< 65375)
		          c[i] = (char) (c[i] - 65248);
		       }
		   return new String(c);*/
		 /*char c[] = input.toCharArray();
         for (int i = 0; i < c.length; i++) {
           if (c[i] == '\u3000') {
             c[i] = ' ';
           } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
             c[i] = (char) (c[i] - 65248);


          }
         }
         String returnString = new String(c);
         return returnString;*/
		 char c[] = input.toCharArray();
         for (int i = 0; i < c.length; i++) {
           if (c[i] == '\u3000') {
             c[i] = ' ';
           } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
             c[i] = (char) (c[i] - 65248);


          }
         }
         String returnString = new String(c);
         return returnString;
	}
	
	/**
	 * 全角转半角的 转换函数
	 * @Methods Name full2HalfChange
	 * @Create In 2012-8-24 By xiaowei
	 * @param QJstr
	 * @return String
	 */
	 public static final String full2HalfChange(String QJstr)
	 {
	 StringBuffer outStrBuf = new StringBuffer("");
	 String Tstr = "";
	 byte[] b = null;
	 for (int i = 0; i < QJstr.length(); i++) {
	 Tstr = QJstr.substring(i, i + 1);
	 // 全角空格转换成半角空格
	 if (Tstr.equals("　")) {
	 outStrBuf.append(" ");
	 continue;
	 }
	 try {
	 b = Tstr.getBytes("unicode");
	 // 得到 unicode 字节数据
	 if (b[2] == -1) {
	 // 表示全角
	 b[3] = (byte) (b[3] + 32);
	 b[2] = 0;
	 outStrBuf.append(new String(b, "unicode"));
	 } else {
	 outStrBuf.append(Tstr);
	 }
	 } catch (UnsupportedEncodingException e) {
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 }
	 
	} // end for.
	 return outStrBuf.toString();
	 

	}
	 
	 
	 
	/**
	 * 半角转全角
	 * @Methods Name half2Fullchange
	 * @Create In 2012-8-24 By xiaowei
	 * @param QJstr
	 * @return String
	 */
	 public static final String half2Fullchange(String QJstr)
	 {
	 StringBuffer outStrBuf = new StringBuffer("");
	 String Tstr = "";
	 byte[] b = null;
	 for (int i = 0; i < QJstr.length(); i++) {
	 Tstr = QJstr.substring(i, i + 1);
	 if (Tstr.equals(" ")) {
	 // 半角空格
	 outStrBuf.append(Tstr);
	 continue;
	 }
	 try {
	 b = Tstr.getBytes("unicode");
	 if (b[2] == 0) {
	 // 半角
	 b[3] = (byte) (b[3] - 32);
	 b[2] = -1;
	 outStrBuf.append(new String(b, "unicode"));
	 } else {
	 outStrBuf.append(Tstr);
	 }
	 return outStrBuf.toString();
	 } catch (UnsupportedEncodingException e) {
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 }
	 
	}
	 return outStrBuf.toString();
	 }
	
	 public static String getDoubleFormat(double d) {
		 if (d <= 0)
			 return null;
		 DecimalFormat decimalFormat = new DecimalFormat("###############0.00");//格式化设置
		 String priceStr = decimalFormat.format(d);
		 return priceStr;
	 }

	/**
	 * 获得自定义字符串
	 * 
	 * @param str
	 * @return
	 */
	public static SpannableString getDiffStr(String str, int color, int start,
			int end) {
		SpannableString ss = new SpannableString(str);
		ss.setSpan(new ForegroundColorSpan(color), start, end,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ss;
	} 
}
