package com.homethy.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.homethy.api.http.HttpApiClient;
import com.homethy.api.http.HttpApiClientBuilder;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.XML;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class SiteHttpUtil {

  private static final Log LOGGER = LogFactory.getLog(SiteHttpUtil.class);

  public static final HttpApiClient CLIENT_200 = new HttpApiClientBuilder().setTimeout(200).build();
  public static final HttpApiClient CLIENT_1S = new HttpApiClientBuilder().setTimeout(1000).build();
  public static final HttpApiClient CLIENT_2S = new HttpApiClientBuilder().setTimeout(2000).build();
  public static final HttpApiClient CLIENT_3S = new HttpApiClientBuilder().setTimeout(3000).build();
  public static final HttpApiClient CLIENT_5S = new HttpApiClientBuilder().setTimeout(5000).build();
  public static final HttpApiClient CLIENT_10S = new HttpApiClientBuilder().setTimeout(10000).build();

  public static void close(AutoCloseable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (Exception e) {
        // IGNORE
      }
    }
  }
//  public static JsonNode getResponseValueByKey(String url, Map<String, Object> params
//      , String key) {
//    return getResponseValueByKey(url,params,key,CLIENT_200);
//  }
  public static String getResponseValueByKey(String url, Map<String, Object> params, HttpApiClient client) {
    List<NameValuePair> list = new ArrayList<>();
    params.forEach((each, value) -> {
      list.add(new BasicNameValuePair(each, value.toString()));
    });

    try {
      HttpResponse response = client.executePostRequest(url, list);
      HttpEntity httpEntity = response.getEntity();
      String content = EntityUtils.toString(httpEntity);
      int code = response.getStatusLine().getStatusCode();
      LOGGER.info("ur:" + url + " code:" + code + " params:" + params + "\n response:" + content);
      if (code > 299) {
        throw new IOException("Bad request ur:" + url + " code:" + code + "\n response:" +
            content);
      }
      return content;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   *
   * @param url
   * @param o
   * @param key
   * @return
   * @throws IOException
   */
  public static JsonNode getResponseValueByKey(String url, Object o, String
      key) throws IOException {
    Map<String, Object> map = bean2Map(o);
    return getResponseValueByKey(url, map, key);
  }


  private static JsonNode findValueInJsonNode(JsonNode root, String key) {
    if (!root.has(key)) {
      if (root.isArray()) {
        for (int i = 0; i < root.size(); i++) {
          if (root.get(i).has(key)) {
            return root.get(i).path(key);
          } else {
            return findValueInJsonNode(root.get(i), key);
          }

        }
      } else {
        Iterator<JsonNode> elements = root.elements();
        while (elements.hasNext()) {
          JsonNode next = elements.next();
          if (next.has(key)) {
            return next.path(key);
          } else {
            findValueInJsonNode(next, key);
          }
        }
      }
    }
    return root.path(key);
  }


  private static <T> Map<String, Object> bean2Map(T t) {

    if (t instanceof Collection) {
      throw new RuntimeException(" can not format a collection ");
    }

    if (t instanceof Map) {
      return (Map<String, Object>) t;
    }

    Map<String, Object> map = new HashMap<>();
    Class<?> clazz = t.getClass();
    Field[] fields = clazz.getDeclaredFields();

    Arrays.stream(fields).forEach(
        item -> {
          item.setAccessible(true);
          try {
            map.put(item.getName(), item.get(t));
          } catch (IllegalAccessException e) {
            throw new RuntimeException(" format field failed : " + item.getName());
          }
        }
    );
    return map;
  }


  public static JSONObject getResult(String url) {
    GetMethod getMethod = new GetMethod(url);
    getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
        new DefaultHttpMethodRetryHandler());
    String responseString = null;
    try {
      HttpResponse response = CLIENT_2S.executeGetRequest(url);
      HttpEntity httpEntity = response.getEntity();
      responseString = EntityUtils.toString(httpEntity);

      JSONObject result = JSONObject.fromObject(responseString);

      return result;
    } catch (Exception e) {
      LOGGER.error("SiteHttpUtil:getResult:url=" + url + ", response string is " + responseString,e);
      return null;
    }
  }

  public static JSONObject getResultXmlToJson(String url) {
    GetMethod getMethod = new GetMethod(url);
    getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
        new DefaultHttpMethodRetryHandler());
    String responseString = null;
    try {
      HttpResponse response = CLIENT_2S.executeGetRequest(url);
      HttpEntity httpEntity = response.getEntity();
      responseString = EntityUtils.toString(httpEntity);

      JSONObject result = JSONObject.fromObject(XML.toJSONObject(responseString).toString());

      return result;
    } catch (Exception e) {
      LOGGER.error("SiteHttpUtil:getResult:url="+url,e);
      return null;
    }
  }

  public static JSONObject getResult2Json4HomeJunction(String url, List<Header> headers) {

    try {
      HttpResponse response = CLIENT_3S.executeGetRequest(url, null, headers);
      HttpEntity httpEntity = response.getEntity();
      String responseString = EntityUtils.toString(httpEntity);

      JSONObject result = JSONObject.fromObject(responseString);

      return result;
    } catch (Exception e) {
      LOGGER.error("SiteHttpUtil:home junction getResult:url="+url,e);
      return null;
    }
  }

  public static JSONObject getResult2Json4HjMs(String url) {
    try {
      return JSONObject.fromObject(getResult4HjMs(url));
    } catch (Exception e) {
      LOGGER.error("SiteHttpUtil:home junction 2 json getResult:url="+url,e);
      return null;
    }
  }

  public static String getResult4HjMs(String url) {

    try {
      HttpResponse response = CLIENT_3S.executeGetRequest(url);
      HttpEntity httpEntity = response.getEntity();
      String responseString = EntityUtils.toString(httpEntity);

      return responseString;
    } catch (Exception e) {
      LOGGER.error("SiteHttpUtil:home junction getResult:url="+url,e);
      return null;
    }
  }

  public static void main(String args[]){
    Map<String, Object> params = new HashedMap();
    params.put("clientId",2674);
    params.put("page",1);
    params.put("type","lead");
    System.out.println(getResponseValueByKey("http://chime.geographicfarmvalues.com/ws/ws.php/lead/admin/list",params,CLIENT_3S));
  }
}
