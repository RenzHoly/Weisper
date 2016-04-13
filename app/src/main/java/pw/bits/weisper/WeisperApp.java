package pw.bits.weisper;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import okhttp3.OkHttpClient;
import pw.bits.weisper.data.LoginManager;
import pw.bits.weisper.data.Oauth2Interceptor;
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
                .addInterceptor(new Oauth2Interceptor(LoginManager.with(this).getAccessToken()))
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
