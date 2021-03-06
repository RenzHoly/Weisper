package pw.bits.weisper.store;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pw.bits.weisper.event.StatusEvent;
import pw.bits.weisper.data.bean.StatusResponse;
import pw.bits.weisper.data.WeiboModel;
import rx.Subscriber;

/**
 * Created by rzh on 16/3/16.
 */
public class FlowStatusStore extends BaseStatusStore {
    public static FlowStatusStore instance = new FlowStatusStore();
    private Timer timer;

    public void start() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                loadFront(count -> EventBus.getDefault().post(new StatusEvent(count)));
            }
        }, 20 * 1000, 20 * 1000);
    }

    public void stop() {
        timer.cancel();
        timer.purge();
    }

    public void loadMiddle(int position, DataCallback callback) {
        if (position + 1 >= statusSortedList.size() || position - 1 < 0) {
            return;
        }
        WeiboModel
                .statusesHomeTimeline(statusSortedList.get(position + 1).getId(), statusSortedList.get(position - 1).getId() - 1)
                .subscribe(new Subscriber<StatusResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(StatusResponse statuses) {
                        load(statuses, callback);
                    }
                });
    }

    public void loadBatch(int start, int end, DataCallback callback) {
        List<Long> ids = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (i >= 0 && i < statusSortedList.size() && !statusSortedList.get(i).isFake()) {
                ids.add(statusSortedList.get(i).getId());
            }
        }
        WeiboModel
                .statusesShowBatch(ids)
                .subscribe(new Subscriber<StatusResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(StatusResponse statuses) {
                        load(statuses, callback);
                    }
                });
    }

    public void loadFront(DataCallback callback) {
        super.loadFront(WeiboModel.statusesHomeTimeline(0, 0), callback);
    }

    public void loadBehind(DataCallback callback) {
        super.loadBehind(WeiboModel.statusesHomeTimeline(0, max_id), callback);
    }
}
