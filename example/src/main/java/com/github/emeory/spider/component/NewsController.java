package com.github.emeory.spider.component;

import com.github.emeory.spider.core.SpiderSession;
import com.github.emeory.spider.http.HttpResponse;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * 获取新闻内容
 * @author emeory
 */
public class NewsController implements Controller{

  @Override
  public void onSuccess(SpiderSession session, HttpResponse response, ResultItems resultItems) {
    Document document = response.parseByJsoup();
    //解析新闻标题
    Element titleEle = document.getElementsByTag("title").first();
    String title = titleEle.text().split("_")[0];

    //解析新闻内容详情
    Element articleEle = document.getElementsByClass("content-article")
        .first();
    String article = articleEle.text();
    //把数据放进持久层
    resultItems.putData("title", title);
    resultItems.putData("article", article);
  }

  @Override
  public boolean onFailed(SpiderSession session, HttpResponse response) {
    return false;
  }

  @Override
  public boolean onException(SpiderSession session, Exception exception) {
    return false;
  }
}
