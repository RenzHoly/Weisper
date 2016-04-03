package pw.bits.weisper.library.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

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

    public String source;
    //微博来源

    public Geo geo;
    //地理信息字段 详细

    public User user;
    //微博作者的用户信息字段 详细

    @SerializedName("retweeted_status")
    public Status retweeted;
    //被转发的原微博信息字段，当该微博为转发微博时返回 详细

    @SerializedName("reposts_count")
    public Integer reposts;
    //转发数

    @SerializedName("comments_count")
    public Integer comments;
    //评论数

    @SerializedName("attitudes_count")
    public Integer attitudes;
    //表态数

    @SerializedName("pic_urls")
    public List<Picture> pictures;
    //微博配图ID。多图时返回多图ID，用来拼接图片url。用返回字段thumbnail_pic的地址配上该返回字段的图片ID，即可得到多个图片url。

    @Expose
    public boolean fake = false;

    public Status(Long id) {
        this.id = id;
        this.fake = true;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Status && this.id.equals(((Status) o).id);
    }
}
