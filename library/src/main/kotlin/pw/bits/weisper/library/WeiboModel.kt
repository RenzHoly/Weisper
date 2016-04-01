package pw.bits.weisper.library

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import com.orhanobut.hawk.Hawk
import okhttp3.Interceptor

import okhttp3.OkHttpClient
import okhttp3.Response
import pw.bits.weisper.library.bean.Status
import pw.bits.weisper.library.bean.Statuses
import pw.bits.weisper.library.bean.User
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by rzh on 16/3/14.
 */
object WeiboModel {
    private val client = OkHttpClient.Builder().addInterceptor(interceptor).addNetworkInterceptor(StethoInterceptor()).build()

    private val converter = GsonConverterFactory.create(GsonBuilder().setDateFormat("EEE MMM dd HH:mm:ss z yyyy").create());

    private val adapter = RxJavaCallAdapterFactory.create();

    private val retrofit = Retrofit.Builder().addConverterFactory(converter).addCallAdapterFactory(adapter).client(client).baseUrl("https://api.weibo.com/2/").build()

    private val service = retrofit.create(WeiboService::class.java)

    object interceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val url = original.url().newBuilder().addQueryParameter("access_token", Hawk.get("access-token", "")).build()
            val request = original.newBuilder().url(url).method(original.method(), original.body()).build()
            return chain.proceed(request)
        }
    }

    fun statusesHomeTimeline(since_id: Long, max_id: Long): Observable<Statuses> {
        return service.statusesHomeTimeline(since_id, max_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun statusesUserTimeLine(screen_name: String, since_id: Long, max_id: Long): Observable<Statuses> {
        return service.statusesUserTimeLine(since_id, max_id, screen_name).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun searchTopics(topic: String): Observable<Statuses> {
        return service.searchTopics(topic).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun usersShow(screen_name: String): Observable<User> {
        return service.usersShow(screen_name).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun usersShow(uid: Long): Observable<User> {
        return service.usersShow(uid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun statusesRepost(id: Long, status: String, is_comment: Boolean): Observable<Status> {
        return service.statusesRepost(id, status, if (is_comment) 1 else 0).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun statusesShowBatch(ids: List<Long>): Observable<Statuses> {
        return service.statusesShowBatch(ids.joinToString(",")).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}
