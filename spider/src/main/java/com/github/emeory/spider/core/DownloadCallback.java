package com.github.emeory.spider.core;

import com.github.emeory.spider.component.Controller;
import com.github.emeory.spider.component.HttpFilter;
import com.github.emeory.spider.component.HttpCallback;
import com.github.emeory.spider.http.HttpResponse;
import com.github.emeory.spider.component.ResultItems;
import java.util.List;

/**
 * @author emeory
 * 下载器下载结束的回调， 框架向下载器提交下载 任务时同时提供此对象，需要由下载器调用回调方法
 */
public class DownloadCallback implements HttpCallback {
  private final ApplicationContext context;

  public DownloadCallback(ApplicationContext context) {
    this.context = context;
  }

  /**
   * 发出HTTP请求之前回调此方法.必须进行的操作, 如果不回调，框架无法进行下一步操作.
   * @param session 会话
   */
  @Override
  public void beforeHttpExecute(SpiderSession session) {
    Controller controller = session.getController();
    try {
      controller.beforeExecute(session, session.getRequest());
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 如果HTTP请求成功，需要下载器回调此方法。 这个是必须的操作。 如果不回调，框架无法进行下一步操作。
   * @param session HTTP爬取任务会话
   * @param response 下载器把响应转换为框架的 HttpResponse
   */
  @Override
  public void onExecuteSuccess(SpiderSession session, HttpResponse response) {
    ApplicationSession applicationSession = getApplicationContext(session);
    //执行拦截器代码, 第一个拦截器是默认去重策略
    List<HttpFilter> filterList = context.getHttpFilterList();
    for (HttpFilter filter : filterList) {
      try {
        response = filter.afterResponseFilter(session, response);
        if (response == null) {
          return;
        }
      }catch (Exception e) {
        e.printStackTrace();
      }
    }
    applicationSession.setResponse(response);
    doProcessSuccess(applicationSession, response);

  }

  /**
   * 处理请求成功后的业务处理
   * @param applicationSession 会话
   * @param response 响应数据
   */
  private void doProcessSuccess(ApplicationSession applicationSession, HttpResponse response) {
    Controller controller = applicationSession.getController();
    ResultItems resultItems = new ResultItems();
    controller.onSuccess(applicationSession, response, resultItems);
    if (!resultItems.isEmpty()) {
      context.getRepository().storage(applicationSession, resultItems);
    }
    //下载把新的爬取请求
    List<SpiderSession> taskList = applicationSession.getTaskList();
    if ((taskList != null) && (!taskList.isEmpty())) {
      context.download(taskList);
    }
  }

  /**
   * 如果HTTP请求失败，需要下载器回调此方法。 这个是必须的操作。如果不回调，框架无法进行重试。
   * @param session HTTP爬取任务会话
   * @param response 失败的响应信息
   */
  @Override
  public void onExecuteFailure(SpiderSession session, HttpResponse response) {

    ApplicationSession applicationSession = getApplicationContext(session);
    applicationSession.increaseFailedCount();
    try {
      Controller controller = applicationSession.getController();
      boolean retry = controller.onFailed(applicationSession, response);
      processRetry(applicationSession, retry);
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 发生异常时回调
   * @param session 会话
   * @param exception 捕获的异常
   */
  @Override
  public void onException(SpiderSession session, Exception exception) {
    ApplicationSession applicationSession = getApplicationContext(session);
    applicationSession.increaseFailedCount();
    Controller controller = applicationSession.getController();
    boolean retry = controller.onException(applicationSession, exception);
    processRetry(applicationSession, retry);
  }

  /**
   * 处理失败重试逻辑, 如果在失败次数之内, 直接重试, 否则根据回调的结果判断是否重试
   * @param session 失败的会话
   * @param retry 是否如果超出了重试次数, 是否重试
   */
  private void processRetry(SpiderSession session, boolean retry) {
    if (session.getFailedCount() < context.getGlobalRetry()) {
      context.getDownloader().download(session);
    }else if (retry) {
      context.download(session);
    }
  }

  private ApplicationSession getApplicationContext(SpiderSession session) {
    return (ApplicationSession) session;
  }
}
