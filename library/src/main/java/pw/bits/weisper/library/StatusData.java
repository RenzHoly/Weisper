package pw.bits.weisper.library;

import com.google.gson.GsonBuilder;
import com.orhanobut.hawk.Hawk;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import pw.bits.weisper.library.api.StatusService;
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
public class StatusData {
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
            .build();

    private static Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                    .setDateFormat("EEE MMM dd HH:mm:ss z yyyy")
                    .create()))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(httpClient)
            .baseUrl("https://api.weibo.com/2/")
            .build();

    private static StatusService service = retrofit.create(StatusService.class);

    public static Observable<Statuses> homeTimeline(long since_id, long max_id) {
        return service.homeTimeline(20, since_id, max_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Statuses> userTimeline(String screen_name, long since_id, long max_id) {
        return service.userTimeLine(20, since_id, max_id, screen_name)
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
}
