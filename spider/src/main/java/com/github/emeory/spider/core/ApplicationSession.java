package com.github.emeory.spider.core;

import com.github.emeory.spider.http.GetRequest;
import com.github.emeory.spider.http.HttpRequest;
import com.github.emeory.spider.http.HttpResponse;
import com.github.emeory.spider.component.ControllerWrapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author emeory
 */
final class ApplicationSession extends SpiderSession{
  private final ApplicationContext context;
  private ApplicationSession parentSession;
  private List<SpiderSession> taskList;

  public ApplicationSession(ApplicationContext context, HttpRequest request) {
    super();
    super.request = request;
    this.context = context;
  }

  void setControllerWrapper(ControllerWrapper controllerWrapper) {
    super.controllerWrapper = controllerWrapper;
  }

  @Override
  public SpiderSession getParentSession() {
    return this.parentSession;
  }

  public void setParentSession(ApplicationSession session) {
    this.parentSession = session;
  }

  void increaseFailedCount() {
    super.failedCount ++;
  }

  void setRequest(HttpRequest request) {
    this.request = request;
  }

  void setResponse(HttpResponse response) {
    super.response = response;
  }

  /**
   * 获取用户通过此 Session 添加的爬虫任务
   * @return
   */
  List<SpiderSession> getTaskList() {
    if (taskList == null) {
      return null;
    }
    for (SpiderSession spiderSession : taskList) {
      ApplicationSession session = (ApplicationSession) spiderSession;
      session.initAttachmentMap(this.attachmentMap);
    }
    return this.taskList;
  }

  ControllerWrapper getControllerWrapper() {
    return super.controllerWrapper;
  }

  @Override
  public SpiderSession addRequest(String url) {
    HttpRequest request = new GetRequest(url);
    return addRequest(request);
  }

  @Override
  public SpiderSession addRequest(String url, String controllerName) {
    HttpRequest request = new GetRequest(url);
    return addRequest(request, controllerName);
  }

  @Override
  public SpiderSession addRequest(HttpRequest request) {
    ControllerWrapper currentWrapper = getControllerWrapper();
    if (currentWrapper.getNextWrapper() == null) {
      throw new IllegalStateException("没有 Controller 处理此任务");
    }
    ApplicationSession session = new ApplicationSession(this.context, request);
    session.setParentSession(this);
    session.setControllerWrapper(currentWrapper.getNextWrapper());
    addSession(session);
    return session;
  }

  @Override
  public SpiderSession addRequest(HttpRequest request, String controllerName) {
    ControllerWrapper wrapper = context.getControllerWrapper(controllerName);
    if (wrapper == null) {
      throw new NullPointerException("找不到指定的 Controller: " + controllerName);
    }
    ApplicationSession session = new ApplicationSession(this.context, request);
    session.setParentSession(this);
    session.setControllerWrapper(wrapper);
    addSession(session);
    return session;
  }

  private void initAttachmentMap(Map<String, Object> map) {
    this.attachmentMap.putAll(map);
  }

  private void addSession(SpiderSession session) {
    if (taskList == null) {
      taskList = new LinkedList<>();
    }
    taskList.add(session);
  }

}
