package pw.bits.weisper.view.holder;

import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import pw.bits.weisper.R;
import pw.bits.weisper.library.bean.Status;
import pw.bits.weisper.view.image.AvatarImageView;
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

    @Bind(R.id.user_screen_name)
    TextView user_screen_name;

    @Bind(R.id.status_source)
    TextView status_source;

    @Bind(R.id.user_profile_image)
    AvatarImageView user_profile_image;

    @Bind(R.id.status_reposts_count)
    TextView status_reposts_count;

    @Bind(R.id.status_reposts_icon)
    ImageView status_reposts_icon;

    @Bind(R.id.status_comments_count)
    TextView status_comments_count;

    @Bind(R.id.status_comments_icon)
    ImageView status_comments_icon;

    @Bind(R.id.status_attitudes_count)
    TextView status_attitudes_count;

    @Bind(R.id.status_attitudes_icon)
    ImageView status_attitudes_icon;

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
        status_text.setText(status.text);
        user_screen_name.setText(status.user.screen_name);
        status_source.setText(String.format("%s 来自 %s", new PrettyTime(Locale.CHINA).format(status.created_at).replace(" ", ""), Html.fromHtml(status.source)));

        setStatusInfo(status_reposts_icon, status_reposts_count, status.reposts_count);
        setStatusInfo(status_comments_icon, status_comments_count, status.comments_count);
        setStatusInfo(status_attitudes_icon, status_attitudes_count, status.attitudes_count);

        retweeted_status.setVisibility(status.retweeted_status == null ? View.GONE : View.VISIBLE);
        if (status.retweeted_status != null) {
            if (status.retweeted_status.user != null) {
                retweeted_status_text.setText(String.format("@%s:%s", status.retweeted_status.user.screen_name, status.retweeted_status.text));
            } else {
                retweeted_status_text.setText("抱歉，此微博已被作者删除");
            }
        }

        user_profile_image.setUser(status.user);

        status_layout.setOnClickListener(v -> {
            StatusPopupMenu popup = new StatusPopupMenu(itemView.getContext(), status_attitudes_count, Gravity.END);
            popup.setStatus(status);
            popup.show();
        });

        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) itemView.getLayoutParams();
        status_pictures_container.setPictures(status.retweeted_status == null ? status.pic_urls : status.retweeted_status.pic_urls, parentWidth - lp.leftMargin - lp.rightMargin);
    }

    private void setStatusInfo(ImageView icon, TextView text, int count) {
        text.setVisibility(count == 0 ? View.GONE : View.VISIBLE);
        icon.setVisibility(count == 0 ? View.GONE : View.VISIBLE);
        text.setText(String.format("%d", count));
    }
}