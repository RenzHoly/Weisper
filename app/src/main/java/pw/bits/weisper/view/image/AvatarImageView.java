package pw.bits.weisper.view.image;

import android.content.Context;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import pw.bits.weisper.R;
import pw.bits.weisper.event.OpenUserEvent;
import pw.bits.weisper.library.bean.User;

/**
 * Created by rzh on 16/3/19.
 */
public class AvatarImageView extends de.hdodenhof.circleimageview.CircleImageView {
    public AvatarImageView(Context context) {
        super(context);
    }

    public AvatarImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AvatarImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setUser(final User user) {
        Glide.with(getContext())
                .load(user.profile_image_url)
                .asBitmap()
                .placeholder(R.drawable.picture_placeholder)
                .into(this);
        setOnClickListener(v -> EventBus.getDefault().post(new OpenUserEvent(user.screen_name)));
    }
}
