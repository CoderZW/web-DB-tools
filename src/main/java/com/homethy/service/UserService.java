package com.homethy.service;


import com.homethy.domain.DatabaseUserFavorite;
import com.homethy.domain.DatabaseUserInfo;
import com.homethy.domain.DatabaseUserSqlHistory;

import java.util.List;

public interface UserService {

  DatabaseUserInfo getUserInfoByAccount(String env, String accout);

  void updateLastLoginData(String env,DatabaseUserInfo user);

  String saveUserFavoriteSql(String name,String statement);

  String updateUserFavoriteSql(long id,String name,String statement);

  List<DatabaseUserFavorite> queryDatabaseUserFavoriteSql();

  String queryDatabaseUserHistorySql();

  String deleteUserFavoriteSql(long id);

  String updateUserPassword(String password,String newPassword,String confirmPassword);

  String createUserSubmit(String account,String password,String confirmPassword,int level,int onlineLevel,String defaultSchema);

  DatabaseUserInfo getUserInfoById(long id,long version);

  String shareUserFavoriteSql(int userId, long sqlId);

  String getUserNamesByKey(String key);

  String queryDatabaseUserInfo(int limit, int offset, String userName);

  String updateUserInfo(DatabaseUserInfo userInfo);

  String updateDefaultSchema(String defaultSchema);

  String deleteUser(String idList);

  String getSqlDetail(int sqlId);
}
