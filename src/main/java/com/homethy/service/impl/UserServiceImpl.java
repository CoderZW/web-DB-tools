package com.homethy.service.impl;

import com.homethy.UserHolder;
import com.homethy.constant.Constant;
import com.homethy.dao.UserDao;
import com.homethy.domain.DatabaseUserFavorite;
import com.homethy.domain.DatabaseUserInfo;
import com.homethy.domain.DatabaseUserSqlHistory;
import com.homethy.service.UserService;
import com.homethy.util.HomethyStringUtil;
import com.homethy.util.MD5Support;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zwk on 17-1-10.
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserDao userDao;

  @Autowired
  UserHolder userHolder;

  @Override
  public DatabaseUserInfo getUserInfoByAccount(String env, String accout){
    List<DatabaseUserInfo> userList = userDao.queryUserInfoByAccount(env,accout);
    if(CollectionUtils.isNotEmpty(userList)){
      return userList.get(0);
    }
    return null;
  }

  @Override
  public void updateLastLoginData(String env,DatabaseUserInfo user){
    userDao.updateLastLoginData(env,user);
  }

  @Override
  public String saveUserFavoriteSql(String name,String statement){
    DatabaseUserFavorite databaseUserFavorite = new DatabaseUserFavorite();
    databaseUserFavorite.setName(name);
    databaseUserFavorite.setFavoriteSqlDetail(HomethyStringUtil.replceSingleQuot(statement));
    databaseUserFavorite.setClientIp(userHolder.getClientIp());
    databaseUserFavorite.setUserId(userHolder.getUserInfo().getId());
    return userDao.saveUserFavoriteSql(databaseUserFavorite);
  }

  @Override
  public String updateUserFavoriteSql(long id,String name,String statement){
    DatabaseUserFavorite databaseUserFavorite = new DatabaseUserFavorite();
    databaseUserFavorite.setId(id);
    databaseUserFavorite.setName(name);
    databaseUserFavorite.setFavoriteSqlDetail(HomethyStringUtil.replceSingleQuot(statement));
    databaseUserFavorite.setClientIp(userHolder.getClientIp());
    databaseUserFavorite.setUserId(userHolder.getUserInfo().getId());
    databaseUserFavorite.setUpdateTime(new Date());
    return userDao.updateUserFavoriteSql(databaseUserFavorite);
  }

  @Override
  public List<DatabaseUserFavorite> queryDatabaseUserFavoriteSql(){
    return userDao.queryDatabaseUserFavoriteSql();
  }

  @Override
  public String queryDatabaseUserHistorySql(){
    return userDao.queryDatabaseUserHistorySql();
  }
  @Override
  public String deleteUserFavoriteSql(long id){
    return userDao.deleteUserFavoriteSql(id);
  }


  @Override
  public String updateUserPassword(String password,String newPassword,String confirmPassword){
    String encPassword = MD5Support.hex(password, Constant.MD5KEY);

    if(!(newPassword.equals(confirmPassword))){
      return HomethyStringUtil.returnResultStr("error","2次输入的密码不匹配");
    }else if(!(userHolder.getUserInfo().getPassword().equals(encPassword))){
      return HomethyStringUtil.returnResultStr("error","原始密码不正确");
    }else {
      String result = userDao.updateUserPassword(MD5Support.hex(newPassword, Constant.MD5KEY));
      if(StringUtils.isNotBlank(result) && result.contains("执行成功，影响行数：1")){
        //更新session中user的密码位新密码
        userHolder.getUserInfo().setPassword(MD5Support.hex(newPassword, Constant.MD5KEY));
      }
      return result;
    }
  }

  @Override
  public String createUserSubmit(String account, String password, String confirmPassword, int level, int onlineLevel, String defaultSchema) {
    DatabaseUserInfo databaseUserInfo = getUserInfoByAccount(userHolder.getENV(),account);
    if(databaseUserInfo!=null){
      return HomethyStringUtil.returnResultStr("error","用户名重复！");
    }else{
      if(!(password.equals(confirmPassword))){
        return HomethyStringUtil.returnResultStr("error","2次输入的密码不匹配");
      }
      DatabaseUserInfo userInfo = new DatabaseUserInfo();
      userInfo.setAccount(account);
      userInfo.setPassword(MD5Support.hex(password, Constant.MD5KEY));
      userInfo.setLevel(level);
      userInfo.setOnlineLevel(onlineLevel);
      userInfo.setDefaultSchema(defaultSchema);
      userInfo.setStatus(1);
      return userDao.insertUser(userHolder.getENV(),userInfo);
    }
  }

  @Override
  public DatabaseUserInfo getUserInfoById(long id,long version) {
    return userDao.getUserById(id,version);
  }

  @Override
  public String shareUserFavoriteSql(int userId, long sqlId){
    return userDao.shareUserFavoriteSql(userId, sqlId);
  }

  @Override
  public String getUserNamesByKey(String key){
    return userDao.getUserNames(key);
  }

  @Override
  public String queryDatabaseUserInfo(int limit, int offset, String userName) {
    return userDao.getUserInfo(offset, limit, userName);
  }

  @Override
  public String updateUserInfo(DatabaseUserInfo userInfo){
    return userDao.updateUserInfo(userInfo);
  }

  @Override
  public String updateDefaultSchema(String defaultSchema) {
    return userDao.updateDefaultSchema(defaultSchema);
  }

  @Override
  public String deleteUser(String idList) {
    idList = idList.substring(0, idList.length()-1);
    return userDao.deleteUser(idList);
  }

  @Override
  public String getSqlDetail(int sqlId) {
    return userDao.getSqlDetailById(sqlId);
  }
}
