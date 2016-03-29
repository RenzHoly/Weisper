package pw.bits.weisper.view.list;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import org.greenrobot.eventbus.EventBus;

import pw.bits.weisper.adapter.FlowAdapter;
import pw.bits.weisper.adapter.PictureFlowAdapter;
import pw.bits.weisper.event.PictureFlowPositionChangeEvent;
import pw.bits.weisper.library.bean.Status;

/**
 * Created by rzh on 16/3/19.
 */
public class PictureFlowListView extends FlowListView {
    public PictureFlowListView(Context context) {
        super(context);
    }

    public PictureFlowListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        super.init();

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
                    EventBus.getDefault().post(new PictureFlowPositionChangeEvent((Status) adapter.getList().get(linearLayoutManager.findFirstVisibleItemPosition()), false));
                }
            }
        });
    }

    @Override
    protected FlowAdapter newFlowAdapter() {
        return new PictureFlowAdapter(getContext());
    }
}
