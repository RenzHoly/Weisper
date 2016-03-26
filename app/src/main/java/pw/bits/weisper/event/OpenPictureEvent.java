package pw.bits.weisper.event;

import java.util.ArrayList;
import java.util.List;

import pw.bits.weisper.model.bean.Picture;

/**
 * Created by rzh on 16/3/20.
 */
public class OpenPictureEvent {
    private ArrayList<String> pictureUrls = new ArrayList<>();
    private int position;

    public OpenPictureEvent(List<Picture> pictures, int position) {
        this.pictureUrls.clear();
        for (Picture picture : pictures) {
            this.pictureUrls.add(picture.large());
        }
        this.position = position;
    }

    public ArrayList<String> getPictureUrls() {
        return pictureUrls;
    }

    public int getPosition() {
        return position;
    }
}
