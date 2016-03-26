package pw.bits.weisper.model.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rzh on 16/3/13.
 */
public class Status {
    public Date created_at;
    //微博创建时间

    public Long id;
    //微博ID

    public String mid;
    //微博MID

    public String idstr;
    //字符串型的微博ID

    public String text;
    //微博信息内容

    public String source;
    //微博来源

    public Boolean favorited;
    //是否已收藏，true：是，false：否

    public Boolean truncated;
    //是否被截断，true：是，false：否

    public String in_reply_to_status_id;
    //（暂未支持）回复ID

    public String in_reply_to_user_id;
    //（暂未支持）回复人UID

    public String in_reply_to_screen_name;
    //（暂未支持）回复人昵称

    public String thumbnail_pic;
    //缩略图片地址，没有时不返回此字段

    public String bmiddle_pic;
    //中等尺寸图片地址，没有时不返回此字段

    public String original_pic;
    //原始图片地址，没有时不返回此字段

    public Geo geo;
    //地理信息字段 详细

    public User user;
    //微博作者的用户信息字段 详细

    public Status retweeted_status;
    //被转发的原微博信息字段，当该微博为转发微博时返回 详细

    public Integer reposts_count;
    //转发数

    public Integer comments_count;
    //评论数

    public Integer attitudes_count;
    //表态数

    public Integer mlevel;
    //暂未支持

    public Object visible;
    //微博的可见性及指定可见分组信息。该object中type取值，0：普通微博，1：私密微博，3：指定分组微博，4：密友微博；list_id为分组的组号

    public List<Picture> pic_urls = new ArrayList<>();
    //微博配图ID。多图时返回多图ID，用来拼接图片url。用返回字段thumbnail_pic的地址配上该返回字段的图片ID，即可得到多个图片url。

    public Object ad;
    //微博流内的推广微博ID

    @Override
    public boolean equals(Object o) {
        return o instanceof Status && this.id.equals(((Status) o).id);
    }
}
