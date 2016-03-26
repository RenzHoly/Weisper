package pw.bits.weisper.event;

import pw.bits.weisper.model.bean.User;

/**
 * Created by rzh on 16/3/20.
 */
public class PictureFlowPositionChangeEvent {
    private User user;
    private String text;
    private boolean isOverlapping;

    public PictureFlowPositionChangeEvent(User user, String text, boolean isOverlapping) {
        this.user = user;
        this.text = text;
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
