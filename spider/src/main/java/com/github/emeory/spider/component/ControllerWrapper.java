package com.github.emeory.spider.component;

/**
 * @author emeory
 */
public interface ControllerWrapper {

  /**
   * 获取控制器的名称
   * @return 名称
   */
  String getName();

  /**
   * 获取控制器实例
   * @return 被包装的控制器
   */
  Controller getController();

  /**
   * 设置下一个包装器
   * @param wrapper 下一个
   */
  void setNextWrapper(ControllerWrapper wrapper);

  /**
   * 设置上一个包装器
   * @param wrapper 上一个
   */
  void setPreWrapper(ControllerWrapper wrapper);

  /**
   * 获取下一个包装器
   * @return 下一个
   */
  ControllerWrapper getNextWrapper();

  /**
   * 获取上一个包装器
   * @return 上一个
   */
  ControllerWrapper getPreWrapper();
}
