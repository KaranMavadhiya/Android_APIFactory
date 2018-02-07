package com.apifectory.retrofit;

import android.content.Context;

import com.apifectory.BuildConfig;
import com.apifectory.okhttp.OkHttpClientFactory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitFactory {


    private static final String BASE_URL = BuildConfig.HOST_URL;

    private Context context;

    private RetroFitFactory(Context context) {
        this.context = context;
    }

    public static RetroFitFactory getInstance(Context context) {
        return new RetroFitFactory(context);
    }

    private Retrofit getRestAdapter() {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpClientFactory.getOkHttpClientInstance(context))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public <S> S getService(Class<S> serviceClass) {
        return getRestAdapter().create(serviceClass);
    }

}