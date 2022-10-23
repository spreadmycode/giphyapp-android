package com.giphy.giphyapp.model;

public class GiphyItemModel {
    private String title;
    private String gifUrl;

    public String getTitle() {
        return title;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public void setTitle(String value) {
        title = value;
    }

    public void setGifUrl(String value) {
        gifUrl = value;
    }

    public GiphyItemModel(String title, String gifUrl) {
        this.title = title;
        this.gifUrl = gifUrl;
    }
}
