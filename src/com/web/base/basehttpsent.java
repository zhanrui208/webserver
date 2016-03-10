package com.web.base;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public  class basehttpsent {
	private Logger logger = LoggerFactory.getLogger(basehttpsent.class);
	private final int HTTP_OK = 200;
	
	/**
	 * get请求
	 * @param uri
	 * @return
	 */
	public String sendHttpGetRequest(String uri) {
		logger.info("发送http请求：url={}", uri);
		HttpUriRequest httpGet = RequestBuilder.get().setUri(uri).build();
		return sendHttpRequest(httpGet);
	}
	
	
	/**
	 * 带参数的"application/x-www-form-urlencoded;charset=UTF-8"格式请求
	 * @param uri
	 * @param valuePairs
	 * @return
	 */
	public String sendHttpPostRequest(String uri, List<NameValuePair> valuePairs) {
		logger.info("发送http请求：url={},valuePairs={}", uri, valuePairs);
		HttpPost httpPost = new HttpPost(uri);
		try {
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					valuePairs, "utf-8");
			httpPost.addHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			httpPost.setEntity(formEntity);
			return sendHttpRequest(httpPost);
		} catch (UnsupportedEncodingException e) {
			logger.error("post请求失败,uri={}, exception={}", uri, e);
		}
		return null;
	}
	
	/**
	 * 带参数的"application/x-www-form-urlencoded;charset=UTF-8"格式请求
	 * @param uri
	 * @param dataMap
	 * @return
	 */
	public String sendHttpPostRequest(String uri, Map dataMap) {
		return sendHttpPostRequest(uri, getNameValuePair(dataMap));
	}
	
	/**
	 * 不带参数的"application/x-www-form-urlencoded;charset=UTF-8"格式请求
	 * @param uri
	 * @return
	 */
	public String sendHttpPostRequest(String uri) {
		logger.info("发送http请求：url={}", uri);
		HttpPost httpPost = new HttpPost(uri);
		httpPost.addHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		return sendHttpRequest(httpPost);
	}

	public String sendHttpPostRequest(String url, String json) {
		logger.info("发送http请求：url={},json={}", url, json);
		HttpEntity httpEntity = new StringEntity(json, "utf-8");
		HttpUriRequest httpPost = RequestBuilder
				.post()
				.addHeader("Content-Type",
						ContentType.APPLICATION_JSON.toString())
				.setEntity(httpEntity).setUri(url).build();
		return sendHttpRequest(httpPost);
	}

	
	/**
	 * 上传文件的
	 * @param uri
	 * @param file
	 * @return
	 */
	public String sendHttpPostRequest(String uri, File file) {
		logger.info("发送http请求：url={},file={}", uri, file);
		PostMethod httpPost = new PostMethod(uri);
		try {
			Part[] parts = { new FilePart("file", file,
					"application/octect-stream", "utf-8") };
			httpPost.setRequestEntity(new MultipartRequestEntity(parts,
					httpPost.getParams()));
			org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(5000);
			int status = httpClient.executeMethod(httpPost);
			if (status == HTTP_OK) {
				httpPost.getResponseBodyAsString();
			}
		} catch (FileNotFoundException e) {
			logger.error("发送http请求失败,uri={}, exception={}", uri, e);
		} catch (IOException e) {
			logger.error("发送http请求失败,uri={}, exception={}", uri, e);
		}
		return null;
	}

	/**
	 * 处理求求的返回结果
	 * @param httpRequest
	 * @return
	 */
	private String sendHttpRequest(HttpUriRequest httpRequest) {
		HttpClient httpClient = HttpClients.createDefault();
		try {
			HttpResponse httpResponse = httpClient.execute(httpRequest);
//			if (isRequestSuccessful(httpResponse)) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				logger.info("发送http请求返回：{}", response);
				return response;
//			}
		} catch (ClientProtocolException e) {
			logger.error("发送http请求失败,uri={}, exception={}",
					httpRequest.getURI(), e);
		} catch (IOException e) {
			logger.error("发送http请求失败,uri={}, exception={}",
					httpRequest.getURI(), e);
		}
		return null;
	}
	
	private boolean isRequestSuccessful(HttpResponse httpResponse) {
		logger.info("调用返回状态码：{}", httpResponse.getStatusLine().getStatusCode());
		return httpResponse.getStatusLine().getStatusCode() == HTTP_OK;
	}
	
	/**
	 * 参数转换
	 * 
	 * @param paramMap
	 *            参数
	 * @return NameValuePair[]
	 */
	private static List<NameValuePair> getNameValuePair(Map paramMap) {
		List<NameValuePair> data = new LinkedList();
		if (null != paramMap) {
			Object[] keys = paramMap.keySet().toArray();
			if (null != keys && keys.length > 0) {
				for (int i = 0; i < keys.length; i++) {
					String clumnName = (String) keys[i];
					Object value = paramMap.get(clumnName);
					NameValuePair nameValuePair = new  BasicNameValuePair(clumnName,
							String.valueOf(value));
					data.add(nameValuePair);
				}
			}
		}
		return data;
	}
}
