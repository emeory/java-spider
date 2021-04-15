package com.github.emeory.spider.core;

import com.github.emeory.spider.component.Controller;
import com.github.emeory.spider.http.HttpRequest;
import com.github.emeory.spider.http.HttpResponse;
import com.github.emeory.spider.component.ControllerWrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author emeory
 */
public abstract class SpiderSession implements Session{
  protected final Map<String, Object> attachmentMap = new HashMap<>();
  protected HttpRequest request;
  protected HttpResponse response;
  protected ControllerWrapper controllerWrapper;
  protected int failedCount = 0;

  public SpiderSession() {
  }

  @Override
  public HttpRequest getRequest() {
    return this.request;
  }

  @Override
  public HttpResponse getResponse() {
    return this.response;
  }

  @Override
  public Controller getController() {
    return controllerWrapper.getController();
  }

  @Override
  public void putAttachment(String key, Object data) {
    attachmentMap.put(key, data);
  }

  @Override
  public <T> T getAttachment(String key) {
    T result = null;
    Object data = attachmentMap.get(key);
    if (data != null) {
      result = (T) data;
    }
    return result;
  }

  public int getFailedCount() {
    return failedCount;
  }

  /**
   * 获取父会话
   * @return 不存在返回 null
   */
  public abstract SpiderSession getParentSession();

  /**
   *添加一个新的 HTTP GET 请求到任务队列中
   * @param url 网址
   * @return 生成的会话, 用户可以进行操作
   */
  public abstract SpiderSession addRequest(String url);

  public void addRequest(List<String> urls) {
    for (String url : urls) {
      addRequest(url);
    }
  }

  /**
   * 添加一个新的 HTTP GET 请求到任务队列中
   * @param url 网址
   * @param controllerName 根据名称, 指定处理此请求的控制器
   * @return 生成的会话, 用户可以进行操作
   */
  public abstract SpiderSession addRequest(String url, String controllerName);

  /**
   * 添加一个新的请求到任务队列中
   * @param request 自己构造的请求, 可以是 GetHttpRequest, 也可以是 PostHttpRequest
   * @return 生成的会话
   */
  public abstract SpiderSession addRequest(HttpRequest request);

  /**
   * 添加一个新的 HTTP GET 请求到任务队列中
   * @param request 自己构造的请求, 可以是 GetHttpRequest, 也可以是 PostHttpRequest
   * @param controllerName 根据名称, 指定处理此请求的控制器
   * @return 生成的会话, 用户可以进行操作
   */
  public abstract SpiderSession addRequest(HttpRequest request, String controllerName);
}
