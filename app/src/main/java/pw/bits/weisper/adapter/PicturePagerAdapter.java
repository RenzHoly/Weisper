package pw.bits.weisper.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pw.bits.weisper.view.image.BigPictureView;

/**
 * Created by rzh on 16/3/20.
 */
public class PicturePagerAdapter extends PagerAdapter {
    private List<String> pictures;
    private List<BigPictureView> viewList = new ArrayList<>();

    public PicturePagerAdapter(Context context, List<String> pictures) {
        this.pictures = pictures;
        LayoutInflater lf = LayoutInflater.from(context);
        for (String picture : pictures) {
            viewList.add(new BigPictureView(context));
        }
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        viewList.get(position).setPicture(pictures.get(position));
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }
}
