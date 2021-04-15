package com.github.emeory.spider.component;

import com.github.emeory.spider.core.SpiderSession;

/**
 * 持久层： 显示爬取到的新闻标题和内容
 * @author emeory
 */
public class DisplayRepository implements Repository{

  @Override
  public void storage(SpiderSession session, ResultItems resultItems) {
    //取出标题
    String title = resultItems.getData("title");
    //取出内容
    String article = resultItems.getData("article");
    //显示新闻
    System.out.println(title + " - " + article);
  }
}
