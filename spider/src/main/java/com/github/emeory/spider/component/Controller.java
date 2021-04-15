package com.github.emeory.spider.component;

import com.github.emeory.spider.core.SpiderSession;
import com.github.emeory.spider.http.HttpRequest;
import com.github.emeory.spider.http.HttpResponse;

/**
 * @author emeory
 */
public interface Controller {

  /**
   * 发出HTTP请求前回调此方法
   * @param session 会话
   * @param request 将要发起的HTTP请求
   */
  default void beforeExecute(SpiderSession session, HttpRequest request){}

  /**
   * HTTP请求成功，会回调此方法交给用户处理爬取到的数据
   * @param session 会话
   * @param response 返回的数据
   * @param resultItems 用来存储结果
   */
  void onSuccess(SpiderSession session, HttpResponse response, ResultItems resultItems);

  /**
   * HTTP请求失败会回调此方法
   * @param session 会话
   * @param response 失败的响应
   * @return 是否重试
   */
  boolean onFailed(SpiderSession session, HttpResponse response);

  /**
   * 抓取网站数据时异常会回调此方法
   * @param session 会话
   * @param exception 捕获的异常
   * @return 是否重试
   */
  boolean onException(SpiderSession session, Exception exception);
}
