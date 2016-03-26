package pw.bits.weisper.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import pw.bits.weisper.R;
import pw.bits.weisper.adapter.PicturePagerAdapter;

/**
 * Created by rzh on 16/3/20.
 */
public class PictureFragment extends Fragment {
    @Bind(R.id.picture_view_pager)
    ViewPager picture_view_pager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_picture, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();
        getArguments().getStringArrayList("pictures");
        picture_view_pager.setAdapter(new PicturePagerAdapter(getContext(), getArguments().getStringArrayList("pictures")));
        picture_view_pager.setCurrentItem(getArguments().getInt("position"));
    }
}
