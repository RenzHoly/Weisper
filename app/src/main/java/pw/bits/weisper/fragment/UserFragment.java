package pw.bits.weisper.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import pw.bits.weisper.R;
import pw.bits.weisper.view.list.UserStatusListView;

/**
 * Created by rzh on 16/3/15.
 */
public class UserFragment extends ChangeTitleFragment {
    @Bind(R.id.user_status_list)
    UserStatusListView user_status_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        user_status_list.setUserName(getArguments().getString("screen_name", null));
        user_status_list.loadFront();
    }

    @Override
    CharSequence getTitle() {
        return String.format("@%s", getArguments().getString("screen_name", "ME"));
    }
}
