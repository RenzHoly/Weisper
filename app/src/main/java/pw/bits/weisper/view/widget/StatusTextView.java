package pw.bits.weisper.view.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import pw.bits.weisper.WeisperApplication;
import pw.bits.weisper.event.OpenTopicEvent;
import pw.bits.weisper.event.OpenUserEvent;
import pw.bits.weisper.library.WeiboTextView;

/**
 * Created by rzh on 16/3/26.
 */
public class StatusTextView extends WeiboTextView {
    private static Clickable clickUser = new Clickable() {
        @Override
        public void onClick(String value) {
            Log.i("click user", value);
            EventBus.getDefault().post(new OpenUserEvent(value));
        }
    };

    private static Clickable clickTopic = new Clickable() {
        @Override
        public void onClick(String value) {
            Log.i("click topic", value);
            EventBus.getDefault().post(new OpenTopicEvent(value));
        }
    };

    private static Clickable clickLink = new Clickable() {
        @Override
        public void onClick(String value) {
            Log.i("click link", value);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(value));
            WeisperApplication.instance.startActivity(browserIntent);
        }
    };

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
        setClickUser(clickUser);
        setClickTopic(clickTopic);
        setClickLink(clickLink);
    }
}
