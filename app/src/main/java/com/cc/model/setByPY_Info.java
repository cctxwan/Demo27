package com.cc.model;

import java.util.List;

public class setByPY_Info {
    /**
     * reason : 返回成功
     * result : {"list":[{"id":"e204a7a91594ce5b","zi":"且","py":"ju,qie","wubi":"egd","pinyin":"jū,qiě","bushou":"一","bihua":"5"},{"id":"931b59df4a1646bc","zi":"举","py":"ju","wubi":"iwfh","pinyin":"jǔ","bushou":"丶","bihua":"9"},{"id":"94d549af5a753da8","zi":"具","py":"ju","wubi":"hwu","pinyin":"jù","bushou":"八","bihua":"8"},{"id":"dbb6a73176f958d6","zi":"匊","py":"ju","wubi":"","pinyin":"jū","bushou":"勹","bihua":"8"},{"id":"449d31209403c3d4","zi":"剧","py":"ju","wubi":"ndjh","pinyin":"jù","bushou":"刂","bihua":"10"},{"id":"4570974f39dbb21a","zi":"劇","py":"ju","wubi":"hae","pinyin":"jù","bushou":"刂","bihua":"15"},{"id":"c70ba3106c299833","zi":"邭","py":"ju","wubi":"","pinyin":"jù","bushou":"阝","bihua":"7"},{"id":"712b0ac03996295b","zi":"陱","py":"ju","wubi":"bqo","pinyin":"jū","bushou":"阝","bihua":"10"},{"id":"2b413d05c7660625","zi":"郹","py":"ju","wubi":"hdbh","pinyin":"jú","bushou":"阝","bihua":"11"},{"id":"347c0c6a9a858c03","zi":"凥","py":"ju","wubi":"nmv","pinyin":"jū","bushou":"几","bihua":"5"}],"page":1,"pagesize":10,"totalpage":17,"totalcount":166}
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
         * list : [{"id":"e204a7a91594ce5b","zi":"且","py":"ju,qie","wubi":"egd","pinyin":"jū,qiě","bushou":"一","bihua":"5"},{"id":"931b59df4a1646bc","zi":"举","py":"ju","wubi":"iwfh","pinyin":"jǔ","bushou":"丶","bihua":"9"},{"id":"94d549af5a753da8","zi":"具","py":"ju","wubi":"hwu","pinyin":"jù","bushou":"八","bihua":"8"},{"id":"dbb6a73176f958d6","zi":"匊","py":"ju","wubi":"","pinyin":"jū","bushou":"勹","bihua":"8"},{"id":"449d31209403c3d4","zi":"剧","py":"ju","wubi":"ndjh","pinyin":"jù","bushou":"刂","bihua":"10"},{"id":"4570974f39dbb21a","zi":"劇","py":"ju","wubi":"hae","pinyin":"jù","bushou":"刂","bihua":"15"},{"id":"c70ba3106c299833","zi":"邭","py":"ju","wubi":"","pinyin":"jù","bushou":"阝","bihua":"7"},{"id":"712b0ac03996295b","zi":"陱","py":"ju","wubi":"bqo","pinyin":"jū","bushou":"阝","bihua":"10"},{"id":"2b413d05c7660625","zi":"郹","py":"ju","wubi":"hdbh","pinyin":"jú","bushou":"阝","bihua":"11"},{"id":"347c0c6a9a858c03","zi":"凥","py":"ju","wubi":"nmv","pinyin":"jū","bushou":"几","bihua":"5"}]
         * page : 1
         * pagesize : 10
         * totalpage : 17
         * totalcount : 166
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
             * id : e204a7a91594ce5b
             * zi : 且
             * py : ju,qie
             * wubi : egd
             * pinyin : jū,qiě
             * bushou : 一
             * bihua : 5
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
