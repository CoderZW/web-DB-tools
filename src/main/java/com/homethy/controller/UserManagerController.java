package com.homethy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.homethy.UserHolder;
import com.homethy.domain.DatabaseUserInfo;
import com.homethy.service.UserService;
import com.homethy.util.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
public class UserManagerController {

  @Autowired
  private UserHolder userHolder;

  @Autowired
  private UserService userService;

  @RequestMapping(value = "update_password", method = RequestMethod.GET)
  public ModelAndView updatePassword() {
    ModelAndView view = new ModelAndView("update_password");
    view.addObject("env",userHolder.getENV());
    view.addObject("user",userHolder.getUserInfo());
    view.addObject("updatePasswordinterval", DateUtil.datesBetween(userHolder.getUserInfo().getLastUpdatePasswordTime(),new Date()));
    view.addObject("isInitPw","6a8c2125406ca9963fcd2aec1578155a".equals(userHolder.getUserInfo().getPassword()));
    view.addObject("schemaList",userHolder.getSchemaList());
    return view;
  }

  @RequestMapping(value = "history",method =RequestMethod.GET)
  public ModelAndView history()
  {
    ModelAndView view = new ModelAndView("history");
    view.addObject("env",userHolder.getENV());
    view.addObject("user",userHolder.getUserInfo());
    view.addObject("historySqlList",userService.queryDatabaseUserHistorySql());
    return view;
  }

  @ResponseBody
  @RequestMapping(value = "update_password_submit", method = RequestMethod.POST)
  public String updatePasswordSubmit (
      @RequestParam(value =  "password", required = true) String password,
      @RequestParam(value = "newPassword", required = true) String newPassword,
      @RequestParam(value = "confirmPassword", required = true) String
          confirmPassword) throws JsonProcessingException {
    return userService.updateUserPassword(password,newPassword,confirmPassword);
  }


  @RequestMapping(value = "create_user", method = RequestMethod.GET)
  public ModelAndView createUser() {
    ModelAndView view = new ModelAndView("page_not_found");
    if(userHolder.getUserInfo().getLevel()==9){
      view = new ModelAndView("create_user");
    }
    view.addObject("env",userHolder.getENV());
    view.addObject("user",userHolder.getUserInfo());
    view.addObject("databaseEnvList",userHolder.getDatabaseEnv());
    return view;
  }

  @ResponseBody
  @RequestMapping(value = "createUserSubmit", method = RequestMethod.POST)
  public String createUserSubmit (
      @RequestParam(value =  "account", required = true) String account,
      @RequestParam(value = "password", required = true) String password,
      @RequestParam(value = "confirmPassword", required = true) String  confirmPassword,
      @RequestParam(value = "level", required = false,defaultValue = "0") int level,
      @RequestParam(value = "onlineLevel", required = false, defaultValue = "0") int onlineLevel,
    @RequestParam(value = "defaultSchema", required = false, defaultValue = "sitebuilt") String defaultSchema) throws JsonProcessingException {
    return userService.createUserSubmit(account,password,confirmPassword,level,onlineLevel,defaultSchema);
  }

  @RequestMapping(value = "userManager", method = RequestMethod.GET)
  public ModelAndView userManager() {
    ModelAndView view = new ModelAndView("page_not_found");
    if(userHolder.getUserInfo().getLevel()==9){
      view = new ModelAndView("userManager");
    }
    view.addObject("env",userHolder.getENV());
    view.addObject("user",userHolder.getUserInfo());
    view.addObject("databaseEnvList",userHolder.getDatabaseEnv());
    view.addObject("schemaList",userHolder.getSchemaList());
    return view;
  }

  @ResponseBody
  @RequestMapping(value = "getUserInfo", method = RequestMethod.GET)
  public String getUserInfo(@RequestParam(value = "limit") int limit,
    @RequestParam(value = "offset") int offset,
    @RequestParam(value = "key", required = false, defaultValue = "") String userName) {
    return userService.queryDatabaseUserInfo(limit, offset, userName);
  }

  @ResponseBody
  @RequestMapping(value = "update/UserInfo", method = RequestMethod.GET)
  public String updateUserInfo(@RequestParam(value = "id") int id,
    @RequestParam(value = "account") String account,
    @RequestParam(value = "level") int level,
    @RequestParam(value = "online_level") int online_level,
    @RequestParam(value = "status") int status,
    @RequestParam(value = "default_schema") String default_schema) {
    DatabaseUserInfo databaseUserInfo = new DatabaseUserInfo();
    databaseUserInfo.setId(id);
    databaseUserInfo.setAccount(account);
    databaseUserInfo.setLevel(level);
    databaseUserInfo.setOnlineLevel(online_level);
    databaseUserInfo.setStatus(status);
    databaseUserInfo.setDefaultSchema(default_schema);
    return userService.updateUserInfo(databaseUserInfo);
  }

  @ResponseBody
  @RequestMapping(value = "update/defaultSchema", method = RequestMethod.GET)
  public String updateDefaultSchema(@RequestParam(value = "defaultSchema") String defaultSchema) {
    return userService.updateDefaultSchema(defaultSchema);
  }

  @ResponseBody
  @RequestMapping(value = "deleteUser", method = RequestMethod.GET)
  public String deleteUser(@RequestParam(value = "idList") String idList) {
    return userService.deleteUser(idList);
  }

  @ResponseBody
  @RequestMapping(value = "getSqlDetail", method = RequestMethod.GET)
  public String getSqlDetail(@RequestParam(value = "sqlId") int sqlId) {
    return userService.getSqlDetail(sqlId);
  }

}
