package pw.bits.weisper.view.list;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by rzh on 16/3/30.
 */
public abstract class FlowListView extends SwipeRefreshLayout {
    protected RecyclerView recyclerView;

    public FlowListView(Context context) {
        super(context);
    }

    public FlowListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void scrollToPosition(int position) {
        recyclerView.scrollToPosition(position);
    }

    public int getScrollPosition() {
        return ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }
}
