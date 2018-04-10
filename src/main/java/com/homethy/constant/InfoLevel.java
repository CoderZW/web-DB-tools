package com.homethy.constant;

/**
 * Created by lanxu on 2017/7/25.
 */
public enum InfoLevel {
  INFO(1, "INFO", "通知"),
  WARN(2, "WARN", "警告"),
  ERROR(3, "ERROR", "错误");

  private int index;
  private String name;
  private String detail;

  InfoLevel(int index, String name, String detail) {
    this.index = index;
    this.name = name;
    this.detail = detail;
  }

  public int getIndex() {
    return index;
  }
  
  public String getName() {
    return name;
  }

  public String getDetail() {
    return detail;
  }
}
