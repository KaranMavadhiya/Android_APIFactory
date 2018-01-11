package com.apifactory.okhttp;

public interface WSCallBack {
    void onRequestSuccess(WSResponse wsResponse);
    void onRequestFailure(int statusCode, String message);
    void onNetworkFailure(int statusCode, String message);
}
