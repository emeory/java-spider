package com.github.emeory.spider.component.impl;

import com.github.emeory.spider.component.HttpFilterContainer;
import com.github.emeory.spider.component.HttpFilter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author emeory
 */
public class HttpFilterManager implements HttpFilterContainer {
  private final  LinkedList<HttpFilter> filterList;

  public HttpFilterManager() {
    filterList = new LinkedList<>();
  }

  @Override
  public void addHttpFilter(HttpFilter filter) {
    filterList.add(filter);
  }

  @Override
  public void addHttpFilter(HttpFilter filter, int index) {
    filterList.add(index, filter);
  }

  @Override
  public HttpFilter removeHttpFilter(int index) {
    HttpFilter remove = null;
    if (index < filterList.size()) {
      remove = filterList.remove(index);
    }
    return remove;
  }

  @Override
  public HttpFilter getHttpFilter(int index) {
    HttpFilter result = null;
    if (index < filterList.size()) {
      result = filterList.get(index);
    }
    return result;
  }

  @Override
  public List<HttpFilter> getHttpFilterList() {
    return new ArrayList<>(filterList);
  }

  @Override
  public int getHttpFilterSize() {
    return 0;
  }
}
