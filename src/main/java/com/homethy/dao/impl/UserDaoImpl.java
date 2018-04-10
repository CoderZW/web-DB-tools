package com.homethy.dao.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.util.json.JSONUtils;
import com.amazonaws.util.json.Jackson;
import com.homethy.UserHolder;
import com.homethy.dao.OperationDao;
import com.homethy.dao.UserDao;
import com.homethy.domain.DatabaseUserFavorite;
import com.homethy.domain.DatabaseUserInfo;

import com.homethy.domain.DatabaseUserSqlHistory;
import com.homethy.util.DBUtil;
import com.homethy.util.JacksonUtils;
import com.homethy.util.ReturnJacksonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

//import com.homethy.service.ExecuteSqlService;

/**
 * Created by leifeifei on 17-9-11.
 */
@Service
public class UserDaoImpl extends BaseDao implements UserDao {
  public static final String SCHEMA = "sitebuilt";
  @Autowired
  UserHolder userHolder;

  @Autowired
  OperationDao operationDao;

  @Override
  public List<DatabaseUserInfo> queryUserInfoByAccount(String env, String account) {
    String statement = "select * from database_user_info where account='" + account + "'";
    try {
      return getDataByClassFiled(executeQuery(env, SCHEMA, statement), DatabaseUserInfo.class);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      DBUtil.closeAllDBConnections();
    }
  }

