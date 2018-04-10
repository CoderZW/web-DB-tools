package com.homethy.dao;

import com.homethy.domain.DatabaseUserFavorite;
import com.homethy.domain.DatabaseUserInfo;
import com.homethy.domain.DatabaseUserSqlHistory;

import java.util.List;

/**
 * Created by leifeifei on 17-9-1.
 */
public interface UserDao{

  List<DatabaseUserInfo> queryUserInfoByAccount(String env, String account);

  void updateLastLoginData(String env,DatabaseUserInfo user);

  String saveUserFavoriteSql(DatabaseUserFavorite databaseUserFavorite);

  String updateUserFavoriteSql(DatabaseUserFavorite databaseUserFavorite);

  List<DatabaseUserFavorite> queryDatabaseUserFavoriteSql();

  String queryDatabaseUserHistorySql();

  String deleteUserFavoriteSql(long id);

  String updateUserPassword(String newPassword);

  String insertUser(String env,DatabaseUserInfo user);

  DatabaseUserInfo getUserById(long id,long version);

  String shareUserFavoriteSql(int userId, long sqlId);

  String getUserNames(String key);

  String getUserInfo(int start, int limit, String userName);

  String updateUserInfo(DatabaseUserInfo userInfo);

  String updateDefaultSchema(String defaultSchema);

  String deleteUser(String idList);

  String getSqlDetailById(int sqlId);
}
