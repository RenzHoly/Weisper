package pw.bits.weisper.model.bean;

/**
 * Created by rzh on 16/3/13.
 */
public class UrlShort {
    public String url_short;
    //短链接

    public String url_long;
    //原始长链接

    public Integer type;
    //链接的类型，0：普通网页、1：视频、2：音乐、3：活动、5、投票

    public Boolean result;
    //短链的可用状态，true：可用、false：不可用。
}
