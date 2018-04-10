package com.homethy.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.homethy.domain.DatabaseUserSqlHistory;
import java.sql.Statement;

/**
 * Created by leifeifei on 17-9-11.
 */
public interface OperationDao {

  String executeSql(String env,String schema,String statement,Statement queryLeadQAInfo) throws JsonProcessingException, Exception;

  void insertExecuteSqlHistory(String env,String statement);

  void closeDBConnection();

  Statement createDBConnection(String env,String schema) throws Exception;

  String getTablesJson(String env, String schema);

}
