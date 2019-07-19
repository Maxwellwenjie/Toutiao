package com.nowcoder.toutiao.model;

import org.springframework.context.annotation.Bean;

import java.util.Date;

public class News {

    private int id;
    private String title;
    private String link;
    private String image;
    private int likeCount;
    private int commentCount;
    private int userId;
    private Date createdDate;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getImage() {
        return image;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public int getUserId() {
        return userId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
