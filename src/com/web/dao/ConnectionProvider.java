package com.web.dao;

import java.sql.Connection;

public interface ConnectionProvider {

	Connection getConnection()throws Exception;

}

