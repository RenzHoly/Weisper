package pw.bits.weisper.library;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.GsonBuilder;
import com.orhanobut.hawk.Hawk;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import pw.bits.weisper.library.bean.Status;
import pw.bits.weisper.library.bean.Statuses;
import pw.bits.weisper.library.bean.User;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by rzh on 16/3/14.
 */
public class WeiboData {
    private static OkHttpClient httpClient = new OkHttpClient.Builder()
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

    private static Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                    .setDateFormat("EEE MMM dd HH:mm:ss z yyyy")
                    .create()))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(httpClient)
            .baseUrl("https://api.weibo.com/2/")
            .build();

    private static WeiboService service = retrofit.create(WeiboService.class);

    public static Observable<Statuses> statusesHomeTimeline(long since_id, long max_id) {
        return service.statusesHomeTimeline(20, since_id, max_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Statuses> statusesUserTimeLine(String screen_name, long since_id, long max_id) {
        return service.statusesUserTimeLine(20, since_id, max_id, screen_name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Statuses> searchTopics(String topic) {
        return service.searchTopics(50, topic)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<User> usersShow(String screen_name) {
        return service.usersShow(screen_name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<User> usersShow(Long uid) {
        return service.usersShow(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Status> statusesRepost(Long id, String status, Boolean is_comment) {
        return service.statusesRepost(id, status, is_comment ? 1 : 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Statuses> statusesShowBatch(long[] ids) {
        String string = "";
        for (int i = 0; i < ids.length - 1; i++) {
            string += ids[i] + ",";
        }
        string += ids[ids.length - 1];
        return service.statusesShowBatch(string)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
