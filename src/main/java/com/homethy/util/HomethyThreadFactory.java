package com.homethy.util;

import java.util.concurrent.ThreadFactory;

/**
 * @author xufeng.zhang@renren-inc.com 2017-12-2017/12/27 14:49
 */
public class HomethyThreadFactory implements ThreadFactory {
  @Override
  public Thread newThread(Runnable r) {
    Thread thread = new Thread(r);
    thread.setUncaughtExceptionHandler(new ThreadExceptionHandler());
    return thread;
  }
}
