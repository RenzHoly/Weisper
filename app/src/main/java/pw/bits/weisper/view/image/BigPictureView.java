package pw.bits.weisper.view.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.greenrobot.eventbus.EventBus;

import pw.bits.weisper.R;
import pw.bits.weisper.event.ClosePictureEvent;
import pw.bits.weisper.library.LongPictureView;
import pw.bits.weisper.library.ZoomImageView;

/**
 * Created by rzh on 16/3/23.
 */
public class BigPictureView extends FrameLayout {
    public BigPictureView(Context context) {
        super(context);
    }

    public BigPictureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BigPictureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private static OnClickListener listener = v -> EventBus.getDefault().post(new ClosePictureEvent());

    public void setPicture(String url) {
        if (isGif(url)) {
            ImageView image = new ImageView(getContext());
            image.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            addView(image);
            Glide.with(getContext())
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(R.drawable.picture_error)
                    .into(image);
            image.setOnClickListener(listener);
            return;
        }

        Glide.with(getContext())
                .load(url)
                .asBitmap()
                .error(R.drawable.picture_error)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        if (isLong(resource)) {
                            LongPictureView image = new LongPictureView(getContext());
                            image.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                            addView(image);
                            image.setPicture(resource);
                            image.setOnItemClickListener(listener);
                        } else {
                            ZoomImageView image = new ZoomImageView(getContext());
                            image.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                            addView(image);
                            image.setImageBitmap(resource);
                            image.setOnClickListener(listener);
                        }
                    }
                });
    }

    private boolean isGif(String url) {
        return url.matches("^.*\\.[gG][iI][fF]$");
    }

    private boolean isLong(Bitmap resource) {
        return resource.getHeight() >= resource.getWidth() * 2;
    }
}
