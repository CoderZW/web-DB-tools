package com.homethy.service;

import com.homethy.constant.SqlType;
import com.homethy.domain.SqlCommand;

/**
 * @author qian.xu@renren-inc.com on 17-11-2.
 */
public interface SqlParse {
  SqlType getSqlType(String sql);

  SqlCommand getTable(String sql, SqlType sqlType);
}
