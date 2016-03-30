package pw.bits.weisper.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import org.greenrobot.eventbus.EventBus;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    protected abstract FlowListView getListView();
}
