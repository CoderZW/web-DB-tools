package com.homethy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.homethy.UserHolder;
import com.homethy.domain.DatabaseUserInfo;
import com.homethy.service.DataOperationService;
import com.homethy.service.UserService;
import com.homethy.util.HomethyStringUtil;
import com.homethy.util.ReturnJacksonUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OperationController {

//  @Value("${env}")
//  private String ENV;

  @Autowired
  UserHolder userHolder;

  @Autowired
  UserService userService;

  @Autowired
  DataOperationService dataOperationService;

  @ResponseBody
  @RequestMapping(value = "executeSql", method = RequestMethod.POST)
  public String update (
    @RequestParam(value =  "env") String env,
    @RequestParam(value = "schema") String schema,
    @RequestParam(value = "statement") String
        statement) throws JsonProcessingException {
      return dataOperationService.executeSql(env,schema, HomethyStringUtil.replceMultipleOnlySpace(statement));
  }


  @RequestMapping(value = "operate", method = RequestMethod.GET)
  public ModelAndView operate() {
    ModelAndView view = new ModelAndView("operate/operate");
    view.addObject("env",userHolder.getENV());
    view.addObject("user",userHolder.getUserInfo());
    view.addObject("favoriteSqlList",userService.queryDatabaseUserFavoriteSql());
    view.addObject("databaseEnvList",userHolder.getDatabaseEnv());
    view.addObject("schemaList",userHolder.getSchemaList());
    return view;
  }

  @ResponseBody
  @RequestMapping(value = "saveUserFavoriteSql", method = RequestMethod.POST)
  public String saveUserFavoriteSql (
      @RequestParam(value =  "id", required = false,defaultValue = "0") long id,
      @RequestParam(value = "name") String name,
      @RequestParam(value = "statement") String statement) throws JsonProcessingException {
    if(id == 0 ){
      userService.saveUserFavoriteSql(name, statement);
    }else{
      return userService.updateUserFavoriteSql(id,name, statement);
    }
    return ReturnJacksonUtil.resultOk(userService.queryDatabaseUserFavoriteSql());
  }

  @ResponseBody
  @RequestMapping(value = "deleteUserFavoriteSql", method = RequestMethod.POST)
  public String deleteUserFavoriteSql (
      @RequestParam(value = "id") long id) throws JsonProcessingException {
    return userService.deleteUserFavoriteSql(id);
//    return ReturnJacksonUtil.resultOk(userService.queryDatabaseUserFavoriteSql());
  }

  @RequestMapping(value = "queryFavoriteSql", method = RequestMethod.GET)
  public ModelAndView queryFavoriteSql() {
    ModelAndView view = new ModelAndView("operate/operate_favorite_sql");
    view.addObject("favoriteSqlList",userService.queryDatabaseUserFavoriteSql());
    return view;
  }

  @ResponseBody
  @RequestMapping(value = "getTables", method = RequestMethod.GET)
  public String getTables(
    @RequestParam(value =  "env", required = false, defaultValue = "") String env,
    @RequestParam(value = "schema") String schema
  ) throws JsonProcessingException {
    if(StringUtils.isBlank(env)) {
      env = userHolder.getENV();
    }
    return dataOperationService.getAllTablesWithSchema(env,schema);
  }

  @ResponseBody
  @RequestMapping(value = "shareUserFavoriteSql", method = RequestMethod.POST)
  public String shareUserFavoriteSql(
    @RequestParam(value = "userId") int userId,
    @RequestParam(value = "sqlId") long sqlId

  ) {
    return userService.shareUserFavoriteSql(userId,sqlId);
  }


  @ResponseBody
  @RequestMapping(value = "getUserNames", method = RequestMethod.GET)
  public String getUserName(@RequestParam(value = "key") String key) {
    return userService.getUserNamesByKey(key);
  }



}
