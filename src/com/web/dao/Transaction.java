package com.web.dao;

/**
 * 执行的事务
 * 
 * @author Administrator
 * 
 */
public interface Transaction {

	Object execute() throws Exception;

}
