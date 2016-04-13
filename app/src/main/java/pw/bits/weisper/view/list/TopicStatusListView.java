package pw.bits.weisper.view.list;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import pw.bits.weisper.adapter.StatusFlowAdapter;
import pw.bits.weisper.WeiboModel;
import pw.bits.weisper.store.BaseStatusStore;

/**
 * Created by rzh on 16/3/24.
 */
public class TopicStatusListView extends SwipeRefreshLayout {
    private String topic;
    private TopicStatusStore statusStore = new TopicStatusStore();

    public TopicStatusListView(Context context) {
        super(context);
        init();
    }

    public TopicStatusListView(Context context, AttributeSet attrs) {
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

        setOnRefreshListener(this::refresh);
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void refresh() {
        statusStore.loadFront(count -> setRefreshing(false));
    }

    private class TopicStatusStore extends BaseStatusStore {
        public void loadFront(DataCallback callback) {
            super.loadFront(WeiboModel.searchTopics(topic), callback);
        }
    }
}