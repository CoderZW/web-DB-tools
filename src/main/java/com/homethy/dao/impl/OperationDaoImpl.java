package com.homethy.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.homethy.UserHolder;
import com.homethy.dao.OperationDao;
import com.homethy.domain.DatabaseUserSqlHistory;
import com.homethy.util.DBUtil;
import com.homethy.util.HomethyStringUtil;
import com.homethy.util.ReturnJacksonUtil;
import java.sql.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Locale;

/**
 * Created by leifeifei on 17-9-11.
 */
@Service
public class OperationDaoImpl extends BaseDao implements OperationDao {

  @Autowired
  UserHolder userHolder;

  @Override
  public String executeSql(String env, String schema, String statement, Statement queryLeadQAInfo) throws Exception {
    String str = statement.trim().toLowerCase();

    if (str.startsWith("select")) {
      statement = "select * from ( " + statement + " ) as query_table limit 0,500";
    }
    int level = 0;
    if(userHolder != null && userHolder.getUserInfo() != null){
      level = userHolder.getUserInfo().getLevel();
      if("prd".equals(env)) {
        level = userHolder.getUserInfo().getOnlineLevel();
      }
    }
    if (level == 0) {
      return executeSqlResultForOnlyRead(env, schema, statement);
    } else if (level == 1) {
      if (str.startsWith("create") || str.startsWith("alter") || str.startsWith("drop"))
        return HomethyStringUtil.returnFailResultStr("error", "Permission denied");
      return executeSqlResultNew(env, schema, statement, queryLeadQAInfo);
    } else
      return executeSqlResultNew(env, schema, statement, queryLeadQAInfo);


  }

  @Override
  public void insertExecuteSqlHistory(String env,String statement){
    DatabaseUserSqlHistory databaseUserSqlHistory = new DatabaseUserSqlHistory();
    databaseUserSqlHistory.setClientIp(userHolder.getClientIp());
    databaseUserSqlHistory.setCreateTime(new Date());
    databaseUserSqlHistory.setEnv(env);
    databaseUserSqlHistory.setSqlDetail(HomethyStringUtil.replceSingleQuot(statement));
    databaseUserSqlHistory.setUserId(userHolder.getUserInfo().getId());
    databaseUserSqlHistory.setServerIp(userHolder.getServerIp());
    executeSqlResult(userHolder.getENV(),"sitebuilt",transformToInsertSql(databaseUserSqlHistory));
  }

  @Override
  public void closeDBConnection(){
    closeAllConnections();
  }

  @Override
  public Statement createDBConnection(String env,String schema) throws Exception{
    return  createConnection(env,schema);
  }

  @Override
  public String getTablesJson(String env, String schema) {
    return executeQueryTablesReturn(env, schema);
  }

}
