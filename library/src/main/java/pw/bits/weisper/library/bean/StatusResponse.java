package pw.bits.weisper.library.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rzh on 16/3/15.
 */
public class StatusResponse {
    List<Status> items = new ArrayList<>();

    public List<Status> getItems() {
        return items;
    }

    public Long getSinceId() {
        return items.size() == 0 ? 0L : items.get(0).id;
    }

    public Long getMaxId() {
        return items.size() == 0 ? 0L : items.get(items.size() - 1).id - 1;
    }
}
