package pw.bits.weisper.view.widget;

import android.content.Context;
import android.view.View;
import android.widget.PopupMenu;

import org.greenrobot.eventbus.EventBus;

import pw.bits.weisper.R;
import pw.bits.weisper.event.OpenEditorEvent;
import pw.bits.weisper.data.bean.Status;

/**
 * Created by rzh on 16/3/28.
 */
public class StatusPopupMenu extends PopupMenu {
    private Status status;

    public StatusPopupMenu(Context context, View anchor) {
        super(context, anchor);
        init();
    }

    public StatusPopupMenu(Context context, View anchor, int gravity) {
        super(context, anchor, gravity);
        init();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    private void init() {
        getMenuInflater().inflate(R.menu.menu_toolbar, getMenu());
        setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_status_repost: {
                    EventBus.getDefault().post(new OpenEditorEvent(status));
                    return true;
                }
                default: {
                    return false;
                }
            }
        });
    }
}
