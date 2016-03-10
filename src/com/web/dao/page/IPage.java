package com.web.dao.page;

/**
 * 产生不同数据库的分页sql
 * 
 * @author teddy
 * 
 */
public interface IPage {

	String synthesisPage(String baseSQL, int from, int pageSize);

}
