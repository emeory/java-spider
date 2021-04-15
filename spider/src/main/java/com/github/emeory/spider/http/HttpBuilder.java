package com.github.emeory.spider.http;

/**
 * @author emeory
 */
public class HttpBuilder {

  private HttpBuilder() {
  }

  public static GetRequestBuilder builderGetRequest(String url) {
    GetRequestBuilder httpBuilder = new GetRequestBuilder(url);
    return httpBuilder;
  }

  public static class GetRequestBuilder {
    private GetRequest request;

    private GetRequestBuilder(String url) {
      request = new GetRequest(url);
    }

    public GetRequest build() {
      return request;
    }
  }

  public static PostRequestBuilder buildPostRequest(String url) {
    return new PostRequestBuilder(url);
  }


  public static final class PostRequestBuilder {
    private PostRequest request;

    private PostRequestBuilder(String url) {
      request = new PostRequest(url);
    }

    public PostRequestBuilder setCookie(String cookie) {
      request.setCookie(cookie);
      return this;
    }

    public PostRequestBuilder setHeader(String name, String value) {
      request.setHeader(name, value);
      return this;
    }

    public JsonRequestBuilder jsonRequest() {
      request.setHttpMethod(HttpMethod.POST_JSON);
      return new JsonRequestBuilder(request);
    }

    public FormRequestBuilder formRequest() {
      request.setHttpMethod(HttpMethod.POST_FORM);
      return new FormRequestBuilder(request);
    }

    public PostRequest build() {
      return request;
    }


    public static class JsonRequestBuilder {
      private PostRequest request;

      private JsonRequestBuilder(PostRequest request) {
        this.request = request;
      }

      public JsonRequestBuilder setJsonObject(Object json) {
        request.setRequestBodyData(json);
        return this;
      }

      public JsonRequestBuilder setJsonString(String json) {
        request.setRequestBodyString(json);
        return this;
      }

      public JsonRequestBuilder addJsonMap(String name, String value) {
        request.addParam(name, value);
        return this;
      }

      public PostRequest build() {
        return request;
      }

    }

    public static class FormRequestBuilder {
      private PostRequest request;

      private FormRequestBuilder(PostRequest request) {
        this.request = request;
      }

      public FormRequestBuilder addFormParam(String name, String value) {
        request.addParam(name, value);
        return this;
      }

      public PostRequest build() {
        return request;
      }
    }

  }
}
