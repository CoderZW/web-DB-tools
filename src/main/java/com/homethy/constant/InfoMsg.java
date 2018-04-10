package com.homethy.constant;

/**
 * Created by lanxu on 2017/7/25.
 */
public enum InfoMsg {
  //检验成功，数据未改变,用1XX表示
  NO_ERROR(101, "NO_ERROR", "没有错误"),

  //警告信息,用2XX表示
  INFO_CHANGED(201, "INFO_CHANGED", "页面数据有变更"),
  INFO_LOST(202, "INFO_LOST", "新抓取页面属性缺失"),
  NUM_NOT_MATCH(203, "NUM_NOT_MATCH", "属性个数不匹配"),
  QUICK_SEARCH_NOT_MATCH(204, "QUICK_SEARCH_NOT_MATCH", "quickSearch不匹配"),
  MENU_MORE(209, "MENU_MORE", "新页面多出菜单子项"),
  MENU_ONLINE_GET_ERROR(210, "MENU_ONLINE_GET_ERROR", "获取线上菜单失败"),
  DOMAIN_REDIRECT(211, "DOMAIN_REDIRECT", "页面重定向"),
  MSG_MORE(212, "MSG_MORE", "新页面多出数据项"),
  MENU_LOST(213, "MENU_LOST", "菜单项缺失"),
  MENU_NOT_MATCH(214, "NU_NOT_MATCH", "菜单项不匹配"),
  MENU_ORDER_NOT_MATCH(215, "MENU_ORDER_NOT_MATCH", "菜单顺序从此处开始不匹配"),
  FILE_NOT_FOUND(217, "FILE_NOT_FOUND", "文件没找到"),
  MENU_LINK_NOT_MATCH(216, "MENU_LINK_NOT_MATCH", "菜单链接不一致"),


  //错误信息，用3XX表示
  FAILED(300, "FAILED", "检测站点失败"),
  INFO_NOT_SAVED(307, "INFO_NOT_SAVED", "原始数据尚未保存"),
  HOME_NOT_GET(340, "HOME_NOT_GET", "home.json获取不到"),
  WRITE_FILE_FAILED(341, "WRITE_FILE_FAILED", "写文件失败");

  private int index;
  private String name;
  private String detail;

  InfoMsg(int index, String name, String detail) {
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
