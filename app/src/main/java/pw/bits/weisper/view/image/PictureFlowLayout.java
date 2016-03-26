package pw.bits.weisper.view.image;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import pw.bits.weisper.model.bean.Picture;

/**
 * Created by rzh on 16/3/21.
 */
public class PictureFlowLayout extends LinearLayout {
    private List<PictureFlowImageView> pictureViews = new ArrayList<>();

    public PictureFlowLayout(Context context) {
        super(context);
        init(context);
    }

    public PictureFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PictureFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        for (int i = 0; i < 9; i++) {
            PictureFlowImageView imageView = new PictureFlowImageView(context);
            imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            imageView.setAdjustViewBounds(true);
            addView(imageView);
            pictureViews.add(imageView);
        }
    }

    public void setPictures(final List<Picture> pictures, int maxHeight) {
        for (int i = 0; i < pictures.size(); i++) {
            final int position = i;
            pictureViews.get(i).setVisibility(View.VISIBLE);
            pictureViews.get(i).setImage(pictures, position);
            pictureViews.get(i).setMaxHeight(maxHeight);
        }
        for (int i = pictures.size(); i < 9; i++) {
            pictureViews.get(i).setVisibility(View.GONE);
            pictureViews.get(i).setImageBitmap(null);
        }
    }
}
