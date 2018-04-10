package com.homethy.util;

import com.homethy.api.http.HttpApiClient;
import com.homethy.api.http.HttpApiClientBuilder;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.XML;
import sun.misc.BASE64Decoder;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Principal;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * @Title: HttpRequestUtil.java
 * @Package com.homethy.app.util
 * @Description: HttpRequest 参数获取 工具类
 * @author yunjie.shen 2015年10月26日 上午11:28:17
 */
public class HttpRequestUtil {
  private final static Log logger = LogFactory.getLog(HttpRequestUtil.class);

  public static final HttpApiClient CLIENT_1S = new HttpApiClientBuilder().setTimeout(1000).build();
  public static final HttpApiClient CLIENT_2S = new HttpApiClientBuilder().setTimeout(2000).build();
    private HttpRequestUtil(){

    }

    private static final char DEFAULT_SEPERATOR = '=';
    
    private static final String CHARSET = "UTF-8";

    public static List<String> getRequestParameterValueList(
        HttpServletRequest request) {
        return getRequestParameterValueList(request, DEFAULT_SEPERATOR);
    }

    public static List<String> getRequestParameterValueList(
        HttpServletRequest request, int maxLen) {
        return getRequestParameterValueList(request, maxLen, DEFAULT_SEPERATOR);
    }

    public static List<String> getRequestParameterValueList(
        HttpServletRequest request, int maxLen, String exclusion) {
        return getRequestParameterValueList(request, maxLen, DEFAULT_SEPERATOR,
            exclusion);
    }

    public static List<String> getRequestParameterValueList(
        HttpServletRequest request, int maxLen, Set<String> exclusions) {
        return getRequestParameterValueList(request, maxLen, DEFAULT_SEPERATOR,
            exclusions);
    }

    public static List<String> getRequestParameterValueList(
        HttpServletRequest request, char seperator) {
        Set<String> exclusions = new HashSet<String>();
        return getRequestParameterValueList(request, 0, seperator, exclusions);
    }

    public static List<String> getRequestParameterValueList(
        HttpServletRequest request, int maxLen, char seperator) {
        Set<String> exclusions = new HashSet<String>();
        return getRequestParameterValueList(request, maxLen, seperator,
            exclusions);
    }

    public static List<String> getRequestParameterValueList(
        HttpServletRequest request, int maxLen, char seperator, String exclusion) {
        Set<String> exclusions = new HashSet<String>();
        exclusions.add(exclusion);
        return getRequestParameterValueList(request, maxLen, seperator,
            exclusions);
    }

    public static List<String> getRequestParameterValueList(
        HttpServletRequest request, int maxLen, char seperator,
        Set<String> exclusions) {
        List<String> paramList = new ArrayList<String>();
        if (null == request) {
            return paramList;
        }

        @SuppressWarnings("unchecked")
        Enumeration<String> e = request.getParameterNames();
        String param = null;
        while (e.hasMoreElements()) {
            param = e.nextElement();
            if (param != null && !exclusions.contains(param)) {
                String value = request.getParameter(param);
                if (value != null) {
                    if (maxLen > 0 && value.length() > maxLen) {
                        value = value.substring(0, maxLen);
                    }
                    String param2value = param + seperator + value;
                    paramList.add(param2value);
                }
            }
        }
        return paramList;
    }

    public static Map<String, String> getRequestHeaderMap(
        HttpServletRequest request) {
        Map<String, String> param2value = new HashMap<String, String>();
        @SuppressWarnings("unchecked")
        Enumeration<String> e = request.getHeaderNames();
        String param = null;
        while (e.hasMoreElements()) {
            param = e.nextElement();
            if (param != null) {
                String value = request.getHeader(param);
                if (value != null) {
                    param2value.put(param, value);
                }
            }
        }
        return param2value;
    }

    public static Map<String, String> getRequestParamValueMap(
        HttpServletRequest request) {
        Map<String, String> param2value = new HashMap<String, String>();
        @SuppressWarnings("unchecked")
        Enumeration<String> e = request.getParameterNames();
        String param = null;
        while (e.hasMoreElements()) {
            param = e.nextElement();
            if (param != null) {
                String value = request.getParameter(param);
                if (value != null) {
                    param2value.put(param, value);
                }
            }
        }
        return param2value;
    }
    
