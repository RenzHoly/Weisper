package pw.bits.weisper.event;

import pw.bits.weisper.bean.Status;

/**
 * Created by rzh on 16/3/20.
 */
public class PictureFlowPositionChangeEvent {
    private Status status;
    private boolean isOverlapping;

    public PictureFlowPositionChangeEvent(Status status, boolean isOverlapping) {
        this.status = status;
        this.isOverlapping = isOverlapping;
    }

    public PictureFlowPositionChangeEvent(boolean isOverlapping) {
        this.isOverlapping = isOverlapping;
    }

    public boolean isOverlapping() {
        return isOverlapping;
    }

    public Status getStatus() {
        return status;
    }
}
