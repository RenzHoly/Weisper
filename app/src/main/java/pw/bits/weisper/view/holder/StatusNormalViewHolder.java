package pw.bits.weisper.view.holder;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import pw.bits.weisper.BR;
import pw.bits.weisper.R;
import pw.bits.weisper.event.OpenUserEvent;
import pw.bits.weisper.data.bean.Status;
import pw.bits.weisper.view.widget.StatusPopupMenu;

/**
 * Created by rzh on 16/3/19.
 */
public class StatusNormalViewHolder extends StatusAbstractViewHolder {
    @Bind(R.id.status_attitudes_count)
    TextView status_attitudes_count;

    private int parentWidth;

    public StatusNormalViewHolder(View itemView, int width) {
        super(itemView);
        parentWidth = width;
        ButterKnife.bind(this, itemView);
    }

    public void bindView(Status status) {
        ViewDataBinding binding = DataBindingUtil.bind(itemView);
        Handlers handlers = new Handlers(status, status_attitudes_count);
        binding.setVariable(BR.status, status);
        binding.setVariable(BR.handlers, handlers);
        binding.setVariable(BR.width, parentWidth);
        binding.executePendingBindings();
    }

    public static class Handlers {
        private Status status;
        private View anchor;

        public Handlers(Status status, View anchor) {
            this.status = status;
            this.anchor = anchor;
        }

        public void onClickStatus(View view) {
            StatusPopupMenu popup = new StatusPopupMenu(view.getContext(), anchor, Gravity.END);
            popup.setStatus(status);
            popup.show();
        }

        public void onClickRetweetedStatus(View view) {
            StatusPopupMenu popup = new StatusPopupMenu(view.getContext(), anchor, Gravity.END);
            popup.setStatus(status.getRetweeted());
            popup.show();
        }

        public void onClickAvatar(View view) {
            if (status != null) {
                EventBus.getDefault().post(new OpenUserEvent(status.getScreenName()));
            }
        }
    }
}