package com.github.emeory.spider;

import com.github.emeory.spider.core.ApplicationContext;
import com.github.emeory.spider.component.DuplicateStrategy;
import com.github.emeory.spider.component.HttpFilter;
import com.github.emeory.spider.component.Controller;
import com.github.emeory.spider.core.SpiderContext;
import com.github.emeory.spider.core.SpiderState;
import com.github.emeory.spider.http.HttpRequest;
import com.github.emeory.spider.component.Downloader;
import com.github.emeory.spider.component.Repository;

public class SpiderApplication implements SpiderContext {

	/**
	 * 默认的 User-Agent
	 */
	public static String DEFAULT_USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36";

	/**
	 * Http默认的 Referer
	 */
	private final static String DEFAULT_REFERER = "https://www.baidu.com";
	private final ApplicationContext context;

	public SpiderApplication() {
		setUserAgent(DEFAULT_USER_AGENT);
		setReferer(DEFAULT_REFERER);
		context = new ApplicationContext();
	}

	@Override
	public void setDuplicateStrategy(DuplicateStrategy strategy) {
		Assert.NotNull(strategy, "DuplicateStrategy不能为NULL");
		context.setDuplicateStrategy(strategy);
	}

	@Override
	public void request(HttpRequest request) {
		start();
		context.request(request);
	}

	public void addHttpFilter(HttpFilter httpFilter) {
		Assert.NotNull(httpFilter, "HttpFilter不能为NULL");
		context.addHttpFilter(httpFilter);
	}

	public void setDownloader(Downloader downloader) {
		checkStartedStatus();
		context.setDownloader(downloader);
	}

	public void setRepository(Repository repository) {
		checkStartedStatus();
		context.setRepository(repository);
	}

	/**
	 * 添加控制器，使用默认的名称
	 */
	public String addController(Controller controller) {
		String name = "Controller-" + context.getControllerSize();
		this.addController(controller, name);
		return name;
	}

	/**
	 * 添加控制器同时设置名称
	 */
	@Override
	public void addController(Controller controller, String name) {
		checkStartedStatus();
		Assert.NotNull(controller, "Controller不能为NULL");
		Assert.NotNull(controller, "Controller Name不能为NULL");
		context.addController(controller, name);
	}

	@Override
	public void start() {
		if (SpiderState.NOT_START.equals(getSpiderState())) {
			checkInitCondition();
			context.start();
		}
	}

	@Override
	public void stop() {
		context.stop();
	}

	@Override
	public SpiderState getSpiderState() {
		return context.getSpiderState();
	}

	@Override
	public void setGlobalRetry(int retry) {
		checkStartedStatus();
		context.setGlobalRetry(retry);
	}

	@Override
	public void setThreadCount(int threadCount) {
		checkStartedStatus();
		context.setThreadCount(threadCount);
	}

	public void setUserAgent(String userAgent) {
		setHeader("User-Agent", userAgent);
	}

	public void setReferer(String referer) {
		setHeader("Referer", referer);
	}

	public void setCookie(String cookie) {
		setHeader("Cookie", cookie);
	}

	public void setHeader(String name, String value) {
		HttpRequest.setGlobalHeader(name, value);
	}

	private void checkStartedStatus() {
		if (SpiderState.RUNNING.equals(context.getSpiderState())) {
			throw new IllegalStateException("非法操作, 爬虫已启动!");
		}
	}

	private void checkInitCondition() {
		if (context.getControllerSize() < 1) {
			throw new IllegalStateException("未添加 Controller");
		}
		if (context.getRepository() == null) {
			throw new IllegalStateException("未设置 Repository");
		}
	}
}
