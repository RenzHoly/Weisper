package pw.bits.weisper.library;

import com.google.gson.GsonBuilder;
import com.orhanobut.hawk.Hawk;

import pw.bits.weisper.library.api.StatusService;
import pw.bits.weisper.library.bean.Statuses;
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
    private static Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                    .setDateFormat("EEE MMM dd HH:mm:ss z yyyy")
                    .create()))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .baseUrl("https://api.weibo.com/2/")
            .build();
    private static StatusService service = retrofit.create(StatusService.class);

    public static Observable<Statuses> homeTimeline(long since_id, long max_id) {
        return service.homeTimeline(Hawk.get("access-token", ""), 20, since_id, max_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Statuses> userTimeline(String screen_name, long since_id, long max_id) {
        return service.userTimeLine(Hawk.get("access-token", ""), 20, since_id, max_id, screen_name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Statuses> searchTopics(String topic) {
        return service.searchTopics(Hawk.get("access-token", ""), 50, topic)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
