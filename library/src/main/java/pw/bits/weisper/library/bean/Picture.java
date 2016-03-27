package pw.bits.weisper.library.bean;

/**
 * Created by rzh on 16/3/14.
 */
public class Picture {
    public String thumbnail_pic;

    public String thumbnail() {
        return thumbnail_pic;
    }

    public String middle() {
        return thumbnail_pic.replace("/thumbnail/", "/bmiddle/");
    }

    public String large() {
        return thumbnail_pic.replace("/thumbnail/", "/large/");
    }
}