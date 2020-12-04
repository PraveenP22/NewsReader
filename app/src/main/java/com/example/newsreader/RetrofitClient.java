package com.example.newsreader;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static androidx.core.content.ContextCompat.getCodeCacheDir;
import static androidx.core.content.ContextCompat.getSystemService;
import static com.example.newsreader.Api.BASE_URL;

public class RetrofitClient {

    private static Context context;

    private static RetrofitClient instance = null;
    private Api myApi;

    private RetrofitClient() {
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//
//        int cacheSize = 10 * 1024 * 1024; // 10 MB
//        Cache cache = new Cache(context.getCacheDir(), cacheSize);
//
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                // .addInterceptor(provideHttpLoggingInterceptor()) // For HTTP request & Response data logging
//                .addInterceptor(offlineInterceptor)
//                .addNetworkInterceptor(onlineInterceptor)
//                .cache(cache)
//                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi = retrofit.create(Api.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }



    public Api getMyApi() {
        return myApi;
    }




//    private static Interceptor onlineInterceptor = new Interceptor() {
//        @Override
//        public okhttp3.Response intercept(Chain chain) throws IOException {
//            okhttp3.Response response = chain.proceed(chain.request());
//            int maxAge = 60; // read from cache for 60 seconds even if there is internet connection
//            return response.newBuilder()
//                    .header("Cache-Control", String.format("max-age=%d", 50000))
//                    .removeHeader("Pragma")
//                    .build();
//        }
//    };
//
//
//    private static Interceptor offlineInterceptor= new Interceptor() {
//        @Override
//        public okhttp3.Response intercept(Chain chain) throws IOException {
//            Request request = chain.request();
//            if (!!isNetworkAvailable()) {
//                int maxStale = 60 * 60 * 24 * 30; // Offline cache available for 30 days
//                request = request.newBuilder()
//                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                        .removeHeader("Pragma")
//                        .build();
//            }
//            return chain.proceed(request);
//        }
//    };
//
//    private static boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService( CONNECTIVITY_SERVICE );
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }


}





