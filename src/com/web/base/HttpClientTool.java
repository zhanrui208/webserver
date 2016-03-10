package com.web.base;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Http客户端-工具类
 * 
 * @author jianliang_xiao
 */
public class HttpClientTool {
	/** 日志 */
	protected static Log log = LogFactory.getLog(HttpClientTool.class);

	private HttpClientTool() {
	}

	/**
	 * HttpClient客户端请求处理 （字符编码：UTF-8）
	 * 
	 * @param client
	 *            client 客户端 连接
	 * @param url
	 *            需要请求的URL
	 * @return String 返回处理结果 （字符编码：UTF-8）
	 */
	public static String httpPost(HttpClient client, String url) {
		return httpPost(client, url, null);
	}

	/**
	 * HttpClient客户端请求处理 （字符编码：UTF-8）
	 * 
	 * @param client
	 *            client 客户端 连接
	 * @param url
	 *            需要请求的URL
	 * @param paramMap
	 *            请求参数 （字符编码：UTF-8）
	 * @return String 返回处理结果 （字符编码：UTF-8）
	 */
	public static String httpPost(HttpClient client, String url, Map paramMap) {
		String result = "";
		NameValuePair[] data = getNameValuePair(paramMap);
		String charSet = "UTF-8";
		result = httpPost(client, url, data, charSet, charSet);
		return result;
	}

	/**
	 * HttpClient客户端请求处理 （字符编码：UTF-8）
	 * 
	 * @param client
	 *            client 客户端 连接
	 * @param url
	 *            需要请求的URL
	 * @param paramMap
	 *            请求参数 （字符编码：UTF-8）
	 * @return String 返回处理结果 （字符编码：UTF-8）
	 */
	public static boolean httpPost(HttpClient client, String url, Map paramMap,
			String fileName) {
		NameValuePair[] data = getNameValuePair(paramMap);
		String charSet = "UTF-8";
		return httpPost(client, url, data, charSet, charSet, fileName);
	}

	/**
	 * 参数转换
	 * 
	 * @param paramMap
	 *            参数
	 * @return NameValuePair[]
	 */
	public static NameValuePair[] getNameValuePair(Map paramMap) {
		NameValuePair[] data = null;
		if (null != paramMap) {
			Object[] keys = paramMap.keySet().toArray();
			if (null != keys && keys.length > 0) {
				data = new NameValuePair[keys.length];
				for (int i = 0; i < keys.length; i++) {
					String clumnName = (String) keys[i];
					Object value = paramMap.get(clumnName);
					data[i] = new NameValuePair(clumnName,
							String.valueOf(value));
				}
			}
		}
		return data;
	}

	/**
	 * HttpClient客户端请求处理
	 * 
	 * @param client
	 *            客户端 连接
	 * @param url
	 *            需要请求的URL
	 * @param data
	 *            请求参数
	 * @param paramsCharSet
	 *            请求参数的字符编码
	 * @param responseCharSet
	 *            返回结果的字符编码
	 * @return String
	 */
	public static String httpPost(HttpClient client, String url,
			NameValuePair[] data, String paramsCharSet, String responseCharSet) {
		if (null == client)
			return null;
		String result = "";
		HttpMethod post = new PostMethod(url);
		if (null == paramsCharSet || "".equals(paramsCharSet)) {
			paramsCharSet = "UTF-8";
		}
		if (null == responseCharSet || "".equals(responseCharSet)) {
			responseCharSet = "UTF-8";
		}
		client.getParams().setBooleanParameter("http.protocol.expect-continue",
				false);
		post.addRequestHeader("Connection", "close");
		// 设置编码
		// String userAgent="10203/2.2.1;Android4.0.3;Meizu;M9";
		// 链接超时 (单位毫秒)
		client.getHttpConnectionManager().getParams()
				.setConnectionTimeout(720000);
		// 读取超时(单位毫秒)
		client.getHttpConnectionManager().getParams().setSoTimeout(720000);
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				paramsCharSet);
		post.getParams().setParameter(HttpMethodParams.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1);
		// 把参数值放入postMethod中
		if (null != data) {
			post.setQueryString(data);
		}
		InputStream in = null;
		try {
			int httpStatus = client.executeMethod(post);
			// System.out.println("httpStatus=="+httpStatus);

			if (httpStatus == HttpStatus.SC_OK) {
				in = post.getResponseBodyAsStream();
				try {
					result = inputStream2String(in, responseCharSet);
				} catch (Exception e) {
					result = post.getResponseBodyAsString();
				}
			} else {
				log.error("HttpStatus error ==" + httpStatus);
			}
		} catch (Exception e) {
			log.error("HttpClient客户端请求处理,失败！", e);
		} finally {
			try {
				post.releaseConnection();
			} catch (Exception e) {
				log.error(e);
			}
			try {
				in.close();
			} catch (Exception e) {
				log.error(e);
			}
		}

