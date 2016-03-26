package pw.bits.weisper.view.widget;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import pw.bits.weisper.R;
import pw.bits.weisper.event.FloatingBarEvent;

/**
 * Created by rzh on 16/3/19.
 */
public class FloatingBar extends CardView {
    public FloatingBar(Context context) {
        super(context);
        init(context);
    }

    public FloatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FloatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.widget_floating_bar, null);
        addView(view);
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onEvent(FloatingBarEvent event) {
        if (event.isVisible()) {
            setX(event.getX() - getWidth() / 2);
            setY(event.getY() - getToolBarHeight() - getStatusBarHeight() - getHeight() * (float) 1.5);
            setVisibility(View.VISIBLE);
        } else {
            setVisibility(View.GONE);
        }
    }

    private int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    private int getToolBarHeight() {
        ActionBar actionBar = ((AppCompatActivity) getContext()).getSupportActionBar();
        return actionBar == null ? 0 : actionBar.getHeight();
    }
}