  @Override
  public void updateLastLoginData(String env,DatabaseUserInfo user){
    try {
      executeSqlResult(env, SCHEMA, transformToUpdateSql(user));
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public String saveUserFavoriteSql(DatabaseUserFavorite databaseUserFavorite){
    return executeSqlResult(userHolder.getENV(),SCHEMA,transformToInsertSql(databaseUserFavorite));
  }

  @Override
  public String updateUserFavoriteSql(DatabaseUserFavorite databaseUserFavorite){
    return executeSqlResult(userHolder.getENV(),SCHEMA,transformToUpdateSql(databaseUserFavorite));
  }

  @Override
  public List<DatabaseUserFavorite> queryDatabaseUserFavoriteSql(){
    String statement = "select * from database_user_favorite where user_id=" + userHolder.getUserInfo().getId() + " order by id desc";
    try {
      return getDataByClassFiled(executeQuery(userHolder.getENV(), SCHEMA, statement), DatabaseUserFavorite.class);
    } catch (Exception e) {
      e.printStackTrace();
      return Collections.emptyList();
    } finally {
      DBUtil.closeAllDBConnections();
    }
  }

  @Override
  public String queryDatabaseUserHistorySql(){
    String statement = "select id,sql_detail,DATE_FORMAT(create_time,'%b %d %Y %h:%i %p') as create_time from database_user_sql_history where user_id=" +userHolder.getUserInfo().getId()+ " order by id desc limit 500";
    try {
      ResultSet resultSet = executeQuery(userHolder.getENV(), SCHEMA, statement);
      return executeQueryResultReturn(resultSet);
    } catch (Exception e) {
      return e.toString();
    } finally {
      DBUtil.closeAllDBConnections();
    }
  }

  @Override
  public String deleteUserFavoriteSql(long id){
    String statement = "delete from  database_user_favorite where user_id=" + userHolder.getUserInfo().getId() + " and id="+id;
    try {
      return executeSqlResult(userHolder.getENV(), SCHEMA, statement);
    }catch (Exception e) {
      return ReturnJacksonUtil.resultFail("error",e.toString(), Locale.CHINESE);
    }
  }

  @Override
  public String updateUserPassword(String newPassword){
    String updatePassword = "update database_user_info set update_time= now(), last_update_password_time=now(), version=version+1, password='" + newPassword + "' where id=" + userHolder.getUserInfo().getId();
    String result = executeSqlResult(userHolder.getENV(),SCHEMA,updatePassword);
    try{
      if(StringUtils.isNoneBlank(result)){
        JSONObject jsonResult = JSONObject.fromObject(result);
        if(jsonResult.getJSONObject("status").getInt("code")==0){
          operationDao.insertExecuteSqlHistory(userHolder.getENV(),updatePassword);
        }
      }
    }catch (Exception e){
      e.printStackTrace();
    }

    return result;
  }

  @Override
  public String insertUser(String env, DatabaseUserInfo user) {
    String insertSql = transformToInsertSql(user);
     String result = executeSqlResult(userHolder.getENV(),SCHEMA,transformToInsertSql(user));
    try{
      if(StringUtils.isNoneBlank(result)){
        JSONObject jsonResult = JSONObject.fromObject(result);
        if(jsonResult.getJSONObject("status").getInt("code")==0){
          operationDao.insertExecuteSqlHistory(userHolder.getENV(),insertSql);
        }
      }
    }catch (Exception e){
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public DatabaseUserInfo getUserById(long id, long version) {
    String statement = "select * from database_user_info where id = " + id + " and version=" + version;
    try {
      ResultSet resultSet = executeQuery(userHolder.getENV(), SCHEMA, statement);
      List<DatabaseUserInfo> result = getDataByClassFiled(resultSet, DatabaseUserInfo.class);
      if (result.isEmpty()) {
        return null;
      }

      return result.get(0);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public String shareUserFavoriteSql(int userId, long sqlId) {
    String statement = "insert into database_user_favorite(user_id ,favorite_sql_detail ,name,delete_flag) select " + userId + " , favorite_sql_detail ,name , delete_flag from database_user_favorite where id = " +  sqlId;
    try {
       return executeSqlResult(userHolder.getENV(), SCHEMA, statement);
    }catch (Exception e) {
      return ReturnJacksonUtil.resultFail("error",e.toString(), Locale.CHINESE);
    }
  }

  @Override
  public String getUserNames(String key){
    String statement = "select id, account from database_user_info where account like '%" + key + "%'";
    try {
      ResultSet resultSet = executeQuery(userHolder.getENV(), SCHEMA, statement);
      List<DatabaseUserInfo> result = getDataByResultSet(resultSet, DatabaseUserInfo.class);
      if(result.isEmpty()) {
        return StringUtils.EMPTY;
      }
      return ReturnJacksonUtil.resultOk(result);
    } catch (Exception e) {
      e.printStackTrace();
      return StringUtils.EMPTY;
    }
  }

  @Override
  public String getUserInfo(int start, int limit, String userName) {
    String statement = "select id, account, level, online_level, status, default_schema from database_user_info " + "where account like '%" + userName + "%'"+ "limit " + start + " , " + limit;
    try {
      ResultSet resultSet = executeQuery(userHolder.getENV(), SCHEMA, statement);
      String row = executeQueryResultReturn(resultSet);
      JSONArray rowData = JSONArray.fromObject(row);
      Map<String,Object> result = new HashMap<>();
      result.put("rows",rowData);
      result.put("total",countTotal(userHolder.getENV(),SCHEMA));
      return JacksonUtils.toJson(result);
    } catch (Exception e) {
      return ReturnJacksonUtil.resultFail("error",e.toString(),Locale.CHINESE);
    }
  }

  @Override
  public String updateUserInfo(DatabaseUserInfo userInfo) {
    try {
      executeSqlResult(userHolder.getENV(), SCHEMA, transformToUpdateSql(userInfo));
      return ReturnJacksonUtil.resultOk();
    }catch (Exception e) {
      e.printStackTrace();
      return ReturnJacksonUtil.resultFail("error",e.toString(),Locale.CHINESE);
    }
  }

  @Override
  public String updateDefaultSchema(String defaultSchema) {
    String statement = "update database_user_info set default_schema = '" + defaultSchema + "' where id = " + userHolder.getUserInfo().getId();
    try {
      return executeSqlResult(userHolder.getENV(), SCHEMA, statement);
    }catch (Exception e) {
      return ReturnJacksonUtil.resultFail("error",e.toString(), Locale.CHINESE);
    }
  }

  @Override
  public String deleteUser(String idList) {
    String statement = "delete from database_user_info where id in (" + idList + ")";
    try {
      return executeSqlResult(userHolder.getENV(), SCHEMA, statement);
    }catch (Exception e) {
      return ReturnJacksonUtil.resultFail("error",e.toString(), Locale.CHINESE);
    }
  }

  @Override
  public String getSqlDetailById(int sqlId) {
    String statement = "select * from database_user_sql_history where id = " + sqlId ;
    try {
      ResultSet resultSet = executeQuery(userHolder.getENV(), SCHEMA, statement);
      List<DatabaseUserSqlHistory> result = getDataByClassFiled(resultSet, DatabaseUserSqlHistory.class);
      if (result.isEmpty()) {
        return null;
      }
      DatabaseUserSqlHistory sqlHistory= result.get(0);
      return ReturnJacksonUtil.resultOk(sqlHistory.getSqlDetail());
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

}