		return result;
	}

	/**
	 * HttpClient客户端请求处理
	 * 
	 * @param client
	 *            客户端 连接
	 * @param url
	 *            需要请求的URL
	 * @param data
	 *            请求参数
	 * @param paramsCharSet
	 *            请求参数的字符编码
	 * @param responseCharSet
	 *            返回结果的字符编码
	 * @param fileName
	 *            结果保存的文件全名
	 * @return String
	 */
	public static boolean httpPost(HttpClient client, String url,
			NameValuePair[] data, String paramsCharSet, String responseCharSet,
			String fileName) {
		boolean result = false;
		if (null == client)
			return result;
		HttpMethod post = new PostMethod(url);
		if (null == paramsCharSet || "".equals(paramsCharSet)) {
			paramsCharSet = "UTF-8";
		}
		if (null == responseCharSet || "".equals(responseCharSet)) {
			responseCharSet = "UTF-8";
		}
		client.getParams().setBooleanParameter("http.protocol.expect-continue",
				false);
		post.addRequestHeader("Connection", "close");
		// 设置编码
		// String userAgent="10203/2.2.1;Android4.0.3;Meizu;M9";
		// 链接超时 (单位毫秒)
		client.getHttpConnectionManager().getParams()
				.setConnectionTimeout(240000);
		// 读取超时(单位毫秒)
		client.getHttpConnectionManager().getParams().setSoTimeout(240000);
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				paramsCharSet);
		post.getParams().setParameter(HttpMethodParams.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1);
		// 把参数值放入postMethod中
		if (null != data) {
			post.setQueryString(data);
		}
		InputStream in = null;
		try {

			int httpStatus = client.executeMethod(post);
			if (httpStatus == HttpStatus.SC_OK) {
				in = post.getResponseBodyAsStream();
				result = inputStream2File(in, paramsCharSet, fileName);

			} else {
				log.error("HttpStatus ==" + httpStatus);
				result = false;
			}
		} catch (Exception e) {
			result = false;
			log.error("HttpClient客户端请求处理,失败！", e);
		} finally {
			try {
				post.releaseConnection();
			} catch (Exception e) {
				log.error(e);
			}
			try {
				in.close();
			} catch (Exception e) {
				log.error(e);
			}

		}

		return result;
	}

	/**
	 * 将InputStream流转化为字符串
	 * 
	 * @param is
	 *            InputStream流
	 * @param charSet
	 *            返回结果的字符编码
	 * @param fileName
	 *            结果保存的文件全名
	 * @return String
	 * @throws Exception
	 */
	public static boolean inputStream2File(InputStream is, String charSet,
			String fileName) throws Exception {
		boolean result = false;
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(is, charSet);
			BufferedReader in = new BufferedReader(reader);
			String line = in.readLine();
			int i = 0;
			while (line != null) {
				line = line.trim();
				if (i > 0)
					line = "\n" + line;
				if (line.length() != 0) {
					// FileTool.appendString(fileName, line);
				}
				i++;
				line = in.readLine();
			}
			result = true;
		} catch (Exception e) {
			result = false;
			log.error("将InputStream流转化为字符串，失败！", e);
			throw new Exception(e);
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				log.error(e);
			}
			try {
				is.close();
			} catch (Exception e) {
				log.error(e);
			}
		}
		return result;
	}

	/**
	 * 将InputStream流转化为字符串
	 * 
	 * @param is
	 *            InputStream流
	 * @param charSet
	 *            返回结果的字符编码
	 * @return String
	 * @throws Exception
	 */
	public static String inputStream2String(InputStream is, String charSet)
			throws Exception {
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(is, charSet);
			BufferedReader in = new BufferedReader(reader);
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while (line != null) {
				line = line.trim();

				if (line.length() != 0) {
					buffer.append(line);
				}
				line = in.readLine();
			}
			return buffer.toString();
		} catch (Exception e) {
			log.error("将InputStream流转化为字符串，失败！", e);
			throw new Exception(e);
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				log.error(e);
			}
			try {
				is.close();
			} catch (Exception e) {
				log.error(e);
			}
		}
	}

	/**
	 * HttpClient客户端请求处理
	 * 
	 * @param client
	 *            客户端 连接
	 * @param url
	 *            需要请求的URL
	 * @param data
	 *            请求参数
	 * @param paramsCharSet
	 *            请求参数的字符编码
	 * @return String
	 */
	public static byte[] httpPost(HttpClient client, String url,
			NameValuePair[] data, String paramsCharSet) {
		if (null == client)
			return null;
		if (null == paramsCharSet || "".equals(paramsCharSet)) {
			paramsCharSet = "UTF-8";
		}
		HttpMethod post = new PostMethod(url);
		client.getParams().setBooleanParameter("http.protocol.expect-continue",
				false);
		post.addRequestHeader("Connection", "close");
		// 链接超时 (单位毫秒)
		client.getHttpConnectionManager().getParams()
				.setConnectionTimeout(240000);
		// 读取超时(单位毫秒)
		client.getHttpConnectionManager().getParams().setSoTimeout(240000);
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				paramsCharSet);
		post.getParams().setParameter(HttpMethodParams.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1);
		// 把参数值放入postMethod中
		if (null != data) {
			post.setQueryString(data);
		}
		byte[] result = null;
		try {
			int httpStatus = client.executeMethod(post);
			if (httpStatus == HttpStatus.SC_OK) {
				result = post.getResponseBody();
			}
		} catch (Exception e) {
			log.error("HttpClient客户端请求处理,失败！", e);
		} finally {
			try {
				// 释放连接
				post.releaseConnection();
			} catch (Exception e) {
				log.error(e);
			}
		}
		return result;
	}

	/**
	 * HttpClient客户端请求处理
	 * 
	 * @param client
	 *            客户端 连接
	 * @param url
	 *            需要请求的URL
	 * @param data
	 *            请求参数 (字符串 示例：name=abc&blah=Whoop )
	 * @param paramsCharSet
	 *            请求参数的字符编码
	 * @param responseCharSet
	 *            返回结果的字符编码
	 * @return String
	 */
	public static String httpPost(HttpClient client, String url, String data,
			String paramsCharSet, String responseCharSet) {
		if (null == client)
			return null;

		// 链接超时 (单位毫秒)
		client.getHttpConnectionManager().getParams()
				.setConnectionTimeout(240000);
		// 读取超时(单位毫秒)
		client.getHttpConnectionManager().getParams().setSoTimeout(240000);
		String result = "";
		HttpMethod post = new PostMethod(url);
		if (null == paramsCharSet || "".equals(paramsCharSet)) {
			paramsCharSet = "UTF-8";
		}
		if (null == responseCharSet || "".equals(responseCharSet)) {
			responseCharSet = "UTF-8";
		}
		client.getParams().setBooleanParameter("http.protocol.expect-continue",
				false);
		post.addRequestHeader("Connection", "close");
		// 设置编码
		// String userAgent="10203/2.2.1;;;";
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				paramsCharSet);
		post.getParams().setParameter(HttpMethodParams.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1);
		// 把参数值放入postMethod中
		if (null != data) {
			post.setQueryString(data);
		}
		InputStream in = null;
		try {
			int httpStatus = client.executeMethod(post);
			if (httpStatus == HttpStatus.SC_OK) {
				in = post.getResponseBodyAsStream();
				try {
					result = inputStream2String(in, responseCharSet);
				} catch (Exception e) {
					result = post.getResponseBodyAsString();
				}
			}
		} catch (Exception e) {
			log.error("HttpClient客户端请求处理,失败！", e);
		} finally {
			try {
				// 释放连接

				post.releaseConnection();
			} catch (Exception e) {
				log.error(e);
			}
			// 释放连接
			try {
				in.close();
			} catch (Exception e) {
				log.error(e);
			}
		}

		return result;
	}

	/**
	 * HttpClient客户端请求处理
	 * 
	 * @param client
	 *            客户端 连接
	 * @param url
	 *            需要请求的URL
	 * @param paramMap
	 *            请求参数
	 * @param paramsCharSet
	 *            请求参数的字符编码
	 * @param responseCharSet
	 *            返回结果的字符编码
	 * @return Map 键：HTTP_CLIENT_USER_TAG -> 值：HttpClient客户端
	 *         键：HTTP_CLIENT_USER_RESULT -> 值：返回结果
	 */
	public static Map httpPost(String url, Map paramMap) {
		Map result = new HashMap();
		HttpClient client = new HttpClient();
		List<Header> headers = new ArrayList<Header>();
		String userAgent = "";
		;
		headers.add(new Header("User-Agent", userAgent));
		client.getHostConfiguration().getParams()
				.setParameter("http.default-headers", headers);
		// 链接超时 (单位毫秒)
		client.getHttpConnectionManager().getParams()
				.setConnectionTimeout(240000);
		// 读取超时(单位毫秒)
		client.getHttpConnectionManager().getParams().setSoTimeout(240000);
		String resStr = httpPost(client, url, paramMap);
		// result.put(Constants.CLIENT_TAG, client);
		// result.put(Constants.CLIENT_RESULT, resStr);
		return result;
	}

	/**
	 * 通过验证串获取HttpClient实例
	 * 
	 * @param openToken
	 *            验证串
	 * @return
	 */
	public static HttpClient getHttpClient(String openToken) {
		String tokenTag = "openToken"; /* open系统改造后 */
		// tokenTag="accessToken";
		if (null == openToken || "".equals(openToken))
			return null;
		// String userAgent="10203/2.2.1;Android4.0.3;Meizu;M9";
		HttpClient client = new HttpClient();
		List<Header> headers = new ArrayList<Header>();
		// String userAgent=Constants.XTWEB_USER_AGENT;
		// headers.add(new Header("User-Agent", userAgent));
		headers.add(new Header(tokenTag, openToken));
		client.getHostConfiguration().getParams()
				.setParameter("http.default-headers", headers);
		return client;

	}

	/**
	 * 通过验证串获取HttpClient实例
	 * 
	 * @return
	 */
	public static HttpClient getHttpClient() {

		HttpClient client = new HttpClient();
		List<Header> headers = new ArrayList<Header>();
		headers.add(new Header("openToken", ""));
		client.getHostConfiguration().getParams()
				.setParameter("http.default-headers", headers);
		return client;

	}

	/**
	 * HttpClient客户端请求处理(JSON格式参数)
	 * 
	 * @param url
	 *            需要请求的URL
	 * @param paramMap
	 *            请求参数(Map)
	 * @return String
	 * @throws Exception
	 */
	public static String httpPostByJson(String url, Map paramMap)
			throws Exception {
		return httpPostByJson(url, null, "UTF-8");
	}

	/**
	 * HttpClient客户端请求处理(JSON格式参数)
	 * 
	 * @param url
	 *            需要请求的URL
	 * @param paramJson
	 *            请求参数(JSONObject)
	 * @return String
	 * @throws Exception
	 */
	public static String httpPostByJson(String url, JSONObject paramJson)
			throws Exception {
		return httpPostByJson(url, paramJson.toString(), "UTF-8");
	}

	/**
	 * HttpClient客户端请求处理(JSON格式参数)
	 * 
	 * @param url
	 *            需要请求的URL
	 * @param jsonParam
	 *            请求参数(JSON格式字符串)
	 * @param charSet
	 *            字符编码
	 * @return String
	 * @throws Exception
	 */
	public static String httpPostByJson(String url, String jsonParam,
			String charSet) throws Exception {
		String result = "";
		HttpClient client = new HttpClient();
		List<Header> headers = new ArrayList<Header>();
		headers.add(new Header("Content-Type", "application/json"));
		client.getHostConfiguration().getParams()
				.setParameter("http.default-headers", headers);
		PostMethod post = new PostMethod(url);
		post.addRequestHeader("Content-Type", "application/json;charset="
				+ charSet);
		// System.out.println(jsonParam);
		client.getParams().setBooleanParameter("http.protocol.expect-continue",
				false);
		post.addRequestHeader("Connection", "close");
		post.setRequestBody(jsonParam);
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				charSet);
		InputStream in = null;
		try {
			int httpStatus = client.executeMethod(post);
			log.error("httpStatus==" + httpStatus);
			if (httpStatus == HttpStatus.SC_OK) {
				in = post.getResponseBodyAsStream();
				try {
					result = inputStream2String(in, charSet);
				} catch (Exception e) {
					result = post.getResponseBodyAsString();
				}
			}
		} catch (Exception e) {
			log.error("HttpClient客户端请求处理,失败！", e);
		} finally {
			try {
				// 释放连接
				post.releaseConnection();
			} catch (Exception e) {
				log.error(e);
			}
			// 释放连接
			try {
				in.close();
			} catch (Exception e) {
				log.error(e);
			}
		}

		return result;
	}

	@SuppressWarnings("unused")
	public static String https(String url, Map<String, String> parm) {
		String response = "";
		HttpClient http = new HttpClient();
		PostMethod post = null;

		try {
			post = new PostMethod(url);
			post.getParams().setContentCharset("UTF-8");
			for (String key : parm.keySet()) {
				post.addParameter(key, (String) parm.get(key));
			}
			// 发送消息
			http.executeMethod(post);

			// 返回状态
			System.out.print(post.getStatusCode());
			if (post.getStatusCode() == HttpStatus.SC_OK) {
				StringBuffer out = new StringBuffer();
				byte[] resultBytes = new byte[1024];
				InputStream in = post.getResponseBodyAsStream();
				for (int n; (n = in.read(resultBytes)) != -1;) {
					out.append(new String(resultBytes, 0, n, "UTF-8"));
				}
				response = out.toString();
			} else {
				response = "";

			}
			post.releaseConnection();
		} catch (Throwable e) {
			e.printStackTrace();
			response = "";
		} finally {
			post.releaseConnection();
		}
		System.out.print(response);
		return response;
	}
}
