package com.github.emeory.spider.component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author emeory
 */
public class ResultItems {
  private final Map<String, Object> resultMap;

  public ResultItems(){
    resultMap = new LinkedHashMap<>();
  }

  public void putData(String name, Object data){
    resultMap.put(name, data);
  }

  public <T> T getData(String name) {
    Object data = resultMap.get(name);
    T result = null;
    if (data != null) {
      result = (T)data;
    }
    return result;
  }

  public boolean isEmpty() {
    return resultMap.isEmpty();
  }

  public Map<String, Object> getAll(){
    return resultMap;
  }
}
