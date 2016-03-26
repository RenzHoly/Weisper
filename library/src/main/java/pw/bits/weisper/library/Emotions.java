package pw.bits.weisper.library;

import java.util.HashMap;
import java.util.Map;

import pw.bits.library.R;

/**
 * Created by rzh on 16/3/15.
 */
public class Emotions {
    private static Emotions instance;

    public Map<String, Integer> map = new HashMap<>();

    public static Emotions instance() {
        if (instance == null) {
            instance = new Emotions();
        }
        return instance;
    }

    public Emotions() {
        map.put("→_→", R.mipmap.emotion_000);
        map.put("[微笑]", R.mipmap.emotion_001);
        map.put("[嘻嘻]", R.mipmap.emotion_002);
        map.put("[哈哈]", R.mipmap.emotion_003);
        map.put("[爱你]", R.mipmap.emotion_004);
        map.put("[挖鼻屎]", R.mipmap.emotion_005);
        map.put("[吃惊]", R.mipmap.emotion_006);
        map.put("[晕]", R.mipmap.emotion_007);
        map.put("[泪]", R.mipmap.emotion_008);
        map.put("[馋嘴]", R.mipmap.emotion_009);

        map.put("[抓狂]", R.mipmap.emotion_010);
        map.put("[哼]", R.mipmap.emotion_011);
        map.put("[可爱]", R.mipmap.emotion_012);
        map.put("[怒]", R.mipmap.emotion_013);
        map.put("[汗]", R.mipmap.emotion_014);
        map.put("[害羞]", R.mipmap.emotion_015);
        map.put("[睡觉]", R.mipmap.emotion_016);
        map.put("[钱]", R.mipmap.emotion_017);
        map.put("[偷笑]", R.mipmap.emotion_018);
        map.put("[笑cry]", R.mipmap.emotion_019);

        map.put("[doge]", R.mipmap.emotion_020);
        map.put("[喵喵]", R.mipmap.emotion_021);
        map.put("[酷]", R.mipmap.emotion_022);
        map.put("[衰]", R.mipmap.emotion_023);
        map.put("[闭嘴]", R.mipmap.emotion_024);
        map.put("[鄙视]", R.mipmap.emotion_025);
        map.put("[花心]", R.mipmap.emotion_026);
        map.put("[鼓掌]", R.mipmap.emotion_027);
        map.put("[悲伤]", R.mipmap.emotion_028);
        map.put("[思考]", R.mipmap.emotion_029);

        map.put("[生病]", R.mipmap.emotion_030);
        map.put("[亲亲]", R.mipmap.emotion_031);
        map.put("[怒骂]", R.mipmap.emotion_032);
        map.put("[太开心]", R.mipmap.emotion_033);
        map.put("[懒得理你]", R.mipmap.emotion_034);
        map.put("[右哼哼]", R.mipmap.emotion_035);
        map.put("[左哼哼]", R.mipmap.emotion_036);
        map.put("[嘘]", R.mipmap.emotion_037);
        map.put("[委屈]", R.mipmap.emotion_038);
        map.put("[吐]", R.mipmap.emotion_039);

        map.put("[可怜]", R.mipmap.emotion_040);
        map.put("[打哈气]", R.mipmap.emotion_041);
        map.put("[挤眼]", R.mipmap.emotion_042);
        map.put("[失望]", R.mipmap.emotion_043);
        map.put("[顶]", R.mipmap.emotion_044);
        map.put("[疑问]", R.mipmap.emotion_045);
        map.put("[困]", R.mipmap.emotion_046);
        map.put("[感冒]", R.mipmap.emotion_047);
        map.put("[拜拜]", R.mipmap.emotion_048);
        map.put("[黑线]", R.mipmap.emotion_049);

        map.put("[阴险]", R.mipmap.emotion_050);
        map.put("[打脸]", R.mipmap.emotion_051);
        map.put("[傻眼]", R.mipmap.emotion_052);
        map.put("[互粉]", R.mipmap.emotion_053);
        map.put("[心]", R.mipmap.emotion_054);
        map.put("[伤心]", R.mipmap.emotion_055);
        map.put("[猪头]", R.mipmap.emotion_056);
        map.put("[熊猫]", R.mipmap.emotion_057);
        map.put("[兔子]", R.mipmap.emotion_058);
        map.put("[握手]", R.mipmap.emotion_059);

        map.put("[作揖]", R.mipmap.emotion_060);
        map.put("[赞]", R.mipmap.emotion_061);
        map.put("[耶]", R.mipmap.emotion_062);
        map.put("[good]", R.mipmap.emotion_063);
        map.put("[弱]", R.mipmap.emotion_064);
        map.put("[不要]", R.mipmap.emotion_065);
        map.put("[ok]", R.mipmap.emotion_066);
        map.put("[haha]", R.mipmap.emotion_067);
        map.put("[来]", R.mipmap.emotion_068);
        map.put("[威武]", R.mipmap.emotion_069);

        map.put("[鲜花]", R.mipmap.emotion_070);
        map.put("[钟]", R.mipmap.emotion_071);
        map.put("[浮云]", R.mipmap.emotion_072);
        map.put("[飞机]", R.mipmap.emotion_073);
        map.put("[月亮]", R.mipmap.emotion_074);
        map.put("[太阳]", R.mipmap.emotion_075);
        map.put("[微风]", R.mipmap.emotion_076);
        map.put("[下雨]", R.mipmap.emotion_077);
        map.put("[给力]", R.mipmap.emotion_078);
        map.put("[神马]", R.mipmap.emotion_079);

        map.put("[围观]", R.mipmap.emotion_080);
        map.put("[话筒]", R.mipmap.emotion_081);
        map.put("[奥特曼]", R.mipmap.emotion_082);
        map.put("[草泥马]", R.mipmap.emotion_083);
        map.put("[萌]", R.mipmap.emotion_084);
        map.put("[囧]", R.mipmap.emotion_085);
        map.put("[织]", R.mipmap.emotion_086);
        map.put("[礼物]", R.mipmap.emotion_087);
        map.put("[喜]", R.mipmap.emotion_088);
        map.put("[围脖]", R.mipmap.emotion_089);

        map.put("[音乐]", R.mipmap.emotion_090);
        map.put("[绿丝带]", R.mipmap.emotion_091);
        map.put("[蛋糕]", R.mipmap.emotion_092);
        map.put("[蜡烛]", R.mipmap.emotion_093);
        map.put("[干杯]", R.mipmap.emotion_094);
        map.put("[男孩儿]", R.mipmap.emotion_095);
        map.put("[女孩儿]", R.mipmap.emotion_096);
        map.put("[肥皂]", R.mipmap.emotion_097);
        map.put("[照相机]", R.mipmap.emotion_098);
        map.put("[沙尘暴]", R.mipmap.emotion_099);
    }
}
