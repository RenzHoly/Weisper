package pw.bits.weisper.view.holder;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import pw.bits.weisper.BR;
import pw.bits.weisper.event.OpenUserEvent;
import pw.bits.weisper.library.bean.Status;

/**
 * Created by rzh on 16/3/19.
 */
public class PictureFlowViewHolder extends RecyclerView.ViewHolder {
    private int parentWidth = 0;

    public PictureFlowViewHolder(View itemView, int parentWidth) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.parentWidth = parentWidth;
    }

    public void bindView(Status status) {
        ViewDataBinding binding = DataBindingUtil.bind(itemView);
        Handlers handlers = new Handlers(status);
        binding.setVariable(BR.status, status);
        binding.setVariable(BR.handlers, handlers);
        binding.setVariable(BR.width, parentWidth);
        binding.executePendingBindings();
    }

    public static class Handlers {
        private Status status;

        public Handlers(Status status) {
            this.status = status;
        }

        public void onClickAvatar(View view) {
            if (status != null) {
                EventBus.getDefault().post(new OpenUserEvent(status.getScreenName()));
            }
        }
    }
}