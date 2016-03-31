package pw.bits.weisper.library

import pw.bits.weisper.library.bean.Status
import pw.bits.weisper.library.bean.Statuses
import pw.bits.weisper.library.bean.User
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import rx.Observable

/**
 * Created by rzh on 16/3/13.
 */
interface WeiboService {
    @GET("statuses/home_timeline.json")
    fun statusesHomeTimeline(
            @Query("count") count: Int,
            @Query("since_id") since_id: Long,
            @Query("max_id") max_id: Long
    ): Observable<Statuses>

    @GET("statuses/user_timeline.json")
    fun statusesUserTimeLine(
            @Query("count") count: Int,
            @Query("since_id") since_id: Long,
            @Query("max_id") max_id: Long,
            @Query("screen_name") screen_name: String
    ): Observable<Statuses>

    @GET("search/topics.json")
    fun searchTopics(
            @Query("count") count: Int,
            @Query("q") topic: String
    ): Observable<Statuses>

    @GET("users/show.json")
    fun usersShow(
            @Query("screen_name") screen_name: String
    ): Observable<User>

    @GET("users/show.json")
    fun usersShow(
            @Query("uid") uid: Long
    ): Observable<User>

    @FormUrlEncoded
    @POST("statuses/repost.json")
    fun statusesRepost(
            @Field("id") id: Long,
            @Field("status") status: String,
            @Field("is_comment") is_comment: Int
    ): Observable<Status>

    @GET("statuses/show_batch.json")
    fun statusesShowBatch(
            @Query("ids") ids: String
    ): Observable<Statuses>
}
