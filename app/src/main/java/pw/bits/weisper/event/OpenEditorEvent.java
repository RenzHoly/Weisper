package pw.bits.weisper.event;

/**
 * Created by rzh on 16/3/28.
 */
public class OpenEditorEvent {
    private long id;

    public OpenEditorEvent(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
