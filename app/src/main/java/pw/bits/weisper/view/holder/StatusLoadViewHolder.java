package pw.bits.weisper.view.holder;

import android.view.View;

import pw.bits.weisper.library.bean.Status;
import pw.bits.weisper.store.FlowStatusStore;

/**
 * Created by rzh on 16/3/24.
 */
public class StatusLoadViewHolder extends StatusAbstractViewHolder {
    public StatusLoadViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindView(final Status status) {
        itemView.setOnClickListener(v -> FlowStatusStore.instance.loadMiddle(status, null));
    }
}
