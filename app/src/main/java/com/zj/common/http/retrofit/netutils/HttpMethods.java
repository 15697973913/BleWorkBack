package com.zj.common.http.retrofit.netutils;


import com.android.common.baselibrary.log.MLog;
import com.blankj.utilcode.util.SPUtils;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.zj.common.http.retrofit.constant.BaseConstant;
import com.zj.common.http.retrofit.interceptor.TokenInterceptor;
import com.zj.common.http.retrofit.netapi.HttpApi;
import com.zj.common.http.retrofit.netapi.URLConstant;
import com.zj.zhijue.BuildConfig;
import com.zj.zhijue.MyApplication;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class HttpMethods {
    public String TAG = HttpMethods.class.getSimpleName();
    public static final String CACHE_NAME = MyApplication.getInstance().getPackageName();
    public static String BASE_URL = URLConstant.BASE_URL;
    private static final int DEFAULT_CONNECT_TIMEOUT = 60;
    private static final int DEFAULT_WRITE_TIMEOUT = 60;
    private static final int DEFAULT_READ_TIMEOUT = 60;
    private static final long MAX_CACHE_SIZE = 50L * 1024 * 1024;
    private Retrofit retrofit;
    private HttpApi httpApi;

    /**
     * 请求重试次数
     */
    private int RETRY_COUNT = 1;
    private OkHttpClient.Builder okHttpBuilder;

    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient 并设置超时时间
        okHttpBuilder = new OkHttpClient.Builder();

        //添加多域名拦截方案 2020-06-21 14:50:57
        okHttpBuilder.addInterceptor(new MoreBaseUrlInterceptor());

        /**
         * 设置缓存
         */
        File cacheFile = new File(MyApplication.getInstance().getExternalCacheDir(), CACHE_NAME);
        Cache cache = new Cache(cacheFile, MAX_CACHE_SIZE);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (NetUtil.isNetworkConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }

                Response response = chain.proceed(request);

                if (NetUtil.isNetworkConnected()) {
                    int maxAge = 0;
                    // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader(CACHE_NAME)//清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效。
                            .build();
                } else {
                    //无网络时，设置超时为 4 周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader(CACHE_NAME)
                            .build();

                }

                return response;
            }
        };

        okHttpBuilder.cache(cache);//.addInterceptor(cacheInterceptor);

        /**
         * 设置头信息
         */

        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        .addHeader("Accept-Encoding", "gzip")
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json; charset=utf-8")
                        .method(originalRequest.method(), originalRequest.body());
                //添加请求头信息，服务器进行token有效验证
                String access_token = SPUtils.getInstance().getString(SPUtils.KEY_ACCESS_TOKEN, "");
                if(access_token != null && !access_token.isEmpty()){
                    requestBuilder.addHeader("Authorization", "Bearer " + access_token);
                }
                // requestBuilder.addHeader(IBaseRequest.VERSION_CODE, String.valueOf(AppUtils.getVersionCode()));
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };

        okHttpBuilder.addInterceptor(headerInterceptor);
        okHttpBuilder.addInterceptor(new TokenInterceptor());


        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Logger.d(message);
                }
            });

            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpBuilder.addInterceptor(loggingInterceptor);
        }

        /**
         * 设置超时和重新连接
         */
        okHttpBuilder.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS);

        /**
         * 设置不使用代理
         */
//        if (!BuildConfig.DEBUG) {
//            okHttpBuilder.proxy(Proxy.NO_PROXY);
//        }

        if (BuildConfig.DEBUG) {
            okHttpBuilder.addNetworkInterceptor(new StethoInterceptor());
        }

        //错误重连
        okHttpBuilder.retryOnConnectionFailure(true);

        retrofit = new Retrofit.Builder()
                .client(okHttpBuilder.build())
                //  .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        httpApi = retrofit.create(HttpApi.class);

    }

    //在访问 HttpMethods 时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取retrofit
     */
    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void changeBaseUrl(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .client(okHttpBuilder.build())
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
        httpApi = retrofit.create(HttpApi.class);
    }

    /**
     * 获取httpService
     *
     * @return
     */
    public HttpApi getHttpApi() {
        return httpApi;
    }

    /**
     * 设置订阅和所在的线程环境
     *
     * @param <T>
     */
    public <T> void toSubscribe(Observable<T> observable, DisposableObserver<T> disposableObserver) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(RETRY_COUNT)
                .subscribe(disposableObserver);
    }

    public <T> void toSubscribeWithRetryTimes(Observable<T> observable, DisposableObserver<T> disposableObserver, int retryTimes) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(retryTimes)
                .subscribe(disposableObserver);
    }

    public <T> void toSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(RETRY_COUNT)
                .subscribe(observer);
    }

    public <T> void toSynSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.retry(RETRY_COUNT)
                .subscribe(observer);
    }

    //多个域名方案的拦截
    public class MoreBaseUrlInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //获取原始的originalRequest
            Request originalRequest = chain.request();
            //获取老的url
            HttpUrl oldUrl = originalRequest.url();
            //获取originalRequest的创建者builder
            Request.Builder builder = originalRequest.newBuilder();
            //获取头信息的集合如：manage,mdffx
            List<String> urlnameList = originalRequest.headers("urlname");
            if (urlnameList != null && urlnameList.size() > 0) {
                //删除原有配置中的值,就是namesAndValues集合里的值
                builder.removeHeader("urlname");
                //获取头信息中配置的value,如：zjfd或者
                String urlname = urlnameList.get(0);
                HttpUrl baseURL = null;
                //根据头信息中配置的value,来匹配新的base_url地址
                if (URLConstant.URLNAME_ZJFD.equals(urlname)) {
                    //智觉枫帝接口域名
                    baseURL = HttpUrl.parse(URLConstant.BASE_URL_ZJFD);
                } else {
                    //甲方接口域名
                    baseURL = HttpUrl.parse(URLConstant.BASE_URL_FIRST_PARTY);
                }

                MLog.e(urlname + "=============" + baseURL);

                //重建新的HttpUrl，需要重新设置的url部分
                HttpUrl newHttpUrl = oldUrl.newBuilder()
                        .scheme(baseURL.scheme())//http协议如：http或者https
                        .host(baseURL.host())//主机地址
                        .port(baseURL.port())//端口
                        .build();
                //获取处理后的新newRequest
                Request newRequest = builder.url(newHttpUrl).build();
                return chain.proceed(newRequest);
            } else {
                return chain.proceed(originalRequest);
            }

        }
    }

}
