package com.cc.HTTP;

/**
 * http的所有请求地址
 */
public class HTTPURLS {

    /** twofrag的头条请求地址 */
    public static final String toutiao = "http://v.juhe.cn/toutiao/index?type=top";

    /** twofrag的社会请求地址 */
    public static final String shehui = "http://v.juhe.cn/toutiao/index?type=shehui";

    /** twofrag的国内请求地址 */
    public static final String guonei = "http://v.juhe.cn/toutiao/index?type=guonei";

    /** twofrag的国际请求地址 */
    public static final String guoji = "http://v.juhe.cn/toutiao/index?type=guoji";

    /** twofrag的娱乐请求地址 */
    public static final String yule = "http://v.juhe.cn/toutiao/index?type=yule";

    /** twofrag的体育请求地址 */
    public static final String tiyu = "http://v.juhe.cn/toutiao/index?type=tiyu";

    /** twofrag的军事请求地址 */
    public static final String junshi = "http://v.juhe.cn/toutiao/index?type=junshi";

    /** twofrag的科技请求地址 */
    public static final String keji = "http://v.juhe.cn/toutiao/index?type=keji";

    /** twofrag的财经请求地址 */
    public static final String caijing = "http://v.juhe.cn/toutiao/index?type=caijing";

    /** twofrag的时尚请求地址 */
    public static final String shishang = "http://v.juhe.cn/toutiao/index?type=shishang";




    /** 请求历史上的今天的URL地址 */
    public static final String lssdjt = "http://api.juheapi.com/japi/toh?v=1.0";



    /** 请求时间戳之前的笑话的URL地址 */
    public static final String joke_desc = "http://v.juhe.cn/joke/content/list.php?sort=desc";
    /** 请求时间戳之后的笑话的URL地址 */
    public static final String joke_asc = "http://v.juhe.cn/joke/content/list.php?sort=asc";
    /** 请求获取最新的笑话的URL地址 */
    public static final String joke_new = "http://v.juhe.cn/joke/content/text.php?page=1";
    /** 请求随机获取笑话的URL地址 */
    public static final String joke_get = "http://v.juhe.cn/joke/randJoke.php?";

    /** 微信精选 */
    public static final String wxjx = "http://v.juhe.cn/weixin/query?";



    /** 根据汉字查询字典 */
    public static final String query = "http://v.juhe.cn/xhzd/query?";
    /** 汉字部首列表 */
    public static final String bushou = "http://v.juhe.cn/xhzd/bushou?";
    /** 汉字拼音列表 */
    public static final String pinyin = "http://v.juhe.cn/xhzd/pinyin?";
    /** 根据部首查询汉字 */
    public static final String querybs = "http://v.juhe.cn/xhzd/querybs?";
    /** 根据拼音查询汉字 */
    public static final String querypy = "http://v.juhe.cn/xhzd/querypy?";
    /** 根据id查询汉字完整信息 */
    public static final String queryid = "http://v.juhe.cn/xhzd/queryid?";



    /** 运势查询（可通过不同的参数查询不同的运势） */
    public static final String ysquery = "http://web.juhe.cn:8080/constellation/getAll?";

    /** 查询NBA */
    public static final String nba = "http://op.juhe.cn/onebox/basketball/nba?";
    /** 球队赛程赛事查询 */
    public static final String nba_sc = "http://op.juhe.cn/onebox/basketball/team?";
    /** 球队对战赛赛程查询 */
    public static final String nba_vs = "http://op.juhe.cn/onebox/basketball/combat?";



    /** 查询梦境所有类型 */
    public static final String zgjm_type = "http://v.juhe.cn/dream/category?";
    /** 根据关键字查询 */
    public static final String zgjm_bystring = "http://v.juhe.cn/dream/query?";
    /** 根据id查询 */
    public static final String zgjm_byid = "http://v.juhe.cn/dream/queryid?";

}
