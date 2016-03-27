package pw.bits.weisper.library;

import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by rzh on 16/3/27.
 */
public class WeiboMovementMethod extends LinkMovementMethod {
    public static final WeiboMovementMethod instance = new WeiboMovementMethod();
    private static BackgroundColorSpan mGray = new BackgroundColorSpan(0xAAAAAA);
    private static boolean isLink = false;

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        isLink = false;
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();
            x += widget.getScrollX();
            y += widget.getScrollY();
            int line = widget.getLayout().getLineForVertical(y);
            int offset = widget.getLayout().getOffsetForHorizontal(line, x);
            ClickableSpan[] spans = buffer.getSpans(offset, offset, ClickableSpan.class);
            if (spans.length != 0) {
                int start = buffer.getSpanStart(spans[0]);
                int end = buffer.getSpanEnd(spans[0]);
                isLink = true;
                if (action == MotionEvent.ACTION_DOWN) {
                    buffer.setSpan(mGray, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    spans[0].onClick(widget);
                    buffer.removeSpan(mGray);
                }
                return true;
            }
        } else {
            buffer.removeSpan(mGray);
        }
        return Touch.onTouchEvent(widget, buffer, event);
    }

    public boolean isLink() {
        boolean value = isLink;
        isLink = false;
        return value;
    }
}
