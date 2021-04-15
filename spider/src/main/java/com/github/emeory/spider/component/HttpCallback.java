package com.github.emeory.spider.component;

import com.github.emeory.spider.core.SpiderSession;
import com.github.emeory.spider.http.HttpResponse;

/**
 * @author emeory
 */
public interface HttpCallback {

  /**
   * 发出HTTP请求之前回调此方法.必须进行的操作, 如果不回调，框架无法进行下一步操作.
   *
   * @param session 会话
   */
  void beforeHttpExecute(SpiderSession session);

  /**
   * 如果HTTP请求成功，需要下载器回调此方法。 这个是必须的操作。 如果不回调，框架无法进行下一步操作。
   *
   * @param session  HTTP爬取任务会话
   * @param response 下载器把响应转换为框架的 HttpResponse
   */
  void onExecuteSuccess(SpiderSession session, HttpResponse response);

  /**
   * 如果HTTP请求失败，需要下载器回调此方法。 这个是必须的操作。如果不回调，框架无法进行重试。
   *
   * @param session  HTTP爬取任务会话
   * @param response 失败的响应信息
   */
  void onExecuteFailure(SpiderSession session, HttpResponse response);

  /**
   * 发生异常时回调
   *
   * @param session   会话
   * @param exception 捕获的异常
   */
  void onException(SpiderSession session, Exception exception);

}