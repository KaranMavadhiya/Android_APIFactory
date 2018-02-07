package com.apifectory.rxjava2;

public interface RxJava2Callback<T> {
    void onRequestSuccess(T t);
    void onRequestFailure(Throwable throwable, String message);
}