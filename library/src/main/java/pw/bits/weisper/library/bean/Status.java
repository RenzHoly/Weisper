package pw.bits.weisper.library.bean;

import android.support.annotation.NonNull;
import android.text.Html;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by rzh on 16/3/13.
 */
public class Status {
    @SerializedName("created_at")
    Date date;
    //微博创建时间

    Long id;
    //微博ID

    String text;
    //微博信息内容

    String source;
    //微博来源

    User user;
    //微博作者的用户信息字段 详细

    @SerializedName("retweeted_status")
    Status retweeted;
    //被转发的原微博信息字段，当该微博为转发微博时返回 详细

    @SerializedName("reposts_count")
    int reposts = 0;
    //转发数

    @SerializedName("comments_count")
    int comments = 0;
    //评论数

    @SerializedName("attitudes_count")
    int attitudes = 0;
    //表态数

    @SerializedName("pic_urls")
    List<Picture> pictures;
    //微博配图ID。多图时返回多图ID，用来拼接图片url。用返回字段thumbnail_pic的地址配上该返回字段的图片ID，即可得到多个图片url。

    @Expose
    boolean fake = false;

    @NonNull
    public String getSource() {
        return source != null && source.length() > 0 ?
                String.format("%s 来自 %s", new PrettyTime(Locale.CHINA).format(date).replace(" ", ""), Html.fromHtml(source)) :
                new PrettyTime(Locale.CHINA).format(date).replace(" ", "");
    }

    @NonNull
    public List<Picture> getPictures() {
        List<Picture> result = retweeted == null ? pictures : retweeted.getPictures();
        return result == null ? new ArrayList<>() : result;
    }

    @NonNull
    public String getScreenName() {
        return user == null ? "" : user.screen_name;
    }

    @NonNull
    public String getRetweetedText() {
        return retweeted != null ? String.format("@%s:%s", retweeted.getScreenName(), retweeted.getText()) : "抱歉，此微博已被作者删除";
    }

    @NonNull
    public Status getRetweeted() {
        return retweeted == null ? new Status(0L) : retweeted;
    }

    public int getReposts() {
        return reposts;
    }

    public int getComments() {
        return comments;
    }

    public int getAttitudes() {
        return attitudes;
    }

    public boolean hasRetweeted() {
        return retweeted != null;
    }

    @NonNull
    public String getText() {
        return text == null ? "" : text;
    }

    @NonNull
    public String getAvatar() {
        return user == null ? "" : user.avatar_large;
    }

    @NonNull
    public Long getId() {
        return id == null ? 0L : id;
    }

    public boolean isFake() {
        return fake;
    }

    public Status(Long id) {
        this.id = id;
        this.fake = true;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Status && this.id.equals(((Status) o).id);
    }
}
