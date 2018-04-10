package com.homethy.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xufeng.zhang@renren-inc.com 2017-12-2017/12/27 14:51
 */
public class ThreadExceptionHandler implements Thread.UncaughtExceptionHandler {
  private Logger LOG = LoggerFactory.getLogger(ThreadExceptionHandler.class);

  @Override
  public void uncaughtException(Thread t, Throwable e) {
    LOG.info("execute task error in thread, error msg is " + e + ", thread name is " + t.getName());
  }
}
