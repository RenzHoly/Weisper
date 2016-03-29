package pw.bits.weisper.fragment;

import android.support.v4.app.Fragment;

import pw.bits.weisper.view.list.FlowListView;

/**
 * Created by rzh on 16/3/30.
 */
public abstract class StorePositionFragment extends Fragment {
    private int position = 0;

    @Override
    public void onResume() {
        super.onResume();
        getListView().scrollToPosition(position);
    }

    @Override
    public void onPause() {
        super.onPause();
        position = getListView().getScrollPosition();
    }

    protected abstract FlowListView getListView();
}
