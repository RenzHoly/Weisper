package pw.bits.weisper.event;

import pw.bits.weisper.library.bean.Status;
import pw.bits.weisper.library.bean.User;

/**
 * Created by rzh on 16/3/20.
 */
public class PictureFlowPositionChangeEvent {
    private User user;
    private String text;
    private boolean isOverlapping;

    public PictureFlowPositionChangeEvent(Status status, boolean isOverlapping) {
        this.user = status.getUser();
        this.text = status.getText();
        this.isOverlapping = isOverlapping;
    }

    public PictureFlowPositionChangeEvent(boolean isOverlapping) {
        this.isOverlapping = isOverlapping;
    }

    public String getText() {
        return text;
    }

    public User getUser() {
        return user;
    }

    public boolean isOverlapping() {
        return isOverlapping;
    }
}
