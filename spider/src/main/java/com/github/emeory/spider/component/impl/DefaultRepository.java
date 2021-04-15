package com.github.emeory.spider.component.impl;

import com.github.emeory.spider.component.Repository;
import com.github.emeory.spider.core.SpiderSession;
import com.github.emeory.spider.component.ResultItems;

/**
 * @author emeory
 */
public class DefaultRepository implements Repository {

  @Override
  public void storage(SpiderSession session, ResultItems resultItems) {
    resultItems.getAll().forEach((n, v) -> {
      System.out.println(String.format("name: %s, value:%s", n, v.toString()));
    });
  }
}
