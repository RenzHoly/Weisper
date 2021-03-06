package pw.bits.weisper.view.list;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import pw.bits.weisper.adapter.FlowAdapter;
import pw.bits.weisper.store.FlowStatusStore;

/**
 * Created by rzh on 16/3/30.
 */
public abstract class FlowListView extends SwipeRefreshLayout {
    protected RecyclerView recyclerView = new RecyclerView(getContext());
    protected FlowAdapter adapter = newFlowAdapter();

    public FlowListView(Context context) {
        super(context);
        init();
    }

    public FlowListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    protected void init() {
        FlowStatusStore.instance.bind(adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean isLoading = false;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!isLoading && ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition() >= recyclerView.getAdapter().getItemCount() - 5) {
                    isLoading = true;
                    FlowStatusStore.instance.loadBehind(count -> isLoading = false);
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    FlowStatusStore.instance.loadBatch(layoutManager.findFirstVisibleItemPosition(), layoutManager.findLastVisibleItemPosition(), null);
                }
            }
        });

        addView(recyclerView);

        setOnRefreshListener(() -> FlowStatusStore.instance.loadFront(count -> setRefreshing(false)));
    }

    public void scrollToPosition(int position) {
        recyclerView.scrollToPosition(position);
    }

    protected abstract FlowAdapter newFlowAdapter();

    public RecyclerView.LayoutManager getLayoutManager() {
        return recyclerView.getLayoutManager();
    }
}
