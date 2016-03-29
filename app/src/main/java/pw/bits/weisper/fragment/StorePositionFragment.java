package pw.bits.weisper.fragment;

import android.os.Parcelable;
import android.support.v4.app.Fragment;

import pw.bits.weisper.view.list.FlowListView;

/**
 * Created by rzh on 16/3/30.
 */
public abstract class StorePositionFragment extends Fragment {
    private static Parcelable recyclerViewState;

    @Override
    public void onResume() {
        super.onResume();
        getListView().getLayoutManager().onRestoreInstanceState(recyclerViewState);
    }

    @Override
    public void onPause() {
        super.onPause();
        recyclerViewState = getListView().getLayoutManager().onSaveInstanceState();
    }

    protected abstract FlowListView getListView();
}
