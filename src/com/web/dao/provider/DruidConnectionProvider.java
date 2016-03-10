package com.web.dao.provider;

import java.sql.Connection;

import javax.sql.DataSource;

import com.web.dao.ConnectionProvider;

/**
 * 基于Druid连接池的连接获取器
 * 
 * @author teddy
 * 
 */
public class DruidConnectionProvider implements ConnectionProvider {

	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Connection getConnection() throws Exception {
		return dataSource.getConnection();
	}

}
