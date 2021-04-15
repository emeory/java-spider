package com.github.emeory.spider.component;

import com.github.emeory.spider.core.SpiderSession;

/**
 * @author emeory
 */
public interface Repository {
  void storage(SpiderSession session, ResultItems resultItems);
}
