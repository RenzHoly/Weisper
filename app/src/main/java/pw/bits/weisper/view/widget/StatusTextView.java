package pw.bits.weisper.view.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;

import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import pw.bits.weisper.event.OpenTopicEvent;
import pw.bits.weisper.event.OpenUserEvent;
import pw.bits.weisper.library.WeiboTextView;

/**
 * Created by rzh on 16/3/26.
 */
public class StatusTextView extends WeiboTextView {
    private static Context context;

    private static Clickable clickUser = new Clickable() {
        @Override
        public void onClick(String value) {
            Logger.i("click user", value);
            EventBus.getDefault().post(new OpenUserEvent(value));
        }
    };

    private static Clickable clickTopic = new Clickable() {
        @Override
        public void onClick(String value) {
            Logger.i("click topic", value);
            EventBus.getDefault().post(new OpenTopicEvent(value));
        }
    };

    private static Clickable clickLink = new Clickable() {
        @Override
        public void onClick(String value) {
            Logger.i("click link", value);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(value));
            context.startActivity(browserIntent);
        }
    };

    public StatusTextView(Context context) {
        super(context);
        init(context);
    }

    public StatusTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StatusTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        StatusTextView.context = context;
        setClickUser(clickUser);
        setClickTopic(clickTopic);
        setClickLink(clickLink);
    }
}
