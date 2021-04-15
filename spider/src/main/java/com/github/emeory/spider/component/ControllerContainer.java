package com.github.emeory.spider.component;

/**
 * @author emeory
 */
public interface ControllerContainer {

  /**
   * 添加控制器, 按照添加顺序排序
   * @param controller 控制器实例
   * @param name 控制器名称
   */
  void addController(Controller controller, String name);

  /**
   * 根据名称移除控制器
   * @param name 控制器名称
   * @return 移除的控制器包装器
   */
  ControllerWrapper removeController(String name);

  /**
   * 根据名字获取 Controller
   * @param name Controller名称
   * @return 查询到的数据， 没有就返回 null
   */
  ControllerWrapper getControllerWrapper(String name);

  /**
   * 根据索引获取 Controller
   * @param index Controller索引
   * @return 查询到的数据， 没有就返回 null
   */
  ControllerWrapper getControllerWrapper(int index);

  /**
   * 获取第一个 Controller
   * @return 不存在返回 null
   */
  default ControllerWrapper getFirstControllerWrapper() {
    return getControllerWrapper(0);
  }

  /**
   * 获取最后一个控制器
   * @return 不存在返回 null
   */
  ControllerWrapper getLastControllerWrapper();

  /**
   * 获取数量
   * @return 管理的控制器的数量
   */
  int getControllerSize();

}
