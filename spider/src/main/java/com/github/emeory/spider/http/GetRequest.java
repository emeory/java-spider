package com.github.emeory.spider.http;

public class GetRequest extends HttpRequest{

	public GetRequest(String url) {
		super(url);
		this.httpMethod = HttpMethod.GET;
	}

	@Override
	public void addParam(String name, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String removeParam(String name) {
		throw  new UnsupportedOperationException();
	}

	@Override
	public String getRequestBody() {
		throw new UnsupportedOperationException();
	}
}
