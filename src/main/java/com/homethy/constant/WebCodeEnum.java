/**
 * Copyright ©2016Renren. All rights reserved.
 */
package com.homethy.constant;

import java.util.Locale;

/**
 * @ClassName: CodeEnum
 * @Description: 中英文网站返回信息枚举类
 * @author: zhe.liu
 * @date: 2016-1-14
 */
public enum WebCodeEnum {

  OK(0, "success", "成功"),
  QUERY_SQL_ERROR(10000, "SQL ERROR", "SQL执行报错"),
  QUERY_SQL_FAIL(10001, "FAIL", "执行失败"),

  // 1默认错误
  UNKNOWN_ERROR(100000, "Server Error", "服务出错"), SIG_ERROR(100001, "Signal Error", "签名检验失败"),
  SESSION_ERROR(100002, "Session Error", "会话出错"), RETURN_NULL_ERROR(100003, "Return Null", "返回为空"),

  // 1.1 Exception
  EXCEPTION_FIELD_NULL(110001, "Field %s can't be null or empty string", "必填信息不能为空"),
  EXCEPTION_FIELD_SCOPE_ERROR(110002, "FIELD %s ", "作用域错误"), EXCEPTION_PARA_ERROR(110003,
      "Pamater %s formate error", "参数格式错误"), EXCEPTION_PRRA_NULL(110004, "Parameter is null",
      "传入参数为空"),

  // 2 登陆
  LOGIN_ACCOUNT_PWD_ERROR(200001, "Your email or password is incorrect", "您输入的账号或密码不正确"),
  LOGIN_GET_TICKET_FAIL(200002, "Failed to login", "设置Ticket失败"),
  LOGIN_NOT_IS_WHITE_ACCOUNT_ERROR(200003, "You account isn't white status", "您不属于白名单用户"),
  LOGIN_AUTHORITY_ERROR(200004, "You have not logged in", "没有登录权限"),

  VISIT_ADMIN_DOMAIN_ERROR(200005, "Admin domain info error", "域名信息错误"),

  AUTHORITY_IP_RESTRICTION(200006, "Authority IP Restriction", "ip 限制"),

  LOGIN_AGENT_ACCOUNT_ERROR(200007, "Sorry, you cannot sign in as a lead of your own team",
      "抱歉，您不能在自己team的站上登录"),

  // 2.1 注册
  REG_EMAIL_FORMATE_ERROR(210001, "Sorry, please input a valid email address.", "请输入正确邮箱地址"),
  REG_EMAIL_ALREADY_TAKEN(210002, "Email already taken", "您输入的邮箱已注册"), REG_PASSWORD_IS_NULL
      (210003, "Password must between 6 and 20 characters", "密码长度为6-20字符"), REG_INFO_IS_NULL
      (210005, "The content can't be blank", "必填信息请补充完整"), REG_USER_FAILED(210006, "Failed to " +
      "register", "注册失败"), REG_USER_NAME_SPLIT_FAILED(210008, "Register user chinese name spell "
      + "wrong", "中文名格式"), REG_PROMO_CODE_ERROR(210012, "The promotion code you entered is " +
      "invalid", "优惠码输入错误"), REG_AGENT_USER_ERROR(210013, "Sorry, you cannot register as a lead "
      + "of your own team", "抱歉，您不能在自己团队的站上注册"), REG_PHONE_ERROR(210014, "Sorry, please input a "
      + "valid phone.", "请输入正确电话号码"),

  // 3 收藏
  COLLECT_TYPE_FAILED(300001, "Collect type is failed", "收藏类型错误"), COLLECT_LISITNGID_FAILED
      (300002, "Collect lisitng id isn't exit", "收藏的listingId不存在"), COLLECT_PAGENO_FAILED(300003,
      "Collect lisitng pageNo failed", "分页数量error"),

  //4 lsiting
  LISTING_QUICKSEARCH_NULL_ERROR(400002, "quick search listing is null error", "快速搜索的内容为空"),
  LISTING_GET_WALK_SCORE_ERROR(400003, "get listing walkscore error", "获得listing walk score错误"),
  //4.1 lsiting
  BLOG_PAGENO_FAILED(401001, "blog pageNo failed", "分页数量error"),

  AGENT_INFO_ERROR(402001, "agent id is null", "agent id 不存在"),

  // 5- Site

  EMAIL_ADDRESS_FORMAT_ERROR(500012, "email address error", "邮件地址格式错误"), LEAD_ACTION_TYPY_ERROR
      (500014, "dialog action type error", "行为类型error"), SETTING_PASSWORD_ERROR(500015,
      "Password" + " error", "密码错误"), PRE_DOMAIN_ALREADY_TAKEN(500016, "Domain already exists",
      "您输入的域名已注册"), SEND_REGISTER_EMAIL_ERROR(500017, "Send register mail error", "发送注册邮件失败"),
  SEND_REGISTER_EMAIL_PARAM_ERROR(500018, "Send register email data error", "注册邮件参数异常"),
  REQUEST_MLS_ORG_PARAM_ERROR(500019, "Request mls org list param error", "获取mlsOrg列表请求的参数错误"),

