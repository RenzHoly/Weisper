package pw.bits.weisper.store;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

import pw.bits.weisper.adapter.PictureFlowAdapter;
import pw.bits.weisper.adapter.StatusFlowAdapter;
import pw.bits.weisper.event.StatusEvent;
import pw.bits.weisper.model.bean.Status;
import pw.bits.weisper.model.bean.Statuses;
import pw.bits.weisper.model.data.StatusData;
import rx.Subscriber;

/**
 * Created by rzh on 16/3/16.
 */
public class FlowStatusStore extends BaseStatusStore {
    public static FlowStatusStore instance = new FlowStatusStore();
    private Timer timer;

    public void bind(StatusFlowAdapter adapter) {
        adapter.setList(statusSortedList);
        adapters.add(adapter);
    }

    public void bind(PictureFlowAdapter adapter) {
        adapter.setList(statusSortedList);
        adapters.add(adapter);
    }

    public void start() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                loadFront(new DataCallback() {
                    @Override
                    public void onDone(int count) {
                        EventBus.getDefault().post(new StatusEvent(count));
                    }
                });
            }
        }, 20 * 1000, 20 * 1000);
    }

    public void stop() {
        timer.cancel();
        timer.purge();
    }


    public void loadMiddle(final Status status, final DataCallback callback) {
        int index = statusSortedList.indexOf(status);
        StatusData
                .homeTimeline(statusSortedList.get(index + 1).id, statusSortedList.get(index - 1).id - 1)
                .subscribe(new Subscriber<Statuses>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Statuses statuses) {
                        if (statuses.getStatuses().size() == 0) {
                            statusSortedList.remove(status);
                        }
                        load(statuses, callback);
                    }
                });
    }
}