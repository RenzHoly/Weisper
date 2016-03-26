package pw.bits.weisper.store;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

import pw.bits.weisper.adapter.PictureFlowAdapter;
import pw.bits.weisper.adapter.StatusFlowAdapter;
import pw.bits.weisper.event.StatusEvent;

/**
 * Created by rzh on 16/3/16.
 */
public class FlowStatusStore extends BaseStatusStore {
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
                loadFront(new getDataCallback() {
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
}
