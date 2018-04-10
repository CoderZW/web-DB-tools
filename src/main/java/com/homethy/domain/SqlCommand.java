package com.homethy.domain;

import com.homethy.constant.SqlType;

import java.util.List;

/**
 * @author qian.xu@renren-inc.com on 17-10-17.
 */
public class SqlCommand {
  private String sql;
  private SqlType sqlType;
  private String database;
  private String table;
  private String columnStr;
  private String valueStr;
  private List<String> newSql;

  public String getSql() {
    return sql;
  }

  public void setSql(String sql) {
    this.sql = sql;
  }

  public SqlType getSqlType() {
    return sqlType;
  }

  public void setSqlType(SqlType sqlType) {
    this.sqlType = sqlType;
  }

  public String getDatabase() {
    return database;
  }

  public void setDatabase(String database) {
    this.database = database;
  }

  public String getTable() {
    return table;
  }

  public void setTable(String table) {
    this.table = table;
  }

  public String getColumnStr() {
    return columnStr;
  }

  public void setColumnStr(String columnStr) {
    this.columnStr = columnStr;
  }

  public String getValueStr() {
    return valueStr;
  }

  public void setValueStr(String valueStr) {
    this.valueStr = valueStr;
  }

  public List<String> getNewSql() {
    return newSql;
  }

  public void addNewSql(String newSql) {
    this.newSql.add(newSql);
  }
}
