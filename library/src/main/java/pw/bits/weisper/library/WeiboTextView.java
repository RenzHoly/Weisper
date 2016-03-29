package pw.bits.weisper.library;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.text.style.ReplacementSpan;
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
    private static final Pattern userPattern = Pattern.compile("@[\u4e00-\u9fa5\\w\\-]+");
    private static final Pattern topicPattern = Pattern.compile("#[\u4e00-\u9fa5\\w\\-]+#");
    private static final Pattern emotionPattern = Pattern.compile("\\[[\u4e00-\u9fa5\\w]+\\]");
    private static final Pattern linkPattern = Pattern.compile("https?://[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)");

    private OnClickSpanListener clickUser;
    private OnClickSpanListener clickTopic;
    private OnClickSpanListener clickLink;

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
        matchUser(spannableString);
        matchTopic(spannableString);
        matchEmotion(spannableString);
        matchLink(spannableString);
        super.setText(spannableString, type);
    }

    private void matchUser(SpannableString spannableString) {
        Matcher matcher = userPattern.matcher(spannableString);
        while (matcher.find()) {
            String user = matcher.group();
            setSpan(spannableString, new WeiboClickableSpan(user, clickUser), matcher.start(0), user.length());
        }
    }

    private void matchTopic(SpannableString spannableString) {
        Matcher matcher = topicPattern.matcher(spannableString);
        while (matcher.find()) {
            String topic = matcher.group();
            setSpan(spannableString, new WeiboClickableSpan(topic, clickTopic), matcher.start(0), topic.length());
        }
    }

    private void matchEmotion(SpannableString spannableString) {
        Matcher matcher = emotionPattern.matcher(spannableString);
        while (matcher.find()) {
            String emotion = matcher.group();
            Integer resId = Emotions.get(emotion);
            Bitmap bitmap = null;
            if (resId != null) {
                bitmap = BitmapFactory.decodeResource(getContext().getResources(), resId);
            }
            if (bitmap != null) {
                int size = (int) Math.ceil(getTextSize());
                ImageSpan imageSpan = new ImageSpan(getContext(), Bitmap.createScaledBitmap(bitmap, size, size, true), DynamicDrawableSpan.ALIGN_BASELINE);
                bitmap.recycle();
                setSpan(spannableString, imageSpan, matcher.start(0), emotion.length());
            }
        }
    }

    private void matchLink(SpannableString spannableString) {
        Matcher matcher = linkPattern.matcher(spannableString);
        while (matcher.find()) {
            String link = matcher.group();
            setSpan(spannableString, new WeiboClickableSpan(link, clickLink), matcher.start(0), link.length());
            setSpan(spannableString, new WeiboLinkSpan(), matcher.start(0), link.length());
        }
    }

    private void setSpan(SpannableString spannableString, Object span, int start, int length) {
        spannableString.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private static class WeiboClickableSpan extends ClickableSpan {
        private String value;
        private OnClickSpanListener listener;

        public WeiboClickableSpan(String value, OnClickSpanListener listener) {
            this.value = value;
            this.listener = listener;
        }

        @Override
        public void onClick(View widget) {
            listener.onClick(value);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }

    private class WeiboLinkSpan extends ReplacementSpan {
        private static final String linkReplacement = "\uD83D\uDD17LINK";

        @Override
        public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            return (int) paint.measureText(linkReplacement, 0, linkReplacement.length());
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
            paint.setColor(getLinkTextColors().getDefaultColor());
            canvas.drawText(linkReplacement, x, y, paint);
        }
    }

    public void setClickUser(OnClickSpanListener clickUser) {
        this.clickUser = clickUser;
    }

    public void setClickTopic(OnClickSpanListener clickTopic) {
        this.clickTopic = clickTopic;
    }

    public void setClickLink(OnClickSpanListener clickLink) {
        this.clickLink = clickLink;
    }

    public interface OnClickSpanListener {
        void onClick(String value);
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
