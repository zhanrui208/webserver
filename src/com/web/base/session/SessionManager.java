package com.web.base.session;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.web.base.session.Session;
import com.web.base.session.SessionNotExistsException.Code;
import com.web.base.session.imp.SessionImpl;
import com.web.base.util.Base64Encoder;
import com.web.dao.UserinfoDao;
import com.web.model.Userinfo;

/**
 * 登录会话管理器
 * 
 * @since 2010-10-9
 * @author xichu_yu
 */
@Component
public class SessionManager {
	private final static Logger logger = LoggerFactory
			.getLogger(SessionManager.class);

	public static final int EXPIRE_TIME = 10 * 60;// 10分钟
	public static final int REMEMBER_ME_TIME = 30 * 24 * 60 * 60;// 30天

	/**
	 * SESSION标识名称，用于request.queryString, request.attribute, cookie中。
	 */
	public static final String AUTH_TOKEN = "auth_token";

	public static final String COOKIE_AUTH_TOKEN = "at";

	public static final String CURRENT_SESSION_ATTR = "qianhong_session";

	/**
	 * 从终端跳转到网页时，给定此参数，则网页自动为用户登录
	 */
	public static final String TSESSION = "tsession";

	/**
	 * 用于记住登录
	 */
	public static final String COOKIE_REMEMBER_ME = "crm";

	/**
	 * 浏览器唯一id
	 */
	public static final String COOKIE_CID = "cid";
	/**
	 * 当前用户id
	 */
	public static final String COOKIE_CURRENT_USER = "cuid";

	private static SessionManager instance;

	private SessionManager() {
		SessionManager.instance = this;
	}

	
	/**
	 * cookie登录方式
	 * cookie保存了用户密码的验证方式，直接用该方法雁阵
	 * @param req
	 * @param res
	 * @return
	 * @throws SessionNotExistsException
	 */
	public static boolean  checkRememberMe(HttpServletRequest req,HttpServletResponse res)  {

		String rememberMe = CookieUtil.readCookie(req, COOKIE_REMEMBER_ME);
		if (StringUtils.isEmpty(rememberMe)) {
//			throw new SessionNotExistsException(Code.LOGIN_EXPIRED,
//					"登录已过期，请重新登录！");
			return false;
		}

		Userinfo userinfo = null;
		try {
			rememberMe = new String(Base64Encoder.base64ToByteArray(rememberMe));
			if (rememberMe.indexOf(":") > 0) {
				String uid = rememberMe.substring(0, rememberMe.indexOf(":"));
				String pw = rememberMe.substring(rememberMe.indexOf(":") + 1);
				UserinfoDao userinfoDao = new UserinfoDao();
				try {
					userinfo = userinfoDao.geSingleUser(uid);
				} catch (Exception e) {
//					throw new SessionNotExistsException(Code.LOGIN_EXPIRED,
//							"查询出错，请联系服务提供者！");
					return false;
				}// 查询数据库有没该字段值
				if (userinfo == null || !pw.equals(userinfo.getPassword())) {
//					throw new SessionNotExistsException(Code.LOGIN_EXPIRED,
//							"登录已过期，请重新登录！");
					return false;
				}
				//把该值存入session中去
				req.getSession().setAttribute(CURRENT_SESSION_ATTR,uid);
			}
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
//			throw new SessionNotExistsException(Code.LOGIN_EXPIRED,
//					"登录已过期，请重新登录！");
			return false;
		}
		return true;
	}

	
	
	/**
	 * nginx会针对每个浏览器生成一次client userinfo id，永久有效（除非用户清理cookie）
	 * 一般用于跟踪客户端唯一地址（技术限制，无法绝对定位用户地址，但这个值可以参考）
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientBrowserId(HttpServletRequest request) {
		return CookieUtil.readCookie(request, COOKIE_CID);
	}

	/**
	 * 创建会话COOKIE
	 * 
	 * @param res
	 * @param cookieAge
	 * @param session
	 */
	public static void createSessionCookie(HttpServletRequest req,
			HttpServletResponse res, boolean remember, Session session,
			Userinfo userinfo) {
		res.setHeader(
				"P3P",
				"CP=\"CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR\"");// P3P
		Cookie cookie = CookieUtil.createCrossDomainCookie(req,
				COOKIE_AUTH_TOKEN, session.getId());
		res.addCookie(cookie);

		setUserCookie(req, res, session);

		if (remember) {// 如果记住登录，则记入永久COOKIE中
			String rememberMe = Base64Encoder.byteArrayToBase64((userinfo
					.getUserID() + ":" + userinfo.getPassword()).getBytes());
			cookie = CookieUtil.createCrossDomainCookie(req,
					COOKIE_REMEMBER_ME, rememberMe);
			cookie.setMaxAge(SessionManager.REMEMBER_ME_TIME);
			res.addCookie(cookie);
		}

	}

