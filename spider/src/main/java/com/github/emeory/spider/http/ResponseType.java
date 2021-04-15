package com.github.emeory.spider.http;

/**
 * @author emeory
 * 响应内容的类型
 */
public enum ResponseType {
  /**
   * HTTP结果是JSON
   */
  JSON("JSON"),
  /**
   * 返回结果是HTML网页
   */
  HTML("HTML")
  ;

  private String type;

  ResponseType(String type) {
    this.type = type;
  }

  public String getType() {
    return this.type;
  }
}
