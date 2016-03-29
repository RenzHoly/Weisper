package pw.bits.weisper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import pw.bits.weisper.R;
import pw.bits.weisper.view.holder.StatusAbstractViewHolder;
import pw.bits.weisper.view.holder.StatusLoadViewHolder;
import pw.bits.weisper.view.holder.StatusNormalViewHolder;

/**
 * Created by rzh on 16/3/13.
 */
public class StatusFlowAdapter extends FlowAdapter<StatusAbstractViewHolder> {
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_LOAD = 1;

    public StatusFlowAdapter(Context context) {
        super(context);
    }

    @Override
    public StatusAbstractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_NORMAL:
                return new StatusNormalViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_status_flow_normal, parent, false), parent.getWidth());
            case TYPE_LOAD:
                return new StatusLoadViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_status_flow_load, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(StatusAbstractViewHolder holder, int position) {
        holder.bindView(list.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).mid == null ? TYPE_LOAD : TYPE_NORMAL;
    }
}
