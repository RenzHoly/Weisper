package pw.bits.weisper.view.list;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import org.greenrobot.eventbus.EventBus;

import pw.bits.weisper.adapter.PictureFlowAdapter;
import pw.bits.weisper.event.PictureFlowPositionChangeEvent;
import pw.bits.weisper.model.bean.Status;
import pw.bits.weisper.store.FlowStatusStore;

/**
 * Created by rzh on 16/3/19.
 */
public class PictureFlowListView extends SwipeRefreshLayout {
    public PictureFlowListView(Context context) {
        super(context);
        init(context);
    }

    public PictureFlowListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        RecyclerView recyclerView = new RecyclerView(context);
        addView(recyclerView);

        final PictureFlowAdapter adapter = new PictureFlowAdapter(getContext());
        FlowStatusStore.instance.bind(adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int previousPosition = -1;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.getChildAt(0).getTop() == 0) {
                    EventBus.getDefault().post(new PictureFlowPositionChangeEvent(true));
                    return;
                }
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int nowPosition = linearLayoutManager.findFirstVisibleItemPosition();
                if (nowPosition != previousPosition || nowPosition == 0) {
                    previousPosition = nowPosition;
                    Status status = adapter.getList().get(linearLayoutManager.findFirstVisibleItemPosition());
                    EventBus.getDefault().post(new PictureFlowPositionChangeEvent(status.user, status.text, false));
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean isLoading = false;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && lastVisibleItemPosition >= recyclerView.getAdapter().getItemCount() - 1) {
                    isLoading = true;
                    FlowStatusStore.instance.loadBehind(count -> isLoading = false);
                }
            }
        });

        setOnRefreshListener(() -> FlowStatusStore.instance.loadFront(count -> setRefreshing(false)));
    }
}
