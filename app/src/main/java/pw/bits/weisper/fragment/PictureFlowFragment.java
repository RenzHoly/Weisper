package pw.bits.weisper.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import pw.bits.weisper.BR;
import pw.bits.weisper.R;
import pw.bits.weisper.event.PictureFlowPositionChangeEvent;
import pw.bits.weisper.view.holder.PictureFlowViewHolder;
import pw.bits.weisper.view.list.FlowListView;
import pw.bits.weisper.view.list.PictureFlowListView;

/**
 * Created by rzh on 16/3/14.
 */
public class PictureFlowFragment extends StorePositionFragment {
    ViewDataBinding binding;

    @Bind(R.id.picture_list)
    PictureFlowListView picture_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_picture_flow, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        binding = DataBindingUtil.bind(view);
        binding.setVariable(BR.overlapping, true);
        binding.executePendingBindings();
    }

    @Subscribe
    public void onEvent(PictureFlowPositionChangeEvent event) {
        PictureFlowViewHolder.Handlers handlers = new PictureFlowViewHolder.Handlers(event.getStatus());
        binding.setVariable(BR.status, event.getStatus());
        binding.setVariable(BR.handlers, handlers);
        binding.setVariable(BR.overlapping, event.isOverlapping());
        binding.executePendingBindings();
    }

    @Override
    protected FlowListView getListView() {
        return picture_list;
    }
}
