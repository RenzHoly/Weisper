package pw.bits.weisper.view.image;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.List;

import pw.bits.weisper.library.bean.Picture;

/**
 * Created by rzh on 16/3/21.
 */
public class PictureFlowLayout extends LinearLayout {
    public PictureFlowLayout(Context context) {
        super(context);
    }

    public PictureFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PictureFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @BindingAdapter({"pictures", "width"})
    public static void setPictures(PictureFlowLayout view, @Nullable List<Picture> pictures, int width) {
        view.removeAllViews();
        for (int i = 0; i < (pictures == null ? 0 : pictures.size()); i++) {
            PictureFlowImageView imageView = new PictureFlowImageView(view.getContext());
            imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            imageView.setAdjustViewBounds(true);
            imageView.setImage(pictures, i);
            imageView.setMaxHeight(width);
            imageView.setMinimumHeight(width / 2);
            view.addView(imageView);
        }
    }
}
