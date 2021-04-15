package com.github.emeory.spider.okhttp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.emeory.spider.http.HttpResponse;
import com.github.emeory.spider.http.ResponseType;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author emeory
 */
public class OkHttpResponse implements HttpResponse {
  private final Response response;
  private ResponseType type;
  private Map<String, String> headersMap;
  private byte[] bodyData = null;
  private String bodyString = null;

  public OkHttpResponse(Response response) {
    this.response = response;
  }

  @Override
  public boolean isSuccessful() {
    return response.isSuccessful();
  }

  @Override
  public int getHttpCode() {
    return response.code();
  }

  @Override
  public String getHttpMessage() {
    return response.message();
  }

  @Override
  public String getHeader(String name) {
    return response.header(name);
  }

  @Override
  public Map<String, String> getHeadersMap() {
    if (headersMap == null) {
      Headers headers = response.headers();
      headersMap = new HashMap<>(headers.size());
      headers.forEach(pair ->
        headersMap.put(pair.getFirst(), pair.getSecond())
      );
    }
    return headersMap;
  }

  @Override
  public String getHeader(String name, String defaultValue) {
    return response.header(name, defaultValue);
  }

  @Override
  public ResponseType getContentType() {
    if (response == null){
      return null;
    }
    if (type == null) {
      MediaType mediaType = response.body().contentType();
      String typeStr = mediaType.type();
      System.out.println(mediaType.toString());
      if (typeStr.toLowerCase().contains(ResponseType.JSON.getType().toLowerCase())) {
        type = ResponseType.JSON;
      }else {
        type = ResponseType.HTML;
      }
    }
    return type;
  }

  @Override
  public String getBodyString() {
    if (response == null){
      return null;
    }
    if (this.bodyString == null) {
      try {
        this.bodyString = new String(getBodyBytes());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return bodyString;
  }

  @Override
  public byte[] getBodyBytes() throws Exception{
    if (response == null){
      return null;
    }
    if (bodyData == null) {
      bodyData = response.body().bytes();
    }
    return bodyData;
  }

  @Override
  public Document parseByJsoup() {
    String html = getBodyString();
    if (html == null){
      return null;
    }
    return Jsoup.parse(html);
  }

  @Override
  public JSONObject parseByJson() {
    String json = getBodyString();
    if (json == null){
      return null;
    }
    return JSON.parseObject(json);
  }

  @Override
  public <T> T parseByJson(Class<T> clazz) {
    String json = getBodyString();
    T t = null;
    if (json != null) {
      t = JSON.parseObject(json, clazz);
    }
    return t;
  }
}
