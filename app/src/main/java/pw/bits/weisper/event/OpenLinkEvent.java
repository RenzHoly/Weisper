package pw.bits.weisper.event;

/**
 * Created by rzh on 16/3/29.
 */
public class OpenLinkEvent {
    private final String value;

    public OpenLinkEvent(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
