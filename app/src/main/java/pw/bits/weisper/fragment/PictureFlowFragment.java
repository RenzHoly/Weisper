package pw.bits.weisper.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import pw.bits.weisper.R;
import pw.bits.weisper.event.PictureFlowPositionChangeEvent;
import pw.bits.weisper.view.image.AvatarImageView;
import pw.bits.weisper.view.widget.StatusTextView;

/**
 * Created by rzh on 16/3/14.
 */
public class PictureFlowFragment extends Fragment {
    @Bind(R.id.pinned_view)
    ViewGroup pinned_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_picture_flow, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        pinned_view.setVisibility(View.GONE);
    }

    @Subscribe
    public void onEvent(PictureFlowPositionChangeEvent event) {
        pinned_view.setVisibility(event.isOverlapping() ? View.GONE : View.VISIBLE);
        AvatarImageView avatarImageView = (AvatarImageView) pinned_view.findViewById(R.id.user_profile_image);
        StatusTextView statusTextView = (StatusTextView) pinned_view.findViewById(R.id.status_text);
        avatarImageView.setUser(event.getUser());
        statusTextView.setText(event.getText());
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
}
