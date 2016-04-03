package pw.bits.weisper.view.holder;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

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
    @Bind(R.id.status_attitudes_count)
    TextView status_attitudes_count;

    public StatusNormalViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindView(Status status) {
        ViewDataBinding binding = DataBindingUtil.bind(itemView);
        binding.setVariable(BR.status, status);
        Handlers handlers = new Handlers(status, status_attitudes_count);
        binding.setVariable(BR.handlers, handlers);
        binding.executePendingBindings();
    }

    public class Handlers {
        private Status status;
        private View anchor;

        public Handlers(Status status, View anchor) {
            this.status = status;
            this.anchor = anchor;
        }

        public void onClickStatus(View view) {
            Logger.i("onClickStatus");
            StatusPopupMenu popup = new StatusPopupMenu(view.getContext(), anchor, Gravity.END);
            popup.setStatus(status);
            popup.show();
        }

        public void onClickRetweetedStatus(View view) {
            Logger.i("onClickRetweetedStatus");
            StatusPopupMenu popup = new StatusPopupMenu(view.getContext(), anchor, Gravity.END);
            popup.setStatus(status.retweeted);
            popup.show();
        }
    }
}