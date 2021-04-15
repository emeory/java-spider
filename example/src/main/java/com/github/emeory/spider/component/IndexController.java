package com.github.emeory.spider.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.emeory.spider.core.SpiderSession;
import com.github.emeory.spider.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取新闻列表
 * @author emeory
 */
public class IndexController implements Controller{
  private static final String URL_PREFIX = "https://new.qq.com/rain/a/";

  @Override
  public void onSuccess(SpiderSession session, HttpResponse response, ResultItems resultItems) {
    JSONArray newsList = JSON.parseObject(response.getBodyString())
        .getJSONObject("data").getJSONArray("list");

    List<String> requestList = new ArrayList<>();

    for (int i = 0; i < newsList.size(); i++) {
      //解析出新闻详情的链接，并且放到List中
      JSONObject data = newsList.getJSONObject(i);
      String cmsId = data.getString("cms_id");
      requestList.add(URL_PREFIX + cmsId);
    }
    //把解析到的新的链接添加到框架进行爬取
    session.addRequest(requestList);
  }

  @Override
  public boolean onFailed(SpiderSession session, HttpResponse response) {
    return true;
  }

  @Override
  public boolean onException(SpiderSession session, Exception exception) {
    return true;
  }
}
