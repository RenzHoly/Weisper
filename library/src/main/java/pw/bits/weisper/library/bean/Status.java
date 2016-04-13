package pw.bits.weisper.library.bean;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
    public Date date;
    //微博创建时间

    public Long id;
    //微博ID

    public String text;
    //微博信息内容

    String source;
    //微博来源

    @Nullable
    public User user;
    //微博作者的用户信息字段 详细

    @SerializedName("retweeted_status")
    @Nullable
    public Status retweeted;
    //被转发的原微博信息字段，当该微博为转发微博时返回 详细

    @SerializedName("reposts_count")
    public int reposts = 0;
    //转发数

    @SerializedName("comments_count")
    public int comments = 0;
    //评论数

    @SerializedName("attitudes_count")
    public int attitudes = 0;
    //表态数

    @SerializedName("pic_urls")
    @Nullable
    List<Picture> pictures;
    //微博配图ID。多图时返回多图ID，用来拼接图片url。用返回字段thumbnail_pic的地址配上该返回字段的图片ID，即可得到多个图片url。

    @Expose
    public boolean fake = false;

    public String getSource() {
        return source != null && source.length() > 0 ?
                String.format("%s 来自 %s", new PrettyTime(Locale.CHINA).format(date).replace(" ", ""), Html.fromHtml(source)) :
                new PrettyTime(Locale.CHINA).format(date).replace(" ", "");
    }

    @NonNull
    public List<Picture> getPictures() {
        List<Picture> result = retweeted == null ? pictures : retweeted.pictures;
        return result == null ? new ArrayList<>() : result;
    }

    public String getRetweetedText() {
        return retweeted != null && retweeted.user != null ? String.format("@%s:%s", retweeted.user.screen_name, retweeted.text) : "抱歉，此微博已被作者删除";
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
