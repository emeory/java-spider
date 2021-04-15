package com.github.emeory.spider.http;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author emeory
 */
public class PostRequest extends HttpRequest{
	Map<String, String> bodyMap = new HashMap<>();
	String bodyString;
	Object requestBodyData;

	public PostRequest(String url) {
		super(url);
		this.httpMethod = HttpMethod.POST_JSON;
	}

	public PostRequest(String url, HttpMethod httpMethod) {
		super(url);
		this.httpMethod = httpMethod;
		headerMap.put(HEADER_CONTENT_TYPE, httpMethod.getMethod());
	}

	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	public void setRequestBodyData(Object body) {
		this.requestBodyData = body;
	}

	public void setRequestBodyString(String bodyString) {
		this.bodyString = bodyString;
	}

	@Override
	public void addParam(String name, String value) {
		bodyMap.put(name, value);
	}

	@Override
	public String removeParam(String name) {
		return bodyMap.remove(name);
	}

	@Override
	public String getRequestBody() {
		if (bodyString != null) {
			return bodyString;
		}
		switch (httpMethod){
			case POST_FORM:
				bodyString = parseFormRequestBody();
				break;
			case POST_JSON:
				bodyString = parseJsonRequestBody();
				break;
			default:
				break;
		}
		return bodyString;
	}

	private String parseFormRequestBody() {
		if (requestBodyData != null) {
			return (String) requestBodyData;
		}
		StringBuilder stringBuilder = new StringBuilder();
		if (null != bodyMap && !bodyMap.isEmpty()){
			for (Entry<String, String> entry : bodyMap.entrySet()) {
				if (stringBuilder.length() > 1) {
					stringBuilder.append("&")
							.append(entry.getKey())
							.append("=")
							.append(entry.getValue());
				} else {
					stringBuilder.append(entry.getKey())
							.append("=")
							.append(entry.getValue());
				}
			}
		}
		bodyString = stringBuilder.toString();
		return bodyString;
	}

	private String parseJsonRequestBody() {
		if (requestBodyData == null) {
			requestBodyData = bodyMap;
		}
		if (requestBodyData instanceof String) {
			bodyString = (String) requestBodyData;
		}else {
			bodyString = JSONObject.toJSONString(requestBodyData);
		}
		return bodyString;
	}
}
