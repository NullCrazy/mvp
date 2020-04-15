package com.xgl.libs.network;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Description:
 * @Author: xingguo.lei@luckincoffee.com
 * @Date: 2020/4/14 15:00
 */
public final class HttpClient {
    /**
     * 最大空闲链接数
     */
    private static final int MAX_IDLE_CONNECTIONS = 5;
    /**
     * 空闲连接数生存时间 （秒）
     */
    private static final long KEEP_ALIVE_DURATION = 60;
    /**
     * 最大链接数 default：32
     */
    private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 32;

    private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 4;

    private OkHttpClient httpClient;

    private Retrofit retrofit;
    /**
     * 基础URL
     */
    private String baseUrl;

    public HttpClient(Builder builder) {
        HttpLog.setLog(builder.log);
        baseUrl(builder.baseUrl);
        httpClient = getOkHttpClient(builder);
        retrofit = getRetrofit(httpClient);
    }

    public String baseUrl() {
        if (baseUrl == null || baseUrl.length() == 0) {
            throw new IllegalArgumentException("baseUrl is null");
        }
        return baseUrl;
    }

    /**
     * 设置baseURL
     *
     * @param baseurl 网络请求基础URL
     */
    private void baseUrl(String baseurl) {
        baseUrl = baseurl;
    }

    /**
     * @return 获取OkHttpClient
     */
    public OkHttpClient getInnerHttpClient() {
        return httpClient;
    }

    public Retrofit getInnerRetrofit() {
        return retrofit;
    }

    private OkHttpClient getOkHttpClient(final Builder innerBuilder) {
        //设置连接池数
        ConnectionPool connectionPool = new ConnectionPool(MAX_IDLE_CONNECTIONS, KEEP_ALIVE_DURATION, TimeUnit.SECONDS);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectionPool(connectionPool);

        //设置并发数
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(DEFAULT_MAX_TOTAL_CONNECTIONS);
        dispatcher.setMaxRequestsPerHost(DEFAULT_MAX_CONNECTIONS_PER_ROUTE);
        builder.dispatcher(dispatcher);

        //手动创建一个OkHttpClient并设置超时时间缓存等设置
        builder.connectTimeout(innerBuilder.connectionTimeOut, TimeUnit.SECONDS);
        builder.readTimeout(innerBuilder.readTimeOut, TimeUnit.SECONDS);
        builder.writeTimeout(innerBuilder.writeTimeOut, TimeUnit.SECONDS);

        //日志打印拦截器
        HttpLogInterceptor interceptor = new HttpLogInterceptor();
        if (innerBuilder.log != null) {
            interceptor.setLevel(HttpLogInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        return builder.build();
    }

    private Retrofit getRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static class Builder {
        private String baseUrl;
        private String appVersion;
        private HttpLog.ILog log;
        private int connectionTimeOut = 60;
        private int readTimeOut = 10;
        private int writeTimeOut = 10;

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder appVersion(String appVersion) {
            this.appVersion = appVersion;
            return this;
        }

        public Builder log(HttpLog.ILog log) {
            this.log = log;
            return this;
        }

        public Builder connectionTimeOut(int connectionTimeOut) {
            this.connectionTimeOut = connectionTimeOut;
            return this;
        }

        public Builder readTimeOut(int readTimeOut) {
            this.readTimeOut = readTimeOut;
            return this;
        }

        public Builder writeTimeOut(int writeTimeOut) {
            this.writeTimeOut = writeTimeOut;
            return this;
        }

        public HttpClient build() {
            return new HttpClient(this);
        }
    }
}
