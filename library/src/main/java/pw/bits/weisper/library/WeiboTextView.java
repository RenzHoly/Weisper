package pw.bits.weisper.library;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.MovementMethod;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rzh on 16/3/15.
 */
public class WeiboTextView extends TextView {
    private static final String user = "@[\u4e00-\u9fa5\\w\\-]+";
    private static final String topic = "#[\u4e00-\u9fa5\\w\\-]+#";
    private static final String emotion = "\\[[\u4e00-\u9fa5\\w]+\\]";
    private static final String link = "https?://[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";

    private WeiboClickableSpan clickUser;
    private WeiboClickableSpan clickTopic;
    private WeiboClickableSpan clickLink;

    public WeiboTextView(Context context) {
        super(context);
        init();
    }

    public WeiboTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WeiboTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setMovementMethod(WeiboMovementMethod.instance);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        SpannableString spannableString = new SpannableString("\uFEFF" + text);
        match(spannableString, String.format("(%s)|(%s)|(%s)|(%s)", user, topic, emotion, link));
        super.setText(spannableString, type);
    }

    private void match(SpannableString spannableString, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(spannableString);
        while (matcher.find()) {
            setUserSpan(spannableString, matcher);
            setTopicSpan(spannableString, matcher);
            setEmotionSpan(spannableString, matcher);
            setLinkSpan(spannableString, matcher);
        }
    }

    private void setUserSpan(SpannableString spannableString, Matcher matcher) {
        String user = matcher.group(1);
        if (user == null) {
            return;
        }
        setSpan(spannableString, clickUser.click(user), matcher, user);
    }

    private void setTopicSpan(SpannableString spannableString, Matcher matcher) {
        String topic = matcher.group(2);
        if (topic == null) {
            return;
        }
        setSpan(spannableString, clickTopic.click(topic), matcher, topic);
    }

    private void setLinkSpan(SpannableString spannableString, Matcher matcher) {
        String link = matcher.group(4);
        if (link == null) {
            return;
        }
        setSpan(spannableString, clickLink.click(link), matcher, link);
    }

    private void setEmotionSpan(SpannableString spannableString, Matcher matcher) {
        String emotion = matcher.group(3);
        if (emotion == null) {
            return;
        }
        Integer resId = Emotions.get(emotion);
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

    private void setSpan(SpannableString spannableString, CharacterStyle span, Matcher matcher, String string) {
        spannableString.setSpan(
                span,
                matcher.start(0),
                matcher.start(0) + string.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public static abstract class WeiboClickableSpan extends ClickableSpan implements Cloneable {
        private String value;

        @Override
        public void onClick(View widget) {
            onClick(value);
        }

        public abstract void onClick(String value);

        public WeiboClickableSpan click(String value) {
            this.value = value;
            try {
                return (WeiboClickableSpan) this.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }

    public void setClickUser(WeiboClickableSpan clickUser) {
        this.clickUser = clickUser;
    }

    public void setClickTopic(WeiboClickableSpan clickTopic) {
        this.clickTopic = clickTopic;
    }

    public void setClickLink(WeiboClickableSpan clickLink) {
        this.clickLink = clickLink;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);
        MovementMethod method = getMovementMethod();
        if (method instanceof WeiboMovementMethod) {
            return ((WeiboMovementMethod) method).isLink();
        }
        return value;
    }
}
