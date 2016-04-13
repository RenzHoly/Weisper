package pw.bits.weisper.library;

import pw.bits.weisper.library.bean.Status;
import pw.bits.weisper.library.bean.StatusResponse;
import pw.bits.weisper.library.bean.User;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by rzh on 16/3/13.
 */
public interface WeiboService {
    @GET("statuses/home_timeline.json")
    Observable<StatusResponse> statusesHomeTimeline(
            @Query("count") Integer count,
            @Query("since_id") Long since_id,
            @Query("max_id") Long max_id
    );

    @GET("statuses/user_timeline.json")
    Observable<StatusResponse> statusesUserTimeLine(
            @Query("count") Integer count,
            @Query("since_id") Long since_id,
            @Query("max_id") Long max_id,
            @Query("screen_name") String screen_name
    );

    @GET("search/topics.json")
    Observable<StatusResponse> searchTopics(
            @Query("count") Integer count,
            @Query("q") String topic
    );

    @GET("users/show.json")
    Observable<User> usersShow(
            @Query("screen_name") String screen_name,
            @Query("uid") Long uid
    );

    @FormUrlEncoded
    @POST("statuses/repost.json")
    Observable<Status> statusesRepost(
            @Field("id") Long id,
            @Field("status") String status,
            @Field("is_comment") Integer is_comment
    );

    @GET("statuses/show_batch.json")
    Observable<StatusResponse> statusesShowBatch(
            @Query("ids") String ids
    );
}