    /**
     * UnsupportedEncodingException一定不会出现在线上环境，故借此方法隐去异常，简化代码
     * @param src
     * @return UTF-8编码的URLEncoded字符串
     */
    public static String getURLEncodedString(String src) {
        return getURLEncodedString(src, CHARSET);
    }
    /**
     * UnsupportedEncodingException一定不会出现在线上环境，故借此方法隐去异常，简化代码
     * @param src
     * @param charset
     * @return
     */
    public static String getURLEncodedString(String src, String charset) {
        String result = "";
        try {
            result = URLEncoder.encode(src, charset);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * UnsupportedEncodingException一定不会出现在线上环境，故借此方法隐去异常，简化代码
     * @param encodedString
     * @return UTF-8编码URLDecoded字符串
     */
    public static String getURLDecodedString(String encodedString) {
        return getURLDecodedString(encodedString, CHARSET);
    }
    
    /**
     * UnsupportedEncodingException一定不会出现在线上环境，故借此方法隐去异常
     * @param encodedString
     * @param charset
     * @return
     */
    public static String getURLDecodedString(String encodedString, String charset) {
        String result = "";
        try {
            result = URLDecoder.decode(encodedString, charset);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

  public static String getResultByPost(String url,NameValuePair[] param) {
    HttpClient httpClient = new HttpClient();
    PostMethod postMethod = new PostMethod(url);
    postMethod.addParameters(param);
    postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
        new DefaultHttpMethodRetryHandler());
//    postMethod.getParams().setVirtualHost("34.231.209.21");
//    httpClient.getHostConfiguration().setProxy("34.231.209.21",8080);
//    httpClient.getHostConfiguration().setHost("34.231.209.21");
    String responseString = null;
    try {
      int code = httpClient.executeMethod(postMethod);
      responseString = new String(postMethod.getResponseBody(), "UTF-8");
      logger.debug(String.format("[HttpRequestUtil.getResultByPost] url:%s, code:%s, rep:%s", url,
          code, responseString));
      return responseString;
    } catch (Exception e) {
      logger.error(String.format("[HttpRequestUtil.getResultByPost] error, url:%s", url), e);
      return null;
    }
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
      logger.error("SiteHttpUtil:getResult:url=" + url + ", response string is " + responseString,e);
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
      logger.error("SiteHttpUtil:getResult:url="+url,e);
      return null;
    }
  }

  /*
   * 处理https GET/POST请求
   * 请求地址、请求方法、参数
   * */
  public static String httpsRequest(String requestUrl,String requestMethod,String outputStr){
    StringBuffer buffer=null;
    try{
      //创建SSLContext
      SSLContext sslContext=SSLContext.getInstance("SSL");
      TrustManager[] tm={new MyX509TrustManager() {
      }};
      //初始化
      sslContext.init(null, tm, new java.security.SecureRandom());;
      //获取SSLSocketFactory对象
      SSLSocketFactory ssf=sslContext.getSocketFactory();
      URL url=new URL(requestUrl);
      HttpsURLConnection conn=(HttpsURLConnection)url.openConnection();
      conn.setDoOutput(true);
      conn.setDoInput(true);
      conn.setUseCaches(false);
      conn.setRequestMethod(requestMethod);
      //设置当前实例使用的SSLSoctetFactory
      conn.setSSLSocketFactory(ssf);
      conn.connect();
      //往服务器端写内容
      if(null!=outputStr){
        OutputStream os=conn.getOutputStream();
        os.write(outputStr.getBytes("utf-8"));
        os.close();
      }

      //读取服务器端返回的内容
      InputStream is=conn.getInputStream();
      InputStreamReader isr=new InputStreamReader(is,"utf-8");
      BufferedReader br=new BufferedReader(isr);
      buffer=new StringBuffer();
      String line=null;
      while((line=br.readLine())!=null){
        buffer.append(line);
      }
    }catch(Exception e){
      e.printStackTrace();
    }
    return buffer.toString();
  }

  public static void main(String args[]) throws Exception{

    String scert = "MIIFVzCCBD+gAwIBAgIQQKY1foNn0BsYOq8TIk8hojANBgkqhkiG9w0BAQUFADCB\n" +
        "tTELMAkGA1UEBhMCVVMxFzAVBgNVBAoTDlZlcmlTaWduLCBJbmMuMR8wHQYDVQQL\n" +
        "ExZWZXJpU2lnbiBUcnVzdCBOZXR3b3JrMTswOQYDVQQLEzJUZXJtcyBvZiB1c2Ug\n" +
        "YXQgaHR0cHM6Ly93d3cudmVyaXNpZ24uY29tL3JwYSAoYykxMDEvMC0GA1UEAxMm\n" +
        "VmVyaVNpZ24gQ2xhc3MgMyBTZWN1cmUgU2VydmVyIENBIC0gRzMwHhcNMTUwNTI3\n" +
        "MDAwMDAwWhcNMTUxMjI4MjM1OTU5WjCBqTELMAkGA1UEBhMCQ04xEDAOBgNVBAgT\n" +
        "B2JlaWppbmcxEDAOBgNVBAcUB2JlaWppbmcxOTA3BgNVBAoUMEJlaUppbmcgQmFp\n" +
        "ZHUgTmV0Y29tIFNjaWVuY2UgVGVjaG5vbG9neSBDby4sIEx0ZDElMCMGA1UECxQc\n" +
        "c2VydmljZSBvcGVyYXRpb24gZGVwYXJ0bWVudDEUMBIGA1UEAxQLKi5iYWlkdS5j\n" +
        "b20wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDS4pfHYsHL5ML9ltnk\n" +
        "LdXG5axFxAw4wkR+AohS75V1CAsFPVi4S67ZJuJAoo7+CLHmakWZenwXzxysvce6\n" +
        "bE9BT27qQe3OCwJueUX/VO8FkmiqK+A9QH9Lgl6egdw1hRV9vvX9fxiGIP/RaafF\n" +
        "rlZLtI23c+z0SfAlyWVQDfc6mnsK5MT7aDreezkDbzJ1poTVVikIJo4+UjLoWheY\n" +
        "cJk8I+3epr3Xb6I5Ga8c0JF8Yotv0gBHfbKS4lhAZjOhsqxmUJKvusNsXCOzSH9G\n" +
        "P41QdjB3bqKPN29QqobDX25SpSOmetBnD4r77Xv+cSRQxPLTBiRPyL/9aNWPfBTe\n" +
        "+D01AgMBAAGjggFrMIIBZzAhBgNVHREEGjAYggsqLmJhaWR1LmNvbYIJYmFpZHUu\n" +
        "Y29tMAkGA1UdEwQCMAAwDgYDVR0PAQH/BAQDAgWgMCsGA1UdHwQkMCIwIKAeoByG\n" +
        "Gmh0dHA6Ly9zZC5zeW1jYi5jb20vc2QuY3JsMGEGA1UdIARaMFgwVgYGZ4EMAQIC\n" +
        "MEwwIwYIKwYBBQUHAgEWF2h0dHBzOi8vZC5zeW1jYi5jb20vY3BzMCUGCCsGAQUF\n" +
        "BwICMBkMF2h0dHBzOi8vZC5zeW1jYi5jb20vcnBhMB0GA1UdJQQWMBQGCCsGAQUF\n" +
        "BwMBBggrBgEFBQcDAjAfBgNVHSMEGDAWgBQNRFwWU0TBgn4dIKsl9AFj2L55pTBX\n" +
        "BggrBgEFBQcBAQRLMEkwHwYIKwYBBQUHMAGGE2h0dHA6Ly9zZC5zeW1jZC5jb20w\n" +
        "JgYIKwYBBQUHMAKGGmh0dHA6Ly9zZC5zeW1jYi5jb20vc2QuY3J0MA0GCSqGSIb3\n" +
        "DQEBBQUAA4IBAQAO0aL9AO6S3zEod2DDIWSz3PP+YXXQNpKF9Kv4KzzFsxPjj1hd\n" +
        "wdlMj/OcOPQ4PSeHyHCSbQ2m5vX2E+0DhUf0rwMhn4oOHQFuzqHKlWPxA51U5pSJ\n" +
        "njTpkS1Hpj7GAz0z0+b2dV7a3//rawJojk2kEog9aVw5U+9fBnXWeO3lhCtNSMrj\n" +
        "Ay/+7hGd/9g59JBSSskd2yoCM0I1wJ7HK1AYoWtZRO1Ufe2I1PfbeY7k520C0x9h\n" +
        "IEXDDVdjurHOiUU2rNz7AjENeXtjpcn8ejx0V92X8ZnA00bZSrVrOYe+0L56yJxh\n" +
        "FUm2aNeOPAaTGk7M7CwWQv0t0FhzcfT08UwL";
// Base64解码
    BASE64Decoder decoder = new BASE64Decoder();
    byte[] byteCert = decoder.decodeBuffer(scert);
//转换成二进制流
    ByteArrayInputStream bain = new ByteArrayInputStream(byteCert);
    CertificateFactory cf = CertificateFactory.getInstance("X.509");
    X509Certificate oCert = (X509Certificate)cf.generateCertificate(bain);
    Principal p = oCert.getSubjectDN();
    Collection e = oCert.getIssuerAlternativeNames();
    String a = oCert.getSigAlgName();
    String b = oCert.getSigAlgOID();
//    String c = oCert.getSigAlgParams().toString();
    String d = oCert.getSignature().toString();
    Collection al = oCert.getSubjectAlternativeNames();
    String info = p.getName();


    NameValuePair[] param = new NameValuePair[3];
    param[0] = new NameValuePair("clientId", "2674");//2409 mymn-homevalue.com
    param[1] = new NameValuePair("page", "1");
    param[2] = new NameValuePair("type", "lead");
    String aa = httpsRequest("https://www.venturehomerealestate.com/search/centerPoint?key=Lawrenceville%2C+PA&keywordType=city","GET",null);
    System.out.println(aa);
  }
}
