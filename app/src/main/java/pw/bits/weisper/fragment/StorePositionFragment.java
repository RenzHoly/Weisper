package pw.bits.weisper.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import pw.bits.weisper.event.FlowPositionChangeEvent;
import pw.bits.weisper.view.list.FlowListView;

/**
 * Created by rzh on 16/3/30.
 */
public abstract class StorePositionFragment extends Fragment {
    private static int position = 0;

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

    @Override
    public void onResume() {
        super.onResume();
        getListView().scrollToPosition(position);
    }

    protected abstract FlowListView getListView();

    @Subscribe
    void onEvent(FlowPositionChangeEvent event) {
        position = event.getPosition();
    }
}
