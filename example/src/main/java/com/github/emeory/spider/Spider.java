package com.github.emeory.spider;

import com.github.emeory.spider.component.Controller;
import com.github.emeory.spider.component.DisplayRepository;
import com.github.emeory.spider.component.IndexController;
import com.github.emeory.spider.component.NewsController;
import com.github.emeory.spider.component.Repository;

/**
 * 爬取腾讯新闻首页新闻
 * @author emeory
 */
public class Spider {

  public static void main(String[] args) {
    String url = "https://i.news.qq.com/trpc.qqnews_web.kv_srv.kv_srv_http_proxy/list?sub_srv_id=24hours&srv_id=pc&offset=0&limit=20&strategy=1&ext={%22pool%22:[%22top%22],%22is_filter%22:7,%22check_type%22:true}";
    SpiderApplication spiderApplication = new SpiderApplication();

    //添加爬取新闻首页列表的Controller
    Controller indexController = new IndexController();
    spiderApplication.addController(indexController);

    //添加爬取新闻内容的Controller
    Controller newsController = new NewsController();
    spiderApplication.addController(newsController);

    //设置持久层: 这里只是把爬取的数据显示出来
    Repository repository = new DisplayRepository();
    spiderApplication.setRepository(repository);

    //开始爬取数据
    spiderApplication.request(url);

  }
}
