package pw.bits.weisper.store;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pw.bits.weisper.adapter.BaseStatusAdapter;
import pw.bits.weisper.model.bean.Status;
import pw.bits.weisper.model.bean.Statuses;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by rzh on 16/3/27.
 */
public class BaseStatusStore {
    protected SortedList<Status> statusSortedList = new StatusSortedList(new StatusCallback());
    protected List<BaseStatusAdapter> adapters = new ArrayList<>();
    protected long since_id = 0;
    protected long max_id = 0;

    public void bind(BaseStatusAdapter adapter) {
        adapter.setList(statusSortedList);
        adapters.add(adapter);
    }

    protected void loadFront(Observable<Statuses> observable, DataCallback callback) {
        observable.subscribe(new Subscriber<Statuses>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Statuses statuses) {
                if (since_id != 0 && statuses.getMaxId() > since_id) {
                    Status emptyStatus = new Status();
                    emptyStatus.id = (statuses.getMaxId() + since_id) / 2;
                    emptyStatus.idstr = String.valueOf(emptyStatus.id);
                    statusSortedList.add(emptyStatus);
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

    public void loadBehind(Observable<Statuses> observable, DataCallback callback) {
        observable.subscribe(new Subscriber<Statuses>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Statuses statuses) {
                if (max_id == 0 || statuses.getMaxId() < max_id)
                    max_id = statuses.getMaxId();
                load(statuses, callback);
            }
        });
    }

    protected void load(Statuses statuses, DataCallback callback) {
        int previousSize = statusSortedList.size();
        statusSortedList.addAll(statuses.getStatuses());
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

    public static class StatusSortedList extends SortedList<Status> {
        public StatusSortedList(Callback<Status> callback) {
            super(Status.class, callback);
        }
    }
}
