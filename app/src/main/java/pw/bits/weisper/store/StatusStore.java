package pw.bits.weisper.store;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pw.bits.weisper.adapter.PictureFlowAdapter;
import pw.bits.weisper.adapter.StatusFlowAdapter;
import pw.bits.weisper.event.StatusEvent;
import pw.bits.weisper.model.bean.Status;
import pw.bits.weisper.model.data.StatusData;
import pw.bits.weisper.model.bean.Statuses;
import rx.Subscriber;

/**
 * Created by rzh on 16/3/16.
 */
public class StatusStore {
    private Timer timer;
    public static StatusStore instance = new StatusStore();
    private SortedList<Status> statusSortedList = new Statuses.StatusSortedList(new StatusCallback());
    private List<RecyclerView.Adapter> adapters = new ArrayList<>();
    private long since_id = 0;
    private long max_id = 0;

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
                loadFront(null);
            }
        }, 20 * 1000, 20 * 1000);
    }

    public void loadFront(final getDataCallback callback) {
        StatusData
                .homeTimeline(since_id, 0)
                .subscribe(new Subscriber<Statuses>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Statuses statuses) {
                        Log.i("  Front", String.format("since_id: %d  max_id: %d  size: %d", statuses.since_id, statuses.max_id, statuses.statuses.size()));
                        if (since_id != 0 && statuses.max_id > since_id) {
                            Status emptyStatus = new Status();
                            emptyStatus.id = (statuses.max_id + since_id) / 2;
                            emptyStatus.idstr = String.valueOf(emptyStatus.id);
                            statusSortedList.add(emptyStatus);
                        }
                        if (statuses.since_id > since_id)
                            since_id = statuses.since_id;
                        if (max_id == 0) {
                            max_id = statuses.max_id;
                        }
                        load(statuses, callback);
                        Log.i(" #Front", String.format("since_id: %d  max_id: %d", since_id, max_id));
                    }
                });
    }

    public void loadMiddle(final Status status, final getDataCallback callback) {
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
                        Log.i(" Middle", String.format("since_id: %d  max_id: %d  size: %d", statuses.since_id, statuses.max_id, statuses.statuses.size()));
                        if (statuses.statuses.size() == 0) {
                            statusSortedList.remove(status);
                        }
                        load(statuses, callback);
                        Log.i("#Middle", String.format("since_id: %d  max_id: %d", since_id, max_id));
                    }
                });
    }

    public void loadBehind(final getDataCallback callback) {
        StatusData
                .homeTimeline(0, max_id)
                .subscribe(new Subscriber<Statuses>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Statuses statuses) {
                        Log.i(" Behind", String.format("since_id: %d  max_id: %d  size: %d", statuses.since_id, statuses.max_id, statuses.statuses.size()));
                        if (max_id == 0 || statuses.max_id < max_id)
                            max_id = statuses.max_id;
                        load(statuses, callback);
                        Log.i("#Behind", String.format("since_id: %d  max_id: %d", since_id, max_id));
                    }
                });
    }

    private void load(Statuses statuses, getDataCallback callback) {
        int previousSize = statusSortedList.size();
        statusSortedList.addAll(statuses.statuses);
        EventBus.getDefault().post(new StatusEvent(statusSortedList.size() - previousSize));
        if (callback != null) {
            callback.onDone();
        }
    }

    public static abstract class getDataCallback {
        public abstract void onDone();
    }

    public void stop() {
        timer.cancel();
        timer.purge();
    }

    public class StatusCallback extends SortedList.Callback<Status> {
        @Override
        public int compare(Status o1, Status o2) {
            return o2.id.compareTo(o1.id);
        }

        @Override
        public void onInserted(int position, int count) {
            for (RecyclerView.Adapter adapter : adapters) {
                adapter.notifyItemRangeInserted(position, count);
            }
        }

        @Override
        public void onRemoved(int position, int count) {
            for (RecyclerView.Adapter adapter : adapters) {
                adapter.notifyItemRangeRemoved(position, count);
            }
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            for (RecyclerView.Adapter adapter : adapters) {
                adapter.notifyItemMoved(fromPosition, toPosition);
            }
        }

        @Override
        public void onChanged(int position, int count) {
            for (RecyclerView.Adapter adapter : adapters) {
                adapter.notifyItemChanged(position, count);
            }
        }

        @Override
        public boolean areContentsTheSame(Status oldItem, Status newItem) {
            return oldItem.idstr.equals(newItem.idstr) &&
                    oldItem.reposts_count.equals(newItem.reposts_count) &&
                    oldItem.comments_count.equals(newItem.comments_count) &&
                    oldItem.attitudes_count.equals(newItem.attitudes_count);
        }

        @Override
        public boolean areItemsTheSame(Status item1, Status item2) {
            return item1.idstr.equals(item2.idstr);
        }
    }
}
