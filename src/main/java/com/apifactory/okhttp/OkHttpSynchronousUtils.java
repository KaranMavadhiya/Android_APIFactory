package com.apifactory.okhttp;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.apifactory.APIFactory;
import com.apifactory.WSConstants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpSynchronousUtils {

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

    private static String TAG = OkHttpSynchronousUtils.class.getSimpleName();

    private static boolean OkHttpClientDebug = false;
    private static boolean localDebug = true;

    private static String AUTHORIZED_USER_NAME = "username";
    private static String AUTHORIZED_PASSWORD = "password";

    private static String AUTHORIZATION = "Authorization";
    private static String CREDENTIAL = Credentials.basic(AUTHORIZED_USER_NAME, AUTHORIZED_PASSWORD);


    public OkHttpSynchronousUtils() {
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
     * @param context Application Context
     * @param url     Base URL
     * @param json    JSONObject
     * @return WSResponse
     */
    public static WSResponse callSynchronousHttpPost(Context context, String url, JSONObject json) {
        return callSynchronousHttpPost(context, url, json.toString());
    }

    /**
     * @param context Application Context
     * @param url     Base URL
     * @param json    JSONArray
     * @return WSResponse
     */
    public static WSResponse callSynchronousHttpPost(Context context, String url, JSONArray json) {
        return callSynchronousHttpPost(context, url, json.toString());
    }

    /**
     * @param context Application Context
     * @param url     Base URL
     * @param json    String
     * @return WSResponse
     */
    public static WSResponse callSynchronousHttpPost(Context context, String url, String json) {
        WSResponse wsResponse = new WSResponse();

        if (!isNetworkAvailable(context)) {
            wsResponse.setStatusCode(WSConstants.error_network);
            wsResponse.setMessage(WSConstants.network_error_message);
            return wsResponse;
        }

        long startTime = System.currentTimeMillis();

        OkHttpClient client = APIFactory.getOkHttpClientInstance(context, OkHttpClientDebug);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody jsonBody = RequestBody.create(JSON, json);

        Request request = new Request.Builder().url(url).post(jsonBody).build();
        try {
            Response response = client.newCall(request).execute();
            String responseString = response.body().string();
            long endTime = System.currentTimeMillis();

            if (localDebug) {
                Log.d(TAG, "URL : " + url);
                Log.d(TAG, "Request : " + json);
                Log.d(TAG, "Response : " + responseString);
                Log.d(TAG, "Response Time  : " + (endTime - startTime) + " ms");
            }

            if (response.code() == WSConstants.success) {
                wsResponse.setStatusCode(WSConstants.success);
                wsResponse.setData(responseString);
                return wsResponse;
            } else if (response.code() == WSConstants.error_bed_request) {
                wsResponse.setStatusCode(WSConstants.error_bed_request);
                wsResponse.setMessage(WSConstants.bed_request_error_message);
                return wsResponse;
            } else if (response.code() == WSConstants.error_server) {
                wsResponse.setStatusCode(WSConstants.error_server);
                wsResponse.setMessage(WSConstants.server_error_message);
                return wsResponse;
            } else if (response.code() == WSConstants.error_request_timeout || response.code() == WSConstants.error_gateway_timeout) {
                wsResponse.setStatusCode(WSConstants.error_request_timeout);
                wsResponse.setMessage(WSConstants.time_out_error_message);
                return wsResponse;
            } else {
                wsResponse.setStatusCode(WSConstants.error_server);
                wsResponse.setMessage(WSConstants.server_error_message);
                return wsResponse;
            }
        } catch (IOException e) {
            e.printStackTrace();
            wsResponse.setStatusCode(WSConstants.error_io_exception);
            wsResponse.setMessage(WSConstants.server_error_message);
            return wsResponse;
        }
    }

    /**
     * @param context     Application Context
     * @param url         Base URL
     * @param requestBody RequestBody
     * @return WSResponse
     */
    public static WSResponse callSynchronousHttpPost(Context context, String url, RequestBody requestBody) {
        WSResponse wsResponse = new WSResponse();

        if (!isNetworkAvailable(context)) {
            wsResponse.setStatusCode(WSConstants.error_network);
            wsResponse.setMessage(WSConstants.network_error_message);
            return wsResponse;
        }

        long startTime = System.currentTimeMillis();

        OkHttpClient client = APIFactory.getOkHttpClientInstance(context, OkHttpClientDebug);

        /* You can add params into RequestBody as shown in below commented example code */
        // RequestBody formBody = new FormBody.Builder().add("key", "value").build();

        Request request = new Request.Builder().url(url).post(requestBody).build();

        try {
            Response response = client.newCall(request).execute();
            String responseString = response.body().string();
            long endTime = System.currentTimeMillis();

            if (localDebug) {
                Log.d(TAG, "URL : " + url);
                Log.d(TAG, "Request : " + requestBody.toString());
                Log.d(TAG, "Response : " + responseString);
                Log.d(TAG, "Response Time  : " + (endTime - startTime) + " ms");
            }

            if (response.code() == WSConstants.success) {
                wsResponse.setStatusCode(WSConstants.success);
                wsResponse.setData(responseString);
                return wsResponse;
            } else if (response.code() == WSConstants.error_bed_request) {
                wsResponse.setStatusCode(WSConstants.error_bed_request);
                wsResponse.setMessage(WSConstants.bed_request_error_message);
                return wsResponse;
            } else if (response.code() == WSConstants.error_server) {
                wsResponse.setStatusCode(WSConstants.error_server);
                wsResponse.setMessage(WSConstants.server_error_message);
                return wsResponse;
            } else if (response.code() == WSConstants.error_request_timeout || response.code() == WSConstants.error_gateway_timeout) {
                wsResponse.setStatusCode(WSConstants.error_request_timeout);
                wsResponse.setMessage(WSConstants.time_out_error_message);
                return wsResponse;
            } else {
                wsResponse.setStatusCode(WSConstants.error_server);
                wsResponse.setMessage(WSConstants.server_error_message);
                return wsResponse;
            }
        } catch (IOException e) {
            e.printStackTrace();
            wsResponse.setStatusCode(WSConstants.error_io_exception);
            wsResponse.setMessage(WSConstants.server_error_message);
            return wsResponse;
        }
    }

    /**
     * @param context     Application Context
     * @param url         Base URL
     * @param requestBody RequestBody
     * @return WSResponse
     */
    public static WSResponse callSynchronousHttpMultiPart(final Context context, final String url, final RequestBody requestBody) {
        WSResponse wsResponse = new WSResponse();

        if (!isNetworkAvailable(context)) {
            wsResponse.setStatusCode(WSConstants.error_network);
            wsResponse.setMessage(WSConstants.network_error_message);
            return wsResponse;
        }

        long startTime = System.currentTimeMillis();

        OkHttpClient client = APIFactory.getOkHttpClientInstance(context, OkHttpClientDebug);

        /* You can add multipart params into RequestBody as shown in below commented example code */
        // MediaType MEDIA_TYPE = MediaType.parse("image/*");
        // MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        // requestBody.addFormDataPart("KEY", "VALUE");
        // requestBody.addFormDataPart("IMAGE_KEY", "IMAGE_NAME", RequestBody.create(MEDIA_TYPE,  new File("FILE_PATH")));


        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response response = client.newCall(request).execute();
            String responseString = response.body().string();
            long endTime = System.currentTimeMillis();

            if (localDebug) {
                Log.d(TAG, "URL : " + url);
                Log.d(TAG, "Request : " + requestBody.toString());
                Log.d(TAG, "Response : " + responseString);
                Log.d(TAG, "Response Time  : " + (endTime - startTime) + " ms");
            }

            if (response.code() == WSConstants.success) {
                wsResponse.setStatusCode(WSConstants.success);
                wsResponse.setData(responseString);
                return wsResponse;
            } else if (response.code() == WSConstants.error_bed_request) {
                wsResponse.setStatusCode(WSConstants.error_bed_request);
                wsResponse.setMessage(WSConstants.bed_request_error_message);
                return wsResponse;
            } else if (response.code() == WSConstants.error_server) {
                wsResponse.setStatusCode(WSConstants.error_server);
                wsResponse.setMessage(WSConstants.server_error_message);
                return wsResponse;
            } else if (response.code() == WSConstants.error_request_timeout || response.code() == WSConstants.error_gateway_timeout) {
                wsResponse.setStatusCode(WSConstants.error_request_timeout);
                wsResponse.setMessage(WSConstants.time_out_error_message);
                return wsResponse;
            } else {
                wsResponse.setStatusCode(WSConstants.error_server);
                wsResponse.setMessage(WSConstants.server_error_message);
                return wsResponse;
            }
        } catch (IOException e) {
            e.printStackTrace();
            wsResponse.setStatusCode(WSConstants.error_io_exception);
            wsResponse.setMessage(WSConstants.server_error_message);
            return wsResponse;
        }
    }

    /**
     * @param context Application Context
     * @param url     Base URL
     * @return WSResponse
     */
    public static WSResponse callSynchronousHttpGet(final Context context, final String url) {
        WSResponse wsResponse = new WSResponse();

        if (!isNetworkAvailable(context)) {
            wsResponse.setStatusCode(WSConstants.error_network);
            wsResponse.setMessage(WSConstants.network_error_message);
            return wsResponse;
        }

        long startTime = System.currentTimeMillis();

        OkHttpClient client = APIFactory.getOkHttpClientInstance(context, OkHttpClientDebug);
        Request.Builder builder = new Request.Builder();
        builder.url(url);

        /* Add authentication header if required */
        // builder.header(AUTHORIZATION, CREDENTIAL);

        try {
            Response response = client.newCall(builder.build()).execute();
            String responseString = response.body().string();
            long endTime = System.currentTimeMillis();

            if (localDebug) {
                Log.d(TAG, "URL : " + url);
                Log.d(TAG, "Response : " + responseString);
                Log.d(TAG, "Response Time  : " + (endTime - startTime) + " ms");
            }

            if (response.code() == WSConstants.success) {
                wsResponse.setStatusCode(WSConstants.success);
                wsResponse.setData(responseString);
                return wsResponse;
            } else if (response.code() == WSConstants.error_bed_request) {
                wsResponse.setStatusCode(WSConstants.error_bed_request);
                wsResponse.setMessage(WSConstants.bed_request_error_message);
                return wsResponse;
            } else if (response.code() == WSConstants.error_server) {
                wsResponse.setStatusCode(WSConstants.error_server);
                wsResponse.setMessage(WSConstants.server_error_message);
                return wsResponse;
            } else if (response.code() == WSConstants.error_request_timeout || response.code() == WSConstants.error_gateway_timeout) {
                wsResponse.setStatusCode(WSConstants.error_request_timeout);
                wsResponse.setMessage(WSConstants.time_out_error_message);
                return wsResponse;
            } else {
                wsResponse.setStatusCode(WSConstants.error_server);
                wsResponse.setMessage(WSConstants.server_error_message);
                return wsResponse;
            }
        } catch (IOException e) {
            e.printStackTrace();
            wsResponse.setStatusCode(WSConstants.error_io_exception);
            wsResponse.setMessage(WSConstants.server_error_message);
            return wsResponse;
        }
    }
}