  // 51-Search error
  SAVE_SEARCH_SUCCESS(510000, "Your search has been saved successfully", "保存成功"),
  SAVE_SEARCH_PARAM_ERROR(510001, "The filed is empty", "save search输入参数为空"),
  SAVE_SEARCH_PAGENO_FAILED(510004, "Save search listing pageNo error", "save search分页数量错误"),
  SAVE_SEARCH_UPDATE_ERROR(510005, "Update save search error", "修改save search错误"),
  SAVE_SEARCH_DELETE_ERROR(510006, "Delete save search error", "删除save search错误"),

  //52-automatic built site
  AUTOMATIC_BUILTSITE_TYPE_ERROR(520000, "Automatic built site type error", "建站类型错误"),
  AUTOMATIC_BUILTSITE_AUTHORITY_ERROR(520001, "Automatic built site authority error", "没有建站权限"),
  AUTOMATIC_BUILTSITE_USER_ERROR(520002, "Automatic built site user errror", "建站用户不存在"),
  AGENT_INFO_COLLECTION_ERROR(520003, "Agent info collection errror", "agent资料收集出错"),
  AGENT_INFO_DOMAIN_EMTRY(520004, "Domain info is emtry", "url domain没有填写"),
  AGENT_INFO_DEFINED_DOMAIN_EMTRY(520005, "Defined domain info is emtry", "url domain没有填写"),
  AGENT_INFO_DOMAIN_LIMIT_ERROR(520006, "Domain name is revert filed", "该域名为保留字段"),
  AGENT_INFO_CHANGE_DOMAIN_ERROR(520007, "You have submitted, the domain can not be changed",
      "agent资料收集出错"), AGENT_INFO_COLLECTION_EMPTY(520008, "Agent info collection data is empty",
      "agent 收集页信息为空"), AGENT_INFO_DOMAIN_EXITS(520009, "The domain name has already existed",
      "该域名已存在"), AGENT_INFO_DOMAIN_FORMAT_ERROR(520010, "Sorry, no spaces/symbols allowed",
      "域名格式error"), DATA_COLLECT_INFO_IS_NOT_INIT(520011, "Data collection info isn't init",
      "资料收集页面未初始化"), CUSTOMIZED_AGENT_INFO_REPEATED(520012, "Record exsits,please use " +
      "modification.", "记录已经存在，如需更新，请使用修改功能"),

  // 15 gather agent info
  GATHER_AGENTINFO_PARAM_ERROR(1500001, "Param error", "参数error"),
  GATHER_AGENTINFO_INVALID_AGENTINFOURL(1500002, "Invalid agent info url error", "初始url error"),
  GATHER_AGENTINFO_INVALID_INITEMAIL(1500003, "Invalid init email", "初始化邮箱error"),
  GATHER_AGENTINFO_INVALID_PASSPORT(1500004, "Invalid generate url passport", "初始化passport " +
      "error"), GATHER_AGENTINFO_INVALID_ACCOUNT(1500005, "Invalid generate account", "初始化账号失败"),

  LEAD_IS_NOT_EXIST(600012, "You cannot send message to yourself", "该留言lead不存在"),
  LISTING_INFO_IS_NOT_EXIST(600013, "listing info is not exist", "该listing 信息不存在"),
  RESULT_IS_NOT_EXIST(600013, "result is null", "该listing 信息不存在");

  private int errorCode; // 错误码

  private String enErrorMsg; // 英文错误信息

  private String zhErrorMsg; // 中文错误信息

  private WebCodeEnum(int errorCode, String enErrorMsg, String zhErrorMsg) {
    this.errorCode = errorCode;
    this.enErrorMsg = enErrorMsg;
    this.zhErrorMsg = zhErrorMsg;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  public String getEnErrorMsg() {
    return enErrorMsg;
  }

  public void setEnErrorMsg(String enErrorMsg) {
    this.enErrorMsg = enErrorMsg;
  }

  public String getZhErrorMsg() {
    return zhErrorMsg;
  }

  public void setZhErrorMsg(String zhErrorMsg) {
    this.zhErrorMsg = zhErrorMsg;
  }

  // 根据中英文获取相应的中英文错误信息
  public String getErrorMsg(Locale locale) {
    if (locale.equals(Locale.CHINESE) || locale.equals(Locale.CHINA) || locale.equals(Locale
        .TAIWAN) || locale.equals(Locale.PRC) || locale.equals(Locale.SIMPLIFIED_CHINESE) ||
        locale.equals(Locale.TRADITIONAL_CHINESE)) {
      return zhErrorMsg;
    } else {
      return enErrorMsg;
    }
  }

}

