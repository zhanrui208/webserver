package com.web.test;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.junit.Test;

import sun.misc.BASE64Encoder;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.web.base.Coder;
import com.web.base.util.MD5Util;
public class sample{
	public  static void  main(String arg[]) {        
		md5();
    
	}

	private static void sendmail() {
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "Ob71Glm5tvs3cnzC", "jMn5zNU5bRCNh0Agk2pQm1Z6N4SuG9");
	    IAcsClient client = new DefaultAcsClient(profile);
	    SingleSendMailRequest request = new SingleSendMailRequest();
	    try {
	        request.setAccountName("service@email.qhongmeet.com");
	        request.setAddressType(1);
	        request.setTagName("控制台创建的标签");
	        request.setReplyToAddress(true);
	        request.setToAddress("271912564@qq.com");
	        request.setSubject("邮件主题");
	        request.setHtmlBody("发送多人邮件");
	        SingleSendMailResponse httpResponse = client.getAcsResponse(request);

	    } catch (ServerException e) {
	        e.printStackTrace();
	    }
	    catch (ClientException e) {
	        e.printStackTrace();
	    }
	    System.out.println("OK");
	}
    
	@Test
	private static void md5(){
		try{
		String inputStr="admin";
		System.out.println(MD5Util.MD5(inputStr));
		System.out.println(MD5Util.md5Encode(inputStr));
		
	    BigInteger md5 = new BigInteger(Coder.encryptMD5(inputStr.getBytes()));
        System.err.println("MD5:\n" + md5.toString(16));
        
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	
}