# Android API Factory Using OkHttp, Retrofit2 and RxJava2
This is library project for call API/Webservie using OkHttp, Retrofit2 and RxJava2

## OkHttp Synchronous Network Calls
Android disallows network calls on the main thread, you can only make synchronous calls if you do so on a separate thread or a background service. You can use AsyncTask for lightweight network calls.
#### Methods
~~~
OkHttpHelper.callHttpSyncGet(android.content.Context context, java.lang.String url)
OkHttpHelper.callHttpSyncPost(android.content.Context context, java.lang.String url, org.json.JSONObject json) 
OkHttpHelper.callHttpSyncPost(android.content.Context context, java.lang.String url, org.json.JSONArray json)
OkHttpHelper.callHttpSyncPost(android.content.Context context, java.lang.String url, okhttp3.RequestBody requestBody) 
OkHttpHelper.callHttpSyncMultiPart(android.content.Context context, java.lang.String url, okhttp3.RequestBody requestBody)

// You can add params into RequestBody as shown in below example code 
RequestBody formBody = new FormBody.Builder().add("KEY", "VALUE").build();
 
// You can add multipart params into RequestBody as shown in below example code 
MediaType MEDIA_TYPE = MediaType.parse("image/*");
MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
requestBody.addFormDataPart("KEY", "VALUE");
requestBody.addFormDataPart("IMAGE_KEY", "IMAGE_NAME", RequestBody.create(MEDIA_TYPE,  new File("FILE_PATH")));
~~~

## OkHttp Asynchronous Network Calls
An asynchronous service does not wait for a response, but continues its work and handles the response later during the execution of the application. When you invoke an asynchronous service call, the user interface (UI) is not blocked, and you can perform other actions such as invoking other services such as updating data on the forms.
#### Methods
~~~
OkHttpHelper.callHttpAsyncGet(android.content.Context context, java.lang.String url, OkHttpCallback okHttpCallback) 
OkHttpHelper.callHttpAsyncPost(android.content.Context context, java.lang.String url, org.json.JSONObject json, OkHttpCallback okHttpCallback) 
OkHttpHelper.callHttpAsyncPost(android.content.Context context, java.lang.String url, org.json.JSONArray json, OkHttpCallback okHttpCallback)
OkHttpHelper.callHttpAsyncPost(android.content.Context context, java.lang.String url, okhttp3.RequestBody requestBody, OkHttpCallback okHttpCallback) 
OkHttpHelper.callHttpAsyncMultiPart(android.content.Context context, java.lang.String url, okhttp3.RequestBody requestBody, OkHttpCallback okHttpCallback) 

// You can add params into RequestBody as shown in below example code 
RequestBody formBody = new FormBody.Builder().add("KEY", "VALUE").build();
 
// You can add multipart params into RequestBody as shown in below example code 
MediaType MEDIA_TYPE = MediaType.parse("image/*");
MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
requestBody.addFormDataPart("KEY", "VALUE");
requestBody.addFormDataPart("IMAGE_KEY", "IMAGE_NAME", RequestBody.create(MEDIA_TYPE,  new File("FILE_PATH")));
~~~

## Retrofit 2 HTTP 
Retrofit is a type-safe HTTP client for Android and Java. Retrofit makes it easy to connect to a REST web service by translating the API into Java interfaces.In this tutorial, I'll show you how to call API using this library.  

Step 1: Import this library project into your project.

Step 2: Declare Host Url into gradle.properties.
~~~
HOST_URL="API_HOST_URL"
~~~
Step 3: Create APIService interface where you declare all the methods.
~~~
public interface APIService {
 @GET("users/{user}/profile")
 Call<User> getUserDetail(@Path("user") String userId);
}
~~~
Step 4: Get instance of Retrofit.
~~~
APIService service = RetroFitFactory.getInstance(this).getService(APIService.class);
~~~
Step 5: call API.
~~~
Call<User> call = service.getUserDetail("USER_ID");
call.enqueue(new Callback<CategoryResponse>() {
   @Override
   public void onResponse(Call<User> call, Response<User> response) {
         
   }
   @Override
   public void onFailure(Call<User> call, Throwable t) {
               
   }
});
~~~

## Reference
http://square.github.io/retrofit/



#### License
~~~~
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
~~~~
