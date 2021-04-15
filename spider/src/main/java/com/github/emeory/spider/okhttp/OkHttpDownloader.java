package com.github.emeory.spider.okhttp;

import com.github.emeory.spider.component.HttpCallback;
import com.github.emeory.spider.component.Downloader;
import com.github.emeory.spider.core.SpiderSession;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;

public class OkHttpDownloader implements Downloader {
	private OkHttpClient okHttpClient;
	private ExecutorService executorService;
	/**
	 * 下载器回调
	 */
	private HttpCallback downloadCallback = null;

	public OkHttpDownloader() {
	}

	private void initClient(){
		okHttpClient = new OkHttpClient.Builder()
				.followSslRedirects(true)
				.hostnameVerifier((s, sslSession) -> true)
				.build();
	}

	@Override
	public void initialize(HttpCallback downloadCallback, int threadCount) {
		this.downloadCallback = downloadCallback;
		initClient();
		executorService = new ThreadPoolExecutor(threadCount, threadCount, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>(), r -> {
			Thread thread = new Thread(r, "Fetch-Thread");
			thread.setDaemon(false);
			return thread;
		});
	}

	@Override
	public void download(SpiderSession spiderSession) {
		OkhttpDownloadTask task = new OkhttpDownloadTask(okHttpClient, downloadCallback, spiderSession);
		executorService.submit(task);
	}

	public static SSLSocketFactory getSSLSocketFactory() {
		try {
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, null, new SecureRandom());
			return sslContext.getSocketFactory();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static X509TrustManager getTrustManager() {
		X509TrustManager trustAllCerts = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[]{};
			}
		};
		return trustAllCerts;
	}


}
