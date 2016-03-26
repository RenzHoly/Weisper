package pw.bits.weisper.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pw.bits.weisper.R;
import pw.bits.weisper.model.bean.Picture;
import pw.bits.weisper.model.bean.Status;
import pw.bits.weisper.view.image.AvatarImageView;
import pw.bits.weisper.view.image.PictureFlowLayout;
import pw.bits.weisper.view.widget.StatusTextView;

/**
 * Created by rzh on 16/3/19.
 */
public class PictureFlowViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.picture_flow_layout)
    PictureFlowLayout picture_flow_layout;

    @Bind(R.id.status_text)
    StatusTextView status_text;

    @Bind(R.id.user_profile_image)
    AvatarImageView user_profile_image;

    private int parentWidth = 0;

    public PictureFlowViewHolder(View itemView, int parentWidth) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.parentWidth = parentWidth;
    }

    public void bindView(Status status) {
        List<Picture> pictures = status.retweeted_status == null ? status.pic_urls : status.retweeted_status.pic_urls;

        itemView.setVisibility(pictures.size() == 0 ? View.GONE : View.VISIBLE);
        if (pictures.size() == 0) {
            itemView.getLayoutParams().height = 0;
        }

        user_profile_image.setUser(status.user);
        status_text.setText(status.text);
        picture_flow_layout.setPictures(pictures, parentWidth);
    }
}