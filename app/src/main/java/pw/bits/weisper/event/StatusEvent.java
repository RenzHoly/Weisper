package pw.bits.weisper.event;

/**
 * Created by rzh on 16/3/16.
 */
public class StatusEvent {
    private int addedCount = 0;

    public StatusEvent(int addedCount) {
        this.addedCount = addedCount;
    }

    public int getAddedCount() {
        return addedCount;
    }
}
