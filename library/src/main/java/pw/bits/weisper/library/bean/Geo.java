package pw.bits.weisper.library.bean;

/**
 * Created by rzh on 16/3/13.
 */
public class Geo {
    public String longitude;
    //经度坐标

    public String latitude;
    //维度坐标

    public String city;
    //所在城市的城市代码

    public String province;
    //所在省份的省份代码

    public String city_name;
    //所在城市的城市名称

    public String province_name;
    //所在省份的省份名称

    public String address;
    //所在的实际地址，可以为空

    public String pinyin;
    //地址的汉语拼音，不是所有情况都会返回该字段

    public String more;
    //更多信息，不是所有情况都会返回该字段
}
