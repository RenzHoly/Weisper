package pw.bits.weisper.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by rzh on 16/3/29.
 */
public class WeiboEditText extends EditText {
    public WeiboEditText(Context context) {
        super(context);
        init();
    }

    public WeiboEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WeiboEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnFocusChangeListener((v, hasFocus) -> post(() -> {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (hasFocus) {
                imm.showSoftInput(WeiboEditText.this, InputMethodManager.SHOW_IMPLICIT);
            } else {
                imm.hideSoftInputFromWindow(getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        }));
    }
}
