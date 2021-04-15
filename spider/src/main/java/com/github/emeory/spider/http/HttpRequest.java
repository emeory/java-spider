package com.github.emeory.spider.http;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HTTP 请求的抽象, 代表一次 HTTP 请求
 */
public abstract class HttpRequest {
	final static String HEADER_CONTENT_TYPE = "Content-Type";
	final static String HEADER_CONTENT_COOKIE = "Cookie";

	String url;
	HttpMethod httpMethod;
	Map<String, String> headerMap = new HashMap<>();
	private static final Map<String, String> globalHeaderMap = new ConcurrentHashMap<>();

	/**
	 * 设置全局 Header
	 * @param name header key
	 * @param value header value
	 */
	public static void setGlobalHeader(String name, String value){
		globalHeaderMap.put(name, value);
	}

	/**
	 * 设置全局 cookie
	 * @param cookie cookie value
	 */
	public static void setGlobalCookie(String cookie) {
		setGlobalHeader(HEADER_CONTENT_COOKIE, cookie);
	}

	/**
	 * 获取全局 headerMap
	 */
	public static Map<String, String> getGlobalHeaderMap(){
		return globalHeaderMap;
	}

	public HttpRequest(String url) {
		if (!url.startsWith("http")){
			url = "http://" + url;
		}
		this.url = url;
	}

	/**
	 * 获取 HTTP 请求地址
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 获取 HTTP 请求方法
	 */
	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	/**
	 * 获取本请求的 HEADER MAP
	 */
	public Map<String, String> getHeaderMap() {
		return headerMap;
	}

	/**
	 * 设置 本请求的 HEADER, 会覆盖全局同名的 Header
	 */
	public void setHeader(String name, String value) {
		headerMap.put(name, value);
	}

	/**
	 * 设置本请求的 Cookie, 会覆盖全局 cookie
	 */
	public void setCookie(String cookie) {
		headerMap.put(HEADER_CONTENT_COOKIE, cookie);
	}

	/**
	 * 添加请求参数
	 * @param name nam
	 * @param value
	 */
	public abstract void addParam(String name, String value);

	public abstract String removeParam(String name);

	/**
	 * 获取请求体的 字符串形式
	 */
	public abstract String getRequestBody();

	/**
	 * 获取主机地址
	 */
	public String getHost() {
		String host = null;
		try {
			URL urlObj = new URL(url);
			host = urlObj.getProtocol() + "://" + urlObj.getHost();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return host;
	}
}
