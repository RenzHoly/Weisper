package pw.bits.weisper.library;

import java.util.List;

import pw.bits.weisper.library.bean.Status;
import pw.bits.weisper.library.bean.StatusResponse;
import pw.bits.weisper.library.bean.User;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by rzh on 16/3/14.
 */
public class WeiboModel {
    private static WeiboService service;

    public static void init(WeiboService service) {
        WeiboModel.service = service;
    }

    public static Observable<StatusResponse> statusesHomeTimeline(long since_id, long max_id) {
        return service.statusesHomeTimeline(20, since_id, max_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<StatusResponse> statusesUserTimeLine(String screen_name, long since_id, long max_id) {
        return service.statusesUserTimeLine(20, since_id, max_id, screen_name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<StatusResponse> searchTopics(String topic) {
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

    public static Observable<StatusResponse> statusesShowBatch(List<Long> ids) {
        String string = "";
        for (int i = 0; i < ids.size() - 1; i++) {
            string += ids.get(i) + ",";
        }
        if (ids.size() > 0) {
            string += ids.get(ids.size() - 1);
        }
        return service.statusesShowBatch(string)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
