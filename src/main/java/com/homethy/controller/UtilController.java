package com.homethy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.homethy.UserHolder;
import com.homethy.domain.dns.DNSRecord;
import com.homethy.domain.dns.ServerCSR;
import com.homethy.service.DataOperationService;
import com.homethy.service.TemplateService;
import com.homethy.service.UserService;
import com.homethy.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.xbill.DNS.Type;

import java.util.*;

@Controller
public class UtilController {

  @Autowired
  UserHolder userHolder;

  @Autowired
  UserService userService;

  @Autowired
  DataOperationService dataOperationService;

  @Autowired
  TemplateService templateService;


  @RequestMapping(value = "domainInfo", method = RequestMethod.GET)
  public ModelAndView createUser() {
    ModelAndView view = new ModelAndView("domain_info");
    view.addObject("user", userHolder.getUserInfo());

    return view;
  }

  @ResponseBody
  @RequestMapping(value = "domainInfo/getDomainInfo", method = RequestMethod.POST)
  public String getDomainInfo(
      @RequestParam(value = "domain", required = true) String domain) throws Exception {
    Map map = new HashMap();
    List<DNSRecord> list = DNSUtil.getMainDomainCommonRecords(domain);
    List infoList = new ArrayList<>();
    Map<String, String> infoMap = DNSUtil.queryDomainInfoMap(domain);
    infoList.add(infoMap);
    if (MapUtils.isNotEmpty(infoMap) && StringUtils.isNotBlank(infoMap.get("Name Server"))) {
      String ns = infoMap.get("Name Server");
//      String registerProvider = infoMap.get("Registrar");
      //域名provider的ns关键字配置
      Map<String, String> dnsMap = PropertiesResolver.REGISTRAR_DNS_KERWORDS_PROPERTIES;
      if (StringUtils.isNotBlank(ns) && MapUtils.isNotEmpty(dnsMap)) {
        String godaddyNs = dnsMap.get("godaddy");
        String gfNs = dnsMap.get("geofarm");
        //NS包含配置的关键字，说明NS为默认NS
        if (ns.toLowerCase().contains(godaddyNs)) {
          map.put("nsFlag", 1);
        } else if(ns.toLowerCase().contains(gfNs)){
          map.put("nsFlag", 2);
        }else {
          map.put("nsFlag", 0);
        }
      }

    }
    map.put("dnsRecords", list);
    map.put("domainInfo", infoList);
    return ReturnJacksonUtil.resultOk(map);
  }


  @RequestMapping(value = "jsonFormart", method = RequestMethod.GET)
  public ModelAndView jsonFormart() {
    ModelAndView view = new ModelAndView("formart_json");
    view.addObject("user", userHolder.getUserInfo());

    return view;
  }

  @RequestMapping(value = "regular", method = RequestMethod.GET)
  public ModelAndView regular() {
    ModelAndView view = new ModelAndView("regular_test");

    return view;
  }

  @RequestMapping(value = "freemarker", method = RequestMethod.GET)
  public ModelAndView freemarker() {
    ModelAndView view = new ModelAndView("freemarker_test");
    view.addObject("user", userHolder.getUserInfo());
    return view;
  }

  @RequestMapping(value = "freemarkerTest", method = RequestMethod.POST)
  @ResponseBody
  public String freemarkerTest(@RequestParam(value = "freemarkerDom", required = true) String freemarkerDom,
                               @RequestParam(value = "freemarkerData", required = false, defaultValue = "") String freemarkerData)
      throws Exception {
    return ReturnJacksonUtil.resultOk(templateService.process("freemarker_test", JacksonUtils.parseJSON2Map(freemarkerData), freemarkerDom));

  }


  @RequestMapping(value = "csrView", method = RequestMethod.GET)
  public ModelAndView csrView() {
    ModelAndView view = new ModelAndView("tools/csr/create_csr");
    view.addObject("user", userHolder.getUserInfo());
    return view;
  }

  @RequestMapping(value = "csrView/createCsr", method = RequestMethod.POST)
  public ModelAndView createCsr(@ModelAttribute ServerCSR serverCSR)
      throws Exception {
    ModelAndView view = new ModelAndView("tools/csr/csr_result");

    if (serverCSR.isAttributeEmpty()) {
      view.addObject("code", 100);
      view.addObject("result", "Input Must Not Be Empty!");
    } else {
      view.addObject("code", 0);
//      String csrJson = FileUtil.readFileByNamePathIgnoreExist(serverCSR.getCommonName()+".csr","/ssl/"+serverCSR.getCommonName()+"/");
//      if(StringUtils.isNotBlank(csrJson)){
//        view.addObject("result",csrJson);
////        view.addObject()
//      }else{
      String resultConsole = ExcShellUtil.makeCSR(serverCSR);
      String csrJson = FileUtil.readFileByNamePathIgnoreExist(serverCSR.getCommonName() + ".csr", "/ssl/" + serverCSR.getCommonName() + "/");
      if (StringUtils.isNotBlank(csrJson)) {
        view.addObject("result", csrJson);
      } else {
        view.addObject("result", resultConsole);
      }
//      }
    }
    return view;
  }

}
