package pw.bits.weisper.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import pw.bits.weisper.library.bean.Status;

/**
 * Created by rzh on 16/3/24.
 */
public abstract class StatusAbstractViewHolder extends RecyclerView.ViewHolder {
    public StatusAbstractViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindView(Status status);
}
