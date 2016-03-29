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
    protected RecyclerView recyclerView;
    protected FlowAdapter adapter;

    public FlowListView(Context context) {
        super(context);
        init();
    }

    public FlowListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    protected void init() {
        recyclerView = new RecyclerView(getContext());
        FlowAdapter adapter = newFlowAdapter();
        recyclerView.setAdapter(adapter);
        FlowStatusStore.instance.bind(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        addView(recyclerView);
    }

    public void scrollToPosition(int position) {
        recyclerView.scrollToPosition(position);
    }

    public int getScrollPosition() {
        return ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }

    protected abstract FlowAdapter newFlowAdapter();
}
