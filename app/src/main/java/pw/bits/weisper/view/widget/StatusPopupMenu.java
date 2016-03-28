package pw.bits.weisper.view.widget;

import android.content.Context;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import pw.bits.weisper.R;
import pw.bits.weisper.library.WeiboData;
import pw.bits.weisper.library.bean.Status;
import rx.Subscriber;

/**
 * Created by rzh on 16/3/28.
 */
public class StatusPopupMenu extends PopupMenu {
    private Status status;

    public StatusPopupMenu(Context context, View anchor) {
        super(context, anchor);
        init(context);
    }

    public StatusPopupMenu(Context context, View anchor, int gravity) {
        super(context, anchor, gravity);
        init(context);
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    private void init(Context context) {
        getMenuInflater().inflate(R.menu.menu_toolbar, getMenu());
        setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_status_repost: {
                    WeiboData.statusesRepost(status.id, null, false).subscribe(new Subscriber<Status>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(context, "转发失败", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNext(Status status) {
                            Toast.makeText(context, "转发成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return true;
                }
                default: {
                    return false;
                }
            }
        });
    }
}
