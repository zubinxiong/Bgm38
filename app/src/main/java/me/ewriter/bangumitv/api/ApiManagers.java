package me.ewriter.bangumitv.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zubin on 2016/7/25.
 */
public class ApiManagers {

    public static final String API_BASE_URL = "http://api.bgm.tv/";
    public static final int DEFAULT_TIMEOUT = 10;

    private static BangumiApi mBangumiApis;


    protected static final Object monitor = new Object();

    public static BangumiApi getBangumiSingleton() {
        synchronized (monitor) {
            if (mBangumiApis == null) {
                initBangumiApi();
            }
            return mBangumiApis;
        }
    }

    private static void initBangumiApi() {

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = builder.client(client).build();

        mBangumiApis = retrofit.create(BangumiApi.class);
    }

}
