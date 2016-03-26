package pw.bits.weisper.event;

/**
 * Created by rzh on 16/3/16.
 */
public class OpenUserEvent {
    private String screenName;

    public OpenUserEvent(String screenName) {
        this.screenName = screenName;
    }

    public String getScreenName() {
        return screenName == null ? null : screenName.replace("@", "");
    }
}
