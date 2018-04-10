package com.homethy.constant;

/**
 * @author qian.xu@renren-inc.com on 17-10-12.
 */
public enum SqlType {
  SELECT(0, "select", "select\\s+", ""),

  //  INSERT_COL(1, "insert_col", "insert\\s + into\\s +[a-z0-9_`\\.]+\\s*\\([\\S\\s]+\\)" +
//      "\\s*values\\s*\\(", 2),
  // insert into a (col1,col2...) values (...)
  INSERT_COL(1, "insert_col",
      "insert\\s + into\\s +[a-z0-9_`\\.]+\\s+\\([\\S\\s]+\\)\\s*values\\s*\\(",
      "insert\\s+into\\s+(.+)\\s*\\((.+)\\)\\s*values\\s*\\((.+)\\)"),
  INSERT_VALUES(2, "insert_values", "insert\\s+into\\s+[a-z0-9_`'\"\\.]+\\s+values",
      "insert\\s+into\\s+(.+)\\s+values"),
  INSERT_SET(3, "insert_set", "insert\\s+into\\s+[a-z0-9_`'\"\\.]+\\s+set\\s+",
      "insert\\s+into\\s+(.+)\\s+set\\s+"),
  INSERT_SELECT(4, "insert_select", "insert\\s+into\\s+[a-z0-9_`'\"\\.]+\\s+select\\s+",
      "insert\\s+into\\s+(.+)\\s+select\\s+"),
  UPDATE(5, "update", "update\\s+[a-z0-9_`'\"\\.]+\\s+set\\s+",
      "update\\s+(.+)\\s+set\\s+"),
  DELETE(6, "delete", "delete\\s+from\\s+[a-z0-9_`'\"\\.]+\\s*",
      "delete\\s+from\\s+(.+)\\s*");

  private int code;
  private String value;
  private String mask;
  private String tableMask;

  private SqlType(int code, String value, String mask, String tableMask) {
    this.code = code;
    this.value = value;
    this.mask = mask;
    this.tableMask = tableMask;
  }

  public int getCode() {
    return this.code;
  }

  public String getValue() {
    return this.value;
  }

  public String getMask() {
    return this.mask;
  }

  public String getTableMask() {
    return this.tableMask;
  }

  public SqlType get(int code) {
    SqlType[] sqlTypes = SqlType.values();
    for (SqlType sqlType : sqlTypes) {
      if (sqlType.getCode() == code) {
        return sqlType;
      }
    }
    return null;
  }
}
