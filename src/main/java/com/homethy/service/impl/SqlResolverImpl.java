package com.homethy.service.impl;

import com.homethy.constant.SqlType;
import com.homethy.dao.CustomDao;
import com.homethy.domain.SqlCommand;
import com.homethy.service.SqlParse;
import com.homethy.service.SqlResolver;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qian.xu@renren-inc.com on 17-10-12.
 */
@Service
public class SqlResolverImpl implements SqlResolver {
  @Autowired
  private CustomDao customDao;

  @Autowired
  private SqlParse sqlParse;

  public void te(String sql) {
    if (StringUtils.isBlank(sql)) {
      // todo
      return;
    }

    String trimCommand;
    String[] commands = sql.trim().split("\\s*;\\s*");
    for (String command : commands) {
      trimCommand = command.trim();
      SqlType sqlType = sqlParse.getSqlType(trimCommand);
      if (sqlType == null) {
        // Todo
        return;
      }

      SqlCommand sqlCommand = sqlParse.getTable(trimCommand, sqlType);
      switch (sqlType) {
        case DELETE:
        case UPDATE:
          if (StringUtils.isBlank(sqlCommand.getDatabase())) {
            // is table
            if (customDao.isTableExistInSchema(sqlCommand.getTable(), CustomDao
                .SCHEMA_SITEBUILT_PREVIEW)) {
              sqlCommand.addNewSql(sqlCommand.getSql().replaceFirst(sqlCommand.getTable(), CustomDao
                  .SCHEMA_SITEBUILT_PREVIEW + "." + sqlCommand.getTable()));
              sqlCommand.addNewSql(sqlCommand.getSql().replaceFirst(sqlCommand.getTable(), CustomDao
                  .SCHEMA_SITEBUILT + "." + sqlCommand.getTable()));
            } else if (customDao.isViewExistInSchema(sqlCommand.getTable(), CustomDao
                .SCHEMA_SITEBUILT_PREVIEW)) {
              sqlCommand.addNewSql(sqlCommand.getSql());
            }
          }
          break;
        case INSERT_COL:

          // if database is null{
          //    if table in pre{
          //      pre.table + ~.tab
          //      if primarykey in columnstr{
          //        columnstr -> map<column, value>
          //        if <primarykey, value> ->value=null{
          //          get primarykey index in columns
          //          ~.Tab -> <primarykey, value> ->value replace to last_insert_id()
          //        }
          //      }else{
          //         ~.Tab -> <primarykey, value> ->value replace to last_insert_id()
          //      }
          //    if view in pre{
          //       ~.Tab
          //    }
          // }
          //
          if (StringUtils.isBlank(sqlCommand.getDatabase())) {
            // is table
            if (customDao.isTableExistInSchema(sqlCommand.getTable(), CustomDao
                .SCHEMA_SITEBUILT_PREVIEW)) {

              sqlCommand.addNewSql(sqlCommand.getSql().replaceFirst(sqlCommand.getTable(), CustomDao
                  .SCHEMA_SITEBUILT_PREVIEW + "." + sqlCommand.getTable()));
              // get primary key
              List<String> primaryKeys = customDao.getPrimaryKey(sqlCommand.getTable(), CustomDao
                  .SCHEMA_SITEBUILT);
              boolean isPrimaryKeyNull = true;
              if (primaryKeys.size() >= 1) {
                if (sqlCommand.getColumnStr().toLowerCase().contains(primaryKeys.get(0)
                    .toLowerCase())) {
                  String[] columns = sqlCommand.getColumnStr().split("\\s*;\\s*");
                  int[] primaryKeysIndex = new int[primaryKeys.size()];
                  int index = 0;
                  String column;
                  for (int i = 0; i < columns.length; i++) {
                    column = columns[i].replace("`", "");
                    for (String primaryKey : primaryKeys) {
                      if (column.equalsIgnoreCase(primaryKey)) {
                        primaryKeysIndex[index] = i;
                        index++;
                      }
                    }
                  }
                  String[] values = sqlCommand.getValueStr().split("\\s*;\\s*");
                  for (int i : primaryKeysIndex) {
                    if (!"null".equalsIgnoreCase(values[i])) {
                      isPrimaryKeyNull = false;
                      break;
                    }
                  }
                }
                //　复合主jian
//                sqlCommand.getColumnStr()
              }
            } else if (customDao.isViewExistInSchema(sqlCommand.getTable(), CustomDao
                .SCHEMA_SITEBUILT_PREVIEW)) {
            }
          }
          // if not contains then  LAST_INSERT_ID()

          break;
        case INSERT_VALUES:

          // if database is null
          //    if table in pre
          //      pre.table + ~.tab
          //      ~.Tab -> < primarykey, value > ->value replace to last_insert_id ()
          //    if view in pre
          //       ~.Tab
          //    end
          // end
          break;
        case INSERT_SET:
          break;
        case INSERT_SELECT:
          break;
      }
      // update delete replace

    }
  }

}
