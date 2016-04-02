package pw.bits.weisper.adapter;

import android.content.Context;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import pw.bits.weisper.event.RemoveLoadViewHolderEvent;
import pw.bits.weisper.library.bean.Status;
import pw.bits.weisper.view.holder.StatusAbstractViewHolder;

/**
 * Created by rzh on 16/3/27.
 */
public abstract class FlowAdapter<T extends StatusAbstractViewHolder> extends RecyclerView.Adapter<T> {
    protected Context context;
    protected SortedList<Status> list;

    public FlowAdapter(Context context) {
        this.context = context;
        EventBus.getDefault().register(this);
    }

    public void setList(SortedList<Status> list) {
        this.list = list;
    }

    public SortedList<Status> getList() {
        return list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Subscribe
    void onEvent(RemoveLoadViewHolderEvent event) {
        list.remove(event.getStatus());
    }
}
