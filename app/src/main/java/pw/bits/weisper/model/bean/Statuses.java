package pw.bits.weisper.model.bean;

import android.support.v7.util.SortedList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rzh on 16/3/15.
 */
public class Statuses {
    public List<Status> statuses = new ArrayList<>();
    public Long since_id;
    public Long max_id;

    public static class StatusSortedList extends SortedList<Status> {
        public StatusSortedList(Callback<Status> callback) {
            super(Status.class, callback);
        }
    }
}
