package pw.bits.weisper.view.holder;

import android.databinding.DataBindingUtil;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import pw.bits.weisper.BR;
import pw.bits.weisper.R;
import pw.bits.weisper.library.bean.Status;
import pw.bits.weisper.view.image.ThumbnailsLayout;
import pw.bits.weisper.view.widget.StatusPopupMenu;
import pw.bits.weisper.view.widget.StatusTextView;

/**
 * Created by rzh on 16/3/19.
 */
public class StatusNormalViewHolder extends StatusAbstractViewHolder {
    @Bind(R.id.status_layout)
    View status_layout;

    @Bind(R.id.status_text)
    StatusTextView status_text;

    @Bind(R.id.retweeted_status_text)
    StatusTextView retweeted_status_text;

    @Bind(R.id.status_attitudes_count)
    TextView status_attitudes_count;

    @Bind(R.id.status_pictures_container)
    ThumbnailsLayout status_pictures_container;

    @Bind(R.id.retweeted_status)
    View retweeted_status;

    private int parentWidth = 0;

    public StatusNormalViewHolder(View itemView, int parentWidth) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.parentWidth = parentWidth;
    }

    public void bindView(Status status) {
        DataBindingUtil.bind(itemView).setVariable(BR.status, status);

        status_text.setText(status.text);

        retweeted_status.setVisibility(status.retweeted == null ? View.GONE : View.VISIBLE);
        if (status.retweeted != null) {
            if (status.retweeted.user != null) {
                retweeted_status_text.setText(String.format("@%s:%s", status.retweeted.user.screen_name, status.retweeted.text));
            } else {
                retweeted_status_text.setText("抱歉，此微博已被作者删除");
            }
        }

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

        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) itemView.getLayoutParams();
        status_pictures_container.setPictures(status.retweeted == null ? status.pictures : status.retweeted.pictures, parentWidth - lp.leftMargin - lp.rightMargin);
    }
}