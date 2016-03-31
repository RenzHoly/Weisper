package pw.bits.weisper.store;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

import pw.bits.weisper.event.StatusEvent;
import pw.bits.weisper.library.bean.Statuses;
import pw.bits.weisper.library.WeiboModel;
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
        WeiboModel.INSTANCE
                .statusesHomeTimeline(statusSortedList.get(position + 1).getId(), statusSortedList.get(position - 1).getId() - 1)
                .subscribe(new Subscriber<Statuses>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Statuses statuses) {
                        load(statuses, callback);
                    }
                });
    }

    public void loadBatch(int start, int end, DataCallback callback) {
        long ids[] = new long[end - start + 1];
        if (ids.length < 1) {
            load(new Statuses(), callback);
            return;
        }
        for (int i = start; i <= end; i++) {
            ids[i - start] = statusSortedList.get(i).getId();
        }
        WeiboModel.INSTANCE
                .statusesShowBatch(ids)
                .subscribe(new Subscriber<Statuses>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Statuses statuses) {
                        load(statuses, callback);
                    }
                });
    }

    public void loadFront(DataCallback callback) {
        super.loadFront(WeiboModel.INSTANCE.statusesHomeTimeline(0, 0), callback);
    }

    public void loadBehind(DataCallback callback) {
        super.loadBehind(WeiboModel.INSTANCE.statusesHomeTimeline(0, max_id), callback);
    }
}
