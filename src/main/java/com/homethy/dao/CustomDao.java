package com.homethy.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author qian.xu@renren-inc.com on 17-10-11.
 */
@Mapper
public interface CustomDao {
  public static final String SCHEMA_SITEBUILT = "sitebuilt";
  public static final String SCHEMA_SITEBUILT_PREVIEW = "sitebuilt_preview";

  @Select("select COLUMN_NAME from information_schema.columns where table_schema = #{schemaName} " +
      "and table_name=#{tableName} ")
  List<String> getColumns(@Param("tableName") String table, @Param("schemaName") String schemaName);

  @Select("select count(*) > 0 from `information_schema`.`views` where `TABLE_NAME` = #{viewName}" +
      " and TABLE_SCHEMA = #{schemaName}")
  boolean isViewExistInSchema(@Param("viewName") String viewName, @Param("schemaName") String
      schemaName);

  @Select("select count(*) > 0 from `information_schema`.`tables` where `TABLE_NAME` = " +
      "#{tableName} and table_type='base table' and TABLE_SCHEMA = #{schemaName}")
  boolean isTableExistInSchema(@Param("tableName") String tableName, @Param("schemaName") String
      schemaName);

  @Select("SELECT `COLUMN_NAME` FROM information_schema.KEY_COLUMN_USAGE " +
      "WHERE TABLE_SCHEMA=#{schemaName} AND TABLE_NAME=#{tableName} AND CONSTRAINT_NAME='PRIMARY' ")
  List<String> getPrimaryKey(@Param("tableName") String table, @Param("schemaName") String
      schemaName);
}
