package me.ewriter.bangumitv.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Zubin on 2016/7/25.
 */
public class ApiManagers {

    public static final String BANGUMI_BASE_URL = "http://api.bgm.tv/";
    /** 网页地址 */
    public static final String WEB_BASE_URL = "http://bgm.tv/";

    public static final int DEFAULT_TIMEOUT = 10;

    private static BangumiApi sBangumiApis;

    private static WebApi sWebApis;

    protected static final Object monitor = new Object();

    public static BangumiApi getBangumiInstance() {
        synchronized (monitor) {
            if (sBangumiApis == null) {
                initBangumiApi();
            }
            return sBangumiApis;
        }
    }

    public static WebApi getWebInstance() {
        synchronized (monitor) {
            if (sWebApis == null) {
                initWebApi();
            }
            return sWebApis;
        }
    }

    private static void initBangumiApi() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BANGUMI_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = builder.client(client).build();

        sBangumiApis = retrofit.create(BangumiApi.class);
    }

    private static void initWebApi() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(WEB_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = builder.client(client).build();

        sWebApis = retrofit.create(WebApi.class);
    }

}
