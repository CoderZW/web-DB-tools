package com.homethy.util;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class ThreadExecutorUtil {

  private static final ThreadExecutor COMMON_EXECUTOR = new ThreadExecutor();
  private static final ThreadExecutor KAFKA_EXECUTOR = new ThreadExecutor();
  private static final ThreadExecutor PICTURE_EXECUTOR = new ThreadExecutor();
  private static final ThreadExecutor LISTING_BTN_EXECUTOR = new ThreadExecutor();
  private static final ThreadExecutor HOME_JUNCTION_EXECUTOR = new ThreadExecutor();
  private static final ThreadExecutor ADS_COUNTS_EXECUTOR = new ThreadExecutor();
  private static final ThreadExecutor ADS_UPDATE_EXECUTOR = new ThreadExecutor();
  private static final ThreadExecutor SEND_VIRTUAL_INFO_EXCUTOR = new ThreadExecutor();

  public static <T> Future<T> submitTask(Callable<T> callable) {
    return COMMON_EXECUTOR.submitTask(callable);
  }

  public static void executeTask(Runnable task) {
    COMMON_EXECUTOR.executeTask(task);
  }

  public static void executeKafka(Runnable task) {
    KAFKA_EXECUTOR.executeTask(task);
  }

  public static void executeBtnTask(Runnable task) {
    LISTING_BTN_EXECUTOR.executeTask(task);
  }

  public static void executeHJTask(Runnable task) {
    HOME_JUNCTION_EXECUTOR.executeTask(task);
  }

  public static <T> Future<T> submitAdsTask(Callable<T> callable) {
    return ADS_COUNTS_EXECUTOR.submitTask(callable);
  }

  public static <T> Future<T> submitPictureTask(Callable<T> callable) {
    return PICTURE_EXECUTOR.submitTask(callable);
  }

  public static void executeAdsUpdateTask(Runnable task){
    ADS_UPDATE_EXECUTOR.executeTask(task);
  }

  public static void executorVitrualTask(Runnable task){
    SEND_VIRTUAL_INFO_EXCUTOR.executeTask(task);
  }
}
