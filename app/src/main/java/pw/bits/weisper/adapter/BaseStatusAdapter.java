package pw.bits.weisper.adapter;

import android.content.Context;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;

import pw.bits.weisper.library.bean.Status;

/**
 * Created by rzh on 16/3/27.
 */
public abstract class BaseStatusAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
    protected Context context;
    protected SortedList<Status> list;

    public BaseStatusAdapter(Context context) {
        this.context = context;
    }

    public void setList(SortedList<Status> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
