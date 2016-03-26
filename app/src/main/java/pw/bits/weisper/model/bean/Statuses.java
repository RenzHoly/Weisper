package pw.bits.weisper.model.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rzh on 16/3/15.
 */
public class Statuses {
    List<Status> statuses = new ArrayList<>();

    public List<Status> getStatuses() {
        return statuses;
    }

    public Long getSinceId() {
        if (statuses.size() == 0) {
            return 0L;
        }
        return statuses.get(0).id;
    }

    public Long getMaxId() {
        if (statuses.size() == 0) {
            return 0L;
        }
        return statuses.get(statuses.size() - 1).id;
    }
}
