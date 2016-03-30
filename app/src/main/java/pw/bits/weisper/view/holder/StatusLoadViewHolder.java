package pw.bits.weisper.view.holder;

import android.view.View;

import org.greenrobot.eventbus.EventBus;

import pw.bits.weisper.event.RemoveLoadViewHolderEvent;
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
    public void bindView(Status status) {
        int position = getAdapterPosition();
        itemView.setOnClickListener(v -> FlowStatusStore.instance.loadMiddle(position, position, count -> {
            if (count == 0) {
                EventBus.getDefault().post(new RemoveLoadViewHolderEvent(position));
            }
        }));
    }
}
