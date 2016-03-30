package pw.bits.weisper.library;

import android.text.TextUtils;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.GsonBuilder;
import com.orhanobut.hawk.Hawk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pw.bits.weisper.library.bean.Status;
import pw.bits.weisper.library.bean.Statuses;
import pw.bits.weisper.library.bean.User;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
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
        return service.usersShow(screen_name, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<User> usersShow(Long uid) {
        return service.usersShow(null, uid)
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

    private static OkHttpClient httpClient2 = new OkHttpClient.Builder()
            .followRedirects(false)
            .followSslRedirects(false)
            .build();

    private static final Pattern linkPattern = Pattern.compile("https?://[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)");

    public static Observable<String> getRedirectUrl(String url) {
        return Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        Request request = new Request.Builder()
                                .url(url)
                                .build();
                        try {
                            Response response = httpClient2.newCall(request).execute();
                            if (response.isRedirect()) {
                                subscriber.onNext(response.header("Location"));
                            }
                        } catch (IOException e) {
                            subscriber.onError(e);
                        }
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<String> convertLink() {
        return service.statusesHomeTimeline(100, 0L, 0L)
                .flatMap(statuses -> {
                    List<String> list = new ArrayList<>();
                    for (Status status : statuses.getStatuses()) {
                        Matcher matcher = linkPattern.matcher(status.text);
                        while (matcher.find()) {
                            list.add(matcher.group());
                        }
                    }
                    return Observable.from(list.toArray(new String[list.size()]));
                })
                .flatMap(WeiboData::getRedirectUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
