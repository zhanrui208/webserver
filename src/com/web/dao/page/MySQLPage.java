package com.web.dao.page;

import com.web.dao.page.BasePage;
import com.web.dao.util.SqlUtils;

public class MySQLPage extends BasePage {

	public String getPage(String baseSQL, int from, int to) {

		return SqlUtils.getSql(baseSQL + " limit {0} ,{1}", new String[] {
				String.valueOf(from), String.valueOf(to) });

	}

}
