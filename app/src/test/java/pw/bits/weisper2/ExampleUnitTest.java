package pw.bits.weisper2;

import com.google.gson.GsonBuilder;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pw.bits.weisper.data.Oauth2Interceptor;
import pw.bits.weisper.data.WeiboService;
import pw.bits.weisper.data.bean.Status;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void Rx() throws Exception {
        CountDownLatch lock = new CountDownLatch(1);
        Pattern linkPattern = Pattern.compile("https?://t\\.cn/\\w+");

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Oauth2Interceptor("2.00eIAEkF06XASOaedab7fa31SZqCVE"))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setDateFormat("EEE MMM dd HH:mm:ss z yyyy")
                        .create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .baseUrl("https://api.weibo.com/2/")
                .build();
        WeiboService service = retrofit.create(WeiboService.class);

        service.statusesHomeTimeline(50, 0L, 0L)
                .flatMap(statusResponse -> Observable.from(statusResponse.getItems()))
                .map(Status::getText)
                .map(text -> {
                    List<String> urls = new ArrayList<>();
                    Matcher link = linkPattern.matcher(text);
                    while (link.find()) {
                        urls.add(link.group());
                    }
                    return urls;
                })
                .flatMap(urls -> Observable.from(urls).flatMap(this::getOriginUrl))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.immediate())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        lock.countDown();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println(s);
                    }
                });
        lock.await();
    }

    public Observable<String> getOriginUrl(String shortUrl) {
        return Observable.create((Observable.OnSubscribe<String>) subscriber -> new OkHttpClient.Builder()
                .followRedirects(false)
                .followSslRedirects(false)
                .build()
                .newCall(new Request.Builder().url(shortUrl).build()).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        subscriber.onNext(response.header("Location"));
                        subscriber.onCompleted();
                    }
                }))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.immediate());
    }
}