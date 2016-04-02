package pw.bits.weisper.event;

import pw.bits.weisper.library.bean.Status;

/**
 * Created by rzh on 16/3/30.
 */
public class RemoveLoadViewHolderEvent {
    private Status status;

    public RemoveLoadViewHolderEvent(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
