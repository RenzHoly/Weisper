package pw.bits.weisper.model.bean;

/**
 * Created by rzh on 16/3/13.
 */
public class Privacy {
    public Integer comment;
    //是否可以评论我的微博，0：所有人、1：关注的人、2：可信用户

    public Integer geo;
    //是否开启地理信息，0：不开启、1：开启

    public Integer message;
    //是否可以给我发私信，0：所有人、1：我关注的人、2：可信用户

    public Integer realname;
    //是否可以通过真名搜索到我，0：不可以、1：可以

    public Integer badge;
    //勋章是否可见，0：不可见、1：可见

    public Integer mobile;
    //是否可以通过手机号码搜索到我，0：不可以、1：可以

    public Integer webim;
    //是否开启webim， 0：不开启、1：开启
}
