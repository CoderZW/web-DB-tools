package com.homethy.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.homethy.util.http.HttpUtil;
import com.homethy.util.model.HttpResult;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.io.InputStream;


public class Request {

  private static Log log = LogFactory.getLog(Request.class);

  public static JsonNode GetRequest(String url) throws IOException {
    log.info(String.format("根据链接获取json文件\turl:%s", url));
    HttpResult result;
    result = HttpUtil.getMethod(url, new NameValuePair[]{});
    if (result.getHttpStatusCode() == 200) {
      JsonNode jsonNode = JacksonUtils.toJsonNode(result.getResponseBody());
      return jsonNode;
    } else {
      log.error("Get request failed. url: " + url + ", code: " + result.getHttpStatusCode()
        + ", content: " + result.getResponseBody());
      return null;
    }
  }

  public static JsonNode GetRequest(HttpClient httpClient, String url) {
    HttpGet get = new HttpGet(url);
    HttpResponse response = null;
    try {
      response = httpClient.execute(get);
    } catch (IOException e) {
      log.error("发送get请求 " + url + " 失败");
      throw new RuntimeException(e);
    }
    String result = getResultFromResponse(response);
    return JacksonUtils.toJsonNode(result);
  }

  public static JsonNode PostRequest(HttpClient httpClient, String url) {
    HttpPost post = new HttpPost(url);
    String result = null;
    try {
      HttpResponse response = httpClient.execute(post);
      result = getResultFromResponse(response);
    } catch (Exception e) {
      log.error("发送post请求 " + url + " 失败");
      throw new RuntimeException(e);
    }
    return JacksonUtils.toJsonNode(result);
  }

  private static String getResultFromResponse(HttpResponse response) {
    StringBuilder stringBuilder = new StringBuilder("");
    try {
      HttpEntity entity = response.getEntity();
      InputStream in = null;
      if (entity != null) {
        in = entity.getContent();
      }
      byte[] b = new byte[2048];
      int l;
      while ((l = in.read(b)) != -1) {
        stringBuilder.append(new String(b, 0, l));
      }
    } catch (Exception e) {
      log.error(e.toString());
      throw new RuntimeException(e);
    }
    return stringBuilder.toString();
  }


  public static boolean isValid(JsonNode result) {
    JsonNode status = result.get("status");
    JsonNode code = status.get("code");
    if (!"0".equals(code.asText())) {
      return false;
    }
    return true;
  }
  
}
