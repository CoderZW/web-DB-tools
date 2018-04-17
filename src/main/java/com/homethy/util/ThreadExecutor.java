package com.homethy.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.*;

public class ThreadExecutor {

  private static final Log LOGGER = LogFactory.getLog(ThreadExecutor.class);

  private final static int LOG_THRESHOLD = 15;
  /**
   * 线程池维护线程的最少数量
   */
  private int CORE_THREAD = 5;
  /**
   * 线程池维护线程的最大数量
   */
  private int MAX_THREAD = 10;
  /**
   * 线程池所使用的缓冲队列
   */
  private BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(40);

  public final ExecutorService executorService = new HomethyThreadPoolExecutor(CORE_THREAD,
      MAX_THREAD, 10L, TimeUnit.SECONDS, queue, new HomethyThreadFactory(), new HomethyThreadPoolExecutor.DiscardOldestPolicy());

  public <T> Future<T> submitTask(Callable<T> callable) {
    printBlockingQueueSize();
    return executorService.submit(callable);
  }

  public void executeTask(Runnable task) {
    executorService.execute(task);
    printBlockingQueueSize();
  }

  private void printBlockingQueueSize() {
    int queueSize = queue.size();
    if (queueSize > LOG_THRESHOLD) {
      LOGGER.info(String.format(
          "[ThreadExecutorUtil.printBlockingQueueSize] blocking queue size : %s", queueSize));
    }
  }
}
