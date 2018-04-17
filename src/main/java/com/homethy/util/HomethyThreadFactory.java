package com.homethy.util;

import java.util.concurrent.ThreadFactory;

public class HomethyThreadFactory implements ThreadFactory {
  @Override
  public Thread newThread(Runnable r) {
    Thread thread = new Thread(r);
    thread.setUncaughtExceptionHandler(new ThreadExceptionHandler());
    return thread;
  }
}
