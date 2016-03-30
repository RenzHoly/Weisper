package pw.bits.weisper.event;

/**
 * Created by rzh on 16/3/30.
 */
public class RemoveLoadViewHolderEvent {
    private int position;

    public RemoveLoadViewHolderEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
