package pw.bits.weisper.view.list;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import pw.bits.weisper.adapter.StatusFlowAdapter;
import pw.bits.weisper.model.bean.Status;
import pw.bits.weisper.model.bean.Statuses;
import pw.bits.weisper.model.data.StatusData;
import rx.Subscriber;

/**
 * Created by rzh on 16/3/24.
 */
public class UserStatusListView extends SwipeRefreshLayout {
    private SortedList<Status> statusSortedList = new Statuses.StatusSortedList(new StatusCallback());
    private StatusFlowAdapter adapter;
    private long since_id = 0;
    private long max_id = 0;
    private String screen_name;

    public UserStatusListView(Context context) {
        super(context);
        init();
    }

    public UserStatusListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        RecyclerView recyclerView = new RecyclerView(getContext());
        addView(recyclerView);

        adapter = new StatusFlowAdapter(getContext());
        adapter.setList(statusSortedList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean isLoading = false;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoading && ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition() >= recyclerView.getAdapter().getItemCount() - 1) {
                    isLoading = true;
                    loadBehind();
                }
            }

            private void loadBehind() {
                StatusData
                        .userTimeline(screen_name, 0, max_id)
                        .subscribe(new Subscriber<Statuses>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Statuses statuses) {
                                isLoading = false;
                                Log.i(" Behind", String.format("since_id: %d  max_id: %d  size: %d", statuses.since_id, statuses.max_id, statuses.statuses.size()));
                                long new_max_id = statuses.statuses.get(statuses.statuses.size() - 1).id;
                                if (max_id == 0 || new_max_id < max_id)
                                    max_id = new_max_id;
                                load(statuses);
                                Log.i("#Behind", String.format("since_id: %d  max_id: %d", since_id, max_id));
                            }
                        });
            }
        });

        setOnRefreshListener(() -> loadFront());
    }

    public void loadFront() {
        StatusData
                .userTimeline(screen_name, since_id, 0)
                .subscribe(new Subscriber<Statuses>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Statuses statuses) {
                        setRefreshing(false);
                        Log.i("  Front", String.format("since_id: %d  max_id: %d  size: %d", statuses.since_id, statuses.max_id, statuses.statuses.size()));
                        long new_since_id = statuses.statuses.get(0).id;
                        if (new_since_id > since_id)
                            since_id = new_since_id;
                        long new_max_id = statuses.statuses.get(statuses.statuses.size() - 1).id;
                        if (max_id == 0) {
                            max_id = new_max_id;
                        }
                        load(statuses);
                        Log.i(" #Front", String.format("since_id: %d  max_id: %d", since_id, max_id));
                    }
                });
    }

    private void load(Statuses statuses) {
        statusSortedList.addAll(statuses.statuses);
    }

    public void setUserName(String screen_name) {
        this.screen_name = screen_name;
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