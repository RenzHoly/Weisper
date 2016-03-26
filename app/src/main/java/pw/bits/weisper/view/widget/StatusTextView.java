package pw.bits.weisper.view.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pw.bits.weisper.library.Emotions;
import pw.bits.weisper.event.OpenTopicEvent;
import pw.bits.weisper.event.OpenUserEvent;

/**
 * Created by rzh on 16/3/15.
 */
public class StatusTextView extends TextView {
    public StatusTextView(Context context) {
        super(context);
    }

    public StatusTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StatusTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        final String user = "@[\u4e00-\u9fa5\\w\\-]+";
        final String topic = "#[\u4e00-\u9fa5\\w]+#";
        final String emotion = "\\[[\u4e00-\u9fa5\\w]+\\]";
        final String url = "https?://[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";
        setMovementMethod(LinkMovementMethod.getInstance());
        SpannableString spannableString = new SpannableString("\uFEFF" + text);
        match(spannableString, String.format("(%s)|(%s)|(%s)|(%s)", user, topic, emotion, url));
        super.setText(spannableString, type);
    }

    private void match(SpannableString spannableString, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(spannableString);
        while (matcher.find()) {
            setUserSpan(spannableString, matcher);
            setTopicSpan(spannableString, matcher);
            setEmotionSpan(spannableString, matcher);
            setUrlSpan(spannableString, matcher);
        }
    }

    private void setUserSpan(SpannableString spannableString, Matcher matcher) {
        final String user = matcher.group(1);
        if (user == null) {
            return;
        }
        setSpan(spannableString, new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                EventBus.getDefault().post(new OpenUserEvent(user));
                Log.i("onClick", "onClick User");
            }
        }, matcher, user);
    }

    private void setTopicSpan(SpannableString spannableString, Matcher matcher) {
        final String topic = matcher.group(2);
        if (topic == null) {
            return;
        }
        setSpan(spannableString, new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                EventBus.getDefault().post(new OpenTopicEvent(topic));
                Log.i("onClick", "onClick Topic");
            }
        }, matcher, topic);
    }

    private void setEmotionSpan(SpannableString spannableString, Matcher matcher) {
        String emotion = matcher.group(3);
        if (emotion == null) {
            return;
        }
        Integer resId = Emotions.instance().map.get(emotion);
        Bitmap bitmap = null;
        if (resId != null) {
            bitmap = BitmapFactory.decodeResource(getContext().getResources(), resId);
        }
        if (bitmap != null) {
            int size = (int) Math.ceil(getTextSize()) + 1;
            bitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);
            ImageSpan imageSpan = new ImageSpan(getContext(), bitmap, DynamicDrawableSpan.ALIGN_BASELINE);
            setSpan(spannableString, imageSpan, matcher, emotion);
        }
    }

    private void setUrlSpan(SpannableString spannableString, Matcher matcher) {
        String url = matcher.group(4);
        if (url == null) {
            return;
        }
        final Uri uri = Uri.parse(url);
        setSpan(spannableString, new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
                getContext().startActivity(browserIntent);
            }
        }, matcher, url);
    }

    private void setSpan(SpannableString spannableString, Object span, Matcher matcher, String string) {
        spannableString.setSpan(
                span,
                matcher.start(0),
                matcher.start(0) + string.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
