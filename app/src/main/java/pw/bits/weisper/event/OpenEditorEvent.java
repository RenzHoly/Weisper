package pw.bits.weisper.event;

import pw.bits.weisper.data.bean.Status;

/**
 * Created by rzh on 16/3/28.
 */
public class OpenEditorEvent {
    private Status status;

    public OpenEditorEvent(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
