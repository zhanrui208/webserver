package com.web.dao.interceptor;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.web.dao.CommonDao;
import com.web.dao.Transaction;
import com.web.dao.annotation.TransactionSupport;
/**
 * 开启事务支持
 * 
 * @author teddy
 * 
 */
public class TransactionSupportInterceptor implements MethodInterceptor {

	private static Logger logger = LoggerFactory.getLogger(TransactionSupportInterceptor.class);

	public Object invoke(final MethodInvocation methodinvocation)
			throws Throwable {

		Method method = methodinvocation.getMethod();
		String signature = method.getName();
		try {

			if (!method.isAnnotationPresent(TransactionSupport.class)) {
				return methodinvocation.proceed();
			}

			// 需要支持事务
			return CommonDao.doTransaction(new Transaction() {
				public Object execute() throws Exception {
					try {
						return methodinvocation.proceed();
					} catch (Throwable e) {
						Exception exception = new Exception();
						exception.initCause(e);
						throw exception;
					}
				}
			});

		} catch (Exception e) {
			logger.error("",e);
			logger.error( "{}:系统内部异常，原因：{}",signature,e.getMessage());
			throw e;

		}

	}

	@Override
	public Object intercept(Object arg0, Method arg1, Object[] arg2,
			MethodProxy arg3) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

}
