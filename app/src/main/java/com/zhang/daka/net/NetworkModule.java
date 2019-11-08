package com.zhang.daka.net;

import android.app.Application;
import android.text.TextUtils;

import com.wushiyi.mvp.MvpExtendsKt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import timber.log.Timber;

import static timber.log.Timber.d;

/**
 * Created by zhangyuncai on 2018/9/11.
 */


public final class NetworkModule {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    /**
     * volatile 修饰的成员变量在每次被线程访问时，都强迫从共享内存中重读该成员变量的值。而且，当成员变量发生变化
     * 时，强迫线程将变化值回写到共享内存。这样在任何时刻，两个不同的线程总是看到某个成员变量的同一个值。
     */
    private static volatile Retrofit retrofit;

    private NetworkModule() {

        throw new AssertionError();
    }

    public static Retrofit getRetrofit(String url) {
        if (retrofit == null) {
            HttpLoggingInterceptor httpLoggingInterceptor = providesHttpLoggingInterceptor();
            OkHttpClient okHttpClient = provideOkHttpClient(httpLoggingInterceptor, MvpExtendsKt.sApplication);
            retrofit = providesRetrofit(url,okHttpClient, MvpExtendsKt.sApplication);
        }
        return retrofit;
    }


    public static void printJson(String msg) {

        String message;

        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(4);//最重要的方法，就一行，返回格式化的json字符串，其中的数字4是缩进字符数
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(4);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        Timber.d("╔═══════════════════════════════════════════════════════════════════════════════════════");
        message = LINE_SEPARATOR + message;
        String[] lines = message.split(LINE_SEPARATOR);
        for (String line : lines) {
//            LogUtil.d("", line);
            Timber.d(line);
        }
        Timber.d("╚═══════════════════════════════════════════════════════════════════════════════════════");
    }

    @Provides
    @Singleton
    public static HttpLoggingInterceptor providesHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (!TextUtils.isEmpty(message)) {
                    String s = message.substring(0, 1);
                    //如果收到响应是json才打印
                    if ("{".equals(s) || "[".equals(s)) {
                        printJson(message);
                        return;
                    }
                }
                d("" + message);
            }
        });
//        if (GlobalValue.isDebug) {
        if (true) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return interceptor;
    }


    @Provides
    @Singleton
    public static OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor,
                                                   Application application) {
        File httpCacheDirectory = new File(application.getCacheDir(), "a5b_app_cache");
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);//10m
        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();


        okHttpBuilder.connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS);
//        okHttpBuilder.retryOnConnectionFailure(true);

        // Adds authentication headers when required in network calls
        //拦截器 日志,所有的请求响应都看到
        okHttpBuilder.addInterceptor(httpLoggingInterceptor);
        //重写拦截器  Interceptor  设置缓存
//        okHttpBuilder.addNetworkInterceptor(addCacheInterceptor(application));//自定义拦截器 网络缓存 拦截
        //错误重连
        okHttpBuilder.retryOnConnectionFailure(true);
        //okhttp 添加缓存
        okHttpBuilder.cache(cache);
        /*if (GlobalValue.isCom()) {//正式环境
            try {
                //添加https 证书
                HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(new InputStream[]{new Buffer().writeUtf8(GlobalValue.SIGN_INFO).inputStream()}, null, null);
                okHttpBuilder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
            } catch (Exception e) {
                e.printStackTrace();
            }
            okHttpBuilder.hostnameVerifier((hostname, session) -> true);
        }*/
        return okHttpBuilder.build();
    }


    private static final long TIMEOUT_CONNECT = 30;

    public static Retrofit providesRetrofit(String url,OkHttpClient okHttpClient, Application application) {
        //创建缓存文件 指定路径 缓存名称

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
//                .validateEagerly(false)// Fail early: check Retrofit configuration at creation time in Debug build.
//                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addConverterFactory(ResponseConvertFactory.create(application))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }


}