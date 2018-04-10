package com.homethy.constant;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Constant {
  public static final String DATA = System.getProperty("user.dir") + File.separator + "json";

  public static final String QUICK_SEARCH_ITEM_LIMIT = "3";
  public static final String QUICK_SEARCH_COUNT_LIMIT = "100";

  public static final String MD5KEY="m3s34pq032zq0uei2k92254303wl23a";

  /**
   * 异常信息统一头信息<br>
   * 非常遗憾的通知您,程序发生了异常
   */
  public static final String Exception_Head = "boom。炸了。";
  /**
   * 缓存键值
   */
  public static final Map<Class<?>, String> cacheKeyMap = new HashMap<>();
  /**
   * 保存文件所在路径的key，eg.FILE_MD5:1243jkalsjflkwaejklgjawe
   */
  public static final String FILE_MD5_KEY = "FILE_MD5:";
  /**
   * 保存上传文件的状态
   */
  public static final String FILE_UPLOAD_STATUS = "FILE_UPLOAD_STATUS";


}
