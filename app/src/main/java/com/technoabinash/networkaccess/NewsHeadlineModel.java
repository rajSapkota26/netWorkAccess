package com.technoabinash.networkaccess;

public class NewsHeadlineModel {
    private String title,content,imageUrl,source;

    public NewsHeadlineModel() {
    }

    public NewsHeadlineModel(String title, String content, String imageUrl, String source) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
