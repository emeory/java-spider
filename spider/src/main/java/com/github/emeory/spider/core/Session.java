package com.github.emeory.spider.core;

import com.github.emeory.spider.component.Controller;
import com.github.emeory.spider.http.HttpRequest;
import com.github.emeory.spider.http.HttpResponse;

/**
 * @author emeory
 */
public interface Session {

  /**
   * 获取会话的 HTTP 请求
   */
  HttpRequest getRequest();

  /**
   * 获取响应内容
   * @return 如果未发起请求返回 null
   */
  HttpResponse getResponse();

  /**
   * 获取处理本次会话的Controller
   * @return
   */
  Controller getController();

  /**
   * 设置附加属性
   * @param key key
   * @param data value
   */
  void putAttachment(String key, Object data);

  /**
   * 获取设置的附加属性
   * @param key key
   * @return 如果找不到返回 null
   */
  <T> T getAttachment(String key);
}