	/**
	 * 在cookie中保持当前用户userID
	 * 
	 * @param response
	 * @param session
	 */
	public static void setUserCookie(HttpServletRequest req,
			HttpServletResponse response, Session session) {
		Cookie usercookie = CookieUtil.createCrossDomainCookie(req,
				COOKIE_CURRENT_USER, session.getUserId());
		response.addCookie(usercookie);
	}

	/**
	 * 在cookie中保持当前用户userID
	 * 
	 * @param response
	 * @param session
	 */
	public static void setUserCookie(HttpServletRequest req,
			HttpServletResponse response,Userinfo userinfo,boolean remember) {

        if (remember) {// 如果记住登录，则记入永久COOKIE中
            String rememberMe = Base64Encoder.byteArrayToBase64((userinfo.getUserID() + ":" + userinfo.getPassword()).getBytes());
            Cookie cookie = CookieUtil.createCrossDomainCookie(req,COOKIE_REMEMBER_ME, rememberMe);
            cookie.setMaxAge(SessionManager.REMEMBER_ME_TIME);
            response.addCookie(cookie);
        }
		
	}
	
	/*
	 * 保存用户id的
	 */
	public static void saveUserSession(HttpServletRequest res,String UserId) {
		res.getSession().setAttribute(CURRENT_SESSION_ATTR, UserId);;
	}
	
	/*
	 * 保存用户id的
	 */
	public static String getUserSession(HttpServletRequest res) {
		return (String) res.getSession().getAttribute(CURRENT_SESSION_ATTR);
	}
	
	
	/*
	 * 保存用户id的
	 */
	public static void saveSession(HttpServletRequest req,String key,String value) {
		req.getSession().setAttribute(key, value);;
	}
	
	/*
	 * 保存用户id的
	 */
	public static String getSession(HttpServletRequest req,String key) {
		return (String) req.getSession().getAttribute(key);
	}
	
	
	public static void removeSession(HttpServletRequest req,String key) {
		 req.getSession().removeAttribute(key);
	}
	
	
	// /**
	// * 注册会话；以后再次获取会话时，需要使用返回会话ＩＤ。
	// *
	// * @param userId
	// * @param networkId
	// * @param tenantId
	// * @return
	// */
	// public static Session login(Userinfo userinfo, boolean rememberMe) {
	// // 保存登录信息
	// String sessionId = UUID.randomUUID().toString();
	// return login(userinfo, rememberMe, sessionId);
	// }
	//
	// /**
	// * 创建从Mobile登录的Session.
	// * @param userinfo 登录用户
	// * @param accessToken 用户的授权AccessToken
	// * @return {@link Session}
	// */
	// public static Session loginFromMobile(Userinfo userinfo, String
	// accessToken) {
	// return login(userinfo, false, accessToken);
	// }

	/**
	 * 注销会话,设置
	 * 
	 * @param req
	 * @param res
	 */
	public static void logout(HttpServletRequest req, HttpServletResponse res) {
		logout(req);
		Cookie cookie = CookieUtil.createCrossDomainCookie(req,
				COOKIE_REMEMBER_ME, "");
		cookie.setMaxAge(0);
		res.addCookie(cookie);
	}

	/**
	 * 移除session中登录标识
	 * 
	 * @param req
	 */
	public static void logout(HttpServletRequest req) {
		req.getSession().removeAttribute(CURRENT_SESSION_ATTR);

	}

	/**
	 * 检查用户当前是否在线
	 * 
	 * @param userId
	 * @return
	 */
	public static boolean isOnline(String userId) {
		// List<Session> sessions = instance.sessionDAO.findByUserId(userId);
		List<Session> sessions = new LinkedList<Session>();
		return sessions != null && sessions.size() > 0;
	}

	// /**
	// * 获取当前会话，如果不存在，则返回NULL。
	// *
	// * @param req
	// * @return
	// */
	// public static Session get(HttpServletRequest req) throws
	// SessionNotExistsException {
	// return get(req, true);
	// }

	// /**
	// * : 针对终端的需求，如果请求中包含TSESSION，则需保证从终端跳转到网页，用户已经登录
	// */
	// private static Session getParamSession(HttpServletRequest req,
	// HttpServletResponse res) {
	// // 2011-05-30 xiang_fu: 针对终端的需求，如果请求中包含TSESSION，则需保证从终端跳转到网页，用户已经登录
	// String token = req.getParameter(SessionManager.TSESSION);
	// if (token != null && !token.isEmpty()) {
	// Session session = get(token, true);
	// if (session != null) {
	// createSessionCookie(req,res, false, session, session.getUserInfo());
	// req.setAttribute(CURRENT_SESSION_ATTR, session);
	// }
	// return session;
	// }
	// return null;
	// }
	//

