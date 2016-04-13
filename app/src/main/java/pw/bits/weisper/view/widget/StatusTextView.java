package pw.bits.weisper.view.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import pw.bits.weisper.event.OpenLinkEvent;
import pw.bits.weisper.event.OpenTopicEvent;
import pw.bits.weisper.event.OpenUserEvent;

/**
 * Created by rzh on 16/3/26.
 */
public class StatusTextView extends WeiboTextView {
    private static OnClickSpanListener clickUser = value -> {
        Logger.i("click user", value);
        EventBus.getDefault().post(new OpenUserEvent(value));
    };

    private static OnClickSpanListener clickTopic = value -> {
        Logger.i("click topic", value);
        EventBus.getDefault().post(new OpenTopicEvent(value));
    };

    private static OnClickSpanListener clickLink = value -> {
        Logger.i("click link", value);
        EventBus.getDefault().post(new OpenLinkEvent(value));
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
