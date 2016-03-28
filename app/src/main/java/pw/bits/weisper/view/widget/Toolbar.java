package pw.bits.weisper.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import pw.bits.weisper.R;
import pw.bits.weisper.library.bean.User;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by rzh on 16/3/28.
 */
public class Toolbar extends android.support.v7.widget.Toolbar {
    public Toolbar(Context context) {
        super(context);
        init();
    }

    public Toolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Toolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }

    public void setAvatar(Observable<User> observable) {
        ImageView avatar = (ImageView) findViewById(R.id.toolbar_avatar);
        observable.subscribe(new Subscriber<User>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(User user) {
                Glide.with(getContext()).load(user.profile_image_url).dontTransform().into(avatar);
            }
        });
    }
}
