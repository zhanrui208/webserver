package com.web.test;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
public class sample{
	public  static void  main(String arg[]) {        
	    IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "Ob71Glm5tvs3cnzC", "jMn5zNU5bRCNh0Agk2pQm1Z6N4SuG9");
	    IAcsClient client = new DefaultAcsClient(profile);
	    SingleSendMailRequest request = new SingleSendMailRequest();
	    try {
	        request.setAccountName("service@email.qhongmeet.com");
	        request.setAddressType(1);
	        request.setTagName("控制台创建的标签");
	        request.setReplyToAddress(true);
	        request.setToAddress("271912564@qq.com,271912564@qq.com");
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
    
}