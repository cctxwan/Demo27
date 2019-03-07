package com.cc.model;

import java.util.List;

public class setNbaVSInfo {
    /**
     * reason : 查询成功
     * result : {"title":"火箭VS湖人赛程赛果","more1":{"link":"","text":""},"more2":{"link":"","text":""},"list":[{"time":"10/21 10:30","m_time":"10-21 10:30","player1":"火箭","player2":"湖人","player1logo":"https://mat1.gtimg.com/sports/nba/logo/1602/10.png","player2logo":"https://mat1.gtimg.com/sports/nba/logo/1602/13.png","player1url":"","m_player1url":"","player2url":"","m_player2url":"","score":"124-115","link1text":"视频直播","link1url":"http://sports.qq.com/kbsweb/game.htm?mid=100000:6024","m_link1url":"","link2text":"数据统计","link2url":"http://sports.qq.com/kbsweb/game.htm?mid=100000:6024","m_link2url":"","status":"2"},{"time":"02/22 11:30","m_time":"02-22 11:30","player1":"火箭","player2":"湖人","player1logo":"https://mat1.gtimg.com/sports/nba/logo/1602/10.png","player2logo":"https://mat1.gtimg.com/sports/nba/logo/1602/13.png","player1url":"","m_player1url":"","player2url":"","m_player2url":"","score":"0-0","link1text":"视频直播","link1url":"http://sports.qq.com/kbsweb/game.htm?mid=100000:6861","m_link1url":"","link2text":"数据统计","link2url":"http://sports.qq.com/kbsweb/game.htm?mid=100000:6861","m_link2url":"","status":"0"}]}
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
         * title : 火箭VS湖人赛程赛果
         * more1 : {"link":"","text":""}
         * more2 : {"link":"","text":""}
         * list : [{"time":"10/21 10:30","m_time":"10-21 10:30","player1":"火箭","player2":"湖人","player1logo":"https://mat1.gtimg.com/sports/nba/logo/1602/10.png","player2logo":"https://mat1.gtimg.com/sports/nba/logo/1602/13.png","player1url":"","m_player1url":"","player2url":"","m_player2url":"","score":"124-115","link1text":"视频直播","link1url":"http://sports.qq.com/kbsweb/game.htm?mid=100000:6024","m_link1url":"","link2text":"数据统计","link2url":"http://sports.qq.com/kbsweb/game.htm?mid=100000:6024","m_link2url":"","status":"2"},{"time":"02/22 11:30","m_time":"02-22 11:30","player1":"火箭","player2":"湖人","player1logo":"https://mat1.gtimg.com/sports/nba/logo/1602/10.png","player2logo":"https://mat1.gtimg.com/sports/nba/logo/1602/13.png","player1url":"","m_player1url":"","player2url":"","m_player2url":"","score":"0-0","link1text":"视频直播","link1url":"http://sports.qq.com/kbsweb/game.htm?mid=100000:6861","m_link1url":"","link2text":"数据统计","link2url":"http://sports.qq.com/kbsweb/game.htm?mid=100000:6861","m_link2url":"","status":"0"}]
         */

        private String title;
        private More1Bean more1;
        private More2Bean more2;
        private List<ListBean> list;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public More1Bean getMore1() {
            return more1;
        }

        public void setMore1(More1Bean more1) {
            this.more1 = more1;
        }

        public More2Bean getMore2() {
            return more2;
        }

        public void setMore2(More2Bean more2) {
            this.more2 = more2;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class More1Bean {
            /**
             * link :
             * text :
             */

            private String link;
            private String text;

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }

        public static class More2Bean {
            /**
             * link :
             * text :
             */

            private String link;
            private String text;

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }

        public static class ListBean {
            /**
             * time : 10/21 10:30
             * m_time : 10-21 10:30
             * player1 : 火箭
             * player2 : 湖人
             * player1logo : https://mat1.gtimg.com/sports/nba/logo/1602/10.png
             * player2logo : https://mat1.gtimg.com/sports/nba/logo/1602/13.png
             * player1url :
             * m_player1url :
             * player2url :
             * m_player2url :
             * score : 124-115
             * link1text : 视频直播
             * link1url : http://sports.qq.com/kbsweb/game.htm?mid=100000:6024
             * m_link1url :
             * link2text : 数据统计
             * link2url : http://sports.qq.com/kbsweb/game.htm?mid=100000:6024
             * m_link2url :
             * status : 2
             */

            private String time;
            private String m_time;
            private String player1;
            private String player2;
            private String player1logo;
            private String player2logo;
            private String player1url;
            private String m_player1url;
            private String player2url;
            private String m_player2url;
            private String score;
            private String link1text;
            private String link1url;
            private String m_link1url;
            private String link2text;
            private String link2url;
            private String m_link2url;
            private String status;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getM_time() {
                return m_time;
            }

            public void setM_time(String m_time) {
                this.m_time = m_time;
            }

            public String getPlayer1() {
                return player1;
            }

            public void setPlayer1(String player1) {
                this.player1 = player1;
            }

            public String getPlayer2() {
                return player2;
            }

            public void setPlayer2(String player2) {
                this.player2 = player2;
            }

            public String getPlayer1logo() {
                return player1logo;
            }

            public void setPlayer1logo(String player1logo) {
                this.player1logo = player1logo;
            }

            public String getPlayer2logo() {
                return player2logo;
            }

            public void setPlayer2logo(String player2logo) {
                this.player2logo = player2logo;
            }

            public String getPlayer1url() {
                return player1url;
            }

            public void setPlayer1url(String player1url) {
                this.player1url = player1url;
            }

            public String getM_player1url() {
                return m_player1url;
            }

            public void setM_player1url(String m_player1url) {
                this.m_player1url = m_player1url;
            }

            public String getPlayer2url() {
                return player2url;
            }

            public void setPlayer2url(String player2url) {
                this.player2url = player2url;
            }

            public String getM_player2url() {
                return m_player2url;
            }

            public void setM_player2url(String m_player2url) {
                this.m_player2url = m_player2url;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getLink1text() {
                return link1text;
            }

            public void setLink1text(String link1text) {
                this.link1text = link1text;
            }

            public String getLink1url() {
                return link1url;
            }

            public void setLink1url(String link1url) {
                this.link1url = link1url;
            }

            public String getM_link1url() {
                return m_link1url;
            }

            public void setM_link1url(String m_link1url) {
                this.m_link1url = m_link1url;
            }

            public String getLink2text() {
                return link2text;
            }

            public void setLink2text(String link2text) {
                this.link2text = link2text;
            }

            public String getLink2url() {
                return link2url;
            }

            public void setLink2url(String link2url) {
                this.link2url = link2url;
            }

            public String getM_link2url() {
                return m_link2url;
            }

            public void setM_link2url(String m_link2url) {
                this.m_link2url = m_link2url;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
