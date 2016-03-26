package pw.bits.weisper.view.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import pw.bits.weisper.event.OpenTopicEvent;
import pw.bits.weisper.event.OpenUserEvent;
import pw.bits.weisper.library.WeiboTextView;

/**
 * Created by rzh on 16/3/26.
 */
public class StatusTextView extends WeiboTextView {
    public StatusTextView(Context context) {
        super(context);
        init();
    }

    public StatusTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StatusTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setClickUser(new Clickable() {
            @Override
            public void onClick(String value) {
                Log.i("click user", value);
                EventBus.getDefault().post(new OpenUserEvent(value));
            }
        });

        setClickTopic(new Clickable() {
            @Override
            public void onClick(String value) {
                Log.i("click topic", value);
                EventBus.getDefault().post(new OpenTopicEvent(value));
            }
        });

        setClickLink(new Clickable() {
            @Override
            public void onClick(String value) {
                Log.i("click link", value);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(value));
                getContext().startActivity(browserIntent);
            }
        });
    }
}
