package com.cc.model;

public class News {

    public String uniquekey;
    public String title;
    public String date;
    public String category;
    public String author_name;
    public String dateilurl;
    public String thumbnail_pic_s;
    public String thumbnail_pic_s02;
    public String thumbnail_pic_s03;

    public News() {
    }

    public News(String uniquekey, String title, String date, String category, String author_name, String dateilurl, String thumbnail_pic_s, String thumbnail_pic_s02, String thumbnail_pic_s03) {
        this.uniquekey = uniquekey;
        this.title = title;
        this.date = date;
        this.category = category;
        this.author_name = author_name;
        this.dateilurl = dateilurl;
        this.thumbnail_pic_s = thumbnail_pic_s;
        this.thumbnail_pic_s02 = thumbnail_pic_s02;
        this.thumbnail_pic_s03 = thumbnail_pic_s03;
    }

    @Override
    public String toString() {
        return "News{" +
                "uniquekey='" + uniquekey + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", category='" + category + '\'' +
                ", author_name='" + author_name + '\'' +
                ", dateilurl='" + dateilurl + '\'' +
                ", thumbnail_pic_s='" + thumbnail_pic_s + '\'' +
                ", thumbnail_pic_s02='" + thumbnail_pic_s02 + '\'' +
                ", thumbnail_pic_s03='" + thumbnail_pic_s03 + '\'' +
                '}';
    }

    public String getUniquekey() {
        return uniquekey;
    }

    public void setUniquekey(String uniquekey) {
        this.uniquekey = uniquekey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getDateilurl() {
        return dateilurl;
    }

    public void setDateilurl(String dateilurl) {
        this.dateilurl = dateilurl;
    }

    public String getThumbnail_pic_s() {
        return thumbnail_pic_s;
    }

    public void setThumbnail_pic_s(String thumbnail_pic_s) {
        this.thumbnail_pic_s = thumbnail_pic_s;
    }

    public String getThumbnail_pic_s02() {
        return thumbnail_pic_s02;
    }

    public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
        this.thumbnail_pic_s02 = thumbnail_pic_s02;
    }

    public String getThumbnail_pic_s03() {
        return thumbnail_pic_s03;
    }

    public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
        this.thumbnail_pic_s03 = thumbnail_pic_s03;
    }
}
