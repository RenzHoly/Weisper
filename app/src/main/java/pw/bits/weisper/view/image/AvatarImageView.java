package pw.bits.weisper.view.image;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import pw.bits.weisper.R;

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

    @BindingAdapter("android:src")
    public static void setUser(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .asBitmap()
                .dontTransform()
                .placeholder(R.drawable.picture_placeholder)
                .into(view);
    }
}
