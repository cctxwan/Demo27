package com.cc.model;

import java.util.List;

public class setJokeNew {
    /**
     * reason : Success
     * result : {"data":[{"content":"@驱逐舰hd&nbsp; ，每天除了发些负能量的东西，就没见这货发过好东西！！！","hashId":"c192f527a4d81686a15ced58e619778a","unixtime":1535590501,"updatetime":"2018-08-30 08:55:01"},{"content":"一个多年未见的同学加你好友，有如下可能    1.他要结婚了    2.她要做微商了    3.他家孩子参赛要投票了","hashId":"9db4d4d7a2f1e19e47b3bbeed5c9d1c2","unixtime":1535547303,"updatetime":"2018-08-29 20:55:03"},{"content":"下班回家路上，我和老公俩都很饿，彼此诉说着饥饿的感觉。老公突然说：等以后有孩子了你就不怕了，饿了的时候，自己捧起奶来就有奶喝了。我还没来得及对付，他又补充了一句：这就叫自取其乳。我。。。","hashId":"a6e312aa66a369d30469dddd2e06b744","unixtime":1535446501,"updatetime":"2018-08-28 16:55:01"},{"content":"女人最大的敌人，其实是自己的情绪。而男人最大的敌人，也是女人的情绪。","hashId":"0ea4d13c77f01d698369d9765e1c5730","unixtime":1535446501,"updatetime":"2018-08-28 16:55:01"},{"content":"婚后的女士们，你老公抽烟，你就买个小耳钉；你老公喝酒，你就买个小戒指；你老公应酬你也不要闹，买上一条小项链。多年后，身缠万贯是你，黄金万两是你，钻戒炫富是你，荣华富贵皆是你。而他。。。只是一个糟老头。","hashId":"909a5215d8c538e969e95a0311a6a656","unixtime":1535446501,"updatetime":"2018-08-28 16:55:01"},{"content":"读书时没什么钱，都是买打折的衣服。工作后就不一样了，打折的也买不起了。","hashId":"7a17101d95c8dff97e4625f612d5a3c5","unixtime":1535446501,"updatetime":"2018-08-28 16:55:01"},{"content":"致男友：我跟你撒娇卖萌嘤嘤嘤用叠词说话，是希望激起你的保护欲和男人力，不是让你跟我学。。。","hashId":"114d7197bbd974ecb9db4faf68ff9a11","unixtime":1535446501,"updatetime":"2018-08-28 16:55:01"},{"content":"80这个尴尬的年龄，谈爱情已老，谈死太早，和年轻人一起谈经历幼稚，和老人一起谈故事又太小，闲在家无聊，出去疯怕吵，时尚说你妖，朴素说你老，觉得累了刚想消极一下，回头一看，上有老下有小，不努力赚钱死都死不了。加油吧！人生百味，各有体会！","hashId":"338d0c7204448391c01375833a7dd6c8","unixtime":1535446501,"updatetime":"2018-08-28 16:55:01"},{"content":"女友说她要离开我，只因我说话太像新闻节目主持人。稍后为您带来详细报道！","hashId":"70d00610e81255ced2f78d4fab5f03ff","unixtime":1535446501,"updatetime":"2018-08-28 16:55:01"},{"content":"吾日三省吾身。是谁带来远古的呼唤？是谁留下千年的期盼？是谁日夜遥望着蓝天？","hashId":"ccae09eb4fc9bb1d80c26c5a3d7c6bf0","unixtime":1535446501,"updatetime":"2018-08-28 16:55:01"}]}
     * error_code : 0
     */

    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * content : @驱逐舰hd&nbsp; ，每天除了发些负能量的东西，就没见这货发过好东西！！！
             * hashId : c192f527a4d81686a15ced58e619778a
             * unixtime : 1535590501
             * updatetime : 2018-08-30 08:55:01
             */

            private String content;
            private String hashId;
            private int unixtime;
            private String updatetime;

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

            public String getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(String updatetime) {
                this.updatetime = updatetime;
            }
        }
    }
}
