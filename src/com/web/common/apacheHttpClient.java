package com.web.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public  class apacheHttpClient {
	private Logger logger = LoggerFactory.getLogger( apacheHttpClient.class );
	private final int HTTP_OK = 200;
	/**
	 * 带参数的普通发送post请求方法 一
	 * @param uri
	 * @param headerMap
	 * @param paramsMap
	 * @return
	 */
	public String sendHttpPostRequest(String uri,Map<String, String> headerMap,
			Map<String, String> paramsMap) {
		logger.info( "发送http请求：url={},valuePairs={}", uri, paramsMap.toString() );
		HttpPost httpPost = new HttpPost(uri);
		try {

			
//			httpPost.addHeader("Content-Type",	"application/x-www-form-urlencoded;charset=UTF-8");
			//循环所有发送请求head
			if (headerMap != null && !headerMap.isEmpty()) {
				for (String keySet : headerMap.keySet()) {
					httpPost.addHeader(keySet, headerMap.get(keySet));
				}
			}
			//循环所有发送请求head
			
			// 循环所有参数
			List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
			if (paramsMap != null && !paramsMap.isEmpty()) {
				for (String keySet : paramsMap.keySet()) {
				    valuePairs.add(new BasicNameValuePair(keySet, paramsMap.get(keySet)));
				}
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(	valuePairs, "utf-8");
				httpPost.setEntity(formEntity);
			}
			// 循环所有参数
			
			//设置http连接的基本参数，可不设置，默认值为
	        RequestConfig requestConfig = RequestConfig.custom()
	                .setSocketTimeout(5000)
	                .setConnectTimeout(5000)
	                .setConnectionRequestTimeout(5000)
	                .build();
	        httpPost.setConfig(requestConfig);
	      //设置http连接的基本参数，可不设置，默认值为
	        
	        
			return sendHttpRequest( httpPost );
		} catch (UnsupportedEncodingException e) {
			logger.error( "post请求失败,uri={}, exception={}", uri, e);
		}
		return null;
	}
	

	/**
	 * 带参数的普通发送post请求方法 二(直接传入String)
	 * @param url
	 * @param json
	 * @return
	 */
	public String sendHttpPostRequest(String url, String json ) {
		logger.info( "发送http请求：url={},json={}", url, json );
		HttpEntity httpEntity = new StringEntity(json, "utf-8");
		HttpUriRequest httpPost = RequestBuilder.post()
												.addHeader("Content-Type", ContentType.APPLICATION_JSON.toString() )
												.setEntity(httpEntity)
												.setUri(url)
												.build();
		return sendHttpRequest( httpPost );
	}
	

	//GET方法
	public String sendHttpGetRequest(String uri) {
		logger.info( "发送http请求：url={}", uri );
		HttpUriRequest httpGet =  RequestBuilder.get()
				                                .setUri( uri )
				                                .build();
		return sendHttpRequest( httpGet );
	}




	/**
	 * 执行发送请求，获取返回值String
	 * @param httpRequest
	 * @return
	 */
	private String sendHttpRequest( HttpUriRequest httpRequest ){
		HttpClient httpClient = HttpClients.createDefault();
		try {
			HttpResponse httpResponse = httpClient.execute( httpRequest );
			if( isRequestSuccessful( httpResponse ) ){
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString( httpEntity, "utf-8" );
				logger.info( "发送http请求返回：{}", response );
				System.out.println(response);
				return response;
			}
		} catch (ClientProtocolException e) {
			logger.error( "发送http请求失败,uri={}, exception={}", httpRequest.getURI(), e);
		} catch (IOException e) {
			logger.error( "发送http请求失败,uri={}, exception={}", httpRequest.getURI(), e);
		}
		return null;
	}
	
	/**
	 * 处理返回值
	 * @param httpResponse
	 * @return
	 */
	private boolean isRequestSuccessful(HttpResponse httpResponse) {
		logger.info( "调用返回状态码：{}", httpResponse.getStatusLine().getStatusCode() );
		return httpResponse.getStatusLine().getStatusCode() == HTTP_OK;
	}
	
	
	/**
	 * 上传文件方法
	 * @param URL
	 * @param localFile
	 */
	 public void upload(String URL,String localFile){
         CloseableHttpClient httpClient = null;
         CloseableHttpResponse response = null;
         try {
             httpClient = HttpClients.createDefault();
             
             // 把一个普通参数和文件上传给下面这个地址 是一个servlet
             HttpPost httpPost = new HttpPost(URL);
             
            // 把文件转换成流对象FileBody
            FileBody bin = new FileBody(new File(localFile));

            StringBody userName = new StringBody("Scott", ContentType.create("text/plain", Consts.UTF_8));
            StringBody password = new StringBody("123456", ContentType.create("text/plain", Consts.UTF_8));

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                   // 相当于<input type="file" name="file"/>
                    .addPart("file", bin)             
                    // 相当于<input type="text" name="userName" value=userName>
                    .addPart("userName", userName)
                    .addPart("pass", password)
                    .build();

            httpPost.setEntity(reqEntity);

            // 发起请求 并返回请求的响应
            response = httpClient.execute(httpPost);
            
            System.out.println("The response value of token:" + response.getFirstHeader("token"));
                
            // 获取响应对象
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                // 打印响应长度
                System.out.println("Response content length: " + resEntity.getContentLength());
                // 打印响应内容
                System.out.println(EntityUtils.toString(resEntity, Charset.forName("UTF-8")));
            }
            
        // 销毁
            EntityUtils.consume(resEntity);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
        try {
            if(response != null){
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
           
            try {
                if(httpClient != null){
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}