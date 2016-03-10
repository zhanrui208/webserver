package com.web.base.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public final class ArrayUtil {
	
	private static final String DEFAULT_JOIN_STR = ",";

	private ArrayUtil() {
	}

	/**
	 * 将一个数组的元素用指定的字符进行连接，（像js中的join方法一样）
	 * @param strArray 字符串数组
	 * @param joinStr  要连接的字符串
	 * @return
	 */
	public static String join(String[] strArray, String joinStr) {
		if (strArray==null || strArray.length==0) {
			return "";
		}

		return join(Arrays.asList(strArray), joinStr);
	}

	/**
	 * 将一个数组的元素用指定的字符进行连接，（像js中的join方法一样）,默认用逗号（，）连接
	 * @param strArray 字符串数组
	 * @return
	 */
	public static String join(String[] strArray) {
		return join(strArray, DEFAULT_JOIN_STR);
	}

	/**
	 * 将一个集合的元素用指定的字符进行连接，（像js中的join方法一样）
	 * @param strCollection   字符串集合
	 * @param joinStr    要连接的字符串
	 * @return
	 */
	public static String join(Collection<?> strCollection, String joinStr) {
		return join(strCollection, joinStr, false);
	}

	/**
	 * 将一个集合的元素用指定的字符进行连接，（像js中的join方法一样）
	 * @param strCollection    字符串集合
	 * @param joinStr  要连接的字符串
	 * @param isFilterNull  是否过滤null值,不过滤用""空字符串填充
	 * @return
	 */
	public static String join(Collection<?> strCollection, String joinStr, boolean isFilterNull) {
		if (strCollection==null || strCollection.size()==0) {
			return "";
		}
		if (joinStr == null) {
			joinStr = DEFAULT_JOIN_STR;
		}
		StringBuilder sb = new StringBuilder();
		for (Object s : strCollection) {
			if(s == null){
				if(isFilterNull){
					continue;
				}
				sb.append("");
			}else{
				sb.append(s.toString());
			}
			sb.append(joinStr);
		}
		String strSB = sb.toString();
		String ret = strSB;
		if (strSB.endsWith(joinStr)) {
			ret = strSB.substring(0, strSB.length() - joinStr.length());
		}
		return ret;
	}

	/**
	 * 将一个集合的元素用指定的字符进行连接，（像js中的join方法一样）,默认用逗号（，）连接
	 * @param strCollection    字符串集合
	 * @return
	 */
	public static String join(Collection<String> strCollection) {
		return join(strCollection, DEFAULT_JOIN_STR);
	}
	/**
	 * 判断当前List是否为空
	 * @param list
	 * @return
	 */
	public static boolean isListEmpty(List<?> list){
		
		if(list == null || list.size() <= 0)
			return true;
		return false;
	}
}