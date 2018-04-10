package com.homethy.service.impl;

import com.homethy.constant.SqlType;
import com.homethy.domain.SqlCommand;
import com.homethy.service.SqlParse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author qian.xu@renren-inc.com on 17-10-11.
 */
@Service
public class SqlParseImpl implements SqlParse {

  public SqlType getSqlType(String sql) {
    SqlType[] sqlTypes = SqlType.values();
    Pattern pattern;
    Matcher matcher;
    for (SqlType sqlType : sqlTypes) {
      pattern = Pattern.compile(sqlType.getMask(), Pattern.CASE_INSENSITIVE);
      matcher = pattern.matcher(sql);
      if (matcher.matches()) {
        return sqlType;
      }
    }
    return null;
  }

  public SqlCommand getTable(String sql, SqlType sqlType) {
    SqlCommand sqlCommand = new SqlCommand();
    sqlCommand.setSql(sql);
    sqlCommand.setSqlType(sqlType);
    if (null != sqlType) {
      Pattern pattern = Pattern.compile(sqlType.getTableMask(), Pattern.CASE_INSENSITIVE);
      Matcher matcher = pattern.matcher(sql);//匹配类
      if (matcher.find()) {
        String detail = matcher.group(1);
        if (!StringUtils.isEmpty(detail)) {
          String[] split = detail.trim().split(".");
          if (split.length == 2) {
            sqlCommand.setDatabase(split[0]);
            sqlCommand.setTable(split[1]);
          } else {
            sqlCommand.setTable(detail);
          }
        }
        if (matcher.groupCount() == 3) {
          sqlCommand.setColumnStr(matcher.group(2));
          sqlCommand.setValueStr(matcher.group(3));
        }
      }
    }
    return sqlCommand;
  }

  public SqlCommand get(String sql) {
    SqlType sqlType = getSqlType(sql);
    return getTable(sql, sqlType);
  }
}
