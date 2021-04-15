package com.github.emeory.spider.core;

import com.github.emeory.spider.component.Downloader;
import com.github.emeory.spider.component.Repository;

/**
 * @author emeory
 */
public interface Context {

  /**
   * 设置下载器
   * @param downloader 用户自己设置的下载器
   */
  void setDownloader(Downloader downloader);

  /**
   * 设置持久化组件
   * @param repository 实现的持久化组件
   */
  void setRepository(Repository repository);
}
