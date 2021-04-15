package com.github.emeory.spider.http;

public enum HttpMethod {
	/**
	 * GET请求
	 */
	GET("GET"),
	/**
	 * POST请求， 使用JSON提交数据
	 */
	POST_JSON("application/json"),
	/**
	 * POST请求，使用表单提交数据
	 */
	POST_FORM("application/x-www-form-urlencoded")
	;
	private final String method;

	HttpMethod(String method) {
		this.method = method;
	}

	public String getMethod() {
		return method;
	}
}
