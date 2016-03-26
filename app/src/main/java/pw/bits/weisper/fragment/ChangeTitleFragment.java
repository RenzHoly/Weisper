package pw.bits.weisper.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by rzh on 16/3/25.
 */
public abstract class ChangeTitleFragment extends Fragment {
    private CharSequence previousTitle;

    private ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    abstract CharSequence getTitle();

    @Override
    public void onResume() {
        super.onResume();
        previousTitle = getActionBar().getTitle();
        getActionBar().setTitle(getTitle());
    }

    @Override
    public void onPause() {
        super.onPause();
        getActionBar().setTitle(previousTitle);
    }
}
