package com.cc.model;

import java.util.List;

public class setByBS_Info {
    /**
     * reason : 返回成功
     * result : {"list":[{"id":"2936e7dc066251fe","zi":"卜","py":"bo,bu","wubi":"hhy","pinyin":"bo,bǔ","bushou":"卜","bihua":"2"},{"id":"a259e0c4e964a288","zi":"卞","py":"bian","wubi":"yhu","pinyin":"biàn","bushou":"卜","bihua":"4"},{"id":"32b31800eff324f8","zi":"卟","py":"bu","wubi":"khy","pinyin":"bǔ","bushou":"卜","bihua":"5"},{"id":"1a9bba4f34b07850","zi":"卢","py":"lu","wubi":"hne","pinyin":"lú","bushou":"卜","bihua":"5"},{"id":"f6bad0e0d6110c2e","zi":"占","py":"zhan","wubi":"hkf","pinyin":"zhàn,zhān","bushou":"卜","bihua":"5"},{"id":"ac2b875eca7303c0","zi":"卤","py":"lu","wubi":"hlqi","pinyin":"lǔ","bushou":"卜","bihua":"7"},{"id":"533086f114b0cfa9","zi":"卣","py":"you","wubi":"hlnf","pinyin":"yǒu","bushou":"卜","bihua":"7"},{"id":"223f5cc87c65fd42","zi":"卦","py":"gua","wubi":"ffhy","pinyin":"guà","bushou":"卜","bihua":"8"},{"id":"85981418b5c875bb","zi":"卧","py":"wo","wubi":"ahnh","pinyin":"wò","bushou":"卜","bihua":"8"},{"id":"67958b9ef1afdeaf","zi":"卨","py":"xie","wubi":"hmnk","pinyin":"xiè","bushou":"卜","bihua":"10"}],"page":1,"pagesize":50,"totalpage":1,"totalcount":10}
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
        /**
         * list : [{"id":"2936e7dc066251fe","zi":"卜","py":"bo,bu","wubi":"hhy","pinyin":"bo,bǔ","bushou":"卜","bihua":"2"},{"id":"a259e0c4e964a288","zi":"卞","py":"bian","wubi":"yhu","pinyin":"biàn","bushou":"卜","bihua":"4"},{"id":"32b31800eff324f8","zi":"卟","py":"bu","wubi":"khy","pinyin":"bǔ","bushou":"卜","bihua":"5"},{"id":"1a9bba4f34b07850","zi":"卢","py":"lu","wubi":"hne","pinyin":"lú","bushou":"卜","bihua":"5"},{"id":"f6bad0e0d6110c2e","zi":"占","py":"zhan","wubi":"hkf","pinyin":"zhàn,zhān","bushou":"卜","bihua":"5"},{"id":"ac2b875eca7303c0","zi":"卤","py":"lu","wubi":"hlqi","pinyin":"lǔ","bushou":"卜","bihua":"7"},{"id":"533086f114b0cfa9","zi":"卣","py":"you","wubi":"hlnf","pinyin":"yǒu","bushou":"卜","bihua":"7"},{"id":"223f5cc87c65fd42","zi":"卦","py":"gua","wubi":"ffhy","pinyin":"guà","bushou":"卜","bihua":"8"},{"id":"85981418b5c875bb","zi":"卧","py":"wo","wubi":"ahnh","pinyin":"wò","bushou":"卜","bihua":"8"},{"id":"67958b9ef1afdeaf","zi":"卨","py":"xie","wubi":"hmnk","pinyin":"xiè","bushou":"卜","bihua":"10"}]
         * page : 1
         * pagesize : 50
         * totalpage : 1
         * totalcount : 10
         */

        private int page;
        private int pagesize;
        private int totalpage;
        private int totalcount;
        private List<ListBean> list;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPagesize() {
            return pagesize;
        }

        public void setPagesize(int pagesize) {
            this.pagesize = pagesize;
        }

        public int getTotalpage() {
            return totalpage;
        }

        public void setTotalpage(int totalpage) {
            this.totalpage = totalpage;
        }

        public int getTotalcount() {
            return totalcount;
        }

        public void setTotalcount(int totalcount) {
            this.totalcount = totalcount;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 2936e7dc066251fe
             * zi : 卜
             * py : bo,bu
             * wubi : hhy
             * pinyin : bo,bǔ
             * bushou : 卜
             * bihua : 2
             */

            private String id;
            private String zi;
            private String py;
            private String wubi;
            private String pinyin;
            private String bushou;
            private String bihua;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getZi() {
                return zi;
            }

            public void setZi(String zi) {
                this.zi = zi;
            }

            public String getPy() {
                return py;
            }

            public void setPy(String py) {
                this.py = py;
            }

            public String getWubi() {
                return wubi;
            }

            public void setWubi(String wubi) {
                this.wubi = wubi;
            }

            public String getPinyin() {
                return pinyin;
            }

            public void setPinyin(String pinyin) {
                this.pinyin = pinyin;
            }

            public String getBushou() {
                return bushou;
            }

            public void setBushou(String bushou) {
                this.bushou = bushou;
            }

            public String getBihua() {
                return bihua;
            }

            public void setBihua(String bihua) {
                this.bihua = bihua;
            }
        }
    }
}
