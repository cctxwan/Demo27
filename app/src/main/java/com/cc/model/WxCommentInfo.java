package com.cc.model;

/**
 * 微信评论
 */
public class WxCommentInfo {

    //评论id
    private Integer comment_id;

    //文章的链接
    private String comment_url;

    //账号id
    private String comment_account;

    //评论内容
    private String comment_string;

    //是否点赞
    private boolean comment_islike;

    //是否收藏
    private boolean comment_iscollection;

    @Override
    public String toString() {
        return "WxCommentInfo{" +
                "comment_id=" + comment_id +
                ", comment_url='" + comment_url + '\'' +
                ", comment_account='" + comment_account + '\'' +
                ", comment_string='" + comment_string + '\'' +
                ", comment_islike=" + comment_islike +
                ", comment_iscollection=" + comment_iscollection +
                '}';
    }

    public Integer getComment_id() {
        return comment_id;
    }

    public void setComment_id(Integer comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment_url() {
        return comment_url;
    }

    public void setComment_url(String comment_url) {
        this.comment_url = comment_url;
    }

    public String getComment_account() {
        return comment_account;
    }

    public void setComment_account(String comment_account) {
        this.comment_account = comment_account;
    }

    public String getComment_string() {
        return comment_string;
    }

    public void setComment_string(String comment_string) {
        this.comment_string = comment_string;
    }

    public boolean isComment_islike() {
        return comment_islike;
    }

    public void setComment_islike(boolean comment_islike) {
        this.comment_islike = comment_islike;
    }

    public boolean isComment_iscollection() {
        return comment_iscollection;
    }

    public void setComment_iscollection(boolean comment_iscollection) {
        this.comment_iscollection = comment_iscollection;
    }

    public WxCommentInfo(){}

    public WxCommentInfo(String comment_url, String comment_account, String comment_string, boolean comment_islike, boolean comment_iscollection) {
        this.comment_url = comment_url;
        this.comment_account = comment_account;
        this.comment_string = comment_string;
        this.comment_islike = comment_islike;
        this.comment_iscollection = comment_iscollection;
    }
}
