package pw.bits.weisper.view.list;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import pw.bits.weisper.adapter.StatusFlowAdapter;
import pw.bits.weisper.library.WeiboData;
import pw.bits.weisper.store.BaseStatusStore;

/**
 * Created by rzh on 16/3/24.
 */
public class UserStatusListView extends SwipeRefreshLayout {
    private String screen_name;
    private UserStatusStore statusStore = new UserStatusStore();

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

        StatusFlowAdapter adapter = new StatusFlowAdapter(getContext());
        statusStore.bind(adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean isLoading = false;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoading && ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition() >= recyclerView.getAdapter().getItemCount() - 1) {
                    isLoading = true;
                    statusStore.loadBehind(count -> isLoading = false);
                }
            }
        });

        setOnRefreshListener(this::refresh);
    }

    public void refresh() {
        statusStore.loadFront(count -> setRefreshing(false));
    }

    public void setUserName(String screen_name) {
        this.screen_name = screen_name;
    }

    private class UserStatusStore extends BaseStatusStore {
        public void loadBehind(DataCallback callback) {
            super.loadBehind(WeiboData.statusesUserTimeLine(screen_name, 0, max_id), callback);
        }

        public void loadFront(DataCallback callback) {
            super.loadFront(WeiboData.statusesUserTimeLine(screen_name, since_id, 0), callback);
        }
    }
}
