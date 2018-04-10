package com.homethy.controller;

import com.homethy.UserHolder;
import com.homethy.domain.GfLeadResult;
import com.homethy.domain.LeadRsyncStatus;
import com.homethy.service.DataOperationService;
import com.homethy.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class RsyncLeadController {

  private static final Log LOGGER = LogFactory.getLog(RsyncLeadController.class);

  public static final String GF_LEAD_API="http://chime.geographicfarmvalues.com/ws/ws.php/lead/admin/list";

  @Value("${rsync.partial.lead}")
  private String RSYNC_PARTIAL_LEAD;

  @Value("${rsync.full.lead}")
  private String RSYNC_FULL_LEAD;

  @Value("${prd.rsync.partial.lead}")
  private String PRD_RSYNC_PARTIAL_LEAD;

  @Value("${prd.rsync.full.lead}")
  private String PRD_RSYNC_FULL_LEAD;

  private static Set<String> keySet = new HashSet();

  private static Set<String> buildSitekeySet = new HashSet();

  @Autowired
  DataOperationService dataOperationService;

  @Autowired
  UserHolder userHolder;


  @RequestMapping(value = "rsyncLead", method = RequestMethod.GET)
  public ModelAndView rsyncLead() {
    ModelAndView view = new ModelAndView("homethy/rsync_lead");
    view.addObject("env",userHolder.getENV());
    return view;
  }

  @RequestMapping(value = "rsyncLeadProd", method = RequestMethod.GET)
  public ModelAndView rsyncLeadProd() {
    ModelAndView view = new ModelAndView("homethy/rsync_lead");
    view.addObject("env","prd");
    return view;
  }



  @ResponseBody
  @RequestMapping(value = "lead/rsync", method = RequestMethod.POST)
  public String newLeadAlert (@RequestParam(value =  "crmAccount", required = true) String account,
                              @RequestParam(value =  "gfdomain", required = true) String domain,
                              @RequestParam(value =  "type", required = true) String type,
                              @RequestParam(value =  "env", required = false, defaultValue = "") String env) throws Exception {

    if(StringUtils.isBlank(domain)){
      return "域名输入为空，你想搞事情？";
    }

    if(StringUtils.isBlank(account)){
      return "Chime Crm账号输入为空，同步给你么？";
    }

    if (keySet.contains(env+type+"_"+domain)){
      return "lead 已正在同步中，请勿重复点击同步！";
    }

    LOGGER.error(String.format("开始同步 %s lead...start by %s,crmAccount:%s,gfDomain:%s",type,userHolder.getUserInfo().getAccount(),account,domain));
    String querySql = String.format("select * from client where deleted=0 and (client_domain='%s' or domain2='%s' or domain3='%s' or domain4='%s')",domain.toLowerCase().trim(),domain.toLowerCase().trim(),domain.toLowerCase().trim(),domain.toLowerCase().trim());
    String queryResult = dataOperationService.excSqlWithoutHistory("prd","gf",querySql);

    LOGGER.error("gddbdata:"+queryResult);
    JSONArray result = JSONArray.fromObject(queryResult);
    Map map = result.size() == 0 ? new HashedMap() : JacksonUtils.parseJSON2Map(result.get(0).toString());
    if(MapUtils.isEmpty(map) || StringUtils.isBlank(String.valueOf(map.get("client_id")))){
      return "查不到gf该域名信息！请输入完整域名！浏览器访问确认是否有www等！";
    }

    String domainPath = "/lead/"+domain+"/";
    if(StringUtils.isNotBlank(env) && "prd".equals(env)){
      domainPath="/prd"+domainPath;
    }
    FileUtil.createDir(domainPath);

    //数据库查询的信息保存在文件中
    boolean storeGfDbInfo = FileUtil.fileWrite(domain+".info",domainPath,queryResult);

    if(!storeGfDbInfo){
      LOGGER.error(String.format("查询域名%s信息保存失败",domain));
    }

    if("full".equals(type)){
      rsyncFullLead(domain,account,map,domainPath,env);
    }else if("partial".equals(type)){
      rsyncPartialLead(domain,account,map,domainPath,env);
    }

    return "lead 同步中`````";


  }

  private void rsyncFullLead(String domain, String account, Map map, String domainPath,String env) {
    String key = env+"full_"+domain;
    if (!keySet.contains(key)) {
      keySet.add(key);
      ThreadExecutorUtil.executeTask(new Runnable() {
        @Override
        public void run() {
          try {
            int fullLeadPage = 1;
            long clientId = NumberUtils.toLong(String.valueOf(map.get("client_id")));
            //full lead
            Map<String, Object> params = new HashedMap();
            params.put("clientId", clientId);

            int fullLeadSuccess = 0;
            int fullLeadFail = 0;
            int fullLeadEmailExists = 0;

            Map<String, String> rsyncResultSuccess = new LinkedHashMap();
            Map<String, String> emailExists = new LinkedHashMap();
            Map<String, String> rsyncResultFail = new LinkedHashMap();
            int successPage = 1;
            int failPage = 1;
            int existsPage = 1;
            while (true) {
              long startPartialLeadTime = System.currentTimeMillis();
              params.put("page", fullLeadPage);
              params.put("type", "lead");
              String leadString = SiteHttpUtil.getResponseValueByKey(GF_LEAD_API, params, SiteHttpUtil.CLIENT_5S);
              //本次循环查询的信息
              boolean storeGfQueryResult = FileUtil.fileWrite(domain + "_" + fullLeadPage + "full" + ".info", domainPath, leadString);
              if (!storeGfQueryResult) {
                LOGGER.error(String.format("分页查询lead信息失败，domain：%s,page：%s,type：lead", domain, fullLeadPage));
              }
//              LOGGER.error(domain + "_leadString:" + leadString);
              if (StringUtils.isNotBlank(leadString)) {
                JSONArray fullLeadResult = JSONArray.fromObject(leadString);

                for (int i = 0; i < fullLeadResult.size(); i++) {
                  JSONObject lead = fullLeadResult.getJSONObject(i);
                  LOGGER.error(String.format("-------------------%s----------------------------", lead.getString("leadId")));
                  LOGGER.error(domain + "_queryData:" + JacksonUtils.toJson(getQueryApiPara(lead, true, account)));
                  StringBuilder str = new StringBuilder();
                  str.append(lead.getString("leadId")).append("_")
                      .append(lead.getString("email")).append("_")
                      .append(lead.getString("name")).append("_").append(fullLeadPage);
                  String dataKey = str.toString();
                  try {
                    String url = RSYNC_FULL_LEAD;
                    if("prd".equals(env)){
                      url = PRD_RSYNC_FULL_LEAD;
                    }
                    String rsyncCrm = SiteHttpUtil.getResponseValueByKey(url, getQueryApiPara(lead, true, account), SiteHttpUtil.CLIENT_5S);
                    LOGGER.error(domain + "_crmApiResult:" + rsyncCrm);
                    if (StringUtils.isNotBlank(rsyncCrm)) {
                      JSONObject fullLeadRsync = JSONObject.fromObject(rsyncCrm);
                      //同步该lead成功


                      if (fullLeadRsync.getJSONObject("status") != null && "0".equals(fullLeadRsync.getJSONObject("status").getString("code"))) {
                        rsyncResultSuccess.put(dataKey, "success");
                      } else if(fullLeadRsync.getJSONObject("status") != null && "300002".equals(fullLeadRsync.getJSONObject("status").getString("code"))){
                        emailExists.put(dataKey, fullLeadRsync.getJSONObject("status").getString("msg"));
                      }else {
                        rsyncResultFail.put(dataKey, fullLeadRsync.getJSONObject("status").getString("msg"));
                      }
                    }
                  } catch (Exception e) {
                    LOGGER.error(String.format(domain + " rsync crm Exception,paras:%s,Exception:%s", getQueryApiPara(lead, true, account), e));
                    rsyncResultFail.put(dataKey, e.getMessage());
                    continue;
                  }

                }


                StringBuilder sb = new StringBuilder();
                sb.append(domain).append("_").append("full");
                String filaName = sb.toString();
                //成功
                if (MapUtils.isNotEmpty(rsyncResultSuccess) && rsyncResultSuccess.size()>=20) {
                  fullLeadSuccess = fullLeadSuccess + rsyncResultSuccess.size();
                  boolean rsyncSuccess = FileUtil.fileWrite(successPage+filaName + ".success", domainPath, JacksonUtils.toJson(rsyncResultSuccess));
                  successPage++;
                  //存档后clear
                  rsyncResultSuccess.clear();
                }
                //email 重复
                if (MapUtils.isNotEmpty(emailExists) && emailExists.size()>=20) {
                  fullLeadEmailExists = fullLeadEmailExists + emailExists.size();
                  boolean rsyncFail = FileUtil.fileWrite(existsPage+filaName + ".exist", domainPath, JacksonUtils.toJson(emailExists));
                  existsPage++;
                  emailExists.clear();
                }

                //失败
                if (MapUtils.isNotEmpty(rsyncResultFail) && rsyncResultFail.size()>=20) {
                  fullLeadFail = fullLeadFail + rsyncResultFail.size();
                  boolean rsyncFail = FileUtil.fileWrite(failPage+filaName + ".fail", domainPath, JacksonUtils.toJson(rsyncResultFail));
                  failPage++;
                  rsyncResultFail.clear();
                }

                fullLeadPage++;
                if (fullLeadResult == null || fullLeadResult.size() < 20) {
                  break;
                }

                long endPartialLeadTime = System.currentTimeMillis();

                if (endPartialLeadTime - startPartialLeadTime < 300) {
                  //避免请求过快，处理一次请求停顿200ms；
                  Thread.sleep(200);
                }

              } else {
                break;
              }
            }

            StringBuilder sb = new StringBuilder();
            sb.append(domain).append("_").append("full");
            String filaName = sb.toString();

            //成功
            if (MapUtils.isNotEmpty(rsyncResultSuccess)) {
              fullLeadSuccess = fullLeadSuccess + rsyncResultSuccess.size();
              boolean rsyncSuccess = FileUtil.fileWrite(successPage+filaName + ".success", domainPath, JacksonUtils.toJson(rsyncResultSuccess));
            }
            //email 重复
            if (MapUtils.isNotEmpty(emailExists)) {
              fullLeadEmailExists = fullLeadEmailExists + emailExists.size();
              boolean rsyncFail = FileUtil.fileWrite(existsPage+filaName + ".exist", domainPath, JacksonUtils.toJson(emailExists));
            }

            //失败
            if (MapUtils.isNotEmpty(rsyncResultFail)) {
              fullLeadFail = fullLeadFail + rsyncResultFail.size();
              boolean rsyncFail = FileUtil.fileWrite(failPage+filaName + ".fail", domainPath, JacksonUtils.toJson(rsyncResultFail));
            }


            Map<String, Integer> totalResult = new HashedMap();
            totalResult.put("fullLeadSuccess", fullLeadSuccess);
            totalResult.put("fullLeadFail", fullLeadFail);
            totalResult.put("fullLeadEmailExists", fullLeadEmailExists);
            FileUtil.fileWrite("full_"+domain + ".total", domainPath, JacksonUtils.toJson(totalResult));
            //如果数据库查询不为空，那么做更新操作
          } catch (Exception e) {
            LOGGER.error("[rsyncFullLead]", e);
          }

          keySet.remove(key);
        }
      });
    }

  }


  private void rsyncPartialLead(String domain, String account, Map map, String domainPath,String env) {
    String key = env+"partial_"+domain;
    if (!keySet.contains(key)) {
      keySet.add(key);
      ThreadExecutorUtil.executeTask(new Runnable() {
        @Override
        public void run() {
          try {
            long clientId = NumberUtils.toLong(String.valueOf(map.get("client_id")));
            //full lead
            Map<String, Object> params = new HashedMap();
            params.put("clientId", clientId);

            int partialLeadPage = 1;
            int partialLeadSuccess = 0;
            int partialLeadEmailExists = 0;
            int partialLeadFail = 0;

            Map<String, String> rsyncResultSuccess = new LinkedHashMap();
            Map<String, String> emailExists = new LinkedHashMap();
            Map<String, String> rsyncResultFail = new LinkedHashMap();
            int successPage = 1;
            int failPage = 1;
            int existsPage = 1;

            //PartialLead
            while (true) {
//              LOGGER.error("partial lead while true!"+partialLeadPage);
              long startPartialLeadTime = System.currentTimeMillis();
              params.put("page", partialLeadPage);
              params.put("type", "address");
              String leadString = SiteHttpUtil.getResponseValueByKey(GF_LEAD_API, params, SiteHttpUtil.CLIENT_5S);
              //本次循环查询的信息
              boolean storeGfQueryResult = FileUtil.fileWrite(domain + "_" + partialLeadPage + "partial" + ".info", domainPath, leadString);
              if (!storeGfQueryResult) {
                LOGGER.error(String.format("分页查询lead信息失败，domain：%s,page：%s,type：lead", domain, partialLeadPage));
              }

              LOGGER.error(domain + "_partialLeadString:" + leadString);
              if (StringUtils.isNotBlank(leadString)) {
                JSONArray partialLeadResult = JSONArray.fromObject(leadString);
                for (int i = 0; i < partialLeadResult.size(); i++) {
                  JSONObject lead = partialLeadResult.getJSONObject(i);
                  LOGGER.error(String.format("-------------------%s----------------------------", lead.getString("leadId")));
                  LOGGER.error(domain + "_partialQueryData:" + JacksonUtils.toJson(getQueryApiPara(lead, false, account)));
                  String dataKey = lead.getString("leadId")+"_"+lead.getString("address");
                  try {
                    String url = RSYNC_PARTIAL_LEAD;
                    if("prd".equals(env)){
                      url = PRD_RSYNC_PARTIAL_LEAD;
                    }
                    String rsyncCrm = SiteHttpUtil.getResponseValueByKey(url, getQueryApiPara(lead, false, account), SiteHttpUtil.CLIENT_5S);
                    LOGGER.error(domain + "_partialCrmApiResult:" + rsyncCrm);
                    if (StringUtils.isNotBlank(rsyncCrm)) {
                      JSONObject partialLeadRsync = JSONObject.fromObject(rsyncCrm);
                      //同步该lead成功
                      if (partialLeadRsync.getJSONObject("status") != null && "0".equals(partialLeadRsync.getJSONObject("status").getString("code"))) {
                        rsyncResultSuccess.put(dataKey, "success");
                      } else if(partialLeadRsync.getJSONObject("status") != null && "8200001".equals(partialLeadRsync.getJSONObject("status").getString("code"))){
                        emailExists.put(dataKey, partialLeadRsync.getJSONObject("status").getString("msg"));
                      }else {
                        rsyncResultFail.put(dataKey, partialLeadRsync.getJSONObject("status").getString("msg"));
                      }
                    }
                  } catch (Exception e) {
                    LOGGER.error(String.format(domain + " rsync crm Exception,paras:%s,Exception:%s", getQueryApiPara(lead, false, account), e));
                    rsyncResultFail.put(dataKey, e.getMessage());
                    continue;
                  }

                }

                StringBuilder sb = new StringBuilder();
                sb.append(domain).append("_").append("partial");
                String filaName = sb.toString();

                //成功
                if (MapUtils.isNotEmpty(rsyncResultSuccess) && rsyncResultSuccess.size()>=20) {
                  partialLeadSuccess = partialLeadSuccess + rsyncResultSuccess.size();
                  boolean rsyncSuccess = FileUtil.fileWrite(successPage+filaName + ".success", domainPath, JacksonUtils.toJson(rsyncResultSuccess));
                  successPage++;
                  rsyncResultSuccess.clear();
                }
                //失败
                if (MapUtils.isNotEmpty(rsyncResultFail) && rsyncResultFail.size()>=20) {
                  partialLeadFail = partialLeadFail + rsyncResultFail.size();
                  boolean rsyncFail = FileUtil.fileWrite(failPage+filaName + ".fail", domainPath, JacksonUtils.toJson(rsyncResultFail));
                  failPage++;
                  rsyncResultFail.clear();
                }

                //email 重复
                if (MapUtils.isNotEmpty(emailExists) && emailExists.size()>=20) {
                  partialLeadEmailExists = partialLeadEmailExists + emailExists.size();
                  boolean rsyncFail = FileUtil.fileWrite(existsPage+filaName + ".exist", domainPath, JacksonUtils.toJson(emailExists));
                  existsPage++;
                  emailExists.clear();
                }

                partialLeadPage++;
                if (partialLeadResult == null || partialLeadResult.size() < 20) {
                  break;
                }

                long endPartialLeadTime = System.currentTimeMillis();

                if (endPartialLeadTime - startPartialLeadTime < 300) {
                  //避免请求过快，处理一次请求停顿200ms；
                  Thread.sleep(200);
                }

              } else {
                break;
              }
            }

            StringBuilder sb = new StringBuilder();
            sb.append(domain).append("_").append("partial");
            String filaName = sb.toString();

            //成功
            if (MapUtils.isNotEmpty(rsyncResultSuccess)) {
              partialLeadSuccess = partialLeadSuccess + rsyncResultSuccess.size();
              boolean rsyncSuccess = FileUtil.fileWrite(successPage+filaName + ".success", domainPath, JacksonUtils.toJson(rsyncResultSuccess));
            }
            //失败
            if (MapUtils.isNotEmpty(rsyncResultFail)) {
              partialLeadFail = partialLeadFail + rsyncResultFail.size();
              boolean rsyncFail = FileUtil.fileWrite(failPage+filaName + ".fail", domainPath, JacksonUtils.toJson(rsyncResultFail));
            }

            //email 重复
            if (MapUtils.isNotEmpty(emailExists)) {
              partialLeadEmailExists = partialLeadEmailExists + emailExists.size();
              boolean rsyncFail = FileUtil.fileWrite(existsPage+filaName + ".exist", domainPath, JacksonUtils.toJson(emailExists));
            }


            Map<String, Integer> totalResult = new HashedMap();
            totalResult.put("partialLeadSuccess", partialLeadSuccess);
            totalResult.put("partialLeadFail", partialLeadFail);
            totalResult.put("partialLeadEmailExists", partialLeadEmailExists);
            FileUtil.fileWrite("partial_"+domain + ".total", domainPath, JacksonUtils.toJson(totalResult));
          } catch (Exception e) {
            LOGGER.error("[rsyncPartialLead]", e);
          }

          keySet.remove(key);
        }
      });
    }

  }







  private Map<String,Object> getQueryApiPara(JSONObject lead,boolean isFullLead,String agentAccount){
    Map<String,Object> map = new HashedMap();

    map.put("city", HomethyStringUtil.nullToEmpty(lead.getString("city")));
    map.put("state",HomethyStringUtil.nullToEmpty(lead.getString("state")));
    map.put("notes",HomethyStringUtil.nullToEmpty(lead.getString("notes")));

    String streetLine = lead.getString("streetLine1");
    long regDate = 0;
    if(StringUtils.isNotBlank(lead.getString("dateCreated"))){
      Date date = DateUtil.parse(lead.getString("dateCreated"),DateUtil.DATEFORMAT_MM_DD_YY);
      regDate = date!=null ? date.getTime() : 0;
    }
    if(isFullLead){
      map.put("agentAccount",agentAccount);
      map.put("zipcode",HomethyStringUtil.nullToEmpty(lead.getString("zip")));
      map.put("name",HomethyStringUtil.nullToEmpty(lead.getString("name")));
      map.put("emails",HomethyStringUtil.nullToEmpty(lead.getString("email")));
      map.put("phones",HomethyStringUtil.nullToEmpty(lead.getString("phone")));

      map.put("regDate",regDate);
      map.put("leadType",StringUtils.isNotBlank(lead.getString("lead_type")) ? lead.getString("lead_type").toLowerCase() : "");
      String streetAddress =  "" + streetLine + (StringUtils.isNotBlank(lead.getString("unit")) ? ","+lead.getString("unit"):"");
      map.put("streetAddress",streetAddress);
      map.put("timeFrame",HomethyStringUtil.nullToEmpty(lead.getString("sellDate")));
      map.put("leadStatus",HomethyStringUtil.nullToEmpty(lead.getString("leadStatus")));
    }else{

      map.put("account",agentAccount);
      map.put("zipCode",HomethyStringUtil.nullToEmpty(lead.getString("zip")));
      map.put("regTime",regDate);

      String houseNumber = "";
      String streetName = "";
      if(StringUtils.isNotBlank(streetLine) && streetLine.trim().contains(" ")){
        houseNumber = streetLine.trim().substring(0,streetLine.trim().indexOf(" "));
        streetName = streetLine.trim().substring(streetLine.trim().indexOf(" ")+1);
      }else if (StringUtils.isNotBlank(streetLine)){
        if(NumberUtils.toLong(streetLine.trim()) == 0){
          streetName = streetLine.trim();
        }else{
          houseNumber = streetLine.trim();
        }
      }

      map.put("houseNumber",houseNumber);
      map.put("streetName",streetName);

    }

    return map;

  }




  @RequestMapping(value = "lead/rsyncCheck", method = RequestMethod.POST)
  public ModelAndView rsyncStatus (@RequestParam(value =  "gfdomain", required = false , defaultValue = "") String domain,
                                   @RequestParam(value =  "env", required = false, defaultValue = "") String env) throws Exception {
    ModelAndView view = new ModelAndView("homethy/rsync_lead_status");
    List<LeadRsyncStatus> result = new ArrayList<>();
    List<LeadRsyncStatus> partialResult = new ArrayList<>();
    if(StringUtils.isNotBlank(domain)){
      LeadRsyncStatus buildSite = new LeadRsyncStatus();
      buildSite.setDomain(domain);
      buildSiteStatus(domain,buildSite,env);
      view.addObject("buildSite",buildSite);

      view.addObject("domain",domain);
      if(keySet.contains(env+"partial_"+domain)){
        LeadRsyncStatus lead = new LeadRsyncStatus();
        lead.setDomain(domain);
        lead.setIsComplete(0);
        partialResult.add(lead);
      }else {
        LeadRsyncStatus lead = new LeadRsyncStatus();
        String domainPath = "/lead/" + domain + "/";
        if(StringUtils.isNotBlank(env) && "prd".equals(env)){
          domainPath="/prd"+domainPath;
        }
        String resultDomain = FileUtil.readFileByNamePathIgnoreExist("partial_" + domain + ".total", domainPath);
        if (StringUtils.isNotBlank(resultDomain)) {

          JSONObject jsonResult = JSONObject.fromObject(resultDomain);
          lead.setDomain(domain);
          lead.setIsComplete(1);
          lead.setPartialLeadFail(jsonResult.getInt("partialLeadFail"));
          lead.setPartialLeadSuccess(jsonResult.getInt("partialLeadSuccess"));
          lead.setPartialLeadEmailExists(jsonResult.getInt("partialLeadEmailExists"));
          partialResult.add(lead);
        } else {

          lead.setDomain(domain);
          lead.setIsComplete(-1);

          partialResult.add(lead);
        }
      }
      if(keySet.contains(env+"full_"+domain)){
        LeadRsyncStatus lead = new LeadRsyncStatus();
        lead.setDomain(domain);
        lead.setIsComplete(0);
        result.add(lead);
      }else{
        LeadRsyncStatus lead = new LeadRsyncStatus();
        String domainPath = "/lead/"+domain+"/";
        if(StringUtils.isNotBlank(env) && "prd".equals(env)){
          domainPath="/prd"+domainPath;
        }
        String resultDomain = FileUtil.readFileByNamePathIgnoreExist("full_"+domain + ".total",domainPath);
        if(StringUtils.isNotBlank(resultDomain)){

          JSONObject jsonResult = JSONObject.fromObject(resultDomain);
          lead.setDomain(domain);
          lead.setIsComplete(1);

          lead.setFullLeadFail(jsonResult.getInt("fullLeadFail"));
          lead.setFullLeadSuccess(jsonResult.getInt("fullLeadSuccess"));
          lead.setFullLeadEmailExists(jsonResult.getInt("fullLeadEmailExists"));
          result.add(lead);
        }else{

          lead.setDomain(domain);
          lead.setIsComplete(-1);

          result.add(lead);
        }
      }
    }else{
      keySet.forEach(doingDomain -> {
        LeadRsyncStatus lead = new LeadRsyncStatus();
        String keys [] = doingDomain.split("_");
        lead.setDomain(keys[1]);
        lead.setIsComplete(0);
        if("full".equals(keys[0])){
          result.add(lead);
        }else if("partial".equals(keys[0])){
          partialResult.add(lead);
        }
      });
    }
    view.addObject("leadRestultList",result);
    view.addObject("partialResultList",partialResult);
    return view;

  }

  private void buildSiteStatus(String domain,LeadRsyncStatus lead,String env){
    String buildSiteResultPath = "/../gf2site/auto-tools-gf/resources/results/"+env+"/";
    String domainBuildResult = FileUtil.readFileByNamePathIgnoreExist(domain + ".json",buildSiteResultPath);
    if(buildSitekeySet.contains(env+domain)){
      lead.setBuildSiteCompleted(0);
      lead.setSiteBuildNotes("建站中！");
    }else if(StringUtils.isEmpty(domainBuildResult)){
      lead.setBuildSiteCompleted(-1);
      lead.setSiteBuildNotes("未查询到该域名建站信息！");
    }else{
      JSONObject jsonObject = JSONObject.fromObject(domainBuildResult);
      String status = jsonObject.getString("status");
      lead.setSiteBuildNotes(jsonObject.getString("msg"));
      lead.setCrmAccount(jsonObject.getJSONObject("data").getString("account"));
      if("ok".equals(status)){
        lead.setBuildSiteCompleted(1);
        lead.setChimeDomain(jsonObject.getJSONObject("data").getString("new_domain_name"));
      }else{
        lead.setBuildSiteCompleted(2);
      }
    }
  }

  @ResponseBody
  @RequestMapping(value = "lead/buildSite", method = RequestMethod.POST)
  public String buildSite (@RequestParam(value =  "crmAccount", required = true) String account,
                           @RequestParam(value =  "gfdomain", required = false , defaultValue = "") String domain,
                           @RequestParam(value =  "env", required = false, defaultValue = "") String env) throws Exception {

    if(StringUtils.isBlank(account) || StringUtils.isBlank(domain)){
      return "account or domain is empty!";
    }
    String key = env+domain;
    if(buildSitekeySet.contains(key)){
      return "已在建站中，请勿重复点击！";
    }
    LOGGER.error(String.format("开始自动建站...start by %s,crmAccount:%s,gfDomain:%s",userHolder.getUserInfo().getAccount(),account,domain));
    buildSitekeySet.add(key);
    ThreadExecutorUtil.executeTask(new Runnable() {
      @Override
      public void run() {
        try {
          String shellEnv = StringUtils.isNotBlank(env) ? env : userHolder.getENV();
          String buildSiteResult = ExcShellUtil.buildSite(account,domain,shellEnv);
          String domainPath = "/lead/"+domain+"/";
          if(StringUtils.isNotBlank(env) && "prd".equals(env)){
            domainPath="/prd"+domainPath;
          }
          FileUtil.createDir(domainPath);
          boolean storeGfQueryResult = FileUtil.fileWrite(domain + "_" + account + ".build", domainPath, buildSiteResult);

        } catch (Exception e) {
          LOGGER.error("[rsyncLead]", e);
        }

        buildSitekeySet.remove(key);
      }
    });

    return "开始建站``````";
  }


  @RequestMapping(value = "gfDomainLeadCheck", method = RequestMethod.GET)
  public ModelAndView gfDomainLeadCheck(@RequestParam(value =  "domain", required = true) String domain,
                                        @RequestParam(value =  "type", required = true) String type,
                                        @RequestParam(value =  "reason", required = true) String reason,
                                        @RequestParam(value =  "total", required = true) String total,
                                        @RequestParam(value =  "env", required = false, defaultValue = "") String env) {
    ModelAndView view = new ModelAndView("homethy/gf_domain_lead_check");
    view.addObject("domain",domain);
    view.addObject("type",type);
    view.addObject("reason",reason);
    view.addObject("total",total);
    view.addObject("env",env);
    return view;
  }

  @ResponseBody
  @RequestMapping(value = "gfDomainLeadCheckResult", method = RequestMethod.GET)
  public String gfDomainLeadCheckResult(@RequestParam(value =  "domain", required = true) String domain,
                                        @RequestParam(value =  "type", required = true) String type,
                                        @RequestParam(value =  "reason", required = true) String reason,
                                        @RequestParam(value =  "page", required = true) String page,
                                        @RequestParam(value =  "total", required = true) String total,
                                        @RequestParam(value =  "env", required = false, defaultValue = "") String env) throws Exception{
    String domainPath = "/lead/"+domain+"/";
    if(StringUtils.isNotBlank(env) && "prd".equals(env)){
      domainPath="/prd"+domainPath;
    }

    String fileName = page+domain+"_"+type+"."+reason;
    String resultDomain = FileUtil.readFileByNamePathIgnoreExist(fileName,domainPath);
    Map result = new HashedMap();
    result.put("code",0);
    result.put("count",total);
    if(StringUtils.isNotBlank(resultDomain)) {
      Map<String,String> map = JacksonUtils.parseJSON2Map(resultDomain);
      List<GfLeadResult> list = new ArrayList<>();
      for (Map.Entry<String, String> entry : map.entrySet()) {
        String key = entry.getKey();
        String msg = entry.getValue();
        GfLeadResult lead = new GfLeadResult();
        lead.setMsg(msg);
        if("full".equals(type)){
          String str [] = key.split("_");
          String leadId=str[0];
          String email="";
          if(str.length>=2){
            email=str[1];
          }
          String name="";
          if(str.length>=3){
             name=str[2];
          }
          lead.setGfLeadId(leadId);
          lead.setEmail(email);
          lead.setName(name);
        }else{
          //_not at last of string
          if(key.indexOf("_")>0 && (key.length()-key.indexOf("_"))>1){
            String leadId=key.substring(0,key.indexOf("_"));
            String address = key.substring(key.indexOf("_")+1,key.length());
            lead.setGfLeadId(leadId);
            lead.setAddress(address);
          }else{
            lead.setGfLeadId(key);
          }
        }

        list.add(lead);
      }
      result.put("data",list);
    }

    return JacksonUtils.toJson(result);
  }


}
