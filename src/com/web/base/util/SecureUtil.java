package com.web.base.util;

import org.apache.commons.lang.StringEscapeUtils;

public class SecureUtil {
	// 防御XSS攻击
	public static String XSSfilter(String data) {
		data = StringEscapeUtils.escapeHtml(data);
		data = StringEscapeUtils.escapeJavaScript(data);
		data = StringEscapeUtils.escapeSql(data);
		return data;
	}
}
