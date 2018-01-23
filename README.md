# Android API Factory Using OkHttp
This is library project for call API Synchronous and Asynchronous with the use of okhttp

## OkHttp Overview
OkHttp is a third-party library developed by Square for sending and receive HTTP-based network requests. It is built on top of the Okio library, which tries to be more efficient about reading and writing data than the standard Java I/O libraries by creating a shared memory pool. It is also the underlying library for Retrofit library that provides type safety for consuming REST-based APIs.

The OkHttp library actually provides an implementation of the HttpUrlConnection interface, which Android 4.4 and later versions now use. Therefore, when using the manual approach described in this section of the guide, the underlying HttpUrlConnection class may be leveraging code from the OkHttp library. However, there is a separate API provided by OkHttp that makes it easier to send and receive network requests, which is described in this guide.

In addition, OkHttp v2.4 also provides a more updated way of managing URLs internally. Instead of the java.net.URL, java.net.URI, or android.net.Uri classes, it provides a new HttpUrl class that makes it easier to get an HTTP port, parse URLs, and canonicalizing URL strings.



## Synchronous Network Calls

We can create a Call object and dispatch the network request synchronously:
~~~
WSResponse wsResponse = OkHttpSynchronousUtils.callSynchronousHttpPost("CONTEXT", "URL","RequestBody");
~~~
![](http://res.cloudinary.com/reyinfotech/image/upload/c_scale,q_auto:best,r_0,w_802/a_0/v1516696861/APIFactory/Screen_Shot_2018-01-23_at_2.08.48_PM.png)

Because Android disallows network calls on the main thread, you can only make synchronous calls if you do so on a separate thread or a background service. You can use also use AsyncTask for lightweight network calls.

## Asynchronous Network Calls
We can also make asynchronous network calls too by creating a Call object, using the enqueue() method, and passing an anonymous Callback object that implements WSResponse for onRequestSuccess onRequestFailure and onNetworkFailure
~~~
OkHttpAsynchronousUtils.callAsynchronousHttpPost("CONTEXT", "URL","RequestBody", new WSCallBack() {
      @Override
      public void onRequestSuccess(WSResponse wsResponse) {

      }
      @Override
      public void onRequestFailure(int statusCode, String message) {

      }
      @Override
      public void onNetworkFailure(int statusCode, String message) {

      }
   });
~~~
![](http://res.cloudinary.com/reyinfotech/image/upload/c_scale,q_100,w_802/v1516697608/APIFactory/Screen_Shot_2018-01-23_at_2.22.55_PM.png)

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
