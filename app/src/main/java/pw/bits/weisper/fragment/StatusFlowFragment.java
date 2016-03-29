package pw.bits.weisper.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import pw.bits.weisper.R;
import pw.bits.weisper.event.StatusEvent;
import pw.bits.weisper.store.FlowStatusStore;
import pw.bits.weisper.view.list.FlowListView;
import pw.bits.weisper.view.list.StatusFlowListView;

/**
 * A placeholder fragment containing a simple view.
 */
public class StatusFlowFragment extends StorePositionFragment {
    @Bind(R.id.status_list)
    StatusFlowListView status_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_status_flow, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Subscribe
    public void onEvent(StatusEvent event) {
        if (event.getAddedCount() <= 0) {
            return;
        }
        final Snackbar snackbar = Snackbar.make(status_list,
                String.format(Locale.getDefault(), getString(R.string.snackbar_updated), event.getAddedCount()),
                Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snackbar_show, v -> {
            status_list.scrollToPosition(0);
            snackbar.dismiss();
        });
        snackbar.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        FlowStatusStore.instance.loadFront(null);
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

    @Override
    protected FlowListView getListView() {
        return status_list;
    }
}
