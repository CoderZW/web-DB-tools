package com.homethy.constant;

public class StringConstants {

  public static final String RESULT_CODE = "code";

  public static final String RESULT_MSG = "msg";

  public static final String RESULT_STATUS = "status";

  public static final String RESULT_DATA = "data";

  public static final int VALUE_NOT_HASMORE = 0;

  public static final int VALUE_HASMORE = 1;

  public static final String WEB_ROOT = "http://10.4.24.116:8080";
  public static final String DOWNLOAD_PATH = "/s3/download/";
  public static final String UPLOAD_PATH = "/s3/upload/";
  public static final String S3_BUCKET = "homefree.renren";
  public static final String THUMBNAIL_TYPE = "jpg";
  public static final int THUMBNAIL_HEIGHT = 1080; // 缩略图标准高度


  public static final String SITEURL_PRE = "/sitebuilt/showsite?siteId=";

  public static final String HTTP_REQ_PARAM_SEPARATOR = "&";
  public static final String HTTP_REQ_PARAM_ADDRESS = "address=";
  public static final String HTTP_REQ_PARAM_CITY = "city=";
  public static final String HTTP_REQ_PARAM_STATE = "state=";
  public static final String HTTP_REQ_PARAM_LAT = "lat=";
  public static final String HTTP_REQ_PARAM_LON = "lon=";
  public static final String HTTP_REQ_PARAM_CRITERIA = "criteria=area:";
  public static final String HTTP_REQ_PARAM_CONNECTOR = ":";

  public static final String WALK_SCORE_API_KEY = "wsapikey=4657fa6ba2f5809c512448d58db99838";
  public static final String WALK_SCORE_REQ_PRE = "http://api.walkscore.com/score?format=json";
  public static final String TRANSIT_SCORE_REQ_PRE = "http://transit.walkscore.com/transit/score/?";
  public static final String TRANSIT_STOP_REQ_PRE =
      "http://transit.walkscore" + "" + ".com/transit/search/stops/?";

  public static final String STREET_EASY_API_KEY = "key=1ba40b84e608e9e7eba7c1549fa70e5c421e36b8";
  public static final String PRICE_REQ_PRE = "http://streeteasy.com/nyc/api/sales/data?format=json";

  public static final String AGENT_INFO_HEAD_URL = "";
  public static final String AGENT_INFO_BACK_HEAD_URL = "";

  public static final String CMA_URL =
      "http://www.zillow.com/seo/howmuch/PropertyDetails" + "" + ".htm?address=";
  public static final String CMA_HTML_PREFIX = "http://www.zillow.com/homedetails/";
  public static final String CMA_HTML_SUFFIX = "_zpid?fromHowMuchMyHomeWorth=true";

  public static final String CMA_ADDRESS_SUGGEST = "http://www.trulia" + ""
      + ".com/_ajax/AutoSuggest/AutoSuggest/?results=5&types%5B%5D=neighborhood&types%5B%5D" +
      "=zipCode&types%5B%5D=city&types%5B%5D=county&types%5B%5D=address&types%5B%5D=street" +
      "&display=h&query=";
  public final static String TRULIA_REFERER = "https://www.trulia.com/";
  public final static String TRULIA_USERAGENT =
      "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36 (KHTML, like Gecko) " +
          "Chrome/56.0.2924.87 Safari/537.36";
  public final static String TRULIA_ACCEPT = "application/json, text/javascript, */*; q=0.0";
  public final static String TRULIA_ACCEPT_LANGUAGE = "en-us,en;q=0.5";

  public final static String TRULIA_REDIS_PROXY_KEY = "homethy_site_http_proxy";

  public static final int MIN_SEARCH_SNAPSHOT_ID = 10000000;

  public static final String CMA_TRULIA_UTL =
      "http://www.trulia" + "" + ".com/submit_search/?locationType=address&tst=h&";
  public static final String CMA_TRULIA_PARAM = "search=%s&locationId=%s";

  public static final String BRAND_DOMAIN_SUFFIX = ".chime.me/brand";

  public final static String IS_POPUP_SUFFIX = "isPopup=0";

}
