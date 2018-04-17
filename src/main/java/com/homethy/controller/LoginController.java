package com.homethy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.homethy.UserHolder;
import com.homethy.WebSecurityConfig;
import com.homethy.constant.Constant;
import com.homethy.constant.WebCodeEnum;
import com.homethy.domain.DatabaseUserInfo;
import com.homethy.service.UserService;
import com.homethy.util.CookieUtil;
import com.homethy.util.MD5Support;
import com.homethy.util.ReturnJacksonUtil;

import com.homethy.util.crypto.Base62Encoder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Locale;

/**
 * Created by leifeifei on 17-9-11.
 */
@Controller
public class LoginController {

  @Autowired
  UserService userService;

//  @Autowired
//  DataOperationService dataOperationService;

  private Logger log = LogManager.getLogger(LoginController.class);

//  @Value("${env}")
//  private String ENV;

  @Autowired
  UserHolder userHolder;

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseBody
  public String login(
    @RequestParam(value = "username", required = true) String username,
    @RequestParam(value = "password", required = true) String password,
    HttpSession session,
    HttpServletRequest request,
    HttpServletResponse response
  ) throws JsonProcessingException {
    boolean pass = false;
    DatabaseUserInfo user = userService.getUserInfoByAccount(userHolder.getENV(), username);
    String encPassword = MD5Support.hex(password, Constant.MD5KEY);
    if (user != null && encPassword.equals(user.getPassword()) && user.getStatus() == 1) {
      pass = true;
    }
    if (pass) {
      session.setAttribute("database_user", user);
      session.setMaxInactiveInterval(120 * 60);
      user.setLastLoginTime(new Date());
      user.setLastLoginIp(userHolder.getClientIp());
      CookieUtil.saveSessionCookie(request, response, WebSecurityConfig.USER_COOKIE, CookieUtil.encode(user.getId(),MD5Support.hex(String.valueOf(System.currentTimeMillis()))));
      CookieUtil.saveSessionCookie(request, response, WebSecurityConfig.VERSION, CookieUtil.encode(user.getVersion()*user.getId(),MD5Support.hex(String.valueOf(user.getLastUpdatePasswordTime()))));

      userService.updateLastLoginData(userHolder.getENV(), user);
      return ReturnJacksonUtil.resultOk();
    } else {
      return ReturnJacksonUtil.resultWithFailed(WebCodeEnum.SIG_ERROR);
    }
  }

  @RequestMapping(value = "/logout", method = {RequestMethod.GET})
  public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
    session.removeAttribute("database_user");
    CookieUtil.clear(request, response, WebSecurityConfig.USER_COOKIE);
    return "login";
  }

  @RequestMapping(value = {"/login", "/"}, method = RequestMethod.GET)
  public String login(HttpSession session) {
    if (session.getAttribute("database_user") != null) {
      DatabaseUserInfo user = (DatabaseUserInfo)session.getAttribute("database_user");
      if(user.getLevel()<0){
        return "redirect:rsyncLead";
      }
      return "redirect:operate";
    }
    return "login";
  }

  @RequestMapping(value = "/test", method = RequestMethod.GET)
  public String test(@RequestParam(value = "page", required = true) String page) {
    return page;
  }

  @RequestMapping(value = "/page/{path}/{page}", method = RequestMethod.GET)
  public ModelAndView pageTest(@PathVariable( "path") String path, @PathVariable( "page") String page) {
    ModelAndView mv = new ModelAndView(path+"/"+page);
    return mv;
  }
}
