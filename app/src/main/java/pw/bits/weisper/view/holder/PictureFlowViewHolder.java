package pw.bits.weisper.view.holder;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pw.bits.weisper.BR;
import pw.bits.weisper.R;
import pw.bits.weisper.event.OpenUserEvent;
import pw.bits.weisper.library.bean.Picture;
import pw.bits.weisper.library.bean.Status;
import pw.bits.weisper.view.image.PictureFlowLayout;

/**
 * Created by rzh on 16/3/19.
 */
public class PictureFlowViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.picture_flow_layout)
    PictureFlowLayout picture_flow_layout;

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
        binding.executePendingBindings();

        List<Picture> pictures = status.retweeted == null ? status.pictures : status.retweeted.pictures;

        itemView.setVisibility(pictures.size() == 0 ? View.GONE : View.VISIBLE);
        if (pictures.size() == 0) {
            itemView.getLayoutParams().height = 0;
        }

        picture_flow_layout.setPictures(pictures, parentWidth);
    }

    public class Handlers {
        private Status status;

        public Handlers(Status status) {
            this.status = status;
        }

        public void onClickAvatar(View view) {
            if (status != null && status.user != null) {
                EventBus.getDefault().post(new OpenUserEvent(status.user.screen_name));
            }
        }
    }
}