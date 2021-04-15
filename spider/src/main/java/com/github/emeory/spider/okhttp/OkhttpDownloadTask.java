package com.github.emeory.spider.okhttp;

import com.github.emeory.spider.component.HttpCallback;
import com.github.emeory.spider.core.SpiderSession;
import com.github.emeory.spider.http.HttpMethod;
import com.github.emeory.spider.http.HttpRequest;
import com.github.emeory.spider.http.HttpResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author emeory
 */
public class OkhttpDownloadTask implements Runnable {
  private OkHttpClient okHttpClient;
  private SpiderSession spiderSession;
  private Request request;
  private HttpCallback downloadCallback;

  public OkhttpDownloadTask(OkHttpClient client, HttpCallback downloadCallback , SpiderSession session) {
    this.okHttpClient = client;
    this.spiderSession = session;
    this.downloadCallback = downloadCallback;
  }

  public Request getRequest() {
    if (request == null) {
      request = convertToOkHttp(spiderSession);
    }
    return request;
  }

  private Request convertToOkHttp(SpiderSession session){
    //进行请求前的回调: beforeHttpExecute()
    downloadCallback.beforeHttpExecute(session);
    HttpRequest request = session.getRequest();
    HttpMethod httpMethod = request.getHttpMethod();
    Request okhttpRequest;
    switch (httpMethod){
      case GET:
        okhttpRequest = parseGetOkHttpRequest(request);
        break;
      case POST_JSON:
      case POST_FORM:
        okhttpRequest = parsePostOkHttpRequest(request);
        break;
      default:
        okhttpRequest = null;
    }
    return okhttpRequest;
  }

  @Override
  public void run() {
    try {
      //getRequest() 会调用 convertToOkHttp()方法
      //会进行downloadCallback.beforeHttpExecute()的回调
      Request okhttpRequest = getRequest();
      Response okhttpResponse = okHttpClient.newCall(okhttpRequest).execute();
      HttpResponse response = new OkHttpResponse(okhttpResponse);
      if (okhttpResponse.isSuccessful()) {
        //进行请求成功的回调
        downloadCallback.onExecuteSuccess(spiderSession, response);
      }else {
        //进行请求失败的回调
        downloadCallback.onExecuteFailure(spiderSession, response);
      }
    } catch (Exception e) {
      downloadCallback.onException(spiderSession, e);
    }
  }

  private Request parseGetOkHttpRequest(HttpRequest httpRequest) {
    Request.Builder builder = new Request.Builder()
        .url(httpRequest.getUrl())
        .get();
    setCommonHeaders(builder, httpRequest);
    return builder.build();
  }

  private Request parsePostOkHttpRequest(HttpRequest httpRequest) {
    RequestBody requestBody = RequestBody.create(httpRequest.getRequestBody(), null);
    Request.Builder builder = new Request.Builder()
        .url(httpRequest.getUrl())
        .post(requestBody);
    setCommonHeaders(builder, httpRequest);
    return builder.build();
  }

  private void setCommonHeaders(Request.Builder builder, HttpRequest httpRequest) {
    Map<String, String> headerMap = HttpRequest.getGlobalHeaderMap();
    Map<String, String> headersMap = new HashMap<>(headerMap);
    headersMap.putAll(httpRequest.getHeaderMap());
    Iterator<Entry<String, String>> iterator = headersMap.entrySet().iterator();
    while (iterator.hasNext()){
      Entry<String, String> entry = iterator.next();
      builder.addHeader(entry.getKey(), entry.getValue());
    }
  }
}
