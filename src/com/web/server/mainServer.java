package com.web.server;



import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import com.web.base.Coder;
import com.web.base.basehttpsent;
import com.web.base.util.HMAC;
import com.web.common.RandomGenerator;

@Service
public class mainServer {
    public static final String KEY_SHA = "SHA";
    public static final String KEY_MD5 = "MD5";
    public static final String KEY_MAC = "HmacSHA1";
    
//    Access Key Secret：    jMn5zNU5bRCNh0Agk2pQm1Z6N4SuG9
//    ID ：    Ob71Glm5tvs3cnzC
    
	public static String mainserver() throws Exception{
		Map<String,Object> paramsMap = new HashMap(); 
		//公共部门参数
		
//		Format	String	否	返回值的类型，支持JSON与XML。默认为XML
//		Version	String	是	API版本号，为日期形式：YYYY-MM-DD，本版本对应为2015-11-23
//		AccessKeyId	String	是	阿里云颁发给用户的访问服务所用的密钥ID
//		Signature	String	是	签名结果串，关于签名的计算方法，请参见 签名机制。
//		SignatureMethod	String	是	签名方式，目前支持HMAC-SHA1
//		Timestamp	String	是	请求的时间戳。日期格式按照ISO8601标准表示，并需要使用UTC时间。格式为YYYY-MM-DDThh:mm:ssZ 例如，2015-11-23T04:00:00Z（为北京时间2015年11月23日12点0分0秒）
//		SignatureVersion	String	是	签名算法版本，目前版本是1.0
//		SignatureNonce	String	是	唯一随机数，用于防止网络重放攻击。用户在不同请求间要使用不同的随机数值
		
	
		
		//公共部门参数
		paramsMap.put("Format", "JSON");
		paramsMap.put("Version", "2015-11-23");
		paramsMap.put("AccessKeyId", "Ob71Glm5tvs3cnzC");
		paramsMap.put("SignatureMethod", "HMAC-SHA1");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");//设置日期格式
		String timestamp = df.format(new Date());
		paramsMap.put("Timestamp", timestamp);
		
		paramsMap.put("SignatureVersion", "1.0");
		
		String random = RandomGenerator.genRandomNum(8);//生成8为随机数
		paramsMap.put("SignatureNonce", random);
		
//		Action	String	是	操作接口名，系统规定参数，取值：SingleSendMail
//		AccountName	String	必须	管理控制台中配置的发信地址
//		ReplyToAddress	Boolean	必须	是否使用管理控制台中配置的回信地址（状态必须是验证通过）
//		AddressType	Number	必须	取值范围0~1: 0为随机账号(推荐,可以更好的统计退信情况);1为发信地址
//		ToAddress	String	必须	目标地址，多个Email地址可以逗号分隔
//		Subject	String	必须	邮件主题
//		HtmlBody	String	可选	邮件html正文
//		TextBody	String	可选	邮件text正文

		paramsMap.put("Action", "SingleSendMail");
		paramsMap.put("AccountName", "service@email.qhongmeet.com");
		paramsMap.put("ReplyToAddress", true);
		paramsMap.put("AddressType", 1);
		paramsMap.put("ToAddress", "284512077@qq.com,271912564@qq.com");
		paramsMap.put("Subject", "Subject测试发邮件接口");
		paramsMap.put("HtmlBody", "HtmlBody内容");
		paramsMap.put("TextBody", "TextBody内容");

		Collection<String> keyset= paramsMap.keySet(); 	
		
		ArrayList arrlist=  new ArrayList(keyset);
		//进行参数排序
		Collections.sort(arrlist);
		
		StringBuffer buf = new StringBuffer();
		
		//对参数名和参数值进行编码
		for(int i = 0 ;i<arrlist.size();i++){
			String key =(String) arrlist.get(i);
			String keyURLCODE = URLEncoder.encode(key, "UTF-8");
			String valURLCODE =URLEncoder.encode(String.valueOf(paramsMap.get(key)), "UTF-8");
			buf.append(keyURLCODE + "=" + valURLCODE +"&");
		}
		
		String StringToSign = buf.toString();
		//去掉最后一个多余的“&”
		StringToSign=StringToSign.substring(0,StringToSign.length()-1);
		
		System.out.println(StringToSign);
		
		//对这些参数组成的字符串进行URL编码
		StringToSign=URLEncoder.encode(StringToSign, "UTF-8");
		
		
		//加上前面的请求方法参数生成新字符串
		StringToSign="POST"+"&"+URLEncoder.encode("/", "UTF-8")+"&"+StringToSign;
		System.out.println(StringToSign);
		
		//对上面的字符串进行HMAC的sha1的数字签名
		String Signature=sign(StringToSign,"jMn5zNU5bRCNh0Agk2pQm1Z6N4SuG9&");
		
		//数字签名后还需要对该编码进行URL编码
		Signature=URLEncoder.encode(Signature, "UTF-8");
		System.out.println(Signature);
		//把该参数放入请求参数中
		paramsMap.put("Signature", Signature);//数字签名
		
		basehttpsent  http = new basehttpsent();
		
		String url ="https://dm.aliyuncs.com/";
		String message =http.sendHttpPostRequest(url, paramsMap);
		
		return message;
	}
	
	private static String sign(String Data,String Key) throws Exception{
        byte[] keyByte = Key.getBytes("UTF-8");
        byte[] dataBytes = Data.getBytes("UTF-8");
        byte[] datas=HMAC.HmacSHA1(dataBytes,keyByte);
        String dataString =Base64.encodeBase64String(datas);
        return dataString;
	}

	//调试代码
	public static void main(String arg[]){
		
		//案例
    	String data="POST&%2F&AccessKeyId%3Dtestid%26AccountName%3D1%26Action%3DSingleSendMail%26AddressType%3D1%26Format%3Dxml%26HtmlBody%3D4%26ReplyToAddress%3Dtrue%26SignatureMethod%3DHmac-SHA1%26SignatureNonce%3De1b44502-6d13-4433-9493-69eeb068e955%26SignatureVersion%3D1.0%26Subject%3D3%26TagName%3D2%26Timestamp%3D2015-11-24T05%253A06%253A00Z%26ToAddress%3D1%2540test.com%26Version%3D2015-11-23";
    	String key ="testsecret&";
    	try {
    		//数字签名
			String sss=sign(data,key);
			//签名后进行URL编码--案例的结果应为：1ohA2le%2BLu4D05AM3MFrI8nJZQs%3D
			sss=URLEncoder.encode(sss, "UTF-8");
			System.out.println(sss);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		String s ="";
		try {
			s = mainserver();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(s);
	}
}
