package pw.bits.weisper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import pw.bits.weisper.R;
import pw.bits.weisper.view.holder.PictureFlowViewHolder;

/**
 * Created by rzh on 16/3/14.
 */
public class PictureFlowAdapter extends FlowAdapter<PictureFlowViewHolder> {
    private Map<Long, Integer> heightCache = new HashMap<>();

    public PictureFlowAdapter(Context context) {
        super(context);
    }

    @Override
    public PictureFlowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PictureFlowViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_picture_flow, parent, false), parent.getWidth());
    }

    @Override
    public void onBindViewHolder(PictureFlowViewHolder holder, int position) {
        if (heightCache.get(list.get(position).getId()) != null) {
            holder.itemView.getLayoutParams().height = heightCache.get(list.get(position).getId());
        }
        holder.bindView(list.get(position));
    }

    @Override
    public void onViewRecycled(PictureFlowViewHolder holder) {
        super.onViewRecycled(holder);
        heightCache.put(list.get(holder.getAdapterPosition()).getId(), holder.itemView.getLayoutParams().height);
    }
}
