package com.cc.model;

/**
 * 微信精选文章收藏保存
 */
public class WxCollectionInfo {

    //收藏id
    private Integer collection_id;

    //收藏的文章标题
    private String collection_title;

    //收藏的文章url
    private String collection_url;

    //收藏文章的账号
    private String collection_account;

    public WxCollectionInfo() {
        super();
    }

    public WxCollectionInfo(String collection_title, String collection_url, String collection_account) {
        this.collection_title = collection_title;
        this.collection_url = collection_url;
        this.collection_account = collection_account;
    }

    @Override
    public String toString() {
        return "WxCollectionInfo{" +
                "collection_id=" + collection_id +
                ", collection_title='" + collection_title + '\'' +
                ", collection_url='" + collection_url + '\'' +
                ", collection_account='" + collection_account + '\'' +
                '}';
    }

    public Integer getCollection_id() {
        return collection_id;
    }

    public void setCollection_id(Integer collection_id) {
        this.collection_id = collection_id;
    }

    public String getCollection_title() {
        return collection_title;
    }

    public void setCollection_title(String collection_title) {
        this.collection_title = collection_title;
    }

    public String getCollection_url() {
        return collection_url;
    }

    public void setCollection_url(String collection_url) {
        this.collection_url = collection_url;
    }

    public String getCollection_account() {
        return collection_account;
    }

    public void setCollection_account(String collection_account) {
        this.collection_account = collection_account;
    }

}
