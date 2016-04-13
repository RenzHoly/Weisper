package pw.bits.weisper.library.bean;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rzh on 16/3/15.
 */
public class StatusResponse {
    @SerializedName("statuses")
    List<Status> items;

    @NonNull
    public List<Status> getItems() {
        return items == null ? new ArrayList<>() : items;
    }

    public long getSinceId() {
        return items.size() == 0 ? 0L : items.get(0).id;
    }

    public long getMaxId() {
        return items.size() == 0 ? 0L : items.get(items.size() - 1).id - 1;
    }
}
