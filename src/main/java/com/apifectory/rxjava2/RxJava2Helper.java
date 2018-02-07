package com.apifectory.rxjava2;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.apifectory.WSConstants;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public final class RxJava2Helper {

    private RxJava2Helper() {

    }

    /**
     * @param observable         Observable
     * @param rxJava2ApiCallback API callback
     * @param <T>                Consumer
     * @return RxJava2Callback
     */
    public static <T> Disposable call(Observable<T> observable, final RxJava2Callback<T> rxJava2ApiCallback) {

        if (observable == null) {
            throw new IllegalArgumentException("Observable must not be null.");
        }

        if (rxJava2ApiCallback == null) {
            throw new IllegalArgumentException("Callback must not be null.");
        }

        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(@NonNull T t) {
                        rxJava2ApiCallback.onRequestSuccess(t);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) {
                        if (throwable != null) {
                            rxJava2ApiCallback.onRequestFailure(throwable, WSConstants.server_error_message);
                        } else {
                            rxJava2ApiCallback.onRequestFailure(new Exception(WSConstants.server_error_message), WSConstants.server_error_message);
                        }
                    }
                });
    }
}
