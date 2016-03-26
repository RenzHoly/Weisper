package pw.bits.weisper.model.bean;

/**
 * Created by rzh on 16/3/13.
 */
public class User {
    public Long id;
    //用户UID

    public String idstr;
    //字符串型的用户UID

    public String screen_name;
    //用户昵称

    public String name;
    //友好显示名称

    public Integer province;
    //用户所在省级ID

    public Integer city;
    //用户所在城市ID

    public String location;
    //用户所在地

    public String description;
    //用户个人描述

    public String url;
    //用户博客地址

    public String profile_image_url;
    //用户头像地址（中图），50×50像素

    public String profile_url;
    //用户的微博统一URL地址

    public String domain;
    //用户的个性化域名

    public String weihao;
    //用户的微号

    public String gender;
    //性别，m：男、f：女、n：未知

    public Integer followers_count;
    //粉丝数

    public Integer friends_count;
    //关注数

    public Integer statuses_count;
    //微博数

    public Integer favourites_count;
    //收藏数

    public String created_at;
    //用户创建（注册）时间

    public Boolean following;
    //暂未支持

    public Boolean allow_all_act_msg;
    //是否允许所有人给我发私信，true：是，false：否

    public Boolean geo_enabled;
    //是否允许标识用户的地理位置，true：是，false：否

    public Boolean verified;
    //是否是微博认证用户，即加V用户，true：是，false：否

    public Integer verified_type;
    //暂未支持

    public String remark;
    //用户备注信息，只有在查询用户关系时才返回此字段

    public Status status;
    //用户的最近一条微博信息字段

    public Boolean allow_all_comment;
    //是否允许所有人对我的微博进行评论，true：是，false：否

    public String avatar_large;
    //用户头像地址（大图），180×180像素

    public String avatar_hd;
    //用户头像地址（高清），高清头像原图

    public String verified_reason;
    //认证原因

    public Boolean follow_me;
    //该用户是否关注当前登录用户，true：是，false：否

    public Integer online_status;
    //用户的在线状态，0：不在线、1：在线

    public Integer bi_followers_count;
    //用户的互粉数

    public String lang;
    //用户当前的语言版本，zh-cn：简体中文，zh-tw：繁体中文，en：英语
}
