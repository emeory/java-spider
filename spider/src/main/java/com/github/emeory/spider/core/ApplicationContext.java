package com.github.emeory.spider.core;

import com.github.emeory.spider.component.DuplicateStrategy;
import com.github.emeory.spider.component.HttpCallback;
import com.github.emeory.spider.component.HttpFilter;
import com.github.emeory.spider.http.HttpRequest;
import java.util.List;

/**
 * @author emeory
 */
public class ApplicationContext extends ConfigurableContext implements SpiderContext {
  private SpiderState state;
  private int globalRetry = 0;
  private int threadCount = 1;
  private HttpCallback downloadCallback;


  public ApplicationContext() {
    super();
    downloadCallback = new DownloadCallback(this);
    state = SpiderState.NOT_START;
  }

  public DuplicateStrategy getDuplicateStrategy() {
    HttpFilter filter = this.getFirstHttpFilter();
    if (filter instanceof DuplicateStrategy) {
      return (DuplicateStrategy) filter;
    }
    return null;
  }

  @Override
  public void setDuplicateStrategy(DuplicateStrategy strategy) {
    HttpFilter filter = this.getFirstHttpFilter();
    if (filter instanceof DuplicateStrategy) {
      this.removeFirstHttpFilter();
    }
    this.addFirstHttpFilter(strategy);
  }

  /**
   * 执行爬取任务, 任务会经过框架的处理
   * @param session 爬取任务
   */
  public void download(SpiderSession session) {
    List<HttpFilter> filterList = this.getHttpFilterList();
    HttpRequest request = session.getRequest();
    for (HttpFilter filter : filterList) {
      try {
        //执行去重策略
        request = filter.beforeRequestFilter(session, request);
        if (request == null) {
          return;
        }
      }catch (Exception e) {
        e.printStackTrace();
      }
    }
    ApplicationSession applicationSession = (ApplicationSession) session;
    applicationSession.setRequest(request);
    getDownloader().download(session);
  }


  public void download(List<SpiderSession> sessionList) {
    for (SpiderSession session : sessionList) {
      download(session);
    }
  }

  @Override
  public void start() {
    state = SpiderState.RUNNING;
    getDownloader().initialize(downloadCallback, getThreadCount());
  }

  @Override
  public void stop() {
    state = SpiderState.STOP;
  }

  @Override
  public SpiderState getSpiderState() {
    return state;
  }

  @Override
  public void setGlobalRetry(int retry) {
    this.globalRetry = Math.max(globalRetry, retry);
  }

  public int getGlobalRetry() {
    return this.globalRetry;
  }

  @Override
  public void setThreadCount(int thread) {
    this.threadCount = Math.max(thread, threadCount);
  }

  public int getThreadCount() {
    return this.threadCount;
  }

  @Override
  public void request(HttpRequest request) {
    ApplicationSession session = new ApplicationSession(this, request);
    session.setControllerWrapper(getFirstControllerWrapper());
    download(session);
  }
}
