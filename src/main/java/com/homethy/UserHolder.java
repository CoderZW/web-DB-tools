package com.homethy;

import com.homethy.domain.DatabaseUserInfo;
import com.homethy.util.ApiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leifeifei on 17-10-12.
 */
@Component
public class UserHolder {

  @Value("${env}")
  private String ENV;
  @Value("${databaseEnv}")
  private String databaseEnv;
  @Value("${schema.list}")
  private String schemaList;


  @Autowired
  private HttpServletRequest httpServletRequest;

  public DatabaseUserInfo getUserInfo(){
    HttpSession session = httpServletRequest.getSession();
    return (DatabaseUserInfo)session.getAttribute(WebSecurityConfig.SESSION_KEY);
  }

  public String getENV(){
    return ENV;
  }

  public List getDatabaseEnv(){
    String envStr [] = databaseEnv.split(",");
    List strList = new ArrayList();
    for (String str : envStr) {
      strList.add(str);
    }
    return strList;
  }

  public List getSchemaList(){
    String envStr [] = schemaList.split(",");
    List strList = new ArrayList();
    for (String str : envStr) {
      strList.add(str);
    }
    return strList;
  }

  public String getClientIp(){
    return ApiUtil.getClientIP(httpServletRequest);
  }

  public String getServerIp(){
    return ApiUtil.getLocalIP();
  }
}
