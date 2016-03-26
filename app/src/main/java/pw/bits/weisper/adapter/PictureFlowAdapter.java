package pw.bits.weisper.adapter;

import android.content.Context;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import pw.bits.weisper.R;
import pw.bits.weisper.model.bean.Status;
import pw.bits.weisper.view.holder.PictureFlowViewHolder;

/**
 * Created by rzh on 16/3/14.
 */
public class PictureFlowAdapter extends RecyclerView.Adapter<PictureFlowViewHolder> {
    private Context context;
    private SortedList<Status> list;
    private Map<Long, Integer> heightCache = new HashMap<>();

    public PictureFlowAdapter(Context context) {
        this.context = context;
    }

    public void setList(SortedList<Status> list) {
        this.list = list;
    }

    public SortedList<Status> getList() {
        return list;
    }

    @Override
    public PictureFlowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PictureFlowViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_picture_flow, parent, false), parent.getWidth());
    }

    @Override
    public void onBindViewHolder(PictureFlowViewHolder holder, int position) {
        if (heightCache.get(list.get(position).id) != null) {
            holder.itemView.getLayoutParams().height = heightCache.get(list.get(position).id);
        }
        holder.bindView(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onViewRecycled(PictureFlowViewHolder holder) {
        super.onViewRecycled(holder);
        heightCache.put(list.get(holder.getAdapterPosition()).id, holder.itemView.getLayoutParams().height);
    }
}