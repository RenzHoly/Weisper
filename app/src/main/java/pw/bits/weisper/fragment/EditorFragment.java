package pw.bits.weisper.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import pw.bits.weisper.R;
import pw.bits.weisper.library.WeiboData;
import pw.bits.weisper.library.bean.Status;
import rx.Subscriber;

/**
 * Created by rzh on 16/3/28.
 */
public class EditorFragment extends Fragment {
    @Bind(R.id.editor_background)
    View background;

    @Bind(R.id.editor)
    EditText editor;

    @Bind(R.id.send)
    Button send;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editor, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setupView();
    }

    private void setupView() {
        send.setOnClickListener(v -> WeiboData.statusesRepost(getArguments().getLong("id", -1), editor.getText().toString(), false).subscribe(new Subscriber<Status>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getContext(), "转发失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Status status) {
                Toast.makeText(getContext(), "转发成功", Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStackImmediate();
            }
        }));
        editor.requestFocus();
        background.setOnClickListener(null);
    }
}
