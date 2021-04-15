package com.github.emeory.spider.component.impl;

import com.github.emeory.spider.component.Controller;
import com.github.emeory.spider.component.ControllerWrapper;

/**
 * @author emeory
 * Controller包装器
 */
public class DefaultControllerWrapper implements ControllerWrapper {
  private String name;
  private Controller controller;
  private ControllerWrapper nextWrapper;
  private ControllerWrapper preWrapper;

  public DefaultControllerWrapper(Controller controller, String name){
    this.controller = controller;
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Controller getController() {
    return this.controller;
  }

  @Override
  public void setNextWrapper(ControllerWrapper wrapper) {
    this.nextWrapper = wrapper;
  }

  @Override
  public void setPreWrapper(ControllerWrapper wrapper) {
    this.preWrapper = wrapper;
  }

  @Override
  public ControllerWrapper getNextWrapper() {
    return this.nextWrapper;
  }

  @Override
  public ControllerWrapper getPreWrapper() {
    return this.preWrapper;
  }

}
