package com.github.emeory.spider.component;

import com.github.emeory.spider.core.SpiderSession;
import com.github.emeory.spider.http.HttpRequest;
import com.github.emeory.spider.http.HttpResponse;

/**
 * HTTP 请求过滤器,可以实现去重策略
 * @author emeory
 */
public interface HttpFilter {

  /**
   * 请求之前去重策略过滤方法,
   * @param session 会话
   * @param request 将要执行的爬虫请求
   * @return 处理之后的请求, 返回 null 表示取消这个 Request
   */
  HttpRequest beforeRequestFilter(SpiderSession session, HttpRequest request);

  /**
   * 拿到响应之后的去重策略过滤方法
   * @param session 会话
   * @param response HTTP 请求的响应数据
   * @return 如果返回 Null, 表示忽略这个响应数据
   */
  HttpResponse afterResponseFilter(SpiderSession session, HttpResponse response);
}
