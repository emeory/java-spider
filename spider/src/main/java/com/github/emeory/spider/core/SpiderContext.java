package com.github.emeory.spider.core;

import com.github.emeory.spider.component.Controller;
import com.github.emeory.spider.component.DuplicateStrategy;
import com.github.emeory.spider.http.GetRequest;
import com.github.emeory.spider.http.HttpRequest;
import java.util.List;

/**
 * @author emeory
 */
public interface SpiderContext extends Context {

  void start();

  void stop();

  SpiderState getSpiderState();

  void setGlobalRetry(int retry);

  void setThreadCount(int thread);

  void addController(Controller controller, String name);

  void setDuplicateStrategy(DuplicateStrategy strategy);

  /**
   * 添加需要爬取的URL, 默认使用 GET 请求
   * @param url 网址
   */
  default void request(String url) {
    request(new GetRequest(url));
  }

  void request(HttpRequest request);

  default void request(List<HttpRequest> requestList) {
    for (HttpRequest request : requestList) {
      request(request);
    }
  }
}
