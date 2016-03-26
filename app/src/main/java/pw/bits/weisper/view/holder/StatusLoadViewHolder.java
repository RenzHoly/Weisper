package pw.bits.weisper.view.holder;

import android.view.View;

import pw.bits.weisper.model.bean.Status;
import pw.bits.weisper.store.StatusStore;

/**
 * Created by rzh on 16/3/24.
 */
public class StatusLoadViewHolder extends StatusAbstractViewHolder {
    public StatusLoadViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindView(final Status status) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatusStore.instance.loadMiddle(status, null);
            }
        });
    }
}
