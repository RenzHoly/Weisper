package pw.bits.weisper.view.list;

import android.content.Context;
import android.util.AttributeSet;

import pw.bits.weisper.adapter.FlowAdapter;
import pw.bits.weisper.adapter.StatusFlowAdapter;

/**
 * Created by rzh on 16/3/19.
 */
public class StatusFlowListView extends FlowListView {
    public StatusFlowListView(Context context) {
        super(context);
    }

    public StatusFlowListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected FlowAdapter newFlowAdapter() {
        return new StatusFlowAdapter(getContext());
    }
}
