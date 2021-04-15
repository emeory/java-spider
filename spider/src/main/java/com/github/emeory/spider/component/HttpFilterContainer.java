package com.github.emeory.spider.component;


import java.util.List;

/**
 * HttpFilter容器
 * @author emeory
 */
public interface HttpFilterContainer {

  /**
   * 添加一个拦截器
   * @param filter
   */
  void addHttpFilter(HttpFilter filter);

  /**
   * 添加一个拦截器
   * @param index 下标
   * @param filter
   */
  void addHttpFilter(HttpFilter filter, int index);

  /**
   * 把 HttpFilter添加在最前面
   * @param filter
   */
  default void addFirstHttpFilter(HttpFilter filter) {
    addHttpFilter(filter, 0);
  }

  /**
   * 移除第一个 Filter
   * @return
   */
  HttpFilter removeHttpFilter(int index);

  /**
   * 移除第一个Filter
   * @return
   */
  default HttpFilter removeFirstHttpFilter() {
    return removeHttpFilter(0);
  }

  /**
   * 通过下标获取 HttpFilter
   * @param index 下标
   * @return
   */
  HttpFilter getHttpFilter(int index);


  default HttpFilter getFirstHttpFilter() {
    return getHttpFilter(0);
  }


  List<HttpFilter> getHttpFilterList();

  /**
   * 获取数量
   * @return
   */
  int getHttpFilterSize();
}
