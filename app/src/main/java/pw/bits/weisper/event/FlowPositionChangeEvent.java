package pw.bits.weisper.event;

/**
 * Created by rzh on 16/3/30.
 */
public class FlowPositionChangeEvent {
    private int position;

    public FlowPositionChangeEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
