package com.github.emeory.spider.component;

import com.github.emeory.spider.core.SpiderSession;
import java.util.List;

/**
 * 下载器， 根据提交的URL以及会话，下载指定网络数据
 */
public interface Downloader {

  /**
   * 初始化设置下载器的参数,由框架调用进行属性注入
   * 下载器在 HTTP 请求的各个阶段必须要回调 HttpCallback 的方法, 否则的话框架不能感知到下载的状态,也就不能执行之后的流程
   * @param downloadCallback 下载器回调
   * @param threadCount 线程数
   */
  void initialize(HttpCallback downloadCallback, int threadCount);

  /**
   * 框架提交爬取任务
   * @param spiderSession 需要爬取的任务，被框架封装为 SpiderSession
   */
  void download(SpiderSession spiderSession);

  /**
   * 批量下载: 调用 download(SpiderSession spiderSession) 方法
   * @param sessionList 任务列表
   */
  default void download(List<SpiderSession> sessionList) {
    for (SpiderSession spiderSession : sessionList) {
      download(spiderSession);
    }
  }
}
