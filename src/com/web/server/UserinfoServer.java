package com.web.server;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.web.common.RandomGenerator;
import com.web.dao.UserinfoDao;
import com.web.model.Userinfo;

@Service
public class UserinfoServer {
	@Autowired
	UserinfoDao userinfoDao;
	
	/**
	 * 登陆
	 * @param UserName
	 * @param Password
	 * @param map
	 */
	public void login(String username, String password, Map<String, Object> map) {
		try {
			if (userinfoDao.checkPwd(username, password)) {
				System.out.println("密码验证成功！");
				map.put("success", true);
			} else {
				map.put("success", false);
			}
		} catch (Exception e) {
			map.put("success", false);
			map.put("err", "服务器内部错误");
		}
	}
	
	/**
	 * 注册
	 * @param UserName
	 * @param Password
	 * @param map
	 */
	public void regedit(Userinfo userinfo, Map<String, Object> map) {
		try {
			if (userinfoDao.creUserinfo(userinfo)) {
				System.out.println("");
				map.put("success", true);
			} else {
				map.put("success", false);
			}
		} catch (Exception e) {
			map.put("success", false);
			map.put("err", "服务器内部错误");
		}
	}
	
	/**
	 * 修改密码
	 * @param userName
	 * @param oldpassword
	 * @param newpassword
	 * @param map
	 */
	public void resetpwd(String userName, String oldpassword,String newpassword,Map<String, Object> map) {
		try {
			if (userinfoDao.checkPwd(userName, oldpassword)) {
				if (userinfoDao.updatePwd(userName, oldpassword, newpassword)){
					map.put("success", true);
				}else{
					map.put("success", false);
					map.put("err", "更新密码失败！");
				}
			} else {
				map.put("success", true);
				map.put("err", "原密码错误");
			}
		} catch (Exception e) {
			map.put("success", true);
			map.put("err", "服务器内部错误");
		}
	}
	
	
	
	

	/**
	 * 发送电子邮件
	 * 
	 * @param toEmails
	 * @param Title
	 * @param context
	 */
	public void sendEmail(String toEmails[], String Title, String context) {
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
				"Ob71Glm5tvs3cnzC", "jMn5zNU5bRCNh0Agk2pQm1Z6N4SuG9");
		IAcsClient client = new DefaultAcsClient(profile);
		SingleSendMailRequest request = new SingleSendMailRequest();
		try {
			request.setAccountName("service@email.qhongmeet.com");
			request.setAddressType(1);
			request.setTagName("控制台创建的标签");
			request.setReplyToAddress(true);
			// request.setToAddress("271912564@qq.com,271912564@qq.com");
			String emails = "";
			for (String email : toEmails) {
				emails = emails + email + ",";
			}
			emails = emails.substring(0, emails.length() - 1);

			request.setToAddress("271912564@qq.com,271912564@qq.com");
			// request.setSubject("邮件主题");
			request.setSubject(Title);
			// request.setHtmlBody("发送多人邮件");
			request.setHtmlBody(context);
			SingleSendMailResponse httpResponse = client
					.getAcsResponse(request);

		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
		System.out.println("OK");
	}
	
	
	public Userinfo getUserinfo(String username){
		Userinfo userinfo  = new Userinfo();
		try {
			userinfo= userinfoDao.geSingleUserbyusername(username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userinfo;
	}
	
	
	/**
	 * 验证是否已经有该用户名
	 * @param username
	 * @return
	 * @throws Exception 
	 */
	public void checkUser(String username,Map<String, Object> map) throws Exception{
		if (userinfoDao.checkUserName(username)){
			map.put("error", "该用户名已注册！");
			map.put("errorCode", 301);
		}else{
			map.put("error", "");
		}
	}
	
	/**
	 * 加密算法
	 * @param username
	 * @param password
	 * @return
	 */
	private String encodepassword(String username,String password){
		String pass = username+password;
		return pass;
	}
	
	/**
	 * 自动产生一个固定长度的数字密码
	 * @param len
	 */
	private String autocreatepwd(int len){
		return RandomGenerator.genRandomNum(len);
	}
	

}
