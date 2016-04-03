package pw.bits.weisper.view.holder;

import android.databinding.DataBindingUtil;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import pw.bits.weisper.BR;
import pw.bits.weisper.R;
import pw.bits.weisper.library.bean.Status;
import pw.bits.weisper.view.widget.StatusPopupMenu;

/**
 * Created by rzh on 16/3/19.
 */
public class StatusNormalViewHolder extends StatusAbstractViewHolder {
    @Bind(R.id.status_layout)
    View status_layout;

    @Bind(R.id.status_attitudes_count)
    TextView status_attitudes_count;

    @Bind(R.id.retweeted_status)
    View retweeted_status;

    public StatusNormalViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindView(Status status) {
        DataBindingUtil.bind(itemView).setVariable(BR.status, status);

        status_layout.setOnClickListener(v -> {
            StatusPopupMenu popup = new StatusPopupMenu(itemView.getContext(), status_attitudes_count, Gravity.END);
            popup.setStatus(status);
            popup.show();
        });

        if (status.retweeted != null) {
            retweeted_status.setOnClickListener(v -> {
                StatusPopupMenu popup = new StatusPopupMenu(itemView.getContext(), status_attitudes_count, Gravity.END);
                popup.setStatus(status.retweeted);
                popup.show();
            });
        }
    }
}