package com.homethy.controller;

import com.homethy.service.DataOperationService;
import com.homethy.util.DateUtil;
import com.homethy.util.FileUtil;
import com.homethy.util.JacksonUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class NewLeadAlertController {

  @Autowired
  DataOperationService dataOperationService;


  @RequestMapping(value = "newLeadAlert", method = RequestMethod.GET)
  public ModelAndView createUser() {
    ModelAndView view = new ModelAndView("homethy/lead_alert");
    return view;
  }

  @ResponseBody
  @RequestMapping(value = "newLeadAlert/setLeadAlert", method = RequestMethod.POST)
  public String newLeadAlert (@RequestParam(value =  "domain", required = true) String domain) throws Exception {
    String siteJson = FileUtil.readFileByNamePath("sites.json","/../homethy_new_lead_alert/conf/");
    JSONObject currentJson  = JSONObject.fromObject(siteJson);
    List<String> sitebuiltList = (List<String>)currentJson.get("sitebuilt");
    String lastSite = sitebuiltList.get(sitebuiltList.size()-1);

    String queryResult = dataOperationService.excSqlWithoutHistory("prd","sitebuilt","select pre_domain from website_info where domain_name='"+domain.toLowerCase().trim()+"'");
//    JSONObject queryJson = JSONObject.fromObject(queryResult);
    JSONArray result = JSONArray.fromObject(queryResult);
    Map map = result.size() == 0 ? new HashedMap() : JacksonUtils.parseJSON2Map(result.get(0).toString());
    if(MapUtils.isEmpty(map) || StringUtils.isBlank((String) map.get("pre_domain"))){
      return "查不到该域名信息！三方域名请输入完整域名！";
    }


    String preDomain = (String) map.get("pre_domain");
    String preDomainForInput = "\""+preDomain +"\"";
    if(siteJson.indexOf(preDomainForInput) >= 0){
      return "当前域名已添加过提醒，请勿重复添加！";
    }
    //备份
    boolean backUpFile = FileUtil.fileWrite("sites.json_"+DateUtil.format(new Date(),DateUtil.DATEFORMAT_YYYY_MM_DD),"/../homethy_new_lead_alert/conf/bak/",siteJson);

    StringBuilder sb = new StringBuilder(siteJson);
    sb.insert(siteJson.indexOf("\""+lastSite+"\"")+lastSite.length()+2,",\n\t\t"+preDomainForInput);

    String newSiteJson = sb.toString();

    boolean writeResult = FileUtil.fileWrite("sites.json","/../homethy_new_lead_alert/conf/",newSiteJson);

    if(!writeResult){
      return "写入siteJson失败，请联系技术人员！";
    }

    return "添加提醒成功！";

  }
}
