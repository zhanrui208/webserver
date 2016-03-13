package com.web.server;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.web.base.Coder;
import com.web.base.Constant;
import com.web.common.RandomGenerator;
import com.web.dao.UserinfoDao;
import com.web.model.Userinfo;
import com.web.model.Userregedit;

@Service
public class UserinfoServer {
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	UserinfoDao userinfoDao;

	/**
	 * 登陆
	 * 
	 * @param UserName
	 * @param Password
	 * @param map
	 */
	public void login(String username, String password, Map<String, Object> map)
			throws Exception {

		if (userinfoDao.checkPwd(username, password)) {
			System.out.println("密码验证成功！");
			map.put("success", true);
		} else {
			map.put("success", true);
			map.put("error", "用户名或密码错误！");
			map.put("errorCode", "601");
		}

	}

	/**
	 * 注册
	 * 
	 * @param UserName
	 * @param Password
	 * @param map
	 */
	public void regedit(Userregedit userregedit, Map<String, Object> map)
			throws Exception {
		String randomNum = RandomGenerator.genRandomCharNum(20);
		userregedit.setRandomNum(randomNum);
		userregedit.setCreatedate(df.format(new Date()));
		if (userinfoDao.creUserregedit(userregedit)) {
			regediteforemail(userregedit.getEMail(), userregedit.getUserName(),
					userregedit.getRandomNum(), map);
		} else {
			map.put("success", false);
		}
	}

	/**
	 * 激活账号
	 * 
	 * @param user
	 * @param randomNum
	 * @param map
	 */
	public void activateUser(String user, String randomNum,
			Map<String, Object> map) throws Exception {

		if (userinfoDao.checkUserName(user)) {
			map.put("success", false);
			map.put("error", "该链接已激活");
			map.put("errorCode", 500);
			return;
		}

		Userregedit userregedit = userinfoDao.getregedit(user, randomNum);
		if (userregedit==null){
			map.put("success", false);
			map.put("error", "没有该用户的注册信息，请重新注册！");
			map.put("errorCode", 500);
			return;
		} 
	
		String datatime = userregedit.getCreatedate();
		Date dtime = df.parse(datatime);
		Userinfo userinfo = new Userinfo();
		if (System.currentTimeMillis() - dtime.getTime() <= 2 * 60 * 60 * 1000) {
			userinfo.setUserName(userregedit.getUserName());
			userinfo.setPassword(userregedit.getPassword());
			userinfo.setEMail(userregedit.getEMail());
			userinfo.setMobile(userregedit.getMobile());
			userinfoDao.creUserinfo(userinfo);
		} else {
			map.put("error", "该链接已超时");
			map.put("errorCode", 500);
		}

	}

	/**
	 * 注册
	 * 
	 * @param UserName
	 * @param Password
	 * @param map
	 */
	public void createUser(Userinfo userinfo, Map<String, Object> map)
			throws Exception {

		if (userinfoDao.creUserinfo(userinfo)) {
			System.out.println("");
			map.put("success", true);
		} else {
			map.put("success", false);
		}

	}

	/**
	 * 重设密码，需要原始密码
	 * 
	 * @param userName
	 * @param oldpassword
	 * @param newpassword
	 * @param map
	 */
	public void resetpwd(String userName, String oldpassword,
			String newpassword, Map<String, Object> map) throws Exception {

		if (userinfoDao.checkPwd(userName, oldpassword)) {
			if (userinfoDao.updatePwd(userName, newpassword)) {
				map.put("success", true);
			} else {
				map.put("error", "修改密码失败！");
				map.put("errorCode", 502);
			}
		} else {
			map.put("error", "原密码错误");
			map.put("errorCode", 502);
		}

	}

	/**
	 * 更新密码，不需要传原始密码
	 * 
	 * @param userName
	 * @param password
	 * @param map
	 */
	public boolean updatepwd(String userName, String password) throws Exception {
		return userinfoDao.updatePwd(userName, password) ;
	}

	public void forgetpwd(String user, String email, Map<String, Object> map)
			throws Exception {

		if (userinfoDao.checkUserName(user)) {
			String emais[] = new String[] { email };
			String randomNum = RandomGenerator.genRandomCharNum(21);
			String title = "重置密码";

			// 激活链接
			String activateUrl = Constant.UPDATEPWD;

			byte[] usernameByte = user.getBytes(Constant.UTF_8);
			String username = Coder.encryptBASE64(usernameByte);

			String context = activateUrl + "?randomNum=" + randomNum + "&user="
					+ username;

			if (sendEmail(emais, title, context)) {
				map.put("success", true);
			} else {
				map.put("error", "发送邮箱雁激活失败！");
				map.put("errorCode", 502);
			}

		} else {
			map.put("error", "邮箱不正确");
			map.put("errorCode", 502);
		}

	}

	public void regediteforemail(String emai, String user, String randomNum,
			Map<String, Object> map) throws Exception {

		String emais[] = new String[] { emai };

		// 激活链接
		String activateUrl = Constant.ACTIVEURL;
		String title = "重置密码";

		byte[] usernameByte = user.getBytes(Constant.UTF_8);
		String username = Coder.encryptBASE64(usernameByte);

		String context = activateUrl + "?randomNum=" + randomNum + "&username="
				+ username;

		if (sendEmail(emais, title, context)) {
			map.put("success", true);
		} else {
			map.put("error", "发送邮箱雁激活失败！");
			map.put("errorCode", 502);
		}
	}

	/**
	 * 发送电子邮件
	 * 
	 * @param toEmails
	 * @param Title
	 * @param context
	 */
	private boolean sendEmail(String toEmails[], String Title, String context) {
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
			if (httpResponse.getRequestId() != null) {
				return true;
			}
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
		System.out.println("OK");
		return false;
	}

	public Userinfo getUserinfo(String username) throws Exception {
		return userinfoDao.geSingleUserbyusername(username);

	}

	/**
	 * 验证是否已经有该用户名
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public void checkUser(String username, Map<String, Object> map)
			throws Exception {
		if (userinfoDao.checkUserName(username)) {
			map.put("error", "该用户名已注册！");
			map.put("errorCode", 301);
		} else {
			map.put("error", "");
		}
	}

	public boolean checkUser(String username) throws Exception {
		return userinfoDao.checkUserName(username);
	}

	/**
	 * 加密算法
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	private String encodepassword(String username, String password) {
		String pass = username + password;
		return pass;
	}

	/**
	 * 自动产生一个固定长度的数字密码
	 * 
	 * @param len
	 */
	private String autocreatepwd(int len) {
		return RandomGenerator.genRandomNum(len);
	}

}
