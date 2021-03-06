package pw.bits.weisper.store;

import android.support.annotation.Nullable;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pw.bits.weisper.adapter.FlowAdapter;
import pw.bits.weisper.data.bean.Status;
import pw.bits.weisper.data.bean.StatusResponse;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by rzh on 16/3/27.
 */
public class BaseStatusStore {
    protected SortedList<Status> statusSortedList = new StatusSortedList(new StatusCallback());
    protected List<FlowAdapter> adapters = new ArrayList<>();
    protected long since_id = 0;
    protected long max_id = 0;

    public void bind(FlowAdapter adapter) {
        adapter.setList(statusSortedList);
        adapters.add(adapter);
    }

    protected void loadFront(Observable<StatusResponse> observable, DataCallback callback) {
        observable.subscribe(new Subscriber<StatusResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(StatusResponse statuses) {
                if (since_id != 0 && statuses.getMaxId() > since_id) {
                    statusSortedList.add(new Status((statuses.getMaxId() + since_id) / 2));
                }
                if (statuses.getSinceId() > since_id)
                    since_id = statuses.getSinceId();
                if (max_id == 0) {
                    max_id = statuses.getMaxId();
                }
                load(statuses, callback);
            }
        });
    }

    protected void loadBehind(Observable<StatusResponse> observable, DataCallback callback) {
        observable.subscribe(new Subscriber<StatusResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(StatusResponse statuses) {
                if (max_id == 0 || statuses.getMaxId() < max_id)
                    max_id = statuses.getMaxId();
                load(statuses, callback);
            }
        });
    }

    protected void load(StatusResponse statuses, @Nullable DataCallback callback) {
        int previousSize = statusSortedList.size();
        statusSortedList.addAll(statuses.getItems());
        if (callback != null) {
            callback.onDone(statusSortedList.size() - previousSize);
        }
    }

    public interface DataCallback {
        void onDone(int count);
    }

    public class StatusCallback extends SortedList.Callback<Status> {
        @Override
        public int compare(Status o1, Status o2) {
            return Long.compare(o2.getId(), o1.getId());
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
            return oldItem.getId() == newItem.getId() &&
                    oldItem.getReposts() == newItem.getReposts() &&
                    oldItem.getComments() == newItem.getComments() &&
                    oldItem.getAttitudes() == newItem.getAttitudes() &&
                    oldItem.isFake() == newItem.isFake();
        }

        @Override
        public boolean areItemsTheSame(Status item1, Status item2) {
            return item1.getId() == item2.getId() &&
                    item1.isFake() == item2.isFake();
        }
    }

    public static class StatusSortedList extends SortedList<Status> {
        public StatusSortedList(Callback<Status> callback) {
            super(Status.class, callback);
        }
    }
}
