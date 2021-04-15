package com.github.emeory.spider;

/**
 * @author emeory
 */
public class Assert {

  public static void NotNull(Object obj, String msg) {
    if (null == obj) {
      throw new IllegalArgumentException(msg);
    }
  }
}
