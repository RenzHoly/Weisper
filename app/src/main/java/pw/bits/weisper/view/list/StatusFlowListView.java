package pw.bits.weisper.view.list;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import pw.bits.weisper.adapter.StatusFlowAdapter;
import pw.bits.weisper.store.FlowStatusStore;

/**
 * Created by rzh on 16/3/19.
 */
public class StatusFlowListView extends FlowListView {
    public StatusFlowListView(Context context) {
        super(context);
        init(context);
    }

    public StatusFlowListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        recyclerView = new RecyclerView(context);
        addView(recyclerView);

        StatusFlowAdapter adapter = new StatusFlowAdapter(getContext());
        FlowStatusStore.instance.bind(adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean isLoading = false;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoading && ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition() >= recyclerView.getAdapter().getItemCount() - 1) {
                    isLoading = true;
                    FlowStatusStore.instance.loadBehind(count -> isLoading = false);
                }
            }
        });

        setOnRefreshListener(() -> FlowStatusStore.instance.loadFront(count -> setRefreshing(false)));
    }

    public void scrollToTop() {
        scrollToPosition(0);
    }
}
