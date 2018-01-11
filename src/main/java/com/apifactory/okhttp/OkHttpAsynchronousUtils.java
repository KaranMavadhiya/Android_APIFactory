package com.apifactory.okhttp;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;

import com.apifactory.APIFactory;
import com.apifactory.WSConstants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpAsynchronousUtils {

    /*
     * Below mentioned urls are for OkHttp guidance
     * https://guides.codepath.com/android/Using-OkHttp
     * https://github.com/square/okhttp/wiki/Recipes
     */

    /**
     * build.gradle dependencies
     * compile 'com.squareup.okhttp3:okhttp:3.9.1'
     * compile 'com.squareup.okhttp3:logging-interceptor:3.9.1'
     */

    private static String TAG = OkHttpAsynchronousUtils.class.getSimpleName();

    private static boolean OkHttpClientDebug = false;
    private static boolean localDebug = true;

    private static String AUTHORIZED_USER_NAME = "username";
    private static String AUTHORIZED_PASSWORD = "password";

    private static String AUTHORIZATION = "Authorization";
    private static String CREDENTIAL = Credentials.basic(AUTHORIZED_USER_NAME, AUTHORIZED_PASSWORD);


    public OkHttpAsynchronousUtils() {
    }

    /**
     * @param context Application Context
     * @return boolean
     */
    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }

    /**
     * @param context    Application Context
     * @param url        Base URL
     * @param json       JSONObject
     * @param wsCallBack WSCallBack
     */
    public static void callAsynchronousHttpPost(Context context, String url, JSONObject json, final WSCallBack wsCallBack) {
        callAsynchronousHttpPost(context, url, json.toString(), wsCallBack);
    }

    /**
     * @param context    Application Context
     * @param url        Base URL
     * @param json       JSONObject
     * @param wsCallBack WSCallBack
     */
    public static void callAsynchronousHttpPost(Context context, String url, JSONArray json, final WSCallBack wsCallBack) {
        callAsynchronousHttpPost(context, url, json.toString(), wsCallBack);
    }

    /**
     * @param context    Application Context
     * @param url        Base URL
     * @param json       JSONObject
     * @param wsCallBack WSCallBack
     */
    public static void callAsynchronousHttpPost(Context context, String url, String json, final WSCallBack wsCallBack) {
        final WSResponse wsResponse = new WSResponse();

        if (!isNetworkAvailable(context)) {
            wsCallBack.onNetworkFailure(WSConstants.error_network, WSConstants.network_error_message);
        }

        OkHttpClient client = APIFactory.getOkHttpClientInstance(context, OkHttpClientDebug);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody jsonBody = RequestBody.create(JSON, json);

        Request request = new Request.Builder().url(url).post(jsonBody).build();

        Log.d(TAG, "URL : " + url);
        Log.d(TAG, "Request : " + json);

        // Get a handler that can be used to post to the main thread
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                wsCallBack.onRequestFailure(WSConstants.error_io_exception, WSConstants.server_error_message);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                if (response.code() == WSConstants.success) {
                    String responseString = response.body().string();
                    Log.d(TAG, "Response : " + responseString);

                    wsResponse.setStatusCode(WSConstants.success);
                    wsResponse.setData(responseString);

                    wsCallBack.onRequestSuccess(wsResponse);
                } else if (response.code() == WSConstants.error_bed_request) {
                    wsCallBack.onRequestFailure(WSConstants.error_bed_request, WSConstants.bed_request_error_message);
                } else if (response.code() == WSConstants.error_server) {
                    wsCallBack.onRequestFailure(WSConstants.error_server, WSConstants.server_error_message);
                } else if (response.code() == WSConstants.error_request_timeout || response.code() == WSConstants.error_gateway_timeout) {
                    wsCallBack.onRequestFailure(WSConstants.error_request_timeout, WSConstants.time_out_error_message);
                } else {
                    wsCallBack.onRequestFailure(WSConstants.error_server, WSConstants.server_error_message);
                }
            }
        });
    }

    /**
     * @param context     Application Context
     * @param url         Base URL
     * @param requestBody RequestBody
     * @param wsCallBack  WSCallBack
     */
    public static void callAsynchronousHttpPost(Context context, String url, RequestBody requestBody, final WSCallBack wsCallBack) {
        final WSResponse wsResponse = new WSResponse();

        if (!isNetworkAvailable(context)) {
            wsCallBack.onNetworkFailure(WSConstants.error_network, WSConstants.network_error_message);
        }

        OkHttpClient client = APIFactory.getOkHttpClientInstance(context, OkHttpClientDebug);

        /* You can add params into RequestBody as shown in below commented example code */
        // RequestBody formBody = new FormBody.Builder().add("key", "value").build();

        Request request = new Request.Builder().url(url).post(requestBody).build();

        Log.d(TAG, "URL : " + url);
        Log.d(TAG, "Request : " + requestBody.toString());

        // Get a handler that can be used to post to the main thread
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                wsCallBack.onRequestFailure(WSConstants.error_io_exception, WSConstants.server_error_message);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                if (response.code() == WSConstants.success) {
                    String responseString = response.body().string();
                    Log.d(TAG, "Response : " + responseString);

                    wsResponse.setStatusCode(WSConstants.success);
                    wsResponse.setData(responseString);

                    wsCallBack.onRequestSuccess(wsResponse);
                } else if (response.code() == WSConstants.error_bed_request) {
                    wsCallBack.onRequestFailure(WSConstants.error_bed_request, WSConstants.bed_request_error_message);
                } else if (response.code() == WSConstants.error_server) {
                    wsCallBack.onRequestFailure(WSConstants.error_server, WSConstants.server_error_message);
                } else if (response.code() == WSConstants.error_request_timeout || response.code() == WSConstants.error_gateway_timeout) {
                    wsCallBack.onRequestFailure(WSConstants.error_request_timeout, WSConstants.time_out_error_message);
                } else {
                    wsCallBack.onRequestFailure(WSConstants.error_server, WSConstants.server_error_message);
                }
            }
        });
    }

    /**
     * @param context     Application Context
     * @param url         Base URL
     * @param requestBody RequestBody
     * @param wsCallBack  WSCallBack
     */
    public static void callAsynchronousHttpMultiPart(final Context context, final String url, final RequestBody requestBody, final WSCallBack wsCallBack) {
        final WSResponse wsResponse = new WSResponse();

        if (!isNetworkAvailable(context)) {
            wsCallBack.onNetworkFailure(WSConstants.error_network, WSConstants.network_error_message);
        }

        OkHttpClient client = APIFactory.getOkHttpClientInstance(context, OkHttpClientDebug);

        /* You can add multipart params into RequestBody as shown in below commented example code */
        // MediaType MEDIA_TYPE = MediaType.parse("image/*");
        // MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        // requestBody.addFormDataPart("KEY", "VALUE");
        // requestBody.addFormDataPart("IMAGE_KEY", "IMAGE_NAME", RequestBody.create(MEDIA_TYPE,  new File("FILE_PATH")));


        Request request = new Request.Builder().url(url).post(requestBody).build();

        Log.d(TAG, "URL : " + url);
        Log.d(TAG, "Request : " + requestBody.toString());

        // Get a handler that can be used to post to the main thread
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                wsCallBack.onRequestFailure(WSConstants.error_io_exception, WSConstants.server_error_message);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                if (response.code() == WSConstants.success) {
                    String responseString = response.body().string();
                    Log.d(TAG, "Response : " + responseString);

                    wsResponse.setStatusCode(WSConstants.success);
                    wsResponse.setData(responseString);

                    wsCallBack.onRequestSuccess(wsResponse);
                } else if (response.code() == WSConstants.error_bed_request) {
                    wsCallBack.onRequestFailure(WSConstants.error_bed_request, WSConstants.bed_request_error_message);
                } else if (response.code() == WSConstants.error_server) {
                    wsCallBack.onRequestFailure(WSConstants.error_server, WSConstants.server_error_message);
                } else if (response.code() == WSConstants.error_request_timeout || response.code() == WSConstants.error_gateway_timeout) {
                    wsCallBack.onRequestFailure(WSConstants.error_request_timeout, WSConstants.time_out_error_message);
                } else {
                    wsCallBack.onRequestFailure(WSConstants.error_server, WSConstants.server_error_message);
                }
            }
        });
    }

    /**
     * @param context    Application Context
     * @param url        Base URL
     * @param wsCallBack WSCallBack
     */
    public static void callSynchronousHttpGet(final Context context, final String url, final WSCallBack wsCallBack) {
        final WSResponse wsResponse = new WSResponse();

        if (!isNetworkAvailable(context)) {
            wsCallBack.onNetworkFailure(WSConstants.error_network, WSConstants.network_error_message);
        }

        OkHttpClient client = APIFactory.getOkHttpClientInstance(context, OkHttpClientDebug);
        Request.Builder builder = new Request.Builder();
        builder.url(url);

        /* Add authentication header if required */
        // builder.header(AUTHORIZATION, CREDENTIAL);

        Log.d(TAG, "URL : " + url);

        // Get a handler that can be used to post to the main thread
        client.newCall(builder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                wsCallBack.onRequestFailure(WSConstants.error_io_exception, WSConstants.server_error_message);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                if (response.code() == WSConstants.success) {
                    String responseString = response.body().string();
                    Log.d(TAG, "Response : " + responseString);

                    wsResponse.setStatusCode(WSConstants.success);
                    wsResponse.setData(responseString);

                    wsCallBack.onRequestSuccess(wsResponse);
                } else if (response.code() == WSConstants.error_bed_request) {
                    wsCallBack.onRequestFailure(WSConstants.error_bed_request, WSConstants.bed_request_error_message);
                } else if (response.code() == WSConstants.error_server) {
                    wsCallBack.onRequestFailure(WSConstants.error_server, WSConstants.server_error_message);
                } else if (response.code() == WSConstants.error_request_timeout || response.code() == WSConstants.error_gateway_timeout) {
                    wsCallBack.onRequestFailure(WSConstants.error_request_timeout, WSConstants.time_out_error_message);
                } else {
                    wsCallBack.onRequestFailure(WSConstants.error_server, WSConstants.server_error_message);
                }
            }
        });
    }
}
