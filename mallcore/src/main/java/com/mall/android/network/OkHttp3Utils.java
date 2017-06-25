package com.mall.android.network;

import android.content.Context;
import android.os.Handler;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.mall.android.common.utils.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttp3Utils {

	private static final String TAG = OkHttp3Utils.class.getSimpleName();

	private volatile static OkHttp3Utils mInstance;

	private OkHttpClient mOkHttpClient;

	private Handler mHandler;

	private Context mContext;

	private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

	private static final String error_0 = "数据解析报错";
	private static final String error_1 = "网络返回失败";

	private OkHttp3Utils(Context context) {
		super();
		OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();
		clientBuilder.readTimeout(30, TimeUnit.SECONDS);
		clientBuilder.connectTimeout(15, TimeUnit.SECONDS);
		clientBuilder.writeTimeout(60, TimeUnit.SECONDS);
		clientBuilder.addInterceptor(new LoggingInterceptor());
//		clientBuilder.addNetworkInterceptor(new StethoInterceptor());
		mOkHttpClient = clientBuilder.build();
		this.mContext = context.getApplicationContext();
		mHandler = new Handler(mContext.getMainLooper());
	}

	public static OkHttp3Utils getInstance(Context context) {
		OkHttp3Utils temp = mInstance;
		if (temp == null) {
			synchronized (OkHttp3Utils.class) {
				temp = mInstance;
				if (temp == null) {
					temp = new OkHttp3Utils(context);
					mInstance = temp;
				}
			}
		}
		return temp;
	}

	class LoggingInterceptor implements Interceptor{

		@Override
		public Response intercept(Chain chain) throws IOException {

			Request request = chain.request();

			long t1 = System.nanoTime();
			Logger.d(TAG, String.format("Sending request %s on %s%n%s",
					request.url(), chain.connection(), request.headers()));

			Response response = chain.proceed(request);

			long t2 = System.nanoTime();
			Logger.d(TAG, String.format("Received response for %s in %.1fms%n%s",
					response.request().url(), (t2 - t1) / 1e6d, response.headers()));
			return response;
		}
	}

	/**
	 * 设置请求头
	 * 
	 * @param headersParams
	 * @return
	 */
	private Headers setHeaders(Map<String, String> headersParams) {
		Headers headers = null;
		Headers.Builder headersbuilder = new Headers.Builder();
		if (headersParams != null) {
			Iterator<String> iterator = headersParams.keySet().iterator();
			String key = "";
			while (iterator.hasNext()) {
				key = iterator.next().toString();
				headersbuilder.add(key, headersParams.get(key));
			}
		}
		headers = headersbuilder.build();
		return headers;
	}

	/**
	 * post请求参数
	 * 
	 * @param BodyParams
	 * @return
	 */
	private RequestBody setPostRequestBody(Map<String, String> BodyParams) {
		RequestBody body = null;
		FormBody.Builder formEncodingBuilder = new FormBody.Builder();
		if (BodyParams != null) {
			Iterator<String> iterator = BodyParams.keySet().iterator();
			String key = "";
			while (iterator.hasNext()) {
				key = iterator.next().toString();
				formEncodingBuilder.add(key, BodyParams.get(key));
			}
		}
		body = formEncodingBuilder.build();
		return body;
	}

	/**
	 * Post上传图片的参数
	 * @param BodyParams
	 * @param filePathParams
     * @return
     */
	private RequestBody setFileRequestBody(Map<String, String> BodyParams, Map<String, String> filePathParams) {
		// 带文件的Post参数
		RequestBody body = null;
		MultipartBody.Builder MultipartBodyBuilder = new MultipartBody.Builder();
		MultipartBodyBuilder.setType(MultipartBody.FORM);
		RequestBody fileBody = null;
		if (BodyParams != null) {
			Iterator<String> iterator = BodyParams.keySet().iterator();
			String key = "";
			while (iterator.hasNext()) {
				key = iterator.next().toString();
				MultipartBodyBuilder.addFormDataPart(key, BodyParams.get(key));
			}
		}
		if (filePathParams != null) {
			Iterator<String> iterator = filePathParams.keySet().iterator();
			String key = "";
			int i = 0;
			while (iterator.hasNext()) {
				key = iterator.next().toString();
				i++;
				MultipartBodyBuilder.addFormDataPart(key, filePathParams.get(key));
				fileBody = RequestBody.create(MEDIA_TYPE_PNG, new File(filePathParams.get(key)));
				MultipartBodyBuilder.addFormDataPart(key, i + ".png", fileBody);
			}
		}
		body = MultipartBodyBuilder.build();
		return body;
	}

	/**
	 * get方法连接拼加参数
	 * 
	 * @param mapParams
	 * @return
	 */
	private String setGetUrlParams(Map<String, String> mapParams) {
		String strParams = "";
		if (mapParams != null) {
			Iterator<String> iterator = mapParams.keySet().iterator();
			String key = "";
			while (iterator.hasNext()) {
				key = iterator.next().toString();
				strParams += "&" + key + "=" + mapParams.get(key);
			}
		}
		return strParams;
	}

	/**
	 * 实现post请求
	 * 
	 * @param reqUrl
	 * @param headersParams
	 * @param params
	 * @param callback
	 */
	public void doPost(String reqUrl, Map<String, String> headersParams, Map<String, String> params, final NetCallback callback) {
		Request.Builder RequestBuilder = new Request.Builder();
		RequestBuilder.url(reqUrl);// 添加URL地址
		RequestBuilder.method("POST", setPostRequestBody(params));
		RequestBuilder.headers(setHeaders(headersParams));// 添加请求头
		Request request = RequestBuilder.build();
		mOkHttpClient.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call call, final Response response) throws IOException {
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						if (response.isSuccessful()){
							try {
								String responseStr = response.body().string();
								Logger.d(TAG, responseStr);
								callback.onSuccess(0, responseStr);
							} catch (IOException e) {
								e.printStackTrace();
								callback.onFailure(-1, error_0);
							}finally {
								call.cancel();
							}

						}else {
							callback.onFailure(-1, error_1);
							call.cancel();
						}
					}
				});
			}

			@Override
			public void onFailure(final Call call, final IOException exception) {
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						callback.onFailure(-1, exception.getMessage());
						call.cancel();
					}
				});
			}
		});
	}

	/**
	 * 实现get请求
	 * 
	 * @param reqUrl
	 * @param headersParams
	 * @param params
	 * @param callback
	 */
	public void doGet(String reqUrl, Map<String, String> headersParams, Map<String, String> params, final NetCallback callback) {
		Request.Builder RequestBuilder = new Request.Builder();
		RequestBuilder.url(reqUrl + setGetUrlParams(params));
		RequestBuilder.headers(setHeaders(headersParams));
		Request request = RequestBuilder.build();
		mOkHttpClient.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call call, final Response response) throws IOException {
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						if (response.isSuccessful()){
							try {
								String responseStr = response.body().string();
								Logger.d(TAG, responseStr);
								callback.onSuccess(0, responseStr);
							} catch (IOException e) {
								e.printStackTrace();
								callback.onFailure(-1, error_0);
							}finally {
								call.cancel();
							}

						}else {
							callback.onFailure(-1, error_1);
							call.cancel();
						}
					}
				});
			}

			@Override
			public void onFailure(final Call call, final IOException exception) {
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						callback.onFailure(-1, exception.getMessage());
						call.cancel();
					}
				});
			}
		});
	}

	/**
	 * 取消所有请求
	 */
	public void CancelAllRequest(){
		mOkHttpClient.dispatcher().cancelAll();
	}
}