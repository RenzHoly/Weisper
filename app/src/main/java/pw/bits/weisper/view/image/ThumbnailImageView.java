package pw.bits.weisper.view.image;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import pw.bits.weisper.R;
import pw.bits.weisper.event.OpenPictureEvent;
import pw.bits.weisper.model.bean.Picture;

/**
 * Created by rzh on 16/3/19.
 */
public class ThumbnailImageView extends ImageView {
    public ThumbnailImageView(Context context) {
        super(context);
    }

    public ThumbnailImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThumbnailImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImage(final List<Picture> pictures, final int position) {
        Glide.with(getContext())
                .load(pictures.get(position).middle())
                .asBitmap()
                .animate(R.anim.fade_in)
                .centerCrop()
                .placeholder(R.drawable.picture_placeholder)
                .error(R.drawable.picture_error)
                .into(this);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new OpenPictureEvent(pictures, position));
            }
        });
    }
}
