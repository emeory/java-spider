## Java爬虫框架

### 1.框架简介
此项目为类似于 Java Web MVC 架构的爬虫框架， 面向接口设计，各个模块之间分离。
可以自己实现组件进行无缝替换，支持过滤器功能，可以自由扩展框架功能.  
此项目最适用于爬取接口数据，也就是说适合爬取前后端分离的网站。用户可以基于项目封装简化的HTTP协议，
封装GET请求或者POST请求，并且可以支持用户设置`Cookie`,`Token` 或者其他`Header`模拟登录状态进行安全认证，可以让用户更简单的专注于业务开发.  
项目最核心的概念有两个： `Context` 和 `Session`. `Context`保存了框架运行过程中的所有资源，
`Session`保存了一次完整的爬取流程过程中的状态数据。 

### 2.组件介绍
框架中一共有 `Controller`, `HttpFilter`, `Repository`, `DuplicateStrategy`, `Downloader` 等五个组件，下面详细介绍五种组件.  
- `Controller`组件
用户实现此接口编写业务逻辑，此接口一共有4个回调方法, 分别用户处理爬取数据前的回调，爬取数据成功, 爬取失败，出现异常这几种事件。
  
- `HttpFilter` 组件
是框架的过滤器，也是扩展点。主要在Http请求之前, 成功拿到Http响应数据之后进行回调。比如可以拦截一个Http请求是否进行爬取，也就是说进行 URL 去重，可以用过滤器。
  `DuplicateStrategy` 去重策略接口就是继承了拦截器接口
  
- `Repository`组件
此组件作为持久层， 用户所有抓取到的数据都会通过这个接口让用户统一处理。

- `Downloader`组件
这个组件就是直接通过HTTP访问网站抓取数据，目前框架默认实现的是基于 `OkHttp3` 封装的下载器。
  
### 3.使用示例
在 `example` 包下面实现了一个完成的爬虫示例：爬取腾讯新闻的首页的新闻列表以及新闻详情
```java
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
```