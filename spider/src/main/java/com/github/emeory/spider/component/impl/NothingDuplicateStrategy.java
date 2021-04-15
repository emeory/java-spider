package com.github.emeory.spider.component.impl;

import com.github.emeory.spider.component.DuplicateStrategy;
import com.github.emeory.spider.core.SpiderSession;
import com.github.emeory.spider.http.HttpRequest;
import com.github.emeory.spider.http.HttpResponse;

/**
 * 默认实现的 HTTP 去重, 不做任何操作
 * @author emeory
 */






















public class NothingDuplicateStrategy implements DuplicateStrategy {

  @Override
  public HttpRequest beforeRequestFilter(SpiderSession session, HttpRequest request) {
    return request;
  }

  @Override
  public HttpResponse afterResponseFilter(SpiderSession session, HttpResponse response) {
    return response;
  }
}
