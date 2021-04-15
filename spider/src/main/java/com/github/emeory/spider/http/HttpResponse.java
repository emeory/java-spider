package com.github.emeory.spider.http;

import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import org.jsoup.nodes.Document;

public interface HttpResponse {
  static final String COOKIE_NAME = "Cookie";

  /**
   * 查询响应的状态, 表示请求是否成功
   * @return 请求成功: true
   */
  boolean isSuccessful();

  /**
   * 返回HTTP响应码
   * @return 正常情况: 200
   */
  int getHttpCode();

  /**
   * 获取 HTTP响应提示
   * @return OK 或者其他
   */
  String getHttpMessage();

  /**
   * 获取请求头
   * @param name 请求头名字
   * @return
   */
  String getHeader(String name);

  /**
   * 获取所有 Header 的 key-value
   * @return
   */
  Map<String, String> getHeadersMap();

  /**
   * 获取请求头
   * @param name 请求头名字
   * @param defaultValue 此默认值
   * @return 存在就返回, 如果不存在, 返回此默认值
   */
  String getHeader(String name, String defaultValue);

  /**
   * 获取 Cookie
   * @return
   */
  default String getCookie() {
    return getHeader(COOKIE_NAME);
  }

  /**
   * 查看响应内容的类型, 目前支持两种: JSON, HTML
   * @return 响应内容是JSON, 否则是 HTML
   */
  ResponseType getContentType();

  /**
   * 获取响应内容的字符串形式
   * @return 字符串
   */
  String getBodyString();

  /**
   * 获取响应内容的字节数组形式
   * @return 字节数组
   * @throws Exception IO失败
   */
  byte[] getBodyBytes() throws Exception;

  /**
   * 把响应的HTML字符串通过JSOUP解析
   * @return 解析成功返回对象, 否则返回 null
   */
  Document parseByJsoup();

  /**
   * 把响应的字符串内容解析为JSON对象
   * @return 成功返回对象
   */
  JSONObject parseByJson();

  /**
   * 把响应的字符串内容序列化为指定的JSON对象
   * @param clazz 指定类
   * @param <T> 类型
   * @return 解析成功的对象
   */
  <T> T parseByJson(Class<T> clazz);
}
