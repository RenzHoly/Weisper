package pw.bits.weisper.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import pw.bits.weisper.R;
import pw.bits.weisper.view.list.TopicStatusListView;

/**
 * Created by rzh on 16/3/24.
 */
public class TopicFragment extends ChangeTitleFragment {
    @Bind(R.id.topic_status_list)
    TopicStatusListView topic_status_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        topic_status_list.setTopic(getArguments().getString("topic", null));
        topic_status_list.refresh();
    }

    @Override
    CharSequence getTitle() {
        return String.format("#%s#", getArguments().getString("topic", null));
    }
}

