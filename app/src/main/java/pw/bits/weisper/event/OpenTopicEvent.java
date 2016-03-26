package pw.bits.weisper.event;

/**
 * Created by rzh on 16/3/24.
 */
public class OpenTopicEvent {
    private String topic;

    public OpenTopicEvent(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic == null ? null : topic.replace("#", "");
    }
}