	//
	// public static Session checkRememberMe(HttpServletRequest req,
	// HttpServletResponse res)
	// throws SessionNotExistsException {
	//
	// String rememberMe = CookieUtil.readCookie(req, COOKIE_REMEMBER_ME);
	// if(StringUtils.isEmpty(rememberMe)) {
	// rememberMe = CookieUtil.readCookie(req, "remember_me");
	// }
	// if (StringUtils.isEmpty(rememberMe)) {
	// throw new SessionNotExistsException(Code.LOGIN_EXPIRED, "登录已过期，请重新登录！");
	// }
	//
	// Userinfo userinfo = null;
	// try {
	// rememberMe = new String(Base64Encoder.base64ToByteArray(rememberMe));
	// if (rememberMe.indexOf(":") > 0) {
	// String uid = rememberMe.substring(0, rememberMe.indexOf(":"));
	// String pw = rememberMe.substring(rememberMe.indexOf(":") + 1);
	// UserinfoDao userinfoDao = new UserinfoDao();
	// userinfo= userinfoDao.geSingleUser(uid);//查询数据库有没该字段值
	// if (userinfo == null ||!pw.equals(userinfo.getPassword())) {
	// throw new SessionNotExistsException(Code.LOGIN_EXPIRED, "登录已过期，请重新登录！");
	// }
	// }
	// } catch (IllegalArgumentException e1) {
	// e1.printStackTrace();
	// throw new SessionNotExistsException(Code.LOGIN_EXPIRED, "登录已过期，请重新登录！");
	// }
	// Session session = login(userinfo, true, UUID.randomUUID().toString(),
	// null);
	// createSessionCookie(req, res, true, session, userinfo);
	// req.setAttribute(CURRENT_SESSION_ATTR, session);
	//
	// return session;
	//
	// }

	/**
	 * 登录 rem 是否记住账号密码
	 */
	public static void login(HttpServletRequest req, HttpServletResponse res,
			boolean rem) {
		String userid = (String) req.getSession().getAttribute(
				CURRENT_SESSION_ATTR);
		if (!StringUtils.isEmpty(userid)) {

		} else {
			checkRememberMe(req, res);
		}
	}

	// /**
	// * 获取当前会话，如果不存在，则返回NULL。
	// *
	// * @param req
	// * @return
	// */
	// public static Session get(HttpServletRequest req, boolean throwException)
	// throws SessionNotExistsException {
	// Session session = (Session) req.getAttribute(CURRENT_SESSION_ATTR);
	// if (session != null) {
	// return session;
	// }
	//
	// session = get(getSessionId(req), throwException);
	// req.setAttribute(CURRENT_SESSION_ATTR, session);
	// return session;
	// }

	// /**
	// * 根据ＳＥＳＳＩＯＮ_ＩＤ获取Session。
	// *
	// * @param sessionId
	// *
	// * @return
	// */
	// public static Session get(String sessionId) {
	// return get(sessionId, true);
	// }

	/**
	 * 根据ＳＥＳＳＩＯＮ_ＩＤ获取Session。
	 * 
	 * @param sessionId
	 * 
	 * @return
	 */
	public static Session get(HttpServletRequest req, String sessionId,
			boolean throwException) {
		if (sessionId == null || sessionId.length() == 0) {
			if (throwException) {
				throw new SessionNotExistsException(Code.NOT_LOGIN,
						"没有登录，请重新登录！");
			} else {
				return null;
			}
		}

		// SessionImpl session = getDao().findOne(sessionId);
		// Session session = instance.sessionDAO.findBySessionId(sessionId);
		// UserRepository userDao = new UserRepositoryMongo();
		// if (session == null || userDao.find(session.getUserId()) == null) {
		// if (throwException) {
		// throw new SessionNotExistsException(Code.LOGIN_EXPIRED,
		// "登录已过期，请重新登录！");
		// } else {
		// return null;
		// }
		// }
		//
		// session.refresh();
		// ((SessionImpl)session).setUserNetworkSerivce(instance.userNetworkService);
		// ((SessionImpl)session).setUserLastLoginService(instance.userLastLoginService);

		Session session = (Session) req.getSession().getAttribute(
				CURRENT_SESSION_ATTR);
		return session;
	}

	private static String getSessionId(HttpServletRequest req) {
		String sessionId = CookieUtil.readCookie(req, AUTH_TOKEN);
		if (sessionId == null) {
			sessionId = readHeader(req, AUTH_TOKEN);
		}
		if (sessionId == null) {
			sessionId = req.getParameter(AUTH_TOKEN);
		}
		return sessionId;
	}

	private static String readHeader(HttpServletRequest req, String name) {
		return req.getHeader(name);
	}


}
