package pw.bits.weisper.view.list;

import android.content.Context;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import pw.bits.weisper.adapter.StatusFlowAdapter;
import pw.bits.weisper.model.bean.Status;
import pw.bits.weisper.model.bean.Statuses;
import pw.bits.weisper.model.data.StatusData;
import pw.bits.weisper.store.StatusStore.StatusSortedList;
import rx.Subscriber;

/**
 * Created by rzh on 16/3/24.
 */
public class TopicStatusListView extends RecyclerView {
    private SortedList<Status> statusSortedList = new StatusSortedList(new StatusCallback());
    private StatusFlowAdapter adapter;

    public TopicStatusListView(Context context) {
        super(context);
        init();
    }

    public TopicStatusListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TopicStatusListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        adapter = new StatusFlowAdapter(getContext());
        adapter.setList(statusSortedList);
        setAdapter(adapter);
        setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setTopic(String topic) {
        StatusData
                .searchTopics(topic)
                .subscribe(new Subscriber<Statuses>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Statuses statuses) {
                        statusSortedList.addAll(statuses.getStatuses());
                    }
                });
    }

    public class StatusCallback extends SortedList.Callback<Status> {
        @Override
        public int compare(Status o1, Status o2) {
            return o2.id.compareTo(o1.id);
        }

        @Override
        public void onInserted(int position, int count) {
            adapter.notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            adapter.notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            adapter.notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count) {
            adapter.notifyItemChanged(position, count);
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