package com.cc.model;

import java.util.List;

/**
 * 根据关键字查询返回的结果
 */
public class setZGByStrInfo {
    @Override
    public String toString() {
        return "setZGByStrInfo{" +
                "reason='" + reason + '\'' +
                ", error_code=" + error_code +
                ", result=" + result +
                '}';
    }

    /**
     * reason : successed
     * result : [{"id":"43f5cb95ed8ebf07a9c9e6d3afb05006","title":"龙虾","des":"梦见龙虾，表示许多的恩惠，大量的财富都要赋予你。"},{"id":"8db836cac7f5f2574c9f12712722a0f3","title":"恐龙","des":"梦见像恐龙一类的远古动物，一般象征母亲，或是对某种逝去情感的怀念和依恋。做这样的梦，还显示出做梦人在潜意识中希望能回归自然，过纯真自然的生活。"},{"id":"c32b88eb8b9ee9094f00a5f9d2a4082a","title":"红色的龙","des":""},{"id":"134730816d203a5f8baffa07c558d571","title":"变色龙","des":"梦见变色龙缠绕着你的心上人，表示好将背叛你，如果你能改变自己，她的运气就会好转。一般来说，变色龙表示欺骗，或把自己的幸福建立在别人的痛苦之上。"},{"id":"b9661d59ca973de5e40c9d6ba238d47a","title":"桂圆 龙眼","des":"梦见桂圆(龙眼)，大吉大利，生活圆满。"},{"id":"fee90f8c94caad3bd1312cd2430150b8","title":"水龙头","des":"梦里的水龙头有两方面含义。"},{"id":"10a41b0f93115c7b4b8b5466da0df165","title":"艺术沙龙","des":"梦见参加艺术沙龙，表示你的生活圈子会有不幸出现，你将努力装着不在意的样子，但暗中却不得不注意别人的动静。"},{"id":"20b76e92295442b8619efe348c3299de","title":"龙袍","des":"龙袍，帝王之物。"},{"id":"dce06b516e88023f5e9589b83ddd2942","title":"龙舟","des":"梦见在龙舟比赛中划船，可能遇盗贼，尤其在餐厅或咖啡店，要特别小心保管所带的财物。"},{"id":"b71cf34ff6fd7a8a7b95d7c8b545452b","title":"龙舟比赛","des":"梦见在龙舟比赛中划船可能遇盗难。尤其在餐厅或咖啡店，要特别小心保管所带的财物。"},{"id":"4eb69826577b7d14449fe19b1ba3cb2e","title":"龙凤胎","des":"周公解梦 梦见龙凤胎"},{"id":"fd2ce32e77efdde5839ae318a0067c0e","title":"龙","des":"梦到龙，表明你一定是个非同寻常的人，上天将赠予你荣华富贵的成功的机遇，要好好把握。"},{"id":"5283b361b969d97ef1fb98c916bd64ce","title":"龙卷风","des":"梦见吹龙卷风，预示有可能会出现失而复得，因祸得福的事情。提醒你遇到不顺心的事时，不要烦恼，打起精神，很快就将雨过天晴，一切都会更好!另一方面，有可能预示你会重新找回失散的朋友，或有贵客登门来访。"},{"id":"8f474311f1811b5eb6ebfd631e8d796b","title":"飞龙","des":"男人梦见飞龙，做事会有阻碍，不会一帆风顺，目标难以达成。"},{"id":"4c2b26432a6a5b1df3a9876a4de837ce","title":"龙王","des":"梦见龙王，吉兆，是发财的预兆。"},{"id":"df9c4eaee750e546b705fe827972e29b","title":"金龙","des":"龙是吉祥物，金龙更是吉上加吉的圣物。"},{"id":"2f919292532cd2b668e8c554d96bb6b1","title":"白龙","des":"白龙是吉利的象征，频频出现在东方的传说和神话中，它给人带来名誉和幸运。会得到上天赐予的名誉和财富，而这种财富应该用于对世界有益的地方。"},{"id":"6fada5bf6f425fe51dd135d1fcebc238","title":"蛟龙","des":"蛟龙是一个吉祥的梦，是好梦。"},{"id":"cec5fa9fd9868bec05a2e66800d959ab","title":"龙蛇","des":"梦见龙蛇进入家里，预示着会发财。"},{"id":"40d2f7fe8ce43c33179edc5cd1d3c97e","title":"车水马龙","des":"梦见车水马龙的景象，象征财富和运气。"},{"id":"66b3e0d57d53db77d6dd83daf62be26a","title":"龙斗 龙头","des":"梦见龙斗,预示会有较多的闲言碎语。"}]
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
         * id : 43f5cb95ed8ebf07a9c9e6d3afb05006
         * title : 龙虾
         * des : 梦见龙虾，表示许多的恩惠，大量的财富都要赋予你。
         */

        private String id;
        private String title;
        private String des;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }
}
