package pw.bits.weisper.view.image;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

import pw.bits.weisper.R;
import pw.bits.weisper.data.bean.Picture;

/**
 * Created by rzh on 16/3/19.
 */
public class ThumbnailsLayout extends FrameLayout {
    private static final int layouts[] = new int[]{
            R.layout.item_status_pictures_1,
            R.layout.item_status_pictures_2,
            R.layout.item_status_pictures_3,
            R.layout.item_status_pictures_4,
            R.layout.item_status_pictures_5,
            R.layout.item_status_pictures_6,
            R.layout.item_status_pictures_7,
            R.layout.item_status_pictures_8,
            R.layout.item_status_pictures_9};
    private static final int ids[] = new int[]{
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

    public ThumbnailsLayout(Context context) {
        super(context);
    }

    public ThumbnailsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThumbnailsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @BindingAdapter({"pictures", "width"})
    public static void setPictures(ThumbnailsLayout view, @NonNull List<Picture> pictures, int width) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int height = width - lp.leftMargin - lp.rightMargin;

        view.removeAllViews();
        if (pictures.size() > 0) {
            view.addView(LayoutInflater.from(view.getContext()).inflate(layouts[pictures.size() - 1], null));
        }
        for (int i = 0; i < pictures.size(); i++) {
            ThumbnailImageView thumbnailImageView = (ThumbnailImageView) view.findViewById(ids[i]);
            thumbnailImageView.setImage(pictures, i);
        }

        switch (pictures.size()) {
            case 0: {
                view.getLayoutParams().height = 0;
                break;
            }
            case 1:
            case 2: {
                view.getLayoutParams().height = height / 2;
                break;
            }
            case 3: {
                view.getLayoutParams().height = height / 3;
                break;
            }
            case 4:
            case 9: {
                view.getLayoutParams().height = height;
                break;
            }
            case 5: {
                view.getLayoutParams().height = height * 5 / 6;
                break;
            }
            case 6: {
                view.getLayoutParams().height = height * 2 / 3;
                break;
            }
            case 7: {
                view.getLayoutParams().height = height * 4 / 3;
                break;
            }
            case 8: {
                view.getLayoutParams().height = height * 7 / 6;
                break;
            }
        }
    }
}
