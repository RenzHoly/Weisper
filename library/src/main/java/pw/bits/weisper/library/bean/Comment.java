package pw.bits.weisper.library.bean;

/**
 * Created by rzh on 16/3/13.
 */
public class Comment {
    public String created_at;
    //评论创建时间

    public Long id;
    //评论的ID

    public String text;
    //评论的内容

    public String source;
    //评论的来源

    public User user;
    //评论作者的用户信息字段 详细

    public String mid;
    //评论的MID

    public String idstr;
    //字符串型的评论ID

    public Status status;
    //评论的微博信息字段 详细

    public Comment reply_comment;
    //评论来源评论，当本评论属于对另一评论的回复时返回此字段
}
