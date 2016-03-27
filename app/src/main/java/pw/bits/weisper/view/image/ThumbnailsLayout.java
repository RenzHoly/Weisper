package pw.bits.weisper.view.image;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

import pw.bits.weisper.R;
import pw.bits.weisper.library.bean.Picture;

/**
 * Created by rzh on 16/3/19.
 */
public class ThumbnailsLayout extends FrameLayout {
    public ThumbnailsLayout(Context context) {
        super(context);
    }

    public ThumbnailsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThumbnailsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPictures(final List<Picture> pictures, int width) {
        int layouts[] = new int[]{
                R.layout.item_status_pictures_1,
                R.layout.item_status_pictures_2,
                R.layout.item_status_pictures_3,
                R.layout.item_status_pictures_4,
                R.layout.item_status_pictures_5,
                R.layout.item_status_pictures_6,
                R.layout.item_status_pictures_7,
                R.layout.item_status_pictures_8,
                R.layout.item_status_pictures_9};
        final int ids[] = new int[]{
                R.id.status_pictures_0,
                R.id.status_pictures_1,
                R.id.status_pictures_2,
                R.id.status_pictures_3,
                R.id.status_pictures_4,
                R.id.status_pictures_5,
                R.id.status_pictures_6,
                R.id.status_pictures_7,
                R.id.status_pictures_8,
        };

        removeAllViews();
        int size = 0;
        if (pictures != null && pictures.size() > 0) {
            size = pictures.size();
            addView(LayoutInflater.from(getContext()).inflate(layouts[size - 1], null));
            setVisibility(View.VISIBLE);
        } else {
            setVisibility(View.GONE);
        }
        for (int i = 0; i < size; i++) {
            final int position = i;
            int resId = ids[i];
            ThumbnailImageView thumbnailImageView = (ThumbnailImageView) findViewById(resId);
            thumbnailImageView.setImage(pictures, position);
        }

        switch (size) {
            case 0: {
                getLayoutParams().height = 0;
                break;
            }
            case 1:
            case 2: {
                getLayoutParams().height = width / 2;
                break;
            }
            case 3: {
                getLayoutParams().height = width / 3;
                break;
            }
            case 4:
            case 9: {
                getLayoutParams().height = width;
                break;
            }
            case 5: {
                getLayoutParams().height = width * 5 / 6;
                break;
            }
            case 6: {
                getLayoutParams().height = width * 2 / 3;
                break;
            }
            case 7: {
                getLayoutParams().height = width * 4 / 3;
                break;
            }
            case 8: {
                getLayoutParams().height = width * 7 / 6;
                break;
            }
        }
    }
}
