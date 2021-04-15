package com.github.emeory.spider.core;

import com.github.emeory.spider.component.HttpFilterContainer;
import com.github.emeory.spider.component.Controller;
import com.github.emeory.spider.component.ControllerContainer;
import com.github.emeory.spider.component.ControllerWrapper;
import com.github.emeory.spider.component.Downloader;
import com.github.emeory.spider.component.HttpFilter;
import com.github.emeory.spider.component.Repository;
import com.github.emeory.spider.component.impl.ControllerManager;
import com.github.emeory.spider.component.impl.DefaultRepository;
import com.github.emeory.spider.component.impl.HttpFilterManager;
import com.github.emeory.spider.component.impl.NothingDuplicateStrategy;
import com.github.emeory.spider.okhttp.OkHttpDownloader;
import java.util.List;

/**
 * @author emeory
 */
public class ConfigurableContext implements Context, ControllerContainer, HttpFilterContainer {
  private Downloader downloader;
  private Repository repository;
  private final ControllerContainer controllerContainer;
  private final HttpFilterContainer httpFilterContainer;

  public ConfigurableContext() {
    this.controllerContainer = new ControllerManager();
    this.httpFilterContainer = new HttpFilterManager();
    httpFilterContainer.addHttpFilter(new NothingDuplicateStrategy(), 0);
    downloader = new OkHttpDownloader();
  }

  public Downloader getDownloader() {
    return this.downloader;
  }

  @Override
  public void setDownloader(Downloader downloader) {
    this.downloader = downloader;
  }

  public Repository getRepository() {
    return this.repository;
  }

  @Override
  public void setRepository(Repository repository) {
    this.repository = repository;
  }

  @Override
  public void addController(Controller controller, String name) {
    this.controllerContainer.addController(controller, name);
  }

  @Override
  public ControllerWrapper removeController(String name) {
    return this.controllerContainer.removeController(name);
  }

  @Override
  public ControllerWrapper getControllerWrapper(String name) {
    return this.controllerContainer.getControllerWrapper(name);
  }

  @Override
  public ControllerWrapper getControllerWrapper(int index) {
    return this.controllerContainer.getControllerWrapper(index);
  }

  @Override
  public ControllerWrapper getLastControllerWrapper() {
    return this.controllerContainer.getLastControllerWrapper();
  }

  @Override
  public int getControllerSize() {
    return this.controllerContainer.getControllerSize();
  }

  @Override
  public void addHttpFilter(HttpFilter filter) {
    this.httpFilterContainer.addHttpFilter(filter);
  }

  @Override
  public void addHttpFilter(HttpFilter filter, int index) {
    this.httpFilterContainer.addHttpFilter(filter, index);
  }

  @Override
  public HttpFilter removeHttpFilter(int index) {
    return this.httpFilterContainer.removeHttpFilter(index);
  }

  @Override
  public HttpFilter getHttpFilter(int index) {
    return this.httpFilterContainer.getHttpFilter(index);
  }

  @Override
  public List<HttpFilter> getHttpFilterList() {
    return this.httpFilterContainer.getHttpFilterList();
  }

  @Override
  public int getHttpFilterSize() {
    return this.httpFilterContainer.getHttpFilterSize();
  }
}
