package com.github.emeory.spider.component.impl;

import com.github.emeory.spider.component.Controller;
import com.github.emeory.spider.component.ControllerContainer;
import com.github.emeory.spider.component.ControllerWrapper;
import java.util.LinkedList;

/**
 * @author emeory
 * Controller 管理器， 用来管理用户添加的所有 Controller
 */
public class ControllerManager implements ControllerContainer {
  private final LinkedList<ControllerWrapper> controllerWrappersList;

  public ControllerManager() {
    controllerWrappersList = new LinkedList<>();
  }

  /**
   * 添加控制器, 按照添加顺序排序
   * @param controller 控制器实例
   * @param name 控制器名称
   */
  @Override
  public void addController(Controller controller, String name) {
    if (name == null) {
      throw new NullPointerException("name can not be null");
    }
    DefaultControllerWrapper controllerWrapper = new DefaultControllerWrapper(controller, name);
    if (getControllerSize() > 0){
      ControllerWrapper last = getLastControllerWrapper();
      last.setNextWrapper(controllerWrapper);
      controllerWrapper.setPreWrapper(last);
    }
    controllerWrappersList.addLast(controllerWrapper);
  }

  @Override
  public ControllerWrapper removeController(String name) {
    ControllerWrapper currentWrapper = this.getControllerWrapper(name);
    if (currentWrapper == null) {
      return null;
    }
    ControllerWrapper preWrapper = currentWrapper.getPreWrapper();
    preWrapper.setNextWrapper(currentWrapper.getNextWrapper());

    ControllerWrapper nextWrapper = currentWrapper.getNextWrapper();
    if (nextWrapper != null) {
      nextWrapper.setPreWrapper(preWrapper);
    }
    return currentWrapper;
  }

  @Override
  public ControllerWrapper getControllerWrapper(String name) {
    if (name == null) {
      throw new NullPointerException("name can not be null");
    }
    for (ControllerWrapper controllerWrapper : controllerWrappersList) {
      if (name.equals(controllerWrapper.getName())){
        return controllerWrapper;
      }
    }
    return null;
  }

  @Override
  public ControllerWrapper getControllerWrapper(int index) {
    if (index > getControllerSize()) {
      return null;
    }
    return controllerWrappersList.get(index);
  }

  @Override
  public ControllerWrapper getFirstControllerWrapper() {
    return controllerWrappersList.getFirst();
  }

  @Override
  public ControllerWrapper getLastControllerWrapper() {
    return controllerWrappersList.getLast();
  }

  @Override
  public int getControllerSize() {
    return controllerWrappersList.size();
  }

}
