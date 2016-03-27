package pw.bits.weisper.library.api;

import pw.bits.weisper.library.bean.Statuses;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by rzh on 16/3/13.
 */
public interface StatusService {
    @GET("statuses/home_timeline.json")
    Observable<Statuses> homeTimeline(
            @Query(value = "access_token") String access_token,
            @Query(value = "count") Integer count,
            @Query(value = "since_id") Long since_id,
            @Query(value = "max_id") Long max_id
    );

    @GET("statuses/user_timeline.json")
    Observable<Statuses> userTimeLine(
            @Query(value = "access_token") String access_token,
            @Query(value = "count") Integer count,
            @Query(value = "since_id") Long since_id,
            @Query(value = "max_id") Long max_id,
            @Query(value = "screen_name") String screen_name
    );

    @GET("search/topics.json")
    Observable<Statuses> searchTopics(
            @Query(value = "access_token") String access_token,
            @Query(value = "count") Integer count,
            @Query(value = "q") String topic
    );
}
