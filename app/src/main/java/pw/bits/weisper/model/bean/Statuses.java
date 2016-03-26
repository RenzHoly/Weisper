package pw.bits.weisper.model.bean;

import android.support.v7.util.SortedList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rzh on 16/3/15.
 */
public class Statuses {
    List<Status> statuses = new ArrayList<>();

    public static class StatusSortedList extends SortedList<Status> {
        public StatusSortedList(Callback<Status> callback) {
            super(Status.class, callback);
        }
    }

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
