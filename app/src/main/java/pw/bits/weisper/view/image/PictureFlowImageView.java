package pw.bits.weisper.view.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import pw.bits.weisper.R;
import pw.bits.weisper.event.OpenPictureEvent;
import pw.bits.weisper.bean.Picture;

/**
 * Created by rzh on 16/3/19.
 */
public class PictureFlowImageView extends ImageView {
    public PictureFlowImageView(Context context) {
        super(context);
    }

    public PictureFlowImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PictureFlowImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImage(final List<Picture> pictures, final int position) {
        setScaleType(ScaleType.CENTER_CROP);
        Glide.with(getContext())
                .load(pictures.get(position).middle())
                .asBitmap()
                .transform(new BitmapTransformation(getContext()) {
                    @Override
                    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
                        if (toTransform.getHeight() >= 2 * toTransform.getWidth()) {
                            Bitmap crop = Bitmap.createBitmap(toTransform, 0, 0, toTransform.getWidth(), toTransform.getWidth());
                            toTransform.recycle();
                            return crop;
                        }
                        return toTransform;
                    }

                    @Override
                    public String getId() {
                        return "crop";
                    }
                })
                .placeholder(R.drawable.picture_placeholder)
                .into(this);
        setOnClickListener(v -> EventBus.getDefault().post(new OpenPictureEvent(pictures, position)));
    }
}
