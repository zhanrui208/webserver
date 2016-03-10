package com.web.dao.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 这个类处理Sql语句的替换功能,提供统一的Sql处理方式. 采用类似于MessageFormat的方法,{0},{1}等表示参数,中间不能有空格.
 * 另外加一个可选功能的实现[条件] 为可选条件,[]里面有一个或两个参数{n},如果参数n为null或者"",不使用该条件.
 * 因为是构建sql字符串,所以参数只限制为字符串,如果是数字,你需要先转为字符串.
 * 
 * 为了提高效率:没有做检查,而且没有使用正则表达式,时间复杂度很低,基本上和遍历字符串相当.
 * 没有验证,要求保证格式正确,否则可能为抛出异常(IndexOutOfBoundExceptoin)
 * 
 * Modified History:
 * 
 */
public class SqlUtils {
	
	private static Logger logger = LoggerFactory.getLogger(SqlUtils.class);

	private SqlUtils() {
	}

	public static void main(String[] ar) {
//		String s = "select column1,column2 from table1 where str='{0}' [and col2={1}] [and clos like '%{2}%'] [and cb between {3} and {4}]";
//		System.out.println(getSql(s, new String[] { "he'llo", null, "ab'c",
//				"3%", "%4%" }));
//		System.out.println(getSql(s, new String[] { "", "", null, "", "" }));
//
//		s = "select row_.*, rownum rownum_ from ( select * from city_user_task  where is_deleted=0 and business_type!=9"
//				+ "       [and a={0}]                                                                                                      "
//				+ "order by task_id desc ) row_ where rownum <= 15";
//		System.out.println(getSql(s, new String[] { null }));
		
		
		System.out.println(getSql(" u ON wl.FUserName = u.FUserName WHERE wl.FAppID=? [ AND (u.FUserName LIKE '%{0}%'] [ OR u.FRealName LIKE '%{0}%'] [ OR u.FEmail LIKE '%{0}%') ]",new String[]{"h","h","h"}));
		
	}

	public static String getSql(String formatSql, String[] values) {
		// 去掉多余的空行
		if (values == null || values.length == 0) {
			logger.debug("You have called the SqlUtil with no params,SqlUtil did nothing and just return the formatSql!");
			return formatSql.trim().replaceAll("\r\n[ \r\n\t\f]+", "\r\n");
		}
		// throw new RuntimeException("formatSql can't be empty!");
		String[] temp = values;
		values = new String[temp.length];
		for (int i = 0; i < temp.length; i++) {
			if (temp[i] != null) {
				values[i] = filterValue(temp[i]);
			}
		}

		StringBuffer sb = new StringBuffer();

		// 遍历字符串

		// 可选参数
		int start = 0; // 开始索引
		int end = 0; // 结束索引

		int iStart = 0;
		int iEnd = 0;
		int index = -1;
		for (int i = 0; i < formatSql.length(); i++) {

			char c = formatSql.charAt(i);

			// 遇到可选条件,
			if (c == '[') {
				// 可选参数的开始位置
				start = i;

				// 可选参数里面肯定有参数项: {}
				// 读入 '{'
				c = formatSql.charAt(++i);
				while (c != '{')
					c = formatSql.charAt(++i);

				// { 位置
				iStart = i;

				// 读入 '}'
				c = formatSql.charAt(++i);
				while (c != '}')
					c = formatSql.charAt(++i);
				// } 位置
				iEnd = i;
				// 取得格式话参数索引
				index = Integer.parseInt(formatSql.substring(iStart + 1, iEnd));
				// 获得替换的值
				String s = values[index];

				boolean two = false; // 处理 between 等子句
				String s2 = null;

				int iiStart = 0;
				int iiEnd = 0;
				// 读入']'
				c = formatSql.charAt(++i);
				// 不知道是否还存在一个 {}
				while (c != ']' && c != '{')
					c = formatSql.charAt(++i);

				// 如果还存在 {}
				if (c == '{') {
					two = true;
					iiStart = i;
					// 读入 '}'
					c = formatSql.charAt(++i);
					while (c != '}')
						c = formatSql.charAt(++i);
					iiEnd = i;
					index = Integer.parseInt(formatSql.substring(iiStart + 1,
							iiEnd));
					s2 = values[index];

				}
				// 如果不是结束，继续读入
				while (c != ']')
					c = formatSql.charAt(++i);

				end = i;

				if (!two) {
					// 一个条件的情况
					if (!isEmpty(s)) {
						sb.append(formatSql.substring(start + 1, iStart))
								.append(s)
								.append(formatSql.substring(iEnd + 1, end));
					}
				} else {
					// 两个条件的情况,只有两个值都不为空是才启用
					if (!isEmpty(s) && !isEmpty(s2)) {
						sb.append(formatSql.substring(start + 1, iStart))
								.append(s)
								.append(formatSql.substring(iEnd + 1, iiStart));
						sb.append(s2).append(
								formatSql.substring(iiEnd + 1, end));
					}
				}
			} else if (c == '{') {
				// 遇到一般条件,不是可选条件,直接替换就可以了
				iStart = i;
				// 读入 '}'
				c = formatSql.charAt(++i);
				while (c != '}')
					c = formatSql.charAt(++i);
				iEnd = i;
				index = Integer.parseInt(formatSql.substring(iStart + 1, iEnd));
				String s = values[index];
				sb.append(s);
			} else {
				// 其他字符,直接处理
				sb.append(c);
			}
		}
		return sb.toString().trim().replaceAll("\r\n[ \r\n\t\f]+", "\r\n");
	}

	// 为了效率考虑,单引号变成两个单引号
	private static String filterValue(String value) {
		StringBuffer sb = new StringBuffer(value.length());
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if (c == '\'') {
				sb.append("''");
			} else if (c == '%') {
				sb.append("\\%");
			} else
				sb.append(c);
		}
		return sb.toString();
	}

	public static String[] singleQuote(String[] items) {

		for (int i = 0; i < items.length; i++) {
			items[i] = "'" + items[i] + "'";
		}

		return items;

	}

	private static boolean isEmpty(String s) {
		return s == null || s.trim().equals("") || s.equals("null");
	}
}