package com.apifactory;


import android.content.Context;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.logging.HttpLoggingInterceptor;

public class APIFactory {

    private static okhttp3.OkHttpClient.Builder okhttpInstance;

    private final static long CONNECTION_TIME_OUT = 30;
    private final static long READ_TIME_OUT = 60;

    private static final int DISK_CACHE_SIZE = 10 * 1024 * 1024; // 10MB

    private APIFactory(){}

    public static okhttp3.OkHttpClient getOkHttpClientInstance(Context context, boolean isDebug){
        if(okhttpInstance == null){
            // Install an HTTP cache in the context cache directory.
            File cacheDir = new File(context.getCacheDir(), "http");
            Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);

            okhttpInstance = new okhttp3.OkHttpClient().newBuilder().cache(cache);
            okhttpInstance.connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS);
            okhttpInstance.readTimeout(READ_TIME_OUT, TimeUnit.SECONDS);

            if (isDebug) {
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okhttpInstance.addInterceptor(loggingInterceptor);
            }
        }
        return okhttpInstance.build();
    }
}
