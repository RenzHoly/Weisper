package pw.bits.weisper.library;

import pw.bits.weisper.library.bean.Status;
import pw.bits.weisper.library.bean.Statuses;
import pw.bits.weisper.library.bean.User;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by rzh on 16/3/13.
 */
public interface WeiboService {
    @GET("statuses/home_timeline.json")
    Observable<Statuses> statusesHomeTimeline(
            @Query(value = "count") Integer count,
            @Query(value = "since_id") Long since_id,
            @Query(value = "max_id") Long max_id
    );

    @GET("statuses/user_timeline.json")
    Observable<Statuses> statusesUserTimeLine(
            @Query(value = "count") Integer count,
            @Query(value = "since_id") Long since_id,
            @Query(value = "max_id") Long max_id,
            @Query(value = "screen_name") String screen_name
    );

    @GET("search/topics.json")
    Observable<Statuses> searchTopics(
            @Query(value = "count") Integer count,
            @Query(value = "q") String topic
    );

    @GET("users/show.json")
    Observable<User> usersShow(
            @Query(value = "screen_name") String screen_name,
            @Query(value = "uid") Long uid
    );

    @POST("statuses/repost.json")
    Observable<Status> statusesRepost(
            @Query(value = "id") Long id,
            @Query(value = "status") String status,
            @Query(value = "is_comment") Boolean is_comment
    );
}
