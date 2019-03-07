package com.cc.model;

import java.util.List;

/**
 * 周公解梦-梦境类型
 */
public class setZGTypeInfo {
    /**
     * reason : successed
     * result : [{"id":"1","name":"人物类","fid":"0"},{"id":"2","name":"其他类","fid":"0"},{"id":"3","name":"动物类","fid":"0"},{"id":"4","name":"娱乐类","fid":"0"},{"id":"5","name":"宗教类","fid":"0"},{"id":"6","name":"山水类","fid":"0"},{"id":"7","name":"建筑类","fid":"0"},{"id":"8","name":"恐怖类","fid":"0"},{"id":"9","name":"情爱类","fid":"0"},{"id":"10","name":"植物类","fid":"0"},{"id":"11","name":"活动类","fid":"0"},{"id":"12","name":"物品类","fid":"0"},{"id":"13","name":"生活类","fid":"0"},{"id":"14","name":"疾病类","fid":"0"},{"id":"15","name":"自然类","fid":"0"},{"id":"16","name":"身体类","fid":"0"},{"id":"17","name":"运动类","fid":"0"},{"id":"18","name":"食物类","fid":"0"},{"id":"19","name":"鬼神类","fid":"0"},{"id":"85","name":"哺乳类","fid":"3"},{"id":"86","name":"昆虫类","fid":"3"},{"id":"87","name":"冷血类","fid":"3"},{"id":"88","name":"鸟禽类","fid":"3"},{"id":"89","name":"其它动物","fid":"3"},{"id":"90","name":"鬼怪类","fid":"19"},{"id":"91","name":"其它鬼神","fid":"19"},{"id":"92","name":"神仙类","fid":"19"},{"id":"93","name":"动作类","fid":"11"},{"id":"94","name":"工作学习","fid":"11"},{"id":"95","name":"劳动类","fid":"11"},{"id":"96","name":"其它活动","fid":"11"},{"id":"97","name":"日常类","fid":"11"},{"id":"98","name":"医疗疾病","fid":"14"},{"id":"99","name":"场所类","fid":"7"},{"id":"100","name":"建筑类","fid":"7"},{"id":"101","name":"灾难罪恶","fid":"8"},{"id":"102","name":"其它","fid":"2"},{"id":"103","name":"时间节日","fid":"2"},{"id":"104","name":"数字形状","fid":"2"},{"id":"105","name":"颜色气味","fid":"2"},{"id":"106","name":"婚恋情感","fid":"9"},{"id":"107","name":"人物称谓","fid":"1"},{"id":"108","name":"地理环境","fid":"6"},{"id":"109","name":"身体器官","fid":"16"},{"id":"110","name":"感觉表情","fid":"13"},{"id":"111","name":"其它生活","fid":"13"},{"id":"112","name":"衣食住行","fid":"13"},{"id":"113","name":"食品饮料","fid":"18"},{"id":"114","name":"财物宝石","fid":"12"},{"id":"115","name":"服装饰品","fid":"12"},{"id":"116","name":"机械电器","fid":"12"},{"id":"117","name":"交通运输","fid":"12"},{"id":"118","name":"其它物品","fid":"12"},{"id":"119","name":"生活用品","fid":"12"},{"id":"120","name":"文化用品","fid":"12"},{"id":"121","name":"武器化学","fid":"12"},{"id":"122","name":"音乐舞蹈","fid":"12"},{"id":"123","name":"娱乐类","fid":"4"},{"id":"124","name":"运动类","fid":"17"},{"id":"125","name":"豆类","fid":"10"},{"id":"126","name":"瓜类","fid":"10"},{"id":"127","name":"果实","fid":"10"},{"id":"128","name":"花草","fid":"10"},{"id":"129","name":"粮食","fid":"10"},{"id":"130","name":"其它植物","fid":"10"},{"id":"131","name":"蔬菜","fid":"10"},{"id":"132","name":"树类","fid":"10"},{"id":"133","name":"自然现象","fid":"15"},{"id":"134","name":"宗教类","fid":"5"}]
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
         * id : 1
         * name : 人物类
         * fid : 0
         */

        private String id;
        private String name;
        private String fid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }
    }
}
