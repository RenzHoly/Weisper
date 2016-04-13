package pw.bits.weisper;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.GsonBuilder;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import pw.bits.weisper.data.WeiboModel;
import pw.bits.weisper.data.WeiboService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rzh on 16/3/27.
 */
public class WeisperApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
        Logger.init().logLevel(LogLevel.FULL);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    HttpUrl url = original.url().newBuilder()
                            .addQueryParameter("access_token", Hawk.get("access-token", ""))
                            .build();
                    Request request = original.newBuilder()
                            .url(url)
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                })
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
         Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setDateFormat("EEE MMM dd HH:mm:ss z yyyy")
                        .create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient)
                .baseUrl("https://api.weibo.com/2/")
                .build();
        WeiboModel.init(retrofit.create(WeiboService.class));
    }
}
