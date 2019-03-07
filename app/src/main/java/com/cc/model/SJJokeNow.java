package com.cc.model;

import java.util.List;

public class SJJokeNow {
    /**
     * reason : success
     * result : [{"content":"今天在操场逛看到一对情侣，女的一直骂那个男的，但是那个男的特别有耐心的一直保持微笑。 后来女的也笑了说：\u201c死样，吵个架也不严肃。\u201d 下午和女友闹别扭了，没错，我学了，一直保持微笑。 女友反手就一巴掌：\u201c你笑你妈了比你笑。\u201d","hashId":"2AE423033CA93A7092A01899897DBD7A","unixtime":1443238403},{"content":"最近新神雕侠侣一出，前面的两个女同事天天在争自己是小龙女！今天还大打出手。 A女同事：\u201c你这个冒牌货，看我的断奶平胸掌！\u201d B女同事：\u201c你这个贱人敢冒充我，看我的天天痛经拳！\u201d 我在想该用什么武功才可将她们分开呢？","hashId":"FBB99996E3FC96533C1FE7C7719BE060","unixtime":1443238403},{"content":"某饭店养只鹦鹉挂在门口，有客到就说：\u201c你好欢迎光临！\u201d 一常客想：\u201c我快点进看你有何反应。\u201d 一天他蹭就跑进去了，鹦鹉说：\u201c丫的！吓我一跳！\u201d","hashId":"20A7E72F6ACD9706C5879F0339AD8307","unixtime":1443238403},{"content":"小北极熊问爸爸：\u201c爸爸爸爸，听人类说他们那里冬天都有暖气，暖气是什么啊？\u201d 北极熊爸爸把小北极熊的爪子放到屁股底下放了个屁：\u201c懂了吗？\u201d 小北极熊：\u201c懂了，不过爸爸以后少放屁哦，现在全球气候变暖，我们家都没了。\u201d","hashId":"8CC7F4ED8177810BA39EC8E1D0F307E1","unixtime":1443238403},{"content":"单身的原因，很大程度上就是因为太宅，没有机会接触到更多的异性。 不管你们信不信，反正我相信了。","hashId":"073E17FBF5CB3AD5BBE2579474685171","unixtime":1443240195},{"content":"儿子：\u201c爸爸，你当年是怎么追到妈妈的？\u201d 父亲：\u201c那天我在街上碰到一对情侣，女的是女神，男的是个龊胖子，我非常愤怒，于是走上前说，美女，做我女朋友吧，你男朋友那么丑。\u201d 儿子：\u201c我好像明白了什么。\u201d 父亲：\u201c明白你妹啊，后来我被胖子打成重伤，他赔了我一笔钱，我用那笔钱到乡下娶了你妈。\u201d","hashId":"E8E2509BDB0D3BF6908854C214EE8CB8","unixtime":1443240195},{"content":"话说，上初中时班主任是个女的，很凶。 有次午自习，班主任在屋里监督记录，大家都闷头写作业。 不敢一抬头，一哥们抬头偷瞄老师，发现老师不见了。 以为走了，大喊：\u201c狗日的班主任走了，大家high起来！\u201d 然后就看到在讲台后系鞋带的班主任幽幽地站了起来。","hashId":"4C95F77C5BCF0CF6DA06EBEF05D6B09B","unixtime":1443240195},{"content":"愚公决心要将大山移走，有人笑他说：\u201c山这么大你移不走的。\u201d 愚公回他说：\u201c我死了还有我儿子还有孙子呀。\u201d 那人呵呵一笑问到：\u201c你有女朋友吗？\u201d","hashId":"87CE39EB8C656EC0D919483BB1275C54","unixtime":1443240195},{"content":"一日刘备与曹操交战，被包围了，眼看就要全军覆没。 士兵急问刘备：\u201c主公，现在该如何是好？\u201d 刘备叹了口气说：\u201c投降吧。\u201d 于是士兵出去了。 过了一会只听曹操大骂：\u201c我去，大耳贼你居然使用生化武器！\u201d 遂退军。 刘备惊问部下：\u201c怎么回事？\u201d 士兵说：\u201c主公不是您让我们投翔的么？\u201d","hashId":"8773BFBBD97A49290027F3986ECAF985","unixtime":1443241994},{"content":"火车靠站，从火车窗口买烧鸡。 给过钱后，老板拿着烧鸡递过来死活不松手。 然后火车开走了，只留一个鸡头在我手里！ 嘛的！奸商！","hashId":"23C70FBEDDF9B8392DBFFA93D1D0A8DF","unixtime":1443241994}]
     * error_code : 0
     */

    private String reason;
    private int error_code;
    private List<ResultBean> result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * content : 今天在操场逛看到一对情侣，女的一直骂那个男的，但是那个男的特别有耐心的一直保持微笑。 后来女的也笑了说：“死样，吵个架也不严肃。” 下午和女友闹别扭了，没错，我学了，一直保持微笑。 女友反手就一巴掌：“你笑你妈了比你笑。”
         * hashId : 2AE423033CA93A7092A01899897DBD7A
         * unixtime : 1443238403
         */

        private String content;
        private String hashId;
        private int unixtime;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getHashId() {
            return hashId;
        }

        public void setHashId(String hashId) {
            this.hashId = hashId;
        }

        public int getUnixtime() {
            return unixtime;
        }

        public void setUnixtime(int unixtime) {
            this.unixtime = unixtime;
        }
    }
}
